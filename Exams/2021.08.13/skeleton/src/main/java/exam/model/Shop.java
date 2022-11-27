package exam.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "shops")
public class Shop extends Base{

    @Size(min = 4)
    @Column(nullable = false, unique = true)
    private String name;

    @Min(value = 20000)
    @Column(nullable = false)
    private int income;

    @Size(min = 4)
    @Column(nullable = false)
    private String address;

    @Min(value = 1)
    @Max(value = 50)
    @Column(nullable = false)
    private int employeeCount;

    @Min(value = 150)
    @Column(nullable = false)
    private int shopArea;

    @ManyToOne
    private Town town;
}
