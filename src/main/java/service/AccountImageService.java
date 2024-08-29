package idb2camp.b2campjufrin.service;

import idb2camp.b2campjufrin.constant.GlobalMessage;
import idb2camp.b2campjufrin.dto.HeaderRequest;
import idb2camp.b2campjufrin.dto.request.AccountImageDto;
import idb2camp.b2campjufrin.dto.response.CustomResponse;
import idb2camp.b2campjufrin.expection.BusinessException;
import idb2camp.b2campjufrin.model.Account;
import idb2camp.b2campjufrin.model.AccountImage;
import idb2camp.b2campjufrin.repository.AccountImageRepo;
import idb2camp.b2campjufrin.repository.Accountdatarepository;
import idb2camp.b2campjufrin.utils.MinioUtil;
import idb2camp.b2campjufrin.utils.RowMapper;
import idb2camp.b2campjufrin.utils.StringHelper;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountImageService {

    private AccountImageRepo accountImagerepo;
    private MinioUtil minioUtil;
    private Accountdatarepository accountdatarepo;

    public List<CustomResponse<AccountImageDto>> findByAccountIdNativeQuery(Integer accountId) {
        List<AccountImageDto> accountImages = accountImagerepo.findByAccountIdNativeQuery(accountId).stream()
                .map(objectMap -> {
                    String firstName = RowMapper.toStr(objectMap.get("first_name"));
                    String lastName = RowMapper.toStr(objectMap.get("last_name"));

                    String accountName = firstName + " " + lastName;
                    return AccountImageDto.builder()
                            .accountImageId(RowMapper.toInt(objectMap.get("account_image_id")))
                            .filePath(RowMapper.toStr(objectMap.get("file_path")))
                            .isPrimary(RowMapper.toBool(objectMap.get("is_primary")))
                            .accountId(RowMapper.toInt(objectMap.get("account_id")))
                            .accountName(accountName)
                            .build();
                })
                .collect(Collectors.toList());

        return accountImages.stream()
                .map(accountImageDto -> CustomResponse
                        .okResponse("Account image retrieved successfully", accountImageDto))
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomResponse<Integer> uploadFile(HeaderRequest headers, Integer accountId, MultipartFile[] files) {
        try {
            Account account = accountdatarepo.findById(accountId)
                    .orElseThrow(BusinessException::dataNotFound);
            softDeleteOldImage(accountId);

            List<AccountImage> accountImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if (Objects.equals(file.getContentType(), "image/png") ||
                        Objects.equals(file.getContentType(), "image/jpg")) {
                    String fileName = StringHelper.toSlug(FilenameUtils.getBaseName(file.getOriginalFilename()));
                    String filePath = buildFilePath(file, account.getAccountId().toString(), fileName);

                    minioUtil.uploadFilesingle(file, filePath);
                    String minioFilePath = minioUtil.getPreSignedUrl(filePath);

                    AccountImage accountImage = AccountImage.builder()
                            .account(account)
                            .filePath(minioFilePath)
                            .isPrimary(false)
                            .createdBy(headers.getEmail())
                            .build();
                    accountImages.add(accountImage);
                } else {
                    throw new BusinessException(GlobalMessage.DOCUMENT_FILE_NOT_SUPPORTED);
                }
            }
            accountImagerepo.saveAll(accountImages);

            return CustomResponse.okResponse("Files uploaded successfully", accountId);
        } catch (BusinessException e) {
            return CustomResponse.errorResponse(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return CustomResponse.errorResponse("Failed to upload files");
        }
    }

    private void softDeleteOldImage(Integer accountId) {
        List<Integer> accountImageIds = accountImagerepo.findByAccount_AccountId(accountId).stream()
                .map(AccountImage::getAccountImageId)
                .collect(Collectors.toList());

        accountImagerepo.deleteAllByIdInBatch(accountImageIds);
    }

    private static String buildFilePath(MultipartFile file, String initialPathName, String fileName) {
        return new StringBuilder("account").append("/")
                .append(initialPathName).append("_")
                .append(fileName).append(".")
                .append(FilenameUtils.getExtension(file.getOriginalFilename()))
                .toString();
    }
}

