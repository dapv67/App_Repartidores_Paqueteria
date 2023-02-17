package logisticdelsur.com.mx.logisticgps;

import android.content.Context;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import logisticdelsur.com.mx.api.interfaces.ISalida;
import logisticdelsur.com.mx.api.requests.RegistrarSalidaRutaRequest;
import logisticdelsur.com.mx.api.responses.SalidaRutaResponse;
import logisticdelsur.com.mx.api.responses.StandardResponse;
import logisticdelsur.com.mx.api.services.ServiceHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RutaSalidaChecklistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RutaSalidaChecklistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnRegistrarCheckSalida;
    private CheckBox[] checkBox;

    private List<String> listaCheckboxes;
    private ServiceHandler api;

    public RutaSalidaChecklistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RutaSalidaChecklistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RutaSalidaChecklistFragment newInstance(String param1, String param2) {
        RutaSalidaChecklistFragment fragment = new RutaSalidaChecklistFragment();
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

    private View.OnClickListener btnRegistrarCheckSalidaHandler = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View view) {
            SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            RegistrarSalidaRutaRequest requestBody = new RegistrarSalidaRutaRequest();
            requestBody.setIdSalidaReparto(preferences.getInt("Id_salida_reparto", 0));

            listaCheckboxes.clear();
            for (CheckBox box : checkBox) {
                String id = getResources().getResourceEntryName(box.getId())
                        .substring("salida_".length());
                if (box.isChecked()) {
                    requestBody.getChecks().put(id, Boolean.TRUE);
                } else {
                    requestBody.getChecks().put(id, Boolean.FALSE);
                }
            }

            ISalida iSalida = ServiceHandler.createService();
            Call<StandardResponse> call = iSalida.registrarChecklistSalida(requestBody);
            call.enqueue(new Callback<StandardResponse>() {
                @Override
                public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                    Log.d("Success", "Con éxito");
                    Toast.makeText(getActivity(), "Salida a ruta registrada.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<StandardResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error al enviar datos al sistema.", Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("checklist", listaCheckboxes.toString());
                    editor.commit();
                    return;
                }
            });

            Navigation.findNavController(view).navigate(R.id.rutaSalidaFragment);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ruta_salida_checklist, container, false);

        listaCheckboxes = new ArrayList<>();
        checkBox = new CheckBox[26];
        api = new ServiceHandler();

        TextView transporteView = view.findViewById(R.id.transporte_asignado_txt);
        TextView rutasView = view.findViewById(R.id.rutas_text_view);

        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String idUsuario = preferences.getString("Id_usuario", "0");
        SharedPreferences.Editor editor = preferences.edit();

        ISalida iSalida = ServiceHandler.createService();
        Call<List<SalidaRutaResponse>> call = iSalida.getSalidaRuta(Integer.parseInt(idUsuario));
        call.enqueue(new Callback<List<SalidaRutaResponse>>() {
            @Override
            public void onResponse(Call<List<SalidaRutaResponse>> call, Response<List<SalidaRutaResponse>> response) {
                if (response.isSuccessful()) {
                    Log.d("success", "onResponse: " + response.body().toString());
                    SalidaRutaResponse salidaRutaResponse = response.body().get(0);
                    transporteView.setText(salidaRutaResponse.getPlaca());
                    rutasView.setText(salidaRutaResponse.getRutas());
                    btnRegistrarCheckSalida.setEnabled(Boolean.TRUE);

                    editor.putInt("Id_salida_reparto", salidaRutaResponse.getId_salida_reparto());
                    editor.putString("placa", salidaRutaResponse.getPlaca());
                    editor.putString("ruta", salidaRutaResponse.getRutas());
                    editor.putInt("Id_transporte", salidaRutaResponse.getId_transporte());

                    editor.commit();
                } else {
                    btnRegistrarCheckSalida.setEnabled(Boolean.FALSE);
                    Toast.makeText(getActivity(), "No hay salida a ruta activa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SalidaRutaResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: No se puede establecer conexión con la BD", Toast.LENGTH_LONG).show();
                Log.d("error", t.toString());
                return;
            }
        });

        btnRegistrarCheckSalida = view.findViewById(R.id.btn_checkSalidaRuta);
        btnRegistrarCheckSalida.setOnClickListener(btnRegistrarCheckSalidaHandler);

        crearCheckBoxes(view);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listaCheckboxes = new ArrayList<>();
        checkBox = new CheckBox[26];
        api = new ServiceHandler();

        btnRegistrarCheckSalida = view.findViewById(R.id.btn_checkSalidaRuta);
        btnRegistrarCheckSalida.setOnClickListener(btnRegistrarCheckSalidaHandler);

        crearCheckBoxes(view);

    }

    public void crearCheckBoxes(View view) {
        checkBox[0] = view.findViewById(R.id.salida_aguaEnTanqueDeRecuperacion);
        checkBox[1] = view.findViewById(R.id.salida_lucesGenerales);
        checkBox[2] = view.findViewById(R.id.salida_liquidoDeFrenos);
        checkBox[3] = view.findViewById(R.id.salida_aceiteDeMotor);
        checkBox[4] = view.findViewById(R.id.salida_nivelDeAguaEnRadiador);
        checkBox[5] = view.findViewById(R.id.salida_aguaEnTanqueDeParabrisas);
        checkBox[6] = view.findViewById(R.id.salida_hulesDeLimpiaParabrisas);
        checkBox[7] = view.findViewById(R.id.salida_presionDeLlantas);
        checkBox[8] = view.findViewById(R.id.salida_aditamientos);
        checkBox[9] = view.findViewById(R.id.salida_accesorios);
        checkBox[10] = view.findViewById(R.id.salida_cruceta);
        checkBox[11] = view.findViewById(R.id.salida_gato);
        checkBox[12] = view.findViewById(R.id.salida_extintor);
        checkBox[13] = view.findViewById(R.id.salida_llantasDeRefaccion);
        checkBox[14] = view.findViewById(R.id.salida_candado);
        checkBox[15] = view.findViewById(R.id.salida_limpiezaGeneralDeUnidad);
        checkBox[16] = view.findViewById(R.id.salida_techo);
        checkBox[17] = view.findViewById(R.id.salida_puertas);
        checkBox[18] = view.findViewById(R.id.salida_vidrios);
        checkBox[19] = view.findViewById(R.id.salida_defensa);
        checkBox[20] = view.findViewById(R.id.salida_fascia);
        checkBox[21] = view.findViewById(R.id.salida_faros);
        checkBox[22] = view.findViewById(R.id.salida_retrovisores);
        checkBox[23] = view.findViewById(R.id.salida_manijasDeCamper);
        checkBox[24] = view.findViewById(R.id.salida_asientos);
        checkBox[25] = view.findViewById(R.id.salida_tablero);
    }
}