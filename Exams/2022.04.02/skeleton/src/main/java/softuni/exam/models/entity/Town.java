package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "towns")
public class Town extends Base{

    @Column(unique = true, nullable = false)
    private String townName;

    @Positive
    @Column(nullable = false)
    private int population;
}
