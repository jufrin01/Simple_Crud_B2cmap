package idb2camp.b2campjufrin.controller;

import idb2camp.b2campjufrin.dto.HeaderRequest;
import idb2camp.b2campjufrin.dto.request.AccountDto;
import idb2camp.b2campjufrin.dto.response.AccountResponse;
import idb2camp.b2campjufrin.dto.response.BaseResponse;
import idb2camp.b2campjufrin.dto.response.CustomResponse;
import idb2camp.b2campjufrin.service.AccountServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("api/v1/account")
public class AccountController  extends BaseCRUDController{
    private final AccountServiceImpl accountService;

    @GetMapping
    public CustomResponse<Page<AccountDto>> getAllAccounts(
            @ModelAttribute HeaderRequest headers,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return accountService.getAllAccountpage(headers, page, size);
    }

    @PostMapping
    public BaseResponse<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }

    @PostMapping("/upsert")
    public Integer upsert(@ModelAttribute HeaderRequest headers,
                          @RequestPart @Valid AccountDto jsonFile,
                          @RequestPart(value = "imageFile") MultipartFile[] files) {
        return accountService.upsert(headers, jsonFile, files);
    }

    @GetMapping("/accountfindall")
    public BaseResponse<List<AccountResponse>> findAccountOfficer(@ModelAttribute(name = "header") HeaderRequest header) {
        return buildSuccessResponse(accountService.findReference(header));
    }
                            }
//    { craattedd account
//    "firstName": "Suaep",
//    "lastName": "khanifa",
//    "email": "suapk11@example.com",
//    "gender": "MALE",
//    "religion": "CHRISTIAN",
//    "departmentId": 1
//
//    }


