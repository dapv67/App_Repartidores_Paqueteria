package logisticdelsur.com.mx.api.modelo;

public class Entrega {

    private String paquete;
    private String status;
    private String fecha;

    public Entrega(String paquete, String status, String fecha) {
        this.paquete = paquete;
        this.status = status;
        this.fecha = fecha;
    }

    public String getPaquete() {
        return paquete;
    }

    public void setPaquete(String paquete) {
        this.paquete = paquete;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
