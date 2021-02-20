package logisticdelsur.com.mx.api.services;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import logisticdelsur.com.mx.api.interfaces.ISalida;
import logisticdelsur.com.mx.api.modelo.SalidaModelo;
import logisticdelsur.com.mx.logisticgps.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceHandler {

    private       Retrofit  retrofit;
    private final String    BASE_URL = "https://logisticdelsur.com.mx/";

    public ServiceHandler(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public List<SalidaModelo> listSalidas() {
        List<SalidaModelo>        resultado = new ArrayList<>();
        ISalida                   iSalida   = retrofit.create(ISalida.class);
        Call<List<SalidaModelo>>  call      = iSalida.listSalidas();

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
}
