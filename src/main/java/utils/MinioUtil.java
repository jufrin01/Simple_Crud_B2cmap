package idb2camp.b2campjufrin.utils;


import idb2camp.b2campjufrin.config.MinioProp;
import idb2camp.b2campjufrin.dto.response.FileUploadResponse;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Bucket;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Slf4j
@Component
public class MinioUtil {

    @Autowired
    private MinioProp minioProp;

    @Autowired
    private MinioClient client;

    @SneakyThrows
    public void uploadFilesingle(MultipartFile file, String filePath) {
        client.putObject(PutObjectArgs.builder()
                .bucket("b2camp-jufrin1")
                .object(filePath)
                .stream(file.getInputStream(), file.getSize(), -1)
                .build());
    }

    public void createBucket(String bucketName) throws Exception {
        if (!client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    @SneakyThrows
    public FileUploadResponse uploadFile(MultipartFile file, String bucketName) throws Exception {
        if (file == null || file.isEmpty()) {
            return null;
        }
        createBucket(bucketName);

        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = bucketName + "_" +
                System.currentTimeMillis() + "_" + format.format(new Date()) + "_" + new Random().nextInt(1000) +
                originalFilename.substring(originalFilename.lastIndexOf("."));

        try (InputStream inputStream = file.getInputStream()) {
            client.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }

        String url = minioProp.getEndpoint() + "/" + bucketName + "/" + fileName;
        String urlHost = minioProp.getFilHost() + "/" + bucketName + "/" + fileName;

        log.info("Uploaded file URL: [{}], URL Host: [{}]", url, urlHost);

        return new FileUploadResponse(url, urlHost);
    }

    public List<Bucket> getAllBuckets() throws Exception {
        return client.listBuckets();
    }

    public Optional<Bucket> getBucket(String bucketName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidResponseException, InternalException, ErrorResponseException, ServerException, XmlParserException {
        return client.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    public void removeBucket(String bucketName) throws Exception {
        client.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }

    public String getObjectURL(String bucketName, String objectName, Integer expires) throws Exception {
        return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(objectName).expiry(expires).build());
    }

    @SneakyThrows
    public ResponseEntity<byte[]> downloadObject(String bucketName, String objectName) {
        try (InputStream stream = getObject(bucketName, objectName)) {
            if (stream == null) {
                log.error("File not found: {}", objectName);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            byte[] bytes = readInputStream(stream);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Accept-Ranges", "bytes");
            httpHeaders.add("Content-Length", String.valueOf(bytes.length));
            httpHeaders.add("Content-Disposition", "attachment; filename=" + objectName);
            httpHeaders.add("Content-Type", determineContentType(objectName));

            return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Error downloading object: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String determineContentType(String fileName) {
        String defaultContentType = "application/octet-stream"; // Default to binary data if type is unknown

        if (fileName == null || fileName.isEmpty()) {
            return defaultContentType;
        }

        String fileExtension = getFileExtension(fileName);

        switch (fileExtension.toLowerCase()) {
            case "pdf":
                return "application/pdf";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            // add
            default:
                return defaultContentType;
        }
    }

    @SneakyThrows
    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "+=======+";
    }

    private InputStream getObject(String bucketName, String objectName) throws Exception {
        return client.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
    }

    public byte[] readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = inputStream.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    @SneakyThrows
    public void writeObjectToResponse(String bucketName, String objectName, HttpServletResponse response) throws IOException, MinioException {
        response.setContentType("image/jpeg");
        try (InputStream stream = getObject(bucketName, objectName);
             ServletOutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int n;
            while (-1 != (n = stream.read(buffer))) {
                out.write(buffer, 0, n);
            }
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void putObject(String bucketName, String objectName, InputStream stream) throws
            Exception {
        client.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, stream.available(), -1).contentType(objectName.substring(objectName.lastIndexOf("."))).build());
    }

    public void putObject(String bucketName, String objectName, InputStream stream, long
            size, String contextType) throws Exception {
        client.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, size, -1).contentType(contextType).build());
    }

    public StatObjectResponse getObjectInfo(String bucketName, String objectName) throws Exception {
        return client.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    public void removeObject(String bucketName, String objectName) throws Exception {
        client.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    public String getPreSignedUrl(String filename) {
        return "/file/".concat(filename);
    }
}
