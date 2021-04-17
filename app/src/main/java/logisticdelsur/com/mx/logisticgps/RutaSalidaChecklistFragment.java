package logisticdelsur.com.mx.logisticgps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logisticdelsur.com.mx.adaptadores.SpinAdapterRuta;
import logisticdelsur.com.mx.api.modelo.Ruta;
import logisticdelsur.com.mx.adaptadores.SpinAdapterTransporte;
import logisticdelsur.com.mx.api.modelo.Transporte;
import logisticdelsur.com.mx.api.services.ServiceHandler;

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

    private Spinner                        spinnerRutaSalida       ;
    private Spinner                        spinnerTransporteSalida ;
    private Button                         btnRegistrarCheckSalida ;
    private CheckBox[]                     checkBox                ;

    private List<String>                   listaCheckboxes         ;
    private Map<Integer, String>           parametrosSalida        ;
    private List<Transporte>               transportesData         ;
    private List<Ruta>                     rutaData                ;
    private ArrayAdapter<Transporte>       transportesAdaptador    ;
    private ArrayAdapter<Ruta>             rutaAdaptador           ;
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
        @Override
        public void onClick(View view) {
            listaCheckboxes.clear();
                for(int i=0; i<checkBox.length; i++){
                    if (checkBox[i].isChecked()) {
                        listaCheckboxes.add(parametrosSalida.get(i));
                    }
                }
                // call api
            Toast.makeText(getContext(), listaCheckboxes.toString(), Toast.LENGTH_SHORT).show();

            Navigation.findNavController(view).navigate(R.id.action_rutaSalidaChecklistFragment_to_rutaSalidaFragment);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ruta_salida_checklist, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        transportesData      = new ArrayList<>();
        rutaData             = new ArrayList<>();
        parametrosSalida     = new HashMap<>();
        listaCheckboxes      = new ArrayList<>();
        checkBox             = new CheckBox[26] ;
        api                  = new ServiceHandler();
        transportesAdaptador = new SpinAdapterTransporte(getContext(),
                                                         android.R.layout.simple_dropdown_item_1line,
                                                         transportesData);
        rutaAdaptador        = new SpinAdapterRuta(getContext(),
                                                  android.R.layout.simple_dropdown_item_1line,
                                                  rutaData);

        Transporte t0 = new Transporte(); t0.setId_transporte(0); t0.setPlaca("Seleccione un transporte");
        Transporte t1 = new Transporte(); t1.setId_transporte(1); t1.setPlaca("MVP-23-12");
        Transporte t2 = new Transporte(); t2.setId_transporte(2); t2.setPlaca("JNL-05-02");
        Transporte t3 = new Transporte(); t3.setId_transporte(3); t3.setPlaca("NPZ-81-20");
        transportesData.add(t0); transportesData.add(t1); transportesData.add(t2); transportesData.add(t3);
        Ruta r0 = new Ruta(); r0.setId_ruta(1); r0.setNombre("Seleccione una ruta"); r0.setPorteo_perteneciente(1);
        Ruta r1 = new Ruta(); r1.setId_ruta(1); r1.setNombre("L01"); r1.setPorteo_perteneciente(1);
        Ruta r2 = new Ruta(); r2.setId_ruta(2); r2.setNombre("F01"); r2.setPorteo_perteneciente(1);
        rutaData.add(r0); rutaData.add(r1); rutaData.add(r2);
        // OBTENER DATOS DE LA API
        //transportesData     .addAll(api.getTransportes());
        transportesAdaptador.notifyDataSetChanged();
        //rutaData            .addAll(api.getRutas());
        rutaAdaptador       .notifyDataSetChanged();




        createHashMap();

        btnRegistrarCheckSalida = view.findViewById(R.id.btn_checkSalidaRuta);
        spinnerRutaSalida       = view.findViewById(R.id.spinnerNivelGasolina);
        spinnerTransporteSalida = view.findViewById(R.id.spinnerTransporteSalida);

        btnRegistrarCheckSalida.setOnClickListener(btnRegistrarCheckSalidaHandler);
        spinnerTransporteSalida.setAdapter(transportesAdaptador);
        spinnerRutaSalida      .setAdapter(rutaAdaptador);

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