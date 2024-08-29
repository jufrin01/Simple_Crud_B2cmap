package idb2camp.b2campjufrin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import idb2camp.b2campjufrin.constant.SalaryType;
import idb2camp.b2campjufrin.dto.request.SalaryDto;
import idb2camp.b2campjufrin.dto.response.BaseResponse;
import idb2camp.b2campjufrin.dto.response.CustomResponse;
import idb2camp.b2campjufrin.model.Salary;
import idb2camp.b2campjufrin.repository.Accountdatarepository;
import idb2camp.b2campjufrin.repository.SalaryRepository;
import idb2camp.b2campjufrin.utils.CustomPage;
import idb2camp.b2campjufrin.utils.RowMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class SalaryService {

    private final ModelMapper modelMapper;
    private final SalaryRepository salaryRepo;
    private Accountdatarepository accountdatarepo;
    private final CacheService cacheService;

    private static final String CACHE_KEY = "SALARY_REF";

    public Page<BaseResponse<SalaryDto>> findAllSalaries(String accountName, Pageable page) throws JsonProcessingException {
        String cacheKey = CACHE_KEY;
        String cachedData = cacheService.findCacheByKey(cacheKey, String.class);

        if (cachedData != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<CustomPage<BaseResponse<SalaryDto>>> typeReference = new TypeReference<>() {
            };
            CustomPage<BaseResponse<SalaryDto>> salaryPage = objectMapper.readValue(cachedData, typeReference);

            return salaryPage;
        }

        Page<BaseResponse<SalaryDto>> salaryPage = salaryRepo.findAllSalaries(RowMapper.addWildcard(accountName), page)
                .map(objectMap -> {
                    SalaryDto salaryDto = SalaryDto.builder()
                            .salaryId(RowMapper.toLong(objectMap.get("salary_id")))
                            .accountName(RowMapper.toStr(objectMap.get("account_name")))
                            .salaryType(RowMapper.toEnum(SalaryType.class, objectMap.get("salary_type")))
                            .amount(RowMapper.toBigDecimal(objectMap.get("amount")))
                            .build();

                    return BaseResponse.<SalaryDto>builder()
                            .data(salaryDto)
                            .message("Success di tampilkan")
                            .build();
                });

        // jangan di cache
//        cacheService.createCache(cacheKey, salaryPage, 360);

        return salaryPage;
    }

    public CustomResponse<Page<SalaryDto>> getAllSalariesPage(int page, int size) {
        String cacheKey = ("GETT_SALARY_PAGE");
        CustomResponse<Page<SalaryDto>> cachedData = cacheService.findCacheByKey(cacheKey, CustomResponse.class);

        if (cachedData != null) {
            return cachedData;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Salary> salaryPage = salaryRepo.findAll(pageable);

        CustomResponse<Page<SalaryDto>> response = CustomResponse.<Page<SalaryDto>>builder()
                .message("Data not found")
                .build();

        if (!salaryPage.isEmpty()) {
            Page<SalaryDto> salaryDtoPage = salaryPage.map(salary -> modelMapper.map(salary, SalaryDto.class));
            response = CustomResponse.<Page<SalaryDto>>builder()
                    .message("Data Salary Berhasil Ditampilkan")
                    .data(salaryDtoPage)
                    .build();

            cacheService.createCache(cacheKey, response, 360);
        }

        return response;
    }

    public CustomResponse<SalaryDto> getSalaryById(Integer salaryId) {
        String cacheKey = "GET_SALARYBY_ID" + salaryId;
        CustomResponse<SalaryDto> cachedData = cacheService.findCacheByKey(cacheKey, CustomResponse.class);

        if (cachedData != null) {
            return cachedData;
        }

        Salary salary = salaryRepo.findById(salaryId).orElse(null);
        CustomResponse<SalaryDto> response = CustomResponse.<SalaryDto>builder()
                .message("Salary data not found for Salary ID: " + salaryId)
                .build();

        if (salary != null) {
            SalaryDto salaryDto = modelMapper.map(salary, SalaryDto.class);
            response = CustomResponse.<SalaryDto>builder()
                    .message("Salary data successfully retrieved")
                    .data(salaryDto)
                    .build();

            cacheService.createCache(cacheKey, response, 360);
        }

        return response;
    }
}
