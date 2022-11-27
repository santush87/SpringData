package exam.model.dtos;

import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@XmlAccessorType(XmlAccessType.FIELD)
public class TownImportDTO {

    @Size(min = 2)
    @XmlElement
    private String name;

    @Positive
    @XmlElement
    private int population;

    @Size(min = 10)
    @XmlElement(name = "travel-guide")
    private String travelGuide;

}
