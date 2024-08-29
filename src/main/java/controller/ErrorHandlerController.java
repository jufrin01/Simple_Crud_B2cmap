package idb2camp.b2campjufrin.controller;


import idb2camp.b2campjufrin.constant.CaseCode;
import idb2camp.b2campjufrin.constant.GlobalMessage;
import idb2camp.b2campjufrin.dto.response.BaseResponse;
import idb2camp.b2campjufrin.expection.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.ServerWebInputException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class ErrorHandlerController {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<Void> exceptionHandler(Exception ex) {
        final String requestId = UUID.randomUUID().toString().replace("-", "");
        log.error("Exception requestId={} Error={}", requestId, ex.getStackTrace());
        return buildErrorResponse(GlobalMessage.CONTACT_OUR_TEAM, null, requestId);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<Void> runTimeExceptionHandler(RuntimeException ex) {
        final String requestId = UUID.randomUUID().toString().replace("-", "");
        log.error("RuntimeException requestId={} Error={}", requestId, ex.getStackTrace());
        return buildErrorResponse(GlobalMessage.CONTACT_OUR_TEAM, null, requestId);
    }

//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
//    public BaseResponse<Void> accessDeniedExceptionHandler(AccessDeniedException ex) {
//        final String requestId = UUID.randomUUID().toString().replace("-", "");
//        log.error("AccessDeniedException requestId={} Error={}", requestId, ex.getStackTrace());
//        return buildErrorResponse(GlobalMessage.UNAUTHORIZED, null, requestId);
//    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse<Void> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        final String requestId = UUID.randomUUID().toString().replace("-", "");
        log.info("HttpRequestMethodNotSupportedException requestId={} Error={}", requestId, ex.getStackTrace());
        return buildErrorResponse(CaseCode.INVALID_REQUEST.code, ex.getMessage(), requestId);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse<Void> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        final String requestId = UUID.randomUUID().toString().replace("-", "");
        log.info("MissingServletRequestParameterException requestId={} Error={}", requestId, ex.getStackTrace());
        return buildErrorResponse(GlobalMessage.INVALID_MANDATORY_PARAMETER.code,
                GlobalMessage.INVALID_MANDATORY_PARAMETER.message + ex.getMessage(), requestId);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse<Void> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        final String requestId = UUID.randomUUID().toString().replace("-", "");
        log.info("MethodArgumentNotValidException requestId={} Error={}", requestId, ex.getStackTrace());
        List<FieldError> methodArgumentNotValidExceptionErrors = ex.getBindingResult()
                .getFieldErrors();
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : methodArgumentNotValidExceptionErrors) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return buildErrorResponse(GlobalMessage.INVALID_FIELD_FORMAT, errors, requestId);
    }

    @ExceptionHandler(ServerWebInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Void> serverWebInputExceptionHandler(ServerWebInputException ex) {
        final String requestId = UUID.randomUUID().toString().replace("-", "");
        log.error("ServerWebInputException requestId={} Error={}", requestId, ex.getStackTrace());
        return buildErrorResponse(GlobalMessage.INVALID_PARAMETER_FORMAT.code,
                GlobalMessage.INVALID_PARAMETER_FORMAT.message + ex.getMethodParameter().getParameterName(), requestId);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Void> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        final String requestId = UUID.randomUUID().toString().replace("-", "");
        log.error("MethodArgumentTypeMismatchException requestId={} Error={}", ex.getMessage(), ex.getStackTrace());
        return buildErrorResponse(GlobalMessage.INVALID_PATH_VARIABLE_FORMAT.code,
                GlobalMessage.INVALID_PATH_VARIABLE_FORMAT.message + ex.getValue(), requestId);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Void> webExchangeBindExceptionHandler(WebExchangeBindException ex) {
        final String requestId = UUID.randomUUID().toString().replace("-", "");
        log.error("WebExchangeBindException requestId={} Error={}", requestId, ex.getStackTrace());
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return buildErrorResponse(GlobalMessage.INVALID_FIELD_FORMAT, errors, requestId);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Void> bindException(BindException ex) {
        final String requestId = UUID.randomUUID().toString().replace("-", "");
        log.error("BindException requestId={} Error={}", requestId, ex.getStackTrace());
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return buildErrorResponse(GlobalMessage.INVALID_FIELD_FORMAT, errors, requestId);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Void> webExchangeBindExceptionHandler(MissingServletRequestPartException ex) {
        final String requestId = UUID.randomUUID().toString().replace("-", "");
        log.error("MissingServletRequestPartException requestId={} Error={}", ex.getMessage(), ex.getStackTrace());
        return buildErrorResponse(GlobalMessage.INVALID_MANDATORY_PARAMETER.code,
                GlobalMessage.INVALID_MANDATORY_PARAMETER.message + ex.getRequestPartName(), requestId);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public BaseResponse<Void> businessExceptionHandler(BusinessException ble) {
        final String requestId = UUID.randomUUID().toString().replace("-", "");
        log.warn("BusinessException requestId={} message={} Error={}", ble.getMessage(), requestId, ble.getStackTrace());
        return buildErrorResponse(CaseCode.GENERAL_BUSINESS_ERROR.code, ble.getMessage(), requestId);
    }

    public static BaseResponse<Void> buildErrorResponse(GlobalMessage globalMessage, Map<String, String> errors, String requestId) {
        return BaseResponse.<Void>builder()
                .code(globalMessage.code)
                .message(globalMessage.message)
                .errorFields(errors)
                .requestId(requestId)
                .build();
    }

    public static BaseResponse<Void> buildErrorResponse(String code, String message, String requestId) {
        return BaseResponse.<Void>builder()
                .code(code)
                .message(message)
                .requestId(requestId)
                .build();
    }
}
