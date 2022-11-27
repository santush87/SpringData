package exam.model.dtos;

import exam.model.Town;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopImportDTO {

    @Size(min = 4)
    @XmlElement
    private String address;

    @Min(value = 1)
    @Max(value = 50)
    @XmlElement(name = "employee-count")
    private int employeeCount;

    @Min(value = 20000)
    @XmlElement
    private int income;

    @Size(min = 4)
    @XmlElement
    private String name;

    @Min(value = 150)
    @XmlElement(name = "shop-area")
    private int shopArea;

    @XmlElement
    private Town town;
}
