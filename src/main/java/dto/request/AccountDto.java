package idb2camp.b2campjufrin.dto.request;

import idb2camp.b2campjufrin.constant.Gender;
import idb2camp.b2campjufrin.constant.Religion;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    @NotBlank
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private Religion religion;
    private Integer departmentId;
    private String departmentName;
    private String DepartmentType;
    private int primaryImageIndex;
    private List<Integer> salaryIds;
    private Integer accountId;
}
