package com.example.football.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "teams")
public class Team extends Base {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String stadiumName;

    @Column(nullable = false)
    private Integer fanBase;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String history;

    @ManyToOne(optional = false)
    private Town town;

    @OneToMany(targetEntity = Player.class, mappedBy = "team")
    private Set<Player> players;
}
