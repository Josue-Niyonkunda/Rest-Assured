package com.pojCollection;

import java.security.PublicKey;
import java.util.List;

public class Request {

    private String url;
    private String method;
    List<HeaderObject> headerObjectList;
    BodyObj bodyObj;
    private String description;
    public Request(){

    }
    public Request(String url, String method, List<HeaderObject> headerObjectList, BodyObj bodyObj, String description) {
        this.url = url;
        this.method = method;
        this.headerObjectList = headerObjectList;
        this.bodyObj = bodyObj;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<HeaderObject> getHeaderObjectList() {
        return headerObjectList;
    }

    public void setHeaderObjectList(List<HeaderObject> headerObjectList) {
        this.headerObjectList = headerObjectList;
    }

    public BodyObj getBodyObj() {
        return bodyObj;
    }

    public void setBodyObj(BodyObj bodyObj) {
        this.bodyObj = bodyObj;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
