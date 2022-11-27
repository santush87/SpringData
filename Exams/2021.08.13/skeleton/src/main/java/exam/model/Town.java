package exam.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "towns")
public class Town extends Base{

    @Size(min = 2)
    @Column(nullable = false, unique = true)
    private String name;

    @Positive
    @Column(nullable = false)
    private int population;

    @Size(min = 10)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String travelGuide;

}
