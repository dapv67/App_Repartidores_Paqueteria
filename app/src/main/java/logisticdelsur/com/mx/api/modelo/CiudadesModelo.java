package logisticdelsur.com.mx.api.modelo;

public class CiudadesModelo {

    private String ciudad;

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "CiudadesModelo{" +
                "ciudad='" + ciudad + '\'' +
                '}';
    }
}
