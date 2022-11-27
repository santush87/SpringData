package com.example.football.models.dto;

import lombok.Getter;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@XmlAccessorType(XmlAccessType.FIELD)
public class StatImportDTO {

    @XmlElement
    @Positive
    private float passing;

    @Positive
    @XmlElement
    private float shooting;

    @Positive
    @XmlElement
    private float endurance;
}
