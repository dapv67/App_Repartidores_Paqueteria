package logisticdelsur.com.mx.api.modelo;

public class UserModelo {
    private int Id_porteo;
    private String rol;
    private String username;
    private String Id_usuario;
    private String token;

    public int getId_porteo() {
        return Id_porteo;
    }

    public String getRol() {
        return rol;
    }

    public String getUsername() {
        return username;
    }

    public void setId_porteo(int id_porteo) {
        Id_porteo = id_porteo;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId_usuario() {
        return Id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        Id_usuario = id_usuario;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "UserModelo{" +
                "Id_porteo=" + Id_porteo +
                ", rol='" + rol + '\'' +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
