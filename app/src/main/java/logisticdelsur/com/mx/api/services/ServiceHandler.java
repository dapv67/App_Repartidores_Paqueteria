package logisticdelsur.com.mx.api.services;

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import logisticdelsur.com.mx.api.interfaces.ISalida;
import logisticdelsur.com.mx.api.modelo.CiudadesModelo;
import logisticdelsur.com.mx.api.modelo.EjemploModelo;
import logisticdelsur.com.mx.api.modelo.EstadosModelo;
import logisticdelsur.com.mx.api.modelo.SalidaModelo;
import logisticdelsur.com.mx.api.modelo.Transporte;
import logisticdelsur.com.mx.api.modelo.Vector;
import logisticdelsur.com.mx.logisticgps.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceHandler {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.logisticexpressdelsur.com/";

    public static ISalida createService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ISalida.class);
    }

    public ServiceHandler() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public List<SalidaModelo> listSalidas() {
        List<SalidaModelo> resultado = new ArrayList<>();
        ISalida iSalida = retrofit.create(ISalida.class);
        Call<List<SalidaModelo>> call = iSalida.listSalidas();

        call.enqueue(new Callback<List<SalidaModelo>>() {
            @Override
            public void onResponse(Call<List<SalidaModelo>> call, Response<List<SalidaModelo>> response) {
                resultado.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<SalidaModelo>> call, Throwable t) {
                return;
            }
        });

        return resultado;
    }

    public List<EjemploModelo> listEjemplo() {
        List<EjemploModelo> resultado = new ArrayList<>();
        ISalida iSalida = retrofit.create(ISalida.class);
        Call<List<EjemploModelo>> call = iSalida.listEjemplo();
        call.enqueue(new Callback<List<EjemploModelo>>() {
            @Override
            public void onResponse(Call<List<EjemploModelo>> call, Response<List<EjemploModelo>> response) {
                resultado.addAll(response.body());
                Log.d("success", "ServiceHandler: " + resultado.toString());
            }

            @Override
            public void onFailure(Call<List<EjemploModelo>> call, Throwable t) {
                Log.d("success", "error en la api");
                return;
            }
        });
        return resultado;
    }

    public List<EstadosModelo> getEstados() {
        List<EstadosModelo> resultado = new ArrayList<>();
        ISalida iSalida = retrofit.create(ISalida.class);
        Call<List<EstadosModelo>> call = iSalida.getEstados();

        call.enqueue(new Callback<List<EstadosModelo>>() {
            @Override
            public void onResponse(Call<List<EstadosModelo>> call, Response<List<EstadosModelo>> response) {
                Log.d("success", response.body().toString());
                resultado.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<EstadosModelo>> call, Throwable t) {

                Log.d("success", t.getMessage().toString());
                return;
            }
        });

        return resultado;
    }

    public List<CiudadesModelo> getCiudades() {
        List<CiudadesModelo> resultado = new ArrayList<>();
        ISalida iSalida = retrofit.create(ISalida.class);
        String estado = "Colima";
        Call<List<CiudadesModelo>> call = iSalida.getCiudades(estado);

        call.enqueue(new Callback<List<CiudadesModelo>>() {
            @Override
            public void onResponse(Call<List<CiudadesModelo>> call, Response<List<CiudadesModelo>> response) {
                Log.d("success", response.body().toString());
                resultado.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<CiudadesModelo>> call, Throwable t) {

                Log.d("success", t.getMessage().toString());
                return;
            }
        });

        return resultado;
    }

    public List<Transporte> getTransportes() {
        List<Transporte> resultado = new ArrayList<>();
        ISalida iSalida = retrofit.create(ISalida.class);
        String porteo = "1";
        Call<List<Transporte>> call = iSalida.getTransportes(porteo);
        call.enqueue(new Callback<List<Transporte>>() {
            @Override
            public void onResponse(Call<List<Transporte>> call, Response<List<Transporte>> response) {
                resultado.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Transporte>> call, Throwable t) {
                Log.d("success", t.getMessage().toString());
                return;
            }
        });
        return resultado;
    }
}
