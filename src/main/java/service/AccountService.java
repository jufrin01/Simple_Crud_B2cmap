package idb2camp.b2campjufrin.service;

import idb2camp.b2campjufrin.dto.HeaderRequest;
import idb2camp.b2campjufrin.dto.request.AccountDto;
import idb2camp.b2campjufrin.dto.response.AccountResponse;
import idb2camp.b2campjufrin.dto.response.CustomResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {

    CustomResponse<Page<AccountDto>> getAllAccountpage(HeaderRequest headers, int page, int size);

    List<AccountResponse> findReference(HeaderRequest headers);
}
