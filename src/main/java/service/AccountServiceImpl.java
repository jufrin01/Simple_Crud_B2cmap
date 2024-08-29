package idb2camp.b2campjufrin.service;

import idb2camp.b2campjufrin.constant.GlobalMessage;
import idb2camp.b2campjufrin.dto.HeaderRequest;
import idb2camp.b2campjufrin.dto.request.AccountDto;
import idb2camp.b2campjufrin.dto.response.AccountResponse;
import idb2camp.b2campjufrin.dto.response.BaseResponse;
import idb2camp.b2campjufrin.dto.response.CustomResponse;
import idb2camp.b2campjufrin.expection.BusinessException;
import idb2camp.b2campjufrin.model.Account;
import idb2camp.b2campjufrin.model.AccountImage;
import idb2camp.b2campjufrin.model.Department;
import idb2camp.b2campjufrin.model.Salary;
import idb2camp.b2campjufrin.repository.AccountImageRepo;
import idb2camp.b2campjufrin.repository.Accountdatarepository;
import idb2camp.b2campjufrin.repository.DepartmentRepository;
import idb2camp.b2campjufrin.repository.SalaryRepository;
import idb2camp.b2campjufrin.utils.MinioUtil;
import idb2camp.b2campjufrin.utils.StringHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final ModelMapper modelMapper;
    private final Accountdatarepository accountRepo;
    private final DepartmentRepository departmentRepo;
    private final AccountImageRepo accountImageRepo;
    private final SalaryRepository salaryRepo;
    private final MinioUtil minioUtil;

    @Override
    public CustomResponse<Page<AccountDto>> getAllAccountpage(HeaderRequest headers, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AccountDto> accountPage = accountRepo.findAllAccountsNativeQuery(pageable)
            .map(account -> modelMapper.map(account, AccountDto.class));
        CustomResponse<Page<AccountDto>> response = new CustomResponse<>();
        if (accountPage.isEmpty()) {
            response.setMessage("Data not found");
        } else {
            response.setMessage("Data Account Berhasil Ditampilkan");
        }
        response.setData(accountPage);
        return response;
    }

    public CustomResponse<AccountDto> getAccountById(Integer accountId) {
        Account account = accountRepo.findById(accountId).orElse(null);
        CustomResponse<AccountDto> response = new CustomResponse<>();
        if (account == null) {
            response.setMessage("Account data not found for Account ID: " + accountId);
        } else {
            AccountDto accountDto = modelMapper.map(account, AccountDto.class);
            response.setMessage("Account data successfully");
            response.setData(accountDto);
        }
        return response;
    }

    public BaseResponse<AccountDto> createAccount(AccountDto accountDto) {
        try {
            validateAccountDto(accountDto);
            Integer departmentId = accountDto.getDepartmentId();
            Department department = departmentRepo.findById(departmentId)
                .orElseThrow(() -> new BusinessException(GlobalMessage.DATA_NOT_FOUND));

            Account account = new Account();
            account.setFirstName(accountDto.getFirstName());
            account.setLastName(accountDto.getLastName());
            account.setEmail(accountDto.getEmail());
            account.setGender(accountDto.getGender());
            account.setReligion(accountDto.getReligion());
            account.setDepartment(department);

            Account savedAccount = accountRepo.save(account);

            AccountDto createdAccountDto = new AccountDto();
            createdAccountDto.setFirstName(savedAccount.getFirstName());
            createdAccountDto.setLastName(savedAccount.getLastName());
            createdAccountDto.setEmail(savedAccount.getEmail());
            createdAccountDto.setGender(savedAccount.getGender());
            createdAccountDto.setReligion(savedAccount.getReligion());
            createdAccountDto.setDepartmentId(savedAccount.getDepartment().getDepartmentId());
            createdAccountDto.setDepartmentName(savedAccount.getDepartment().getDepartmentName());

            return BaseResponse.<AccountDto>builder()
                .message("Account created successfully")
                .data(createdAccountDto)
                .build();
        } catch (BusinessException e) {
            return BaseResponse.<AccountDto>builder()
                .message(e.getMessage())
                .code("400")
                .build();
        }
    }

    @Transactional
    public Integer upsert(HeaderRequest headers, AccountDto accountDto, MultipartFile[] files) {
        Account account = new Account();
        if (ObjectUtils.isEmpty(accountDto.getAccountId())) {
            List<Salary> salaries = salaryRepo.findBySalaryIds(accountDto.getSalaryIds());
            account.setFirstName(accountDto.getFirstName());
            account.setEmail(accountDto.getEmail());
            account.setSalaries(new HashSet<>(salaries));
            account.setCreatedBy(headers.getEmail());
            accountRepo.save(account);
        } else {
            account = accountRepo.findById(accountDto.getAccountId())
                .orElseThrow(BusinessException::dataNotFound);
            List<Salary> salaries = salaryRepo.findBySalaryIds(accountDto.getSalaryIds());
            account.setFirstName(accountDto.getFirstName());
            account.setEmail(accountDto.getEmail());
            account.setSalaries(new HashSet<>(salaries));
            account.setUpdatedBy(headers.getEmail());
            accountRepo.save(account);
        }
        hardDeleteOldImage(account.getAccountId());
        proceedUploadImage(headers, accountDto, account, files);

        return account.getAccountId();
    }

    private void hardDeleteOldImage(Integer accountId) {
        List<Integer> accountImageIds = accountImageRepo.findByAccount_AccountId(accountId).stream()
            .map(AccountImage::getAccountImageId)
            .collect(Collectors.toList());

        accountImageRepo.deleteAllByIdInBatch(accountImageIds);
    }

    private void proceedUploadImage(HeaderRequest headers, AccountDto accountDto, Account account, MultipartFile[] files) {
        List<AccountImage> accountImages = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (Objects.equals(file.getContentType(), "image/png") ||
                Objects.equals(file.getContentType(), "image/jpg") ||
                Objects.equals(file.getContentType(), "image/jpeg") ||
                Objects.equals(file.getContentType(), "application/pdf") ||
                Objects.equals(file.getContentType(), "application/zip") ||
                Objects.equals(file.getContentType(), "application/vnd.rar") ||
                Objects.equals(file.getContentType(), "image/*")) {
                String fileName = StringHelper.toSlug(FilenameUtils.getBaseName(file.getOriginalFilename()));
                String filePath = buildFilePath(file, account.getAccountId().toString(), fileName);

                minioUtil.uploadFilesingle(file, filePath);
                String minioFilePath = minioUtil.getPreSignedUrl(filePath);

                AccountImage accountImage = AccountImage.builder()
                    .account(account)
                    .filePath(minioFilePath)
                    .isPrimary(accountDto.getPrimaryImageIndex() == i)
                    .createdBy(headers.getEmail())
                    .build();
                accountImages.add(accountImage);
            } else {
                throw new BusinessException(GlobalMessage.DOCUMENT_FILE_NOT_SUPPORTED);
            }
        }

        accountImageRepo.saveAll(accountImages);
    }


    private static String buildFilePath(MultipartFile file, String initialPathName, String fileName) {
        return new StringBuilder("account").append("/")
            .append(initialPathName).append("_")
            .append(fileName).append(".")
            .append(FilenameUtils.getExtension(file.getOriginalFilename()))
            .toString();
    }

    @Override
    public List<AccountResponse> findReference(HeaderRequest headers) {
        List<AccountResponse> responseList = accountRepo.findAll()
            .stream()
            .map(account -> AccountResponse.builder()
                .accountId(account.getAccountId())
                .firsName(account.getFirstName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .religion(account.getReligion())
                .gender(account.getGender())
                .build())
            .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(responseList)) {
            throw  new BusinessException(GlobalMessage.DATA_NOT_FOUND);
        }else
            return responseList;
    }


    private void validateAccountDto(AccountDto accountDto) {
    }
}

