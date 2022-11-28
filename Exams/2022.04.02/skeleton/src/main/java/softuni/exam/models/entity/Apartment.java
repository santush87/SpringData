package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.enums.ApartmentType;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "apartments")
public class Apartment extends Base{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApartmentType apartmentType;

    @Column(nullable = false)
    private double area;

    @ManyToOne
    private Town town;
}
