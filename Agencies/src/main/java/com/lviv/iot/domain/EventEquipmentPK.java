package com.lviv.iot.domain;

import java.io.Serializable;
import java.util.Objects;

public class EventEquipmentPK implements Serializable {
    private Integer event;
    private Integer equipment;

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer eventId) {
        this.event = eventId;
    }

    public Integer getEquipment() {
        return equipment;
    }

    public void setEquipment(Integer equipmentId) {
        this.equipment = equipmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventEquipmentPK that = (EventEquipmentPK) o;
        return Objects.equals(event, that.event) && Objects.equals(equipment, that.equipment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, equipment);
    }
}
