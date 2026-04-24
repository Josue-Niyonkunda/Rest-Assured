package com.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponsePojo {

    private String msg3;

    public ResponsePojo() {}

    public String getMsg3() {
        return msg3;
    }

    public void setMsg3(String msg3) {
        this.msg3 = msg3;
    }
}
