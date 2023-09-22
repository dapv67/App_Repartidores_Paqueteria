package logisticdelsur.com.mx.logisticgps;

import static logisticdelsur.com.mx.logisticgps.RutaSalidaFragment.ACCESS_TOKEN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import logisticdelsur.com.mx.api.interfaces.ISalida;
import logisticdelsur.com.mx.api.modelo.Entrega;
import logisticdelsur.com.mx.api.modelo.SalidaRuta;
import logisticdelsur.com.mx.api.responses.StandardResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntregasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntregasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner spinnerNivelGasolina;

    private Button btnEscanearEntrega;
    private TextView txtEntregaPaquete;
    private Button btnGuardarStatusPaquetes;
    private Button btnEntregasPendientes;

    public EntregasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EntregasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EntregasFragment newInstance(String param1, String param2) {
        EntregasFragment fragment = new EntregasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private View.OnClickListener btnEscanearHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            IntentIntegrator integrator = IntentIntegrator.forSupportFragment(EntregasFragment.this);
            integrator.setOrientationLocked(false);
            integrator.setPrompt("Escanear Código de barras");
            integrator.setBeepEnabled(false);
            integrator.initiateScan();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                //Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_LONG).show();
            } else {
                txtEntregaPaquete.setText(result.getContents());
                //Toast.makeText(getContext(), "Escaneado : " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public View.OnClickListener btnRegistrarEntregaHandler = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View view) {
            SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            int IdSalidaReparto = preferences.getInt("Id_salida_reparto", 0);
            String status = spinnerNivelGasolina.getSelectedItem().toString();
            String paquete = (String) txtEntregaPaquete.getText();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String fecha = dtf.format(LocalDateTime.now());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.logisticexpressdelsur.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Entrega entrega = new Entrega(paquete, status, fecha);
            ISalida iSalida = retrofit.create(ISalida.class);

            String accessToken = preferences.getString(ACCESS_TOKEN, "notoken");
            Call<StandardResponse> call = iSalida.setEntrega(entrega, IdSalidaReparto,"Bearer " +  accessToken);

            call.enqueue(new Callback<StandardResponse>() {
                @Override
                public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                    Log.d("Success", response.body().toString());
                    Toast.makeText(getActivity(), "Entrega registrada!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<StandardResponse> call, Throwable t) {
                    SharedPreferences.Editor editor = preferences.edit();
                    String pendientes = preferences.getString("pendientes", "");
                    if (!pendientes.equals("")) {
                        pendientes = pendientes + ",";
                    }
                    pendientes = pendientes + "{paquete:'" + paquete + "',status:'" + status + "',fecha:'" + fecha + "'}";
                    //Toast.makeText(getContext(),"Pendientes: "+pendientes,Toast.LENGTH_SHORT).show();
                    editor.putString("pendientes", pendientes);
                    editor.commit();
                    Log.d("Success", t.getMessage());
                    Toast.makeText(getActivity(), "Sin internet. Se ha guardado en los paquetes pendientes.", Toast.LENGTH_SHORT).show();
                    return;
                }
            });

            Navigation.findNavController(view).navigate(R.id.action_entregasFragment_to_homeFragment);
        }
    };

    public View.OnClickListener btnEntregasPendientesHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(view).navigate(R.id.action_entregasFragment_to_entregasPendientesFragment);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entregas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtEntregaPaquete = view.findViewById(R.id.txtEntregaPaquete);
        spinnerNivelGasolina = view.findViewById(R.id.spinnerNivelGasolina);
        btnEscanearEntrega = view.findViewById(R.id.btnEscanearEntrega);

        btnEscanearEntrega.setOnClickListener(btnEscanearHandler);

        btnEntregasPendientes = view.findViewById(R.id.btn_entregasPendientes);
        btnEntregasPendientes.setOnClickListener(btnEntregasPendientesHandler);

        btnGuardarStatusPaquetes = view.findViewById(R.id.btn_GuardarStatusPaquetes);
        btnGuardarStatusPaquetes.setOnClickListener(btnRegistrarEntregaHandler);
    }
}