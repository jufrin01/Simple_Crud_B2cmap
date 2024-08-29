package idb2camp.b2campjufrin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountOfficerResponse {
    private String accountOfficerId;
    private String accountOfficerName;
    private String accountOfficerCode;
    private String officeCode;
    private String officeName;
    private String jobTitle;

}
