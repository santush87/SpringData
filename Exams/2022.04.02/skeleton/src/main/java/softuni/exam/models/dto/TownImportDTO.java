package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class TownImportDTO {

    @Size(min = 2)
    @NotNull
    private String townName;

    @Positive
    @NotNull
    private int population;
}
