package idb2camp.b2campjufrin.dto.request;

import idb2camp.b2campjufrin.constant.DepartmentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Data
public class DepartmentDto {

    @NotNull
    @NotEmpty
    private DepartmentType departmentType;

    @NotBlank
    private String departmentName;

    @NotNull
    @NotEmpty
    private List<@Valid AccountDto> account;

    @NotNull
    @NotEmpty
    private LocationDto location;

    @Min(1)
    private Integer locationId;
}
