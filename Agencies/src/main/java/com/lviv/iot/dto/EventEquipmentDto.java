package com.lviv.iot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(itemRelation = "eventEquipment", collectionRelation = "eventEquipments")
public class EventEquipmentDto extends RepresentationModel<EventEquipmentDto> {
    private final String equipmentName;
    private final Integer quantity;
}
