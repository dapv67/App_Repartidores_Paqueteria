package logisticdelsur.com.mx.api.modelo;

public class Ruta {

    private int Id_ruta;
    private String Nombre;
    private int Porteo_perteneciente;

    public int getId_ruta() {
        return Id_ruta;
    }

    public void setId_ruta(int id_ruta) {
        Id_ruta = id_ruta;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getPorteo_perteneciente() {
        return Porteo_perteneciente;
    }

    public void setPorteo_perteneciente(int porteo_perteneciente) {
        Porteo_perteneciente = porteo_perteneciente;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}
