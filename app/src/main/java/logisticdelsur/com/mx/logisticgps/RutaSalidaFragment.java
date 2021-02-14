package logisticdelsur.com.mx.logisticgps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnEscanear, btnRegistrarPaquetes;
    private TextView txtTotalPaquetes;
    private ListView listViewPaquetes;
    private ArrayAdapter<String> adaptador;

    private List<String> listaPaquetes = new ArrayList<>();

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
                Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_LONG).show();
            } else {
                listaPaquetes.add(result.getContents());
                adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,listaPaquetes);
                listViewPaquetes.setAdapter(adaptador);
                txtTotalPaquetes.setText( "Paquetes: " + listaPaquetes.size());

                Toast.makeText(getContext(), "Escaneado : " + result.getContents(), Toast.LENGTH_LONG).show();
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
        Toast.makeText(getContext(), "Eliminado", Toast.LENGTH_SHORT).show();
    }

    public void cancelar(){
        Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_SHORT).show();
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

    private View.OnClickListener btnRegistrarPaquetesHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // call Api
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ruta_salida, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnSalidaRuta = view.findViewById(R.id.btn_registrarSalidaRuta);
        btnSalidaRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_rutaSalidaFragment_to_homeFragment);
            }
        });

        btnEscanear = view.findViewById(R.id.btnEscanear);
        txtTotalPaquetes   = view.findViewById(R.id.txtTotalPaquetes);
        listViewPaquetes = view.findViewById(R.id.listRegistrarPaquetes);
        btnRegistrarPaquetes = view.findViewById(R.id.btn_registrarSalidaRuta);

        btnEscanear.setOnClickListener(btnEscanearHandler);
        listViewPaquetes.setOnItemClickListener(listViewEliminarHandler);
        btnRegistrarPaquetes.setOnClickListener(btnRegistrarPaquetesHandler);
    }
}