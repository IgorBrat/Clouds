provider "google" {
  credentials = file('creds.json')
  project     = var.project_id
  region      = var.region
  zone        = var.zone
}

resource "google_compute_network" "vpc-net" {
  name = "db-net"
}

resource "google_sql_database_instance" "db_instance" {
  database_version = "MYSQL_8_0"
  name             = "db_terraform"
  project          = var.project_id
  region           = var.region
  root_password =

  settings {
    tier            = "db-f1-micro"
    edition         = "ENTERPRISE"
    disk_autoresize = false
    disk_size       = 10
    disk_type       = "PD_HDD"
    ip_configuration {
      private_network = google_compute_network.vpc-net.id
    }
  }
}