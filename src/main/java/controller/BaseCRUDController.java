package idb2camp.b2campjufrin.controller;

import idb2camp.b2campjufrin.constant.GlobalMessage;
import idb2camp.b2campjufrin.dto.HeaderRequest;
import idb2camp.b2campjufrin.dto.response.BaseResponse;
import idb2camp.b2campjufrin.expection.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.server.ServerWebExchange;


@ControllerAdvice
public abstract class BaseCRUDController {

    @ModelAttribute
    public HeaderRequest getMandatoryParameter(ServerWebExchange request) {
        return (HeaderRequest) request.getAttributes().get("header");
    }

    @ModelAttribute("header")
    public HeaderRequest getMandatoryParameterNonReactive(ServerWebExchange request) {
        return (HeaderRequest) request.getAttributes().get("header");
    }

/*    void validateDto(Object o) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(o);

        if (!violations.isEmpty()) {
            List<String> errors = violations.stream()
                    .map(objectError -> objectError.getMessage())
                    .collect(Collectors.toList());
            throw new ValidationException(StringUtils.join(errors, ", "));
        }
    }*/

    BaseResponse buildFailResponse(Throwable e) {
        return BaseResponse.builder()
                .code(GlobalMessage.ERROR.message)
                .message(e.getMessage())
                .build();
    }

//    public void validateHeader(HeaderRequest headerRequest) throws BusinessException {
//        if (StringUtils.isEmpty(headerRequest.getUserId())) {
//            throw new BusinessException(GlobalMessage.HEADER_USER_ID_NOT_NULL);
//        }
//        if (StringUtils.isEmpty(headerRequest.getIp())) {
//            throw new BusinessException(GlobalMessage.HEADER_IP_NOT_NULL);
//        }
//
//    }

    public static BaseResponse buildResponse(String code, String message, Object data) {
        return BaseResponse.builder()
                .code(code)
                .message(message)
                .data(data)
                .build();

    }

    public static BaseResponse buildSuccessResponse(Object data) {
        return BaseResponse.builder()
                .code(GlobalMessage.SUCCESS.code)
                .message(GlobalMessage.SUCCESS.message)
                .data(data)
                .build();
    }
}
