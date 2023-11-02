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
@Relation(itemRelation = "animator", collectionRelation = "animators")
public class AnimatorDto extends RepresentationModel<AnimatorDto> {
    private final Integer id;
    private final String surname;
    private final String name;
    private final BigDecimal salaryPerHour;
    private final String cityName;
    private final String regionName;
    private final String phone;
    private final String email;
}
