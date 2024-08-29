package idb2camp.b2campjufrin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountResponse {
    private Integer accountId;
    private String firsName;
    private String lastName;
    private String email;
    private Enum gender;
    private Enum religion;
}
