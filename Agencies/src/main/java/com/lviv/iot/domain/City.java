package com.lviv.iot.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class City {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "region_name")
    private String regionName;
    @OneToMany(mappedBy = "city")
    private List<Agency> agencies;
    @OneToMany(mappedBy = "city")
    private List<Animator> animators;
    @OneToMany(mappedBy = "city")
    private List<Client> clients;
    @OneToMany(mappedBy = "city")
    private List<Order> orders;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(name, city.name) && Objects.equals(regionName, city.regionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, regionName);
    }

    public List<Agency> getAgencies() {
        return agencies;
    }

    public void setAgencies(List<Agency> agencies) {
        this.agencies = agencies;
    }

    public List<Animator> getAnimators() {
        return animators;
    }

    public void setAnimators(List<Animator> animators) {
        this.animators = animators;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}