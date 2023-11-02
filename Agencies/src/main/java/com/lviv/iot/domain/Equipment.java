package com.lviv.iot.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Equipment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "shop_id")
    private Integer shopId;
    @ManyToMany
    @JoinTable(name = "event_equipment", catalog = "", schema = "boklach", joinColumns = @JoinColumn(name = "equipment_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false))
    private Set<Event> events;
    @OneToMany(mappedBy = "equipment")
    private List<EventEquipment> eventsRelation;

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

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shop_id) {
        this.shopId = shop_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return Objects.equals(id, equipment.id) && Objects.equals(name, equipment.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> event) {
        this.events = event;
    }

    public void setEventsRelation(List<EventEquipment> eventsRelation) {
        this.eventsRelation = eventsRelation;
    }

    public List<EventEquipment> getEventsRelation() {
        return eventsRelation;
    }
}
