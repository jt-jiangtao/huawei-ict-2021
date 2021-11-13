package com.cty.whereareyou.exception.http;

public class ServerUnavailableException extends HttpException {
    public ServerUnavailableException(int code){
        this.code = code;
        this.httpStatusCode = 503;
    }
}
