package idb2camp.b2campjufrin.service;

import idb2camp.b2campjufrin.constant.GlobalMessage;
import idb2camp.b2campjufrin.dto.HeaderRequest;
import idb2camp.b2campjufrin.dto.response.AccountOfficerResponse;
import idb2camp.b2campjufrin.expection.BusinessException;
import idb2camp.b2campjufrin.repository.MAccountOfficerRepository;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public class MAccountOfficerServiceImpl  implements MAccountOfficerService {

    private MAccountOfficerRepository mAccountOfficerRepository;

    public Mono<List<AccountOfficerResponse>> findReference(HeaderRequest header) {
        return Mono.create(monoSink -> {
            List<AccountOfficerResponse> responseList = mAccountOfficerRepository.findAll()
                .stream()
                .map(mAccountOfficer -> AccountOfficerResponse.builder()
                    .accountOfficerId(mAccountOfficer.getAccountOfficerId())
                    .accountOfficerCode(mAccountOfficer.getAccountOfficerCode())
                    .accountOfficerName(mAccountOfficer.getAccountOfficerName())
                    .officeCode(mAccountOfficer.getMOffice().getOfficeCode())
                    .officeName(mAccountOfficer.getMOffice().getOfficeName())
                    .jobTitle(mAccountOfficer.getJobTitle())
                    .build()).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(responseList)) {
                monoSink.error(new BusinessException(GlobalMessage.DATA_NOT_FOUND));
            } else {
                monoSink.success(responseList);
            }
        });
    }
}