package idb2camp.b2campjufrin.controller;

import idb2camp.b2campjufrin.annotation.IgnoreResponseBinding;
import idb2camp.b2campjufrin.dto.response.FileUploadResponse;
import idb2camp.b2campjufrin.utils.MinioUtil;
import io.minio.errors.MinioException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

;

@Slf4j
@RestController
@RequestMapping("/file")
public class MinioController {

    @Autowired
    private MinioUtil minioUtil;

    @PostMapping("/upload")
    @IgnoreResponseBinding
    public FileUploadResponse upload(
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam(required = false, defaultValue = "b2camp-jufrin1") String bucketName) throws Exception {
        return minioUtil.uploadFile(file, bucketName);
    }

    @IgnoreResponseBinding
    @DeleteMapping("/delete/{objectName}")
    public void delete(@PathVariable("objectName") String objectName,
                       @RequestParam(required = false, defaultValue = "b2camp-jufrin")
                       String bucketName) throws Exception {
        minioUtil.removeObject(bucketName, objectName);
        log.error("terdelete");
    }

    @IgnoreResponseBinding
    @GetMapping("/download/{objectName}")
    public ResponseEntity<byte[]> downloadToLocal(@PathVariable("objectName") String objectName,
                                                  HttpServletResponse response) {
        return minioUtil.downloadObject("b2camp-jufrin1", objectName);
    }

    @IgnoreResponseBinding
    @GetMapping("/preViewPicture/{objectName}")
    public void preViewPicture
            (@PathVariable("objectName") String objectName, HttpServletResponse response)
            throws IOException,
            MinioException {
        minioUtil.writeObjectToResponse("b2camp-jufrin1", objectName, response);
    }
}
