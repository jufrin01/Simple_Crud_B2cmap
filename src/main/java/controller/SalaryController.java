package idb2camp.b2campjufrin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import idb2camp.b2campjufrin.dto.request.SalaryDto;
import idb2camp.b2campjufrin.dto.response.BaseResponse;
import idb2camp.b2campjufrin.dto.response.CustomResponse;
import idb2camp.b2campjufrin.service.SalaryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/salaries")
public class SalaryController {

    private final SalaryService salaryService;

    @GetMapping
    public ResponseEntity<CustomResponse<Page<SalaryDto>>> getAllSalaries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        CustomResponse<Page<SalaryDto>> response = salaryService.getAllSalariesPage(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{salaryId}")
    public ResponseEntity<CustomResponse<SalaryDto>> getSalaryById(@PathVariable Integer salaryId) {
        CustomResponse<SalaryDto> response = salaryService.getSalaryById(salaryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public Page<BaseResponse<SalaryDto>> getAllSalaries(
            @RequestParam(name = "accountName", required = false) String accountName,
            Pageable page) throws JsonProcessingException {
        return salaryService.findAllSalaries(accountName, page);

    }
}