package exam.model.dtos;

import exam.model.Shop;
import exam.model.enums.WarrantyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class LaptopImportDTO {

    @Size(min = 8)
    @NotNull
    @Column(unique = true)
    private String macAddress;

    @Positive
    @NotNull
    private float cpuSpeed;

    @Min(value = 8)
    @Max(value = 128)
    @NotNull
    private int ram;

    @Min(value = 128)
    @Max(value = 1024)
    @NotNull
    private int storage;

    @Size(min = 10)
    @NotNull
    private String description;

    @Positive
    @NotNull
    private double price;

    @NotNull
    private WarrantyType warrantyType;

    private Shop shop;
}
