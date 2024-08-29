package idb2camp.b2campjufrin.controller;

import idb2camp.b2campjufrin.annotation.IgnoreResponseBinding;
import idb2camp.b2campjufrin.constant.CaseCode;
import idb2camp.b2campjufrin.dto.response.BaseResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class CustomResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        if (!methodParameter.getContainingClass().isAnnotationPresent(RestController.class)) {
            return o;
        }
        if (Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(IgnoreResponseBinding.class)) {
            return o;
        }
        if (methodParameter.getMethod().getName().equals("openapiJson") || methodParameter.getMethod().getName().equals("openapiYaml")) {
            return o;
        }
        if (o instanceof List || o instanceof Map) {
            return buildSuccessResponse(o);
        }
        if (o instanceof BaseResponse && ((BaseResponse<?>) o).getCode() != null) {
            return o;
        }

        return buildSuccessResponse(o);
    }

    private static <T> BaseResponse<T> buildSuccessResponse(T data) {
        return BaseResponse.<T>builder()
                .code(CaseCode.SUCCESS.code)
                .message(CaseCode.SUCCESS.desc)
                .data(data)
                .build();
    }
}
