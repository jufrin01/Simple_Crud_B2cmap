package idb2camp.b2campjufrin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeptlistDto {
    private List<DepartmentDto> deptlistDto;
    private Integer currentPage;
    private Long totalItems;
    private Integer totalPages;
}
