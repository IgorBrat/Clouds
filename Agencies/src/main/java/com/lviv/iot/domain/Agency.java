package com.lviv.iot.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Agency {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "owner")
    private String owner;
    @Basic
    @Column(name = "hq_address")
    private String hqAddress;
    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    private City city;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @ManyToMany
    @JoinTable(name = "agency_animator", catalog = "", schema = "boklach", joinColumns = @JoinColumn(name = "agency_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "animator_id", referencedColumnName = "id", nullable = false))
    private Set<Animator> animators;

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getHqAddress() {
        return hqAddress;
    }

    public void setHqAddress(String hqAddress) {
        this.hqAddress = hqAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agency agency = (Agency) o;
        return Objects.equals(id, agency.id) && Objects.equals(name, agency.name) && Objects.equals(owner, agency.owner) && Objects.equals(hqAddress, agency.hqAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, hqAddress);
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Animator> getAnimators() {
        return animators;
    }

    public void setAnimators(Set<Animator> animators) {
        this.animators = animators;
    }
}
