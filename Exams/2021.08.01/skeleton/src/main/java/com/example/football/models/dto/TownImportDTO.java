package com.example.football.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class TownImportDTO {

    @Size(min = 2)
    @Column(unique = true, nullable = false)
    private String name;

    @Positive
    @Column(nullable = false)
    private Integer population;

    @Size(min = 10)
    @Column(columnDefinition = "TEXT", nullable = false)
    private String travelGuide;
}
