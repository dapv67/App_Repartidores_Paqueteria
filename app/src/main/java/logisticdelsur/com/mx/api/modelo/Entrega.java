package logisticdelsur.com.mx.api.modelo;

public class Entrega {

    private String paquete;
    private String status;

    public Entrega(String paquete, String status) {
        this.paquete = paquete;
        this.status = status;
    }

    public String getPaquete() {
        return paquete;
    }

    public void setPaquete(String paquete) {
        this.paquete = paquete;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
