package com.lviv.iot.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "event_equipment", schema = "boklach", catalog = "")
@IdClass(EventEquipmentPK.class)
public class EventEquipment {
    @Id
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;
    @Id
    @ManyToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    private Equipment equipment;
    @Basic
    @Column(name = "quantity")
    private Integer quantity;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventEquipment that = (EventEquipment) o;
        return Objects.equals(event, that.event) && Objects.equals(equipment, that.equipment) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, equipment, quantity);
    }
}
