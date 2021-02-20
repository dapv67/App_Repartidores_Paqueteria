package logisticdelsur.com.mx.api.interfaces;

import java.util.List;

import logisticdelsur.com.mx.api.modelo.Ruta;
import logisticdelsur.com.mx.api.modelo.SalidaModelo;
import logisticdelsur.com.mx.api.modelo.Transporte;
import logisticdelsur.com.mx.api.modelo.Vector;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ISalida {

    @GET("posts")
    Call<List<SalidaModelo>> listSalidas();


    @GET("rutas")
    Call<List<Ruta>> getRutas();

    @GET("transportes")
    Call<List<Transporte>> getTransportes();

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
}
