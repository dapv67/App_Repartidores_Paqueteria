package logisticdelsur.com.mx.api.modelo;

public class EstadosModelo {

    private String Id_estado;
    private String estado;

    public String getId_estado() {
        return Id_estado;
    }

    public void setId_estado(String id_estado) {
        Id_estado = id_estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "EstadosModelo{" +
                "Id_estado='" + Id_estado + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
