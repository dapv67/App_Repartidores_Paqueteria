package logisticdelsur.com.mx.logisticgps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logisticdelsur.com.mx.api.services.ServiceHandler;

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
    private Map<Integer, String> parametrosSalida;
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

    private View.OnClickListener btnRegistrarCheckLlegadaHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            //api.registrarLlegada(nivelGasolina,KM,listaCheckboxes.toString());

            listaCheckboxes.clear();
            for(int i=0; i<checkBox.length; i++){
                if (checkBox[i].isChecked()) {
                    listaCheckboxes.add(parametrosSalida.get(i));
                }
            }
            // call api
            Toast.makeText(getContext(), listaCheckboxes.toString(), Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Programación de mantenimientos");
            builder.setMessage("¿Quieres programar un mantenimiento?");

            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //api.registrarMantenimiento();
                    Navigation.findNavController(view).navigate(R.id.action_rutaLlegadaChecklistFragment_to_rutaMantenimientoFragment);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
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
        parametrosSalida = new HashMap<>();
        checkBox         = new CheckBox[26];

        spinnerNivelGasolina = view.findViewById(R.id.spinnerNivelGasolina)      ;
        //txtKMLlegada         = view.findViewById(R.id.txtKMLlegada)              ;
        btnCheckLlegada      = view.findViewById(R.id.btn_checkLlegadaRuta)      ;
        btnCheckLlegada      .setOnClickListener(btnRegistrarCheckLlegadaHandler);

        crearCheckBoxes(view);
        createHashMap();
    }

    public void crearCheckBoxes(View view){
        checkBox[0]  = view.findViewById(R.id.checkBox43);
        checkBox[1]  = view.findViewById(R.id.checkBox44);
        checkBox[2]  = view.findViewById(R.id.checkBox45);
        checkBox[3]  = view.findViewById(R.id.checkBox46);
        checkBox[4]  = view.findViewById(R.id.checkBox47);
        checkBox[5]  = view.findViewById(R.id.checkBox48);
        checkBox[6]  = view.findViewById(R.id.checkBox49);
        checkBox[7]  = view.findViewById(R.id.checkBox50);
        checkBox[8]  = view.findViewById(R.id.checkBox51);
        checkBox[9]  = view.findViewById(R.id.checkBox52);
        checkBox[10] = view.findViewById(R.id.checkBox53);
        checkBox[11] = view.findViewById(R.id.checkBox54);
        checkBox[12] = view.findViewById(R.id.checkBox55);
        checkBox[13] = view.findViewById(R.id.checkBox56);
        checkBox[14] = view.findViewById(R.id.checkBox57);
        checkBox[15] = view.findViewById(R.id.checkBox58);
        checkBox[16] = view.findViewById(R.id.checkBox59);
        checkBox[17] = view.findViewById(R.id.checkBox60);
        checkBox[18] = view.findViewById(R.id.checkBox61);
        checkBox[19] = view.findViewById(R.id.checkBox62);
        checkBox[20] = view.findViewById(R.id.checkBox63);
        checkBox[21] = view.findViewById(R.id.checkBox64);
        checkBox[22] = view.findViewById(R.id.checkBox65);
        checkBox[23] = view.findViewById(R.id.checkBox66);
        checkBox[24] = view.findViewById(R.id.checkBox67);
        checkBox[25] = view.findViewById(R.id.checkBox68);
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