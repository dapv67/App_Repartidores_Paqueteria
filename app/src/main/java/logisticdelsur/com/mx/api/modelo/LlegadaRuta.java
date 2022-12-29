package logisticdelsur.com.mx.api.modelo;

public class LlegadaRuta {

    private String gas;
    private String km;
    private String min;

    public LlegadaRuta(String gas, String km, String min) {
        this.gas = gas;
        this.km = km;
        this.min = min;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }
}
