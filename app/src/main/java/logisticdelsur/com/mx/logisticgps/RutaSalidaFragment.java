package logisticdelsur.com.mx.logisticgps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import logisticdelsur.com.mx.api.interfaces.ISalida;
import logisticdelsur.com.mx.api.modelo.Entrega;
import logisticdelsur.com.mx.api.modelo.SalidaModelo;
import logisticdelsur.com.mx.api.modelo.SalidaRuta;
import logisticdelsur.com.mx.api.responses.SalidaRutaPaqueteResponse;
import logisticdelsur.com.mx.api.responses.SalidaRutaResponse;
import logisticdelsur.com.mx.api.services.ServiceHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RutaSalidaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RutaSalidaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String ACCESS_TOKEN = "access_token";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button               btnEscanear;
    private TextView             txtTotalPaquetes                 ;
    private ListView             listViewPaquetes                 ;
    private ArrayAdapter<String> adaptador                        ;
    private Button               btnSalidaRuta                    ;

    private ServiceHandler       api                              ;
    private List<String>         listaPaquetes = new ArrayList<>();

    public RutaSalidaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RutaSalidaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RutaSalidaFragment newInstance(String param1, String param2) {
        RutaSalidaFragment fragment = new RutaSalidaFragment();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                //Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_LONG).show();
            } else {
                listaPaquetes    .add(result.getContents());
                adaptador        = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,listaPaquetes);
                listViewPaquetes .setAdapter(adaptador);
                txtTotalPaquetes .setText( "Paquetes: " + listaPaquetes.size());

                //Toast.makeText(getContext(), "Escaneado : " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private AdapterView.OnItemClickListener listViewEliminarHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            AlertDialog.Builder dialogConfirmacion = new AlertDialog.Builder(getContext());
            dialogConfirmacion.setTitle("Importante");
            dialogConfirmacion.setMessage("¿Desea eliminar este paquete de la lista? " + listaPaquetes.get(position));
            dialogConfirmacion.setCancelable(false);
            dialogConfirmacion.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    aceptar(position);
                }
            });
            dialogConfirmacion.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    cancelar();
                }
            });
            dialogConfirmacion.show();
            return;
        }
    };

    public void aceptar(int position){
        listaPaquetes.remove(position);
        adaptador.notifyDataSetChanged();
        txtTotalPaquetes.setText("Paquetes: " + listaPaquetes.size());
        //Toast.makeText(getContext(), "Eliminado", Toast.LENGTH_SHORT).show();
    }

    public void cancelar(){
        //Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_SHORT).show();
    }

    private View.OnClickListener btnEscanearHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            IntentIntegrator integrator = IntentIntegrator.forSupportFragment(RutaSalidaFragment.this);
            integrator.setOrientationLocked(false);
            integrator.setPrompt("Escanear Código de barras");
            integrator.setBeepEnabled(false);
            integrator.initiateScan();
        }
    };

    private View.OnClickListener btnSalidaRutaHandler = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View view) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.logisticexpressdelsur.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            String ruta = preferences.getString("ruta","No hay ruta ");
            String transporte = preferences.getString("placa","No hay transporte ");
            String username = preferences.getString("Id_usuario","No hay username ");

            SharedPreferences.Editor editor = preferences.edit();
            LocalTime tiempoSalida = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String aux = tiempoSalida.format(formatter);
            editor.putString("tiempoSalida", aux);
            editor.commit();

            String accessToken = preferences.getString(ACCESS_TOKEN, "notoken");

            SalidaRuta salidaRuta = new SalidaRuta(ruta,transporte,username,listaPaquetes);
            ISalida iSalida = retrofit.create(ISalida.class);
            Call<SalidaRuta> call = iSalida.setSalidaRuta(salidaRuta, "Bearer " + accessToken);

            call.enqueue(new Callback<SalidaRuta>() {
                @Override
                public void onResponse(Call<SalidaRuta> call, Response<SalidaRuta> response) {
                    if(!response.isSuccessful()){
                        Log.d("Success","Falló");
                        return;
                    }
                    Log.d("Success","Con éxito");
                    Toast.makeText(getActivity(), "Ruta de salida registrada!", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.action_rutaSalidaFragment_to_homeFragment);
                }

                @Override
                public void onFailure(Call<SalidaRuta> call, Throwable t) {
                    Log.d("Success",t.getMessage());
                    return;
                }
            });
            //Toast.makeText(getActivity(), "Ruta de salida registrada!", Toast.LENGTH_SHORT).show();
            //Navigation.findNavController(view).navigate(R.id.action_rutaSalidaFragment_to_homeFragment);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ruta_salida_checklist, container, false);


        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        int IdSalidaReparto = preferences.getInt("Id_salida_reparto", 0);

        ISalida iSalida = ServiceHandler.createService();
        String accessToken = preferences.getString(ACCESS_TOKEN, "notoken");
        Call<List<SalidaRutaPaqueteResponse>> call = iSalida.getSalidaRutaPaquetes(IdSalidaReparto,"Bearer " + accessToken);
        call.enqueue(new Callback<List<SalidaRutaPaqueteResponse>>() {
            @Override
            public void onResponse(Call<List<SalidaRutaPaqueteResponse>> call, Response<List<SalidaRutaPaqueteResponse>> response) {
                if (response.isSuccessful()) {
                    Log.d("success", "onResponse: " + response.body().toString());
                    for (SalidaRutaPaqueteResponse salidaRutaPaqueteResponse : response.body()) {
                        listaPaquetes.add(salidaRutaPaqueteResponse.getNum_guia());
                        Log.d("Test: ","onResponse " + salidaRutaPaqueteResponse.getNum_guia());
                    }
                    adaptador        = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,listaPaquetes);
                    listViewPaquetes .setAdapter(adaptador);
                    txtTotalPaquetes .setText( "Paquetes: " + listaPaquetes.size());
                } else {
                    Toast.makeText(getActivity(), "No hay salida a ruta activa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SalidaRutaPaqueteResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: No se puede establecer conexión con la BD", Toast.LENGTH_LONG).show();
                Log.d("error", t.toString());
                return;
            }
        });

        return inflater.inflate(R.layout.fragment_ruta_salida, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSalidaRuta = view.findViewById(R.id.btn_registrarSalidaRuta);
        btnSalidaRuta.setOnClickListener(btnSalidaRutaHandler);

        btnEscanear          = view.findViewById(R.id.btnEscanear);
        txtTotalPaquetes     = view.findViewById(R.id.txtTotalPaquetes);
        listViewPaquetes     = view.findViewById(R.id.listRegistrarPaquetes);

        btnEscanear          .setOnClickListener(btnEscanearHandler);
        listViewPaquetes     .setOnItemClickListener(listViewEliminarHandler);

    }
}