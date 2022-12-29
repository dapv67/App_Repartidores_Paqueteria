package logisticdelsur.com.mx.api.interfaces;

import java.util.ArrayList;
import java.util.List;

import logisticdelsur.com.mx.api.modelo.EjemploModelo;
import logisticdelsur.com.mx.api.modelo.Entrega;
import logisticdelsur.com.mx.api.modelo.EstadosModelo;
import logisticdelsur.com.mx.api.modelo.CiudadesModelo;
import logisticdelsur.com.mx.api.modelo.Ruta;
import logisticdelsur.com.mx.api.modelo.SalidaRuta;
import logisticdelsur.com.mx.api.modelo.LlegadaRuta;
import logisticdelsur.com.mx.api.modelo.SalidaModelo;
import logisticdelsur.com.mx.api.modelo.Transporte;
import logisticdelsur.com.mx.api.modelo.UserModelo;
import logisticdelsur.com.mx.api.modelo.Vector;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.Query;


public interface ISalida {

    @GET("VerifUser")
    Call<UserModelo> verificarUsuario(@Query("inputUser") String inputUser,@Query("inputPassword") String inputpassword);

    @GET("transportes")
    Call<List<Transporte>> getTransportes(@Query("por") String por);

    @GET("posts")
    Call<List<SalidaModelo>> listSalidas();

    @GET("posts")
    Call<List<EjemploModelo>> listEjemplo();

    @GET("rutas")
    Call<List<Ruta>> getRutas();

    @GET("proveedores/estados")
    Call<List<EstadosModelo>> getEstados();

    @GET("proveedores/ciudades")
    Call<List<CiudadesModelo>> getCiudades(@Query("estado") String estado);

    @POST("salida")
    Call<Vector> setSalidaChecks(
            @Field("ruta")       String ruta,
            @Field("Transporte") String transporte,
            @Field("checks")     String checks
    );

    @POST("paquetes")
    Call<List<Vector>> setRegistrarPaquetes(
            @Field("paquetes") List<Vector> paquetes
    );

    @POST("llegada")
    Call<Vector> setLlegadaChecks(
            @Field("ruta")       String ruta,
            @Field("Transporte") String transporte,
            @Field("km")         double km,
            @Field("gasolina")   double gasolina,
            @Field("checks")     String checks
    );

    @POST("mantenimiento")
    Call<Vector> setRegistrarMantenimiento(
            @Field("transporte") String transporte,
            @Field("motivo")     String motivo
    );

    @POST("Bodega/SalidaRuta")
    Call<SalidaRuta> setSalidaRuta(@Body SalidaRuta salidaRuta);

    @POST("Bodega/LlegadaRuta")
    Call<LlegadaRuta> setLlegadaRuta(@Body LlegadaRuta llegadaRuta);

    @POST("Bodega/Entrega")
    Call<Entrega> setEntrega(@Body Entrega entrega);
}
