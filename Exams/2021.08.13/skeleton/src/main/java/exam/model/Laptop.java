package exam.model;

import exam.model.enums.WarrantyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "laptops")
public class Laptop extends Base {

    @Size(min = 8)
    @Column(nullable = false, unique = true)
    private String macAddress;

    @Positive
    @Column(nullable = false)
    private float cpuSpeed;

    @Min(value = 8)
    @Max(value = 128)
    @Column(nullable = false)
    private int ram;

    @Min(value = 128)
    @Max(value = 1024)
    @Column(nullable = false)
    private int storage;

    @Size(min = 10)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Positive
    @Column(nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WarrantyType warrantyType;

    @ManyToOne
    private Shop shop;
}
