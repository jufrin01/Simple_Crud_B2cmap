package idb2camp.b2campjufrin.dto.request;

import idb2camp.b2campjufrin.constant.SalaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SalaryDto {
    private Long salaryId;
    private String accountName;
    private SalaryType salaryType;
    //    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime paymentDate;
    private BigDecimal amount;
}
