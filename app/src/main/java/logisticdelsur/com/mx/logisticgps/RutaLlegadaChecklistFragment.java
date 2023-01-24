package logisticdelsur.com.mx.logisticgps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logisticdelsur.com.mx.api.interfaces.ISalida;
import logisticdelsur.com.mx.api.modelo.LlegadaRuta;
import logisticdelsur.com.mx.api.requests.ProgramarMantenimientoRequest;
import logisticdelsur.com.mx.api.requests.RegistrarLlegadaRutaRequest;
import logisticdelsur.com.mx.api.responses.StandardResponse;
import logisticdelsur.com.mx.api.services.ServiceHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RutaLlegadaChecklistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RutaLlegadaChecklistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner    spinnerNivelGasolina;
    private EditText   txtKMLlegada        ;
    private CheckBox[] checkBox            ;
    private Button     btnCheckLlegada     ;

    private List<String>         listaCheckboxes ;
    private ServiceHandler       api             ;

    public RutaLlegadaChecklistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RutaLlegadaChecklistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RutaLlegadaChecklistFragment newInstance(String param1, String param2) {
        RutaLlegadaChecklistFragment fragment = new RutaLlegadaChecklistFragment();
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

    private final View.OnClickListener btnRegistrarCheckLlegadaHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            RegistrarLlegadaRutaRequest requestBody = new RegistrarLlegadaRutaRequest();
            requestBody.setIdSalidaReparto(preferences.getInt("Id_salida_reparto",0));

            listaCheckboxes.clear();
            for (CheckBox box : checkBox) {
                String id = getResources().getResourceEntryName(box.getId())
                        .substring("llegada_".length());
                if (box.isChecked()) {
                    requestBody.getChecks().put(id, Boolean.TRUE);
                } else {
                    requestBody.getChecks().put(id, Boolean.FALSE);
                }
            }

            Log.d("REQUEST", requestBody.toString());
            ISalida iSalida = ServiceHandler.createService();
            Call<StandardResponse> call = iSalida.registrarChecklistLlegada(requestBody);

            call.enqueue(new Callback<StandardResponse>() {
                @Override
                public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                    Log.d("Success", "Con éxito");
                    Toast.makeText(getActivity(), "Llegada de ruta registrada.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<StandardResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error al conectar con el sistema.", Toast.LENGTH_SHORT).show();
                    return;
                }
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            String placa = preferences.getString("placa", "");
            builder.setTitle("Programación de mantenimientos");
            builder.setMessage("¿Quieres programar un mantenimiento?\n"
            + "\nTransporte: " + placa);

            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    ProgramarMantenimientoRequest request = new ProgramarMantenimientoRequest();
                    request.setPlaca(placa);
                    request.setIdSalidaReparto(preferences.getInt("Id_salida_reparto",0));
                    request.setIdTransporte(preferences.getInt("Id_transporte", 0));
                    request.setIdUsuario(Integer.valueOf(preferences.getString("Id_usuario", "0")));

                    ISalida iSalida = ServiceHandler.createService();
                    Call<StandardResponse> call = iSalida.programarMantenimiento(request);
                    call.enqueue(new Callback<StandardResponse>() {
                        @Override
                        public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                            Log.d("Success", "Con éxito mantenimiento");
                            Toast.makeText(getActivity(), "Mantenimiento registrado", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<StandardResponse> call, Throwable t) {
                            Log.d("FAILURE", t.getMessage());
                            Toast.makeText(getActivity(), "Error al conectar con el sistema.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });
                    Navigation.findNavController(view).navigate(R.id.action_rutaLlegadaChecklistFragment_to_homeFragment);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Navigation.findNavController(view).navigate(R.id.action_rutaLlegadaChecklistFragment_to_homeFragment);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ruta_llegada_checklist, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listaCheckboxes  = new ArrayList<>();
        checkBox         = new CheckBox[26];

        spinnerNivelGasolina = view.findViewById(R.id.spinnerNivelGasolina)      ;
        btnCheckLlegada      = view.findViewById(R.id.btn_checkLlegadaRuta)      ;
        btnCheckLlegada      .setOnClickListener(btnRegistrarCheckLlegadaHandler);

        crearCheckBoxes(view);
    }

    public void crearCheckBoxes(View view){
        checkBox[0]  = view.findViewById(R.id.llegada_aguaEnTanqueDeRecuperacion);
        checkBox[1]  = view.findViewById(R.id.llegada_lucesGenerales);
        checkBox[2]  = view.findViewById(R.id.llegada_liquidoDeFrenos);
        checkBox[3]  = view.findViewById(R.id.llegada_aceiteDeMotor);
        checkBox[4]  = view.findViewById(R.id.llegada_nivelDeAguaEnRadiador);
        checkBox[5]  = view.findViewById(R.id.llegada_aguaEnTanqueDeParabrisas);
        checkBox[6]  = view.findViewById(R.id.llegada_hulesDeLimpiaParabrisas);
        checkBox[7]  = view.findViewById(R.id.llegada_presionDeLlantas);
        checkBox[8]  = view.findViewById(R.id.llegada_aditamientos);
        checkBox[9]  = view.findViewById(R.id.llegada_accesorios);
        checkBox[10] = view.findViewById(R.id.llegada_cruceta);
        checkBox[11] = view.findViewById(R.id.llegada_gato);
        checkBox[12] = view.findViewById(R.id.llegada_extintor);
        checkBox[13] = view.findViewById(R.id.llegada_llantasDeRefaccion);
        checkBox[14] = view.findViewById(R.id.llegada_candado);
        checkBox[15] = view.findViewById(R.id.llegada_limpiezaGeneralDeUnidad);
        checkBox[16] = view.findViewById(R.id.llegada_techo);
        checkBox[17] = view.findViewById(R.id.llegada_puertas);
        checkBox[18] = view.findViewById(R.id.llegada_vidrios);
        checkBox[19] = view.findViewById(R.id.llegada_defensa);
        checkBox[20] = view.findViewById(R.id.llegada_fascia);
        checkBox[21] = view.findViewById(R.id.llegada_faros);
        checkBox[22] = view.findViewById(R.id.llegada_retrovisores);
        checkBox[23] = view.findViewById(R.id.llegada_manijasDeCamper);
        checkBox[24] = view.findViewById(R.id.llegada_asientos);
        checkBox[25] = view.findViewById(R.id.llegada_tablero);
    }

}