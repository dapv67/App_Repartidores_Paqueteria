package logisticdelsur.com.mx.api.responses;

public class SalidaRutaResponse {

    private Integer Id_salida_reparto;
    private Integer Id_usuario;
    private Integer Id_transporte;
    private String placa;
    private String rutas;

    public Integer getId_salida_reparto() {
        return Id_salida_reparto;
    }

    public void setId_salida_reparto(Integer id_salida_reparto) {
        Id_salida_reparto = id_salida_reparto;
    }

    public Integer getId_usuario() {
        return Id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        Id_usuario = id_usuario;
    }

    public Integer getId_transporte() {
        return Id_transporte;
    }

    public void setId_transporte(Integer id_transporte) {
        Id_transporte = id_transporte;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getRutas() {
        return rutas;
    }

    public void setRutas(String rutas) {
        this.rutas = rutas;
    }
}
