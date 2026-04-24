package code;

public enum StatusCode {
    CODE_200(200,"OK"),
    CODE_201(201," CREATED"),
    CODE_203(203,""),
    CODE_204(204,""),
    CODE_400(400,"NOT FOUND"),
    CODE_401(401,"BAD REQUEST"),
    CODE_404(404," PAGE NOT FOUND"),
    CODE_500(500,"SERVER ERROR");

    private int code;

    public String getSmg() {
        return smg;
    }

    public void setSmg(String smg) {
        this.smg = smg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private String smg;

    StatusCode(int code, String smg) {
        this.code=code;
        this.smg=smg;
    }
}
