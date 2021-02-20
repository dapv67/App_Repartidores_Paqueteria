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
        txtKMLlegada         = view.findViewById(R.id.txtKMLlegada)              ;
        btnCheckLlegada      = view.findViewById(R.id.btn_checkLlegadaRuta)      ;
        btnCheckLlegada      .setOnClickListener(btnRegistrarCheckLlegadaHandler);

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
}