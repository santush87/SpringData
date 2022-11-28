package softuni.exam.models.dto;

import lombok.Getter;
import softuni.exam.models.enums.ApartmentType;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentImportDTO {

    @XmlElement
    private ApartmentType apartmentType;

    @Min(40)
    @XmlElement
    private double area;

    @Size(min = 2)
    @XmlElement
    private String town;
}
