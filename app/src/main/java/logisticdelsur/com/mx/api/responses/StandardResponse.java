package logisticdelsur.com.mx.api.responses;

public class StandardResponse {
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public StandardResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "StandardResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String code;
    private String message;
}
