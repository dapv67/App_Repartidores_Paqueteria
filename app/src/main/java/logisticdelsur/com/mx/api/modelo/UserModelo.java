package logisticdelsur.com.mx.api.modelo;

public class UserModelo {
    private int Id_porteo;
    private String rol;
    private String username;

    public int getId_porteo() {
        return Id_porteo;
    }

    public String getRol() {
        return rol;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "UserModelo{" +
                "Id_porteo=" + Id_porteo +
                ", rol='" + rol + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
