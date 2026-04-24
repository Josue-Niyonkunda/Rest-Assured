package com.pojCollection;

public class BodyObj {
    private String mode;
    private String raw;
    public BodyObj(){

    }
    public BodyObj(String mode, String raw) {
        this.mode = mode;
        this.raw = raw;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }


}
