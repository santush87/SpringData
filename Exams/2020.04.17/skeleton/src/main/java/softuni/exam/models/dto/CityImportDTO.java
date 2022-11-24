package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
@Getter
@Setter
@NoArgsConstructor
public class CityImportDTO {

    @Expose
    @Size(min = 2, max = 60)
    private String cityName;

    @Expose
    @Size(min = 2)
    private String description;

    @Expose
    @Min(value = 500)
    private Long population;

    @Expose
    private Long country;

}
