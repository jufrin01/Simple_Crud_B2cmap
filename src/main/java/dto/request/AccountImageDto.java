package idb2camp.b2campjufrin.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountImageDto {
    private Integer accountImageId;
    private String filePath;
    private Boolean isPrimary;
    private Integer accountId;
    private String accountName;
}
