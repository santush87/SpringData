package softuni.exam.models.dto;

import lombok.Getter;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportDTO {

    @Positive
    @NotNull
    @XmlElement
    private double price;

    @NotNull
    @XmlElement
    private AgentName agent;

    @NotNull
    @XmlElement
    private Apartment apartment;

    @NotNull
    @XmlElement
    private String publishedOn;
}
