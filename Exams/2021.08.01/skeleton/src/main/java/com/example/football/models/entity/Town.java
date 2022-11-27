package com.example.football.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "towns")
public class Town extends Base{

    @Size(min = 2)
    @Column(unique = true, nullable = false)
    private String name;

    @Positive
    @Column(nullable = false)
    private Integer population;

    @Size(min = 10)
    @Column(columnDefinition = "TEXT", nullable = false)
    private String travelGuide;

    @ManyToOne
    private Player player;
}
