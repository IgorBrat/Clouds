package com.lviv.iot.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
public class Animator {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "surname")
    private String surname;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "salary_per_hour")
    private BigDecimal salaryPerHour;
    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    private City city;
    @ManyToMany(mappedBy = "animators")
    private Set<Agency> agencies;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @ManyToMany(mappedBy = "animators")
    private Set<Order> orders;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(BigDecimal salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animator animator = (Animator) o;
        return Objects.equals(id, animator.id) && Objects.equals(surname, animator.surname) && Objects.equals(name, animator.name) && Objects.equals(salaryPerHour, animator.salaryPerHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, name, salaryPerHour);
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Agency> getAgencies() {
        return agencies;
    }

    public void setAgencies(Set<Agency> agencies) {
        this.agencies = agencies;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
