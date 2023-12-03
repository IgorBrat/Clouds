# Save state on GCP
terraform {
  backend "gcs" {
    bucket = "terraform-backend-buck"
    prefix = "terraform/state"
  }
}

# Provider
provider "google" {
  project = var.project_id
  region  = var.region
  zone    = var.zone
}

# Services

# Compute Engine service
resource "google_project_service" "compute_engine" {
  project                    = var.project_id
  service                    = "compute.googleapis.com"
  disable_dependent_services = true
}

resource "google_project_service" "iam" {
  project                    = var.project_id
  service                    = "iam.googleapis.com"
  disable_dependent_services = true
}

resource "google_project_service" "cloud_build" {
  project                    = var.project_id
  service                    = "cloudbuild.googleapis.com"
  disable_dependent_services = true
}

resource "google_project_service" "artifact_registry" {
  project                    = var.project_id
  service                    = "artifactregistry.googleapis.com"
  disable_dependent_services = true
}

resource "google_project_service" "cloud_run" {
  project                    = var.project_id
  service                    = "run.googleapis.com"
  disable_dependent_services = true
}

# Service Accounts

resource "google_service_account" "cloud_build_service_account" {
  display_name = "Cloud Build SA"
  account_id   = "cloud-build--service-account"
  depends_on   = [google_project_service.iam]
}


resource "google_project_iam_member" "cloud_build_editor" {
  project = var.project_id
  role    = "roles/editor"
  member  = google_service_account.cloud_build_service_account.member
}

resource "google_artifact_registry_repository_iam_member" "artifact_registry_writer" {
  repository = "cloud-run-source-deploy"
  role       = "roles/artifactregistry.createOnPushWriter"
  member     = google_service_account.cloud_build_service_account.member
}

# VPC

# Network
resource "google_compute_network" "terraform_vpc" {
  name = "db-net"
}

# Subnetwork
resource "google_compute_subnetwork" "vpc_subnet" {
  name          = "vpc-subnet"
  network       = google_compute_network.terraform_vpc.id
  ip_cidr_range = "10.0.0.0/24"
}

# Private IP address
resource "google_compute_global_address" "db_private_ip" {
  name          = "db-private-ip"
  purpose       = "VPC_PEERING"
  address_type  = "INTERNAL"
  prefix_length = 16
  network       = google_compute_network.terraform_vpc.id
}

# Private connection for DB
resource "google_service_networking_connection" "db_private_connection" {
  network                 = google_compute_network.terraform_vpc.id
  service                 = "servicenetworking.googleapis.com"
  reserved_peering_ranges = [google_compute_global_address.db_private_ip.name]
}

# DB

# Instance
resource "google_sql_database_instance" "db_instance" {
  database_version = "MYSQL_8_0"
  name             = var.db_instance
  project          = var.project_id
  region           = var.region
  root_password    = var.db_root_password

  deletion_protection = false
  depends_on          = [google_service_networking_connection.db_private_connection]

  settings {
    tier            = "db-f1-micro"
    edition         = "ENTERPRISE"
    disk_autoresize = false
    disk_size       = 10
    disk_type       = "PD_HDD"

    ip_configuration {
      ipv4_enabled    = false
      private_network = google_compute_network.terraform_vpc.id
    }
  }
}

# Database
resource "google_sql_database" "db_scheme" {
  name     = var.scheme_name
  instance = google_sql_database_instance.db_instance.id
}

# DB user
resource "google_sql_user" "terraform_user" {
  instance = google_sql_database_instance.db_instance.id
  name     = var.db_user
  password = var.db_user_password
  host     = "%"
}

# CI/CD

# App trigger
resource "google_cloudbuild_trigger" "app-trigger" {
  name            = "agencies-terraform-trigger"
  service_account = google_service_account.cloud_build_service_account.id
  depends_on      = [
    google_project_iam_member.cloud_build_editor,
    google_project_service.cloud_build
  ]
  github {
    name  = "Clouds"
    owner = "IgorBrat"
    push {
      branch = "lab2"
    }
  }

  build {
    step {
      name = "gcr.io/cloud-builders/docker"
      args = [
        "build",
        "--no-cache",
        "-t",
        "$_AR_HOSTNAME/$PROJECT_ID/cloud-run-source-deploy/$REPO_NAME/$_SERVICE_NAME:$COMMIT_SHA",
        "Agencies",
        "-f",
        "Agencies/Dockerfile"
      ]
      id = "Build"
    }
    step {
      name = "gcr.io/cloud-builders/docker"
      args = [
        "push",
        "$_AR_HOSTNAME/$PROJECT_ID/cloud-run-source-deploy/$REPO_NAME/$_SERVICE_NAME:$COMMIT_SHA"
      ]
      id = "Push"
    }
    step {
      name = "gcr.io/google.com/cloudsdktool/cloud-sdk:slim"
      args = [
        "run",
        "services",
        "update",
        "$_SERVICE_NAME",
        "--platform=managed",
        "--image=$_AR_HOSTNAME/$PROJECT_ID/cloud-run-source-deploy/$REPO_NAME/$_SERVICE_NAME:$COMMIT_SHA",
        "--labels=managed-by=gcp-cloud-build-deploy-cloud-run,commit-sha=$COMMIT_SHA,gcb-build-id=$BUILD_ID,gcb-trigger-id=$_TRIGGER_ID",
        "--region=$_DEPLOY_REGION",
        "--quiet"
      ]
      id         = "Deploy"
      entrypoint = "gcloud"
    }

    images = ["$_AR_HOSTNAME/$PROJECT_ID/cloud-run-source-deploy/$REPO_NAME/$_SERVICE_NAME:$COMMIT_SHA"]

    options {
      substitution_option = "ALLOW_LOOSE"
      logging             = "CLOUD_LOGGING_ONLY"
    }

    substitutions = {
      _AR_HOSTNAME = "${var.region}-docker.pkg.dev"
      _PLATFORM : "managed"
      _SERVICE_NAME : var.app_service
      _DEPLOY_REGION : var.region
      REPO_NAME: "clouds"
    }

    tags = [
      "gcp-cloud-build-deploy-cloud-run",
      "gcp-cloud-build-deploy-cloud-run-managed",
      "clouds"
    ]
  }
}

# Load trigger
resource "google_cloudbuild_trigger" "locust-trigger" {
  name            = "locust-terraform-trigger"
  service_account = google_service_account.cloud_build_service_account.id
  depends_on      = [
    google_project_iam_member.cloud_build_editor,
    google_project_service.cloud_build
  ]
  github {
    name  = "CloudsLocust"
    owner = "IgorBrat"
    push {
      branch = "master"
    }
  }

  build {
    step {
      name = "gcr.io/cloud-builders/docker"
      args = [
        "build",
        "--no-cache",
        "-t",
        "$_AR_HOSTNAME/$PROJECT_ID/cloud-run-source-deploy/$REPO_NAME/$_SERVICE_NAME:$COMMIT_SHA",
        ".",
        "-f",
        "Dockerfile"
      ]
      id = "Build"
    }
    step {
      name = "gcr.io/cloud-builders/docker"
      args = [
        "push",
        "$_AR_HOSTNAME/$PROJECT_ID/cloud-run-source-deploy/$REPO_NAME/$_SERVICE_NAME:$COMMIT_SHA"
      ]
      id = "Push"
    }
    step {
      name = "gcr.io/google.com/cloudsdktool/cloud-sdk:slim"
      args = [
        "run",
        "services",
        "update",
        "$_SERVICE_NAME",
        "--platform=managed",
        "--image=$_AR_HOSTNAME/$PROJECT_ID/cloud-run-source-deploy/$REPO_NAME/$_SERVICE_NAME:$COMMIT_SHA",
        "--labels=managed-by=gcp-cloud-build-deploy-cloud-run,commit-sha=$COMMIT_SHA,gcb-build-id=$BUILD_ID,gcb-trigger-id=$_TRIGGER_ID",
        "--region=$_DEPLOY_REGION",
        "--quiet"
      ]
      id         = "Deploy"
      entrypoint = "gcloud"
    }

    images = ["$_AR_HOSTNAME/$PROJECT_ID/cloud-run-source-deploy/$REPO_NAME/$_SERVICE_NAME:$COMMIT_SHA"]

    options {
      substitution_option = "ALLOW_LOOSE"
      logging             = "CLOUD_LOGGING_ONLY"
    }

    substitutions = {
      _AR_HOSTNAME = "${var.region}-docker.pkg.dev"
      _PLATFORM : "managed"
      _SERVICE_NAME : var.load_service
      _DEPLOY_REGION : var.region
    }

    tags = [
      "gcp-cloud-build-deploy-cloud-run",
      "gcp-cloud-build-deploy-cloud-run-managed",
      "locust"
    ]
  }
}

# App Cloud Run service
resource "google_cloud_run_v2_service" "agencies_tf" {
  location     = var.region
  name         = var.app_service
  ingress      = "INGRESS_TRAFFIC_ALL"
  launch_stage = "BETA"

  template {
    scaling {
      min_instance_count = 1
      max_instance_count = 10
    }
    volumes {
      name = "cloudsql"
      cloud_sql_instance {
        instances = [google_sql_database_instance.db_instance.connection_name]
      }
    }
    # First time there is no image of app because trigger never triggered
    containers {
      image = "us-docker.pkg.dev/cloudrun/container/hello"

      env {
        name  = "DB_NAME"
        value = var.scheme_name
      }
      env {
        name  = "USER_NAME"
        value = var.db_user
      }
      env {
        name  = "USER_PASSWORD"
        value = var.db_user_password
      }
      env {
        name  = "DB_IP"
        value = google_sql_database_instance.db_instance.private_ip_address
      }
      env {
        name  = "DB_CONNECTION_NAME"
        value = "${var.project_id}:${var.region}:${var.db_instance}"
      }
      env {
        name  = "SERVER_ADMIN"
        value = var.server_admin
      }
      env {
        name  = "SERVER_ADMIN_PASSWORD"
        value = var.server_admin_password
      }
    }

    vpc_access {
      egress = "PRIVATE_RANGES_ONLY"

      network_interfaces {
        network    = google_compute_network.terraform_vpc.id
        subnetwork = google_compute_subnetwork.vpc_subnet.id
      }
    }
  }
}

resource "google_cloud_run_v2_service_iam_member" "app_invoker" {
  name   = google_cloud_run_v2_service.agencies_tf.name
  role   = "roles/run.invoker"
  member = "allUsers"
}

# Load Cloud Run service
resource "google_cloud_run_v2_service" "load_tf" {
  location = var.region
  name     = var.load_service
  ingress  = "INGRESS_TRAFFIC_ALL"

  template {
    scaling {
      min_instance_count = 0
      max_instance_count = 1
    }
    # First time there is no image of app because trigger never triggered
    containers {
      image = "us-docker.pkg.dev/cloudrun/container/hello"
      ports {
        container_port = 8089
      }
    }
  }
}

resource "google_cloud_run_v2_service_iam_member" "load_invoker" {
  name   = google_cloud_run_v2_service.load_tf.name
  role   = "roles/run.invoker"
  member = "allUsers"
}