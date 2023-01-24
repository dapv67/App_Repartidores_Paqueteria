package logisticdelsur.com.mx.api.requests;

import java.util.HashMap;
import java.util.Map;

public class RegistrarSalidaRutaRequest {

    private Integer idSalidaReparto;
    private Map<String, Boolean> checks = new HashMap<>();

    public Integer getIdSalidaReparto() {
        return idSalidaReparto;
    }

    public void setIdSalidaReparto(Integer idSalidaReparto) {
        this.idSalidaReparto = idSalidaReparto;
    }

    public Map<String, Boolean> getChecks() {
        return checks;
    }

    public void setChecks(Map<String, Boolean> checks) {
        this.checks = checks;
    }

    @Override
    public String toString() {
        return "RegistrarSalidaRutaRequest{" +
                "idSalidaReparto=" + idSalidaReparto +
                ", checks=" + checks +
                '}';
    }
}
