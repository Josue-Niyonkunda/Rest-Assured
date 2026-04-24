package com.pojCollection;

import java.util.List;

public class Collection {
    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    Info info;

    public Collection(Info info, List<Object> list) {
        this.info = info;
        this.list = list;
    }

    List<Object> list;
}
