package com.cyj.whereareyou.core;

import com.cyj.whereareyou.core.configuration.ExceptionCodeConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Author: jiangtao
 * @Date: 2021/11/5 13:52
 */
@ControllerAdvice
@Slf4j
public class ResponseJsonFormatHandler implements ResponseBodyAdvice {

    private final ExceptionCodeConfiguration exceptionCodeConfiguration;

    public ResponseJsonFormatHandler(ExceptionCodeConfiguration exceptionCodeConfiguration) {
        this.exceptionCodeConfiguration = exceptionCodeConfiguration;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        Class<?> clazz = returnType.getNestedParameterType();
        return !clazz.isAssignableFrom(ResponseEntity.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Class<?> clazz = returnType.getNestedParameterType();
        UnifyResponse unifyResponse;
        if (UnifyResponse.class.getName().equals(clazz.getName())){
            unifyResponse = (UnifyResponse) body;
        }else {
            unifyResponse = new UnifyResponse(10204);
            if (body instanceof String){
                unifyResponse.setData(body);
            }else {
                unifyResponse.setData(body);
            }
        }
        unifyResponse.setRequest(request.getMethod() + " " + request.getURI().getPath());
        unifyResponse.setMessage(exceptionCodeConfiguration.getMessage(unifyResponse.getCode()));
        log.info("Response(" + request.getRemoteAddress() + "):" + unifyResponse);
        return unifyResponse;
    }
}
