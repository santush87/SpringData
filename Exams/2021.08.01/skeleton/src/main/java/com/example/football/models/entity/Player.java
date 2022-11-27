package com.example.football.models.entity;

import com.example.football.models.entity.enums.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "players")
public class Player extends Base{

    @Min(value = 2)
    @Column(unique = true, nullable = false)
    private String firstName;

    @Min(value = 2)
    @Column(unique = true, nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalTime birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Position position;

    @ManyToOne(optional = false)
    private Town town;

    @ManyToOne
    private Team team;

    @OneToOne
    private Stat stat;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return email.equals(player.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
