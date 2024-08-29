package idb2camp.b2campjufrin;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import idb2camp.b2campjufrin.dto.request.DepartmentDto;
import idb2camp.b2campjufrin.model.Department;
import jakarta.persistence.EntityManagerFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@SpringBootApplication
public class B2campJufrinApplication {

    public static void main(String[] args) {

        SpringApplication.run(B2campJufrinApplication.class, args);

    }

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.modulesToInstall(new JavaTimeModule());
        return builder;
    }

    @Bean
    public ModelMapper modelMapper(EntityManagerFactory emFactory) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(context -> emFactory.getPersistenceUnitUtil().isLoaded(context.getSource()));
        mapper.typeMap(Department.class, DepartmentDto.class).addMappings(new PropertyMap<Department, DepartmentDto>() {
            @Override
            protected void configure() {
                map().setLocation(null);
            }
        });
        return mapper;
    }
}
