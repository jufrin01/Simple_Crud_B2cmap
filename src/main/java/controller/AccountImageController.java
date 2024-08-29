package idb2camp.b2campjufrin.controller;

import idb2camp.b2campjufrin.dto.HeaderRequest;
import idb2camp.b2campjufrin.dto.request.AccountImageDto;
import idb2camp.b2campjufrin.dto.response.CustomResponse;
import idb2camp.b2campjufrin.service.AccountImageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/account-images")
public class AccountImageController {

    private final AccountImageService accountImageService;

    @GetMapping // Perbaiki jadi base response
    public List<CustomResponse<AccountImageDto>> findByAccountIdNativeQuery(@RequestParam Integer accountId) {
        return accountImageService.findByAccountIdNativeQuery(accountId);
    }

    @PostMapping("/upload/{accountId}/account")
    public CustomResponse<Integer> uploadFiles(@ModelAttribute HeaderRequest headers,
                                               @PathVariable Integer accountId,
                                               @RequestPart("file") MultipartFile[] files) {
        return accountImageService.uploadFile(headers, accountId, files);
    }

}


