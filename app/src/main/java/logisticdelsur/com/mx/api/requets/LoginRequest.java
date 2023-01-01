package logisticdelsur.com.mx.api.requets;


public class LoginRequest {

    private String inputUser;
    private String inputPassword;

    public String getInputUser() {
        return inputUser;
    }

    public void setInputUser(String inputUser) {
        this.inputUser = inputUser;
    }

    public String getInputPassword() {
        return inputPassword;
    }

    public LoginRequest(String inputUser, String inputPassword) {
        this.inputUser = inputUser;
        this.inputPassword = inputPassword;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "inputUser='" + inputUser + '\'' +
                ", inputPassword='" + inputPassword + '\'' +
                '}';
    }

    public void setInputPassword(String inputPassword) {
        this.inputPassword = inputPassword;
    }
}
