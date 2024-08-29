package idb2camp.b2campjufrin.dto.request;

import idb2camp.b2campjufrin.constant.DepartmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentLocationDto {

    private DepartmentType departmentType;
    private String departmentName;
    private Integer locationId;
    private String city;
    private String postalCode;
    private String stateProvince;
    private String streetAddress;
}
