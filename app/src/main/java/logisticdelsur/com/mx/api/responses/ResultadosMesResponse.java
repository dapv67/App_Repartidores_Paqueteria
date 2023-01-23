package logisticdelsur.com.mx.api.responses;

public class ResultadosMesResponse {

    private String chofer;
    private String total;
    private String porcentajeEntregas;
    private String entregasOntime;
    private String porcentajeOntime;
    private String promedio;

    public String getChofer() {
        return chofer;
    }

    public void setChofer(String chofer) {
        this.chofer = chofer;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPorcentajeEntregas() {
        return porcentajeEntregas;
    }

    public void setPorcentajeEntregas(String porcentajeEntregas) {
        this.porcentajeEntregas = porcentajeEntregas;
    }

    public String getEntregasOntime() {
        return entregasOntime;
    }

    public void setEntregasOntime(String entregasOntime) {
        this.entregasOntime = entregasOntime;
    }

    public String getPorcentajeOntime() {
        return porcentajeOntime;
    }

    public ResultadosMesResponse(String chofer, String total, String porcentajeEntregas, String entregasOntime, String porcentajeOntime, String promedio) {
        this.chofer = chofer;
        this.total = total;
        this.porcentajeEntregas = porcentajeEntregas;
        this.entregasOntime = entregasOntime;
        this.porcentajeOntime = porcentajeOntime;
        this.promedio = promedio;
    }

    public ResultadosMesResponse() {
    }

    public void setPorcentajeOntime(String porcentajeOntime) {
        this.porcentajeOntime = porcentajeOntime;
    }

    public String getPromedio() {
        return promedio;
    }

    public void setPromedio(String promedio) {
        this.promedio = promedio;
    }
}
