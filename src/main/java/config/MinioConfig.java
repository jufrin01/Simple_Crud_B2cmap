package idb2camp.b2campjufrin.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MinioProp.class)
public class MinioConfig {

    @Autowired
    private MinioProp minioProp;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                        .endpoint(minioProp.getEndpoint())
                        .credentials(minioProp.getAccessKey(), minioProp.getSecretKey())
                        .build();
    }

}
