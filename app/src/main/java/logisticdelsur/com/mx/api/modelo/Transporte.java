package logisticdelsur.com.mx.api.modelo;

public class Transporte {

    private int    Id_transporte;
    private String Placa        ;

    public int getId_transporte() {
        return Id_transporte;
    }

    public void setId_transporte(int id_transporte) {
        Id_transporte = id_transporte;
    }

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String placa) {
        Placa = placa;
    }
}
