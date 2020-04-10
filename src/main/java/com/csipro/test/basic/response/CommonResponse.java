package com.csipro.test.basic.response;

public class CommonResponse {

    public CommonResponse() {}

    public CommonResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public CommonResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public CommonResponse(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    private String status;
    private String message;
    private Object data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
