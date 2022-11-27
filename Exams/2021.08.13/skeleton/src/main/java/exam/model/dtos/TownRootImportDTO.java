package exam.model.dtos;

import exam.model.Town;
import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@XmlRootElement(name = "towns")
@XmlAccessorType(XmlAccessType.FIELD)
public class TownRootImportDTO {

    @XmlElement(name = "town")
    private List<TownImportDTO> towns;
}
