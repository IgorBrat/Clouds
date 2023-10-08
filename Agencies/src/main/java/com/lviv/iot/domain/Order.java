package com.lviv.iot.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "`order`")
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "datetime")
    private Timestamp datetime;
    @Basic
    @Column(name = "duration")
    private Time duration;
    @Basic
    @Column(name = "street_address")
    private String streetAddress;
    @Basic
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    private Event event;
    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    private City city;
    @ManyToMany
    @JoinTable(name = "order_agency_animator", catalog = "", schema = "boklach",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "animator_id", referencedColumnName = "id", nullable = false))
    private Set<Animator> animators;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(datetime, order.datetime) && Objects.equals(duration, order.duration) && Objects.equals(streetAddress, order.streetAddress) && Objects.equals(totalPrice, order.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datetime, duration, streetAddress, totalPrice);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client clientByClientId) {
        this.client = clientByClientId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event eventByEventId) {
        this.event = eventByEventId;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Animator> getAnimators() {
        return animators;
    }

    public void setAnimators(Set<Animator> animators) {
        this.animators = animators;
    }
}
