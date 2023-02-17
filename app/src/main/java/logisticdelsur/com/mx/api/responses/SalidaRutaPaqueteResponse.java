package logisticdelsur.com.mx.api.responses;

public class SalidaRutaPaqueteResponse {

    private String Num_guia;

    public String getNum_guia() {
        return Num_guia;
    }

    public void setNum_guia(String num_guia) {
        this.Num_guia = num_guia;
    }

    @Override
    public String toString() {
        return "SalidaRutaPaqueteResponse{" +
                "num_guia='" + Num_guia + '\'' +
                '}';
    }
}
