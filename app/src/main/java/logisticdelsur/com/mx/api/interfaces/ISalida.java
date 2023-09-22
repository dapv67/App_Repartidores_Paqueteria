package logisticdelsur.com.mx.api.interfaces;

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
import logisticdelsur.com.mx.api.requests.LoginRequest;
import logisticdelsur.com.mx.api.requests.ProgramarMantenimientoRequest;
import logisticdelsur.com.mx.api.requests.RegistrarLlegadaRutaRequest;
import logisticdelsur.com.mx.api.requests.RegistrarSalidaRutaRequest;
import logisticdelsur.com.mx.api.responses.ResultadosMesResponse;
import logisticdelsur.com.mx.api.responses.SalidaRutaPaqueteResponse;
import logisticdelsur.com.mx.api.responses.SalidaRutaResponse;
import logisticdelsur.com.mx.api.responses.StandardResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.Query;


public interface ISalida {

    @POST("VerifUser")
    Call<UserModelo> verificarUsuario(@Body LoginRequest loginRequest);

    @GET("VerifUser")
    Call<UserModelo> verificarUsuarioGet(@Query("inputUser")String inputUser, @Query("inputPassword") String inputPassword);

    @GET("transportes")
    Call<List<Transporte>> getTransportes(@Query("por") String por, @Header("Authorization") String accessToken);

    @GET("posts")
    Call<List<SalidaModelo>> listSalidas(@Header("Authorization") String accessToken);

    @GET("posts")
    Call<List<EjemploModelo>> listEjemplo(@Header("Authorization") String accessToken);

    @GET("rutas")
    Call<List<Ruta>> getRutas(@Header("Authorization") String accessToken);

    @GET("proveedores/estados")
    Call<List<EstadosModelo>> getEstados(@Header("Authorization") String accessToken);

    @GET("proveedores/ciudades")
    Call<List<CiudadesModelo>> getCiudades(@Query("estado") String estado,@Header("Authorization") String accessToken);

    @POST("salida")
    Call<Vector> setSalidaChecks(
            @Field("ruta")       String ruta,
            @Field("Transporte") String transporte,
            @Field("checks")     String checks,
            @Header("Authorization") String accessToken
    );

    @POST("paquetes")
    Call<List<Vector>> setRegistrarPaquetes(
            @Field("paquetes") List<Vector> paquetes,
            @Header("Authorization") String accessToken
    );

    @POST("llegada")
    Call<Vector> setLlegadaChecks(
            @Field("ruta")       String ruta,
            @Field("Transporte") String transporte,
            @Field("km")         double km,
            @Field("gasolina")   double gasolina,
            @Field("checks")     String checks,
            @Header("Authorization") String accessToken
    );

    @POST("mantenimiento")
    Call<Vector> setRegistrarMantenimiento(
            @Field("transporte") String transporte,
            @Field("motivo")     String motivo,
            @Header("Authorization") String accessToken
    );

    @POST("API/SalidaRuta")
    Call<SalidaRuta> setSalidaRuta(@Body SalidaRuta salidaRuta,
                                   @Header("Authorization") String accessToken);

    @POST("API/LlegadaRuta")
    Call<LlegadaRuta> setLlegadaRuta(@Body LlegadaRuta llegadaRuta,@Header("Authorization") String accessToken);

    @POST("API/Entrega")
    Call<StandardResponse> setEntrega(@Body Entrega entrega,
                                      @Query("Id_salida_reparto") int Id_salida_reparto,
                                      @Header("Authorization") String accessToken);

    @POST("API/Entrega/Batch")
    Call<StandardResponse> guardarEntregasBatch(@Body List<Entrega> entregas,
                                                @Query("Id_salida_reparto") int Id_salida_reparto,
                                                @Header("Authorization") String accessToken);

    @GET("API/SalidaRuta")
    Call<List<SalidaRutaResponse>> getSalidaRuta(@Query("Id_usuario") Integer Id_usuario,
                                                 @Header("Authorization") String accessToken);

    @GET("API/SalidaRutaPaquetes")
    Call<List<SalidaRutaPaqueteResponse>> getSalidaRutaPaquetes(@Query("Id_salida_reparto") Integer Id_salida_reparto,
                                                                @Header("Authorization") String accessToken);

    @GET("API/ResultadosMes")
    Call<ResultadosMesResponse> getResultadosMes(@Query("Id_usuario") Integer Id_usuario,
                                                 @Header("Authorization") String accessToken);

    @POST("API/ChecklistSalida")
    Call<StandardResponse> registrarChecklistSalida(@Body RegistrarSalidaRutaRequest request,
                                                    @Header("Authorization") String accessToken);

    @POST("API/ChecklistLlegada")
    Call<StandardResponse> registrarChecklistLlegada(@Body RegistrarLlegadaRutaRequest request,
                                                     @Header("Authorization") String accessToken);

    @POST("API/ProgramarMantenimiento")
    Call<StandardResponse> programarMantenimiento(@Body ProgramarMantenimientoRequest request,
                                                  @Header("Authorization") String accessToken);
}
