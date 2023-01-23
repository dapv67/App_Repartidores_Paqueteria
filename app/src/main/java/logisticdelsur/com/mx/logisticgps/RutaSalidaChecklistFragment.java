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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logisticdelsur.com.mx.adaptadores.SpinAdapterRuta;
import logisticdelsur.com.mx.api.interfaces.ISalida;
import logisticdelsur.com.mx.api.modelo.Ruta;
import logisticdelsur.com.mx.adaptadores.SpinAdapterTransporte;
import logisticdelsur.com.mx.api.modelo.Transporte;
import logisticdelsur.com.mx.api.modelo.UserModelo;
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

    //private Spinner                        spinnerRutaSalida       ;
    //private Spinner                        spinnerTransporteSalida ;
    private Button                         btnRegistrarCheckSalida ;
    private CheckBox[]                     checkBox                ;

    private List<String>                   listaCheckboxes         ;
    private Map<Integer, String>           parametrosSalida        ;
    //private List<Transporte>               transportesData         ;
    //private List<Ruta>                     rutaData                ;
    //private ArrayAdapter<Transporte>       transportesAdaptador    ;
    //private ArrayAdapter<Ruta>             rutaAdaptador           ;
    private ServiceHandler                 api                     ;

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
            listaCheckboxes.clear();
                for(int i=0; i<checkBox.length; i++){
                    if (checkBox[i].isChecked()) {
                        listaCheckboxes.add(parametrosSalida.get(i));
                    }
                }

            ISalida iSalida = ServiceHandler.createService();
            Call<StandardResponse> call = iSalida.registrarChecklistSalida();
            call.enqueue(new Callback<StandardResponse>() {
                @Override
                public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                    Log.d("Success", "Con éxito");
                    Toast.makeText(getActivity(), "Salida a ruta registrada.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<StandardResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "No fue posible conectar con el sistema.", Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("checklist",listaCheckboxes.toString());
                    editor.commit();
                    return;
                }
            });

            Navigation.findNavController(view).navigate(R.id.action_rutaSalidaChecklistFragment_to_rutaSalidaFragment);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ruta_salida_checklist, container, false);

        parametrosSalida     = new HashMap<>();
        listaCheckboxes      = new ArrayList<>();
        checkBox             = new CheckBox[26] ;
        api                  = new ServiceHandler();

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
                if(response.isSuccessful()){
                    Log.d("success", "onResponse: " + response.body().toString());
                    SalidaRutaResponse salidaRutaResponse = response.body().get(0);
                    transporteView.setText(salidaRutaResponse.getPlaca());
                    rutasView.setText(salidaRutaResponse.getRutas());
                    btnRegistrarCheckSalida.setEnabled(Boolean.TRUE);

                    editor.putInt("Id_salida_reparto",salidaRutaResponse.getId_salida_reparto());
                    editor.putString("placa",salidaRutaResponse.getPlaca());
                    editor.putInt("Id_transporte",salidaRutaResponse.getId_transporte());

                    editor.commit();
                }
                else{
                    btnRegistrarCheckSalida.setEnabled(Boolean.FALSE);
                    Toast.makeText(getActivity(),"No hay salida a ruta activa",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SalidaRutaResponse>> call, Throwable t) {
                Toast.makeText(getActivity(),"Error: No se puede establecer conexión con la BD",Toast.LENGTH_LONG).show();
                Log.d("error", t.toString());
                return;
            }
        });

        createHashMap();

        btnRegistrarCheckSalida = view.findViewById(R.id.btn_checkSalidaRuta);
        btnRegistrarCheckSalida.setOnClickListener(btnRegistrarCheckSalidaHandler);

        crearCheckBoxes(view);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        parametrosSalida     = new HashMap<>();
        listaCheckboxes      = new ArrayList<>();
        checkBox             = new CheckBox[26] ;
        api                  = new ServiceHandler();




        createHashMap();

        btnRegistrarCheckSalida = view.findViewById(R.id.btn_checkSalidaRuta);

        btnRegistrarCheckSalida.setOnClickListener(btnRegistrarCheckSalidaHandler);

        crearCheckBoxes(view);

    }

    public void crearCheckBoxes(View view){
        checkBox[0]  = view.findViewById(R.id.checkBox);
        checkBox[1]  = view.findViewById(R.id.checkBox2);
        checkBox[2]  = view.findViewById(R.id.checkBox3);
        checkBox[3]  = view.findViewById(R.id.checkBox4);
        checkBox[4]  = view.findViewById(R.id.checkBox5);
        checkBox[5]  = view.findViewById(R.id.checkBox6);
        checkBox[6]  = view.findViewById(R.id.checkBox7);
        checkBox[7]  = view.findViewById(R.id.checkBox8);
        checkBox[8]  = view.findViewById(R.id.checkBox9);
        checkBox[9]  = view.findViewById(R.id.checkBox10);
        checkBox[10] = view.findViewById(R.id.checkBox11);
        checkBox[11] = view.findViewById(R.id.checkBox12);
        checkBox[12] = view.findViewById(R.id.checkBox13);
        checkBox[13] = view.findViewById(R.id.checkBox14);
        checkBox[14] = view.findViewById(R.id.checkBox15);
        checkBox[15] = view.findViewById(R.id.checkBox16);
        checkBox[16] = view.findViewById(R.id.checkBox17);
        checkBox[17] = view.findViewById(R.id.checkBox18);
        checkBox[18] = view.findViewById(R.id.checkBox19);
        checkBox[19] = view.findViewById(R.id.checkBox20);
        checkBox[20] = view.findViewById(R.id.checkBox21);
        checkBox[21] = view.findViewById(R.id.checkBox22);
        checkBox[22] = view.findViewById(R.id.checkBox23);
        checkBox[23] = view.findViewById(R.id.checkBox24);
        checkBox[24] = view.findViewById(R.id.checkBox25);
        checkBox[25] = view.findViewById(R.id.checkBox26);
    }

    public void createHashMap(){
        parametrosSalida.put(0, "Agua en tanque de recuperación");
        parametrosSalida.put(1, "Luces generales");
        parametrosSalida.put(2, "Liquido de frenos");
        parametrosSalida.put(3, "Aceite de motor");
        parametrosSalida.put(4, "Nivel de agua en radiador");
        parametrosSalida.put(5, "Agua en tanque de parabrisas");
        parametrosSalida.put(6, "Hules de limpia parabrisas");
        parametrosSalida.put(7, "Presión de llantas");
        parametrosSalida.put(8, "Aditamientos");
        parametrosSalida.put(9, "Accesorios");
        parametrosSalida.put(10, "Cruceta");
        parametrosSalida.put(11, "Gato");
        parametrosSalida.put(12, "Extintor");
        parametrosSalida.put(13, "Llantas de refacción");
        parametrosSalida.put(14, "Candado");
        parametrosSalida.put(15, "Limpieza general de unidad");
        parametrosSalida.put(16, "Techo");
        parametrosSalida.put(17, "Puertas");
        parametrosSalida.put(18, "Vidrios");
        parametrosSalida.put(19, "Defensa");
        parametrosSalida.put(20, "Fascia");
        parametrosSalida.put(21, "Faros");
        parametrosSalida.put(22, "Retrovisores");
        parametrosSalida.put(23, "Manijas de camper");
        parametrosSalida.put(24, "Asientos");
        parametrosSalida.put(25, "Tablero");
    }
}