package com.lviv.iot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(itemRelation = "order", collectionRelation = "orders")
public class OrderDto extends RepresentationModel<OrderDto> {
    private final Integer id;
    private final Timestamp datetime;
    private final Time duration;
    private final String streetAddress;
    private final BigDecimal totalPrice;
    private final String eventName;
    private final String cityName;
    private final String regionName;
    private final String clientSurname;
    private final String clientEmail;
}
