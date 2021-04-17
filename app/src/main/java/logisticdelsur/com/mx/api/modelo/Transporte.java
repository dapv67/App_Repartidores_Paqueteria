package logisticdelsur.com.mx.api.modelo;

public class Transporte {

    private int Id_transporte;
    private String placa;

    public int getId_transporte() {
        return Id_transporte;
    }

    public void setId_transporte(int Id_transporte) {
        this.Id_transporte = Id_transporte;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    @Override
    public String toString() {
        return "{Id_transporte:" + Id_transporte +", placa:" + placa +'}';
    }
}
