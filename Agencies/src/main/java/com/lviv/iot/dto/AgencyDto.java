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
@Relation(itemRelation = "agency", collectionRelation = "agencies")
public class AgencyDto extends RepresentationModel<AgencyDto> {
    private final Integer id;
    private final String name;
    private final String owner;
    private final String hqAddress;
    private final String city_name;
    private final String region_name;
    private final String phone;
    private final String email;
}
