package com.cty.whereareyou.core;

import com.cty.whereareyou.core.configuration.ExceptionCodeConfiguration;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@ToString
public class UnifyResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String request;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    public UnifyResponse(int code) {
        this(code, null);
    }

    public UnifyResponse(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public static UnifyResponse build(int code){
        return new UnifyResponse(code);
    }

    public static UnifyResponse build(int code, Object data){
        return new UnifyResponse(code, data);
    }
}

