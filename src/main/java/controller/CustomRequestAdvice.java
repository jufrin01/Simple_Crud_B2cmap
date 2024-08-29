package idb2camp.b2campjufrin.controller;


import idb2camp.b2campjufrin.dto.HeaderRequest;
import idb2camp.b2campjufrin.utils.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;
import java.util.Enumeration;

@Slf4j
public class CustomRequestAdvice extends RequestBodyAdviceAdapter {

    @Autowired
    private Environment environment;

    @ModelAttribute
    public HeaderRequest passMandatoryHeadersFromServletRequestIntoBeanOfHeadersDto(HttpServletRequest request) {
        log.info("{} {}", request.getMethod(), request.getRequestURI() + getParameters(request));

        HeaderRequest headers = getHeaders(request);
        StringBuilder rawTypingHeaders = new StringBuilder("x-requestid: ").append(headers.getRequestId())
                .append("\nx-email: ").append(headers.getEmail());
        log.info(rawTypingHeaders.toString());
        return headers;
    }

    private static HeaderRequest getHeaders(HttpServletRequest request) {
        return HeaderRequest.builder()
                .requestId(StringUtils.isEmpty(request.getHeader("x-requestid")) ? "null" : request.getHeader("x-requestid"))
                .email(StringUtils.isEmpty(request.getHeader("x-email")) ? "null" : request.getHeader("x-email"))
                .build();
    }

    private String getParameters(HttpServletRequest request) {
        StringBuilder strParameters = new StringBuilder();
        Enumeration<?> e = request.getParameterNames();
        if (e != null) {
            strParameters.append("?");
        }
        while (e.hasMoreElements()) {
            if (strParameters.length() > 1) {
                strParameters.append("&");
            }
            String curr = (String) e.nextElement();
            strParameters.append(curr).append("=").append(request.getParameter(curr));
        }
        return strParameters.toString();
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("payload={}", JsonUtils.objectToStringJson(body));
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
