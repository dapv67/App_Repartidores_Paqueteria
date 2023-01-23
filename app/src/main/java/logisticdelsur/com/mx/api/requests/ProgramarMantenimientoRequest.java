package logisticdelsur.com.mx.api.requests;

public class ProgramarMantenimientoRequest {

    private Integer idUsuario;
    private Integer idSalidaReparto;
    private Integer idTransporte;
    private String placa;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdSalidaReparto() {
        return idSalidaReparto;
    }

    public void setIdSalidaReparto(Integer idSalidaReparto) {
        this.idSalidaReparto = idSalidaReparto;
    }

    public Integer getIdTransporte() {
        return idTransporte;
    }

    public void setIdTransporte(Integer idTransporte) {
        this.idTransporte = idTransporte;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
