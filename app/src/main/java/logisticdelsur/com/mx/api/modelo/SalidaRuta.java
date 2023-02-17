package logisticdelsur.com.mx.api.modelo;

import java.util.List;

public class SalidaRuta {

    private String ruta;
    private String transporte;
    private String trabajador;
    private List paquetes;

    public SalidaRuta(String ruta, String transporte, String trabajador, List paquetes) {
        this.ruta = ruta;
        this.transporte = transporte;
        this.trabajador = trabajador;
        this.paquetes = paquetes;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getTransporte() {
        return transporte;
    }

    public void setTransporte(String transporte) {
        this.transporte = transporte;
    }

    public String getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(String trabajador) {
        this.trabajador = trabajador;
    }

    public List getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(List paquetes) {
        this.paquetes = paquetes;
    }

}
