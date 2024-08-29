package idb2camp.b2campjufrin.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class BaseResponse<T> {
    private String code;
    private String message;
    private Map<String, String> errorFields;
    private T data;
    private String requestId;
}
