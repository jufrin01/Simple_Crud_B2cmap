package idb2camp.b2campjufrin.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse<T> {
    private String message;
    private T data;

    public static <T> CustomResponse<T> notFoundResponse(String message) {
        return CustomResponse.<T>builder()
                .message(message)
                .build();
    }

    public static <T> CustomResponse<T> okResponse(String message, T data) {
        return CustomResponse.<T>builder()
                .message(message)
                .data(data)
                .build();
    }

    public static <T> CustomResponse<T> errorResponse(String message) {
        return new CustomResponse<>(message, null);
    }
}
