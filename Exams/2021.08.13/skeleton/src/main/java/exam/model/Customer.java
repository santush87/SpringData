package exam.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends Base{

    @Size(min = 2)
    @Column(nullable = false)
    private String firstName;

    @Size(min = 2)
    @Column(nullable = false)
    private String lastName;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalTime registeredOn;

    @ManyToOne
    private Town town;
}
