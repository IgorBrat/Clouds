package com.lviv.iot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(itemRelation = "clientCard", collectionRelation = "clientCards")
public class ClientCardDto extends RepresentationModel<ClientCardDto> {
    private final Integer id;
    private final String name;
    private final BigDecimal discountPercentage;
}
