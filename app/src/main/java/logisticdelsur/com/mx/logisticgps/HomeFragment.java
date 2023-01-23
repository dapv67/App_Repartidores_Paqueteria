package logisticdelsur.com.mx.logisticgps;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import logisticdelsur.com.mx.api.interfaces.ISalida;
import logisticdelsur.com.mx.api.responses.ResultadosMesResponse;
import logisticdelsur.com.mx.api.responses.StandardResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView textViewChofer = view.findViewById(R.id.textViewChofer);
        TextView textViewTotalEntregas = view.findViewById(R.id.textViewEntregas);
        TextView textViewPorcentajeEntregas = view.findViewById(R.id.textViewPorcentajeEntregas);
        TextView textViewEntregasOntime = view.findViewById(R.id.textViewEntregasOntime);
        TextView textViewPorcentajeOntime = view.findViewById(R.id.textViewPorcentajeOntime);
        TextView textViewEntregasPorHora = view.findViewById(R.id.textViewEntregasHora);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.logisticexpressdelsur.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ISalida iSalida = retrofit.create(ISalida.class);
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String idUsuario = preferences.getString("Id_usuario", "0");

        Call<ResultadosMesResponse> call = iSalida.getResultadosMes(Integer.valueOf(idUsuario));
        call.enqueue(new Callback<ResultadosMesResponse>() {
            @Override
            public void onResponse(Call<ResultadosMesResponse> call, Response<ResultadosMesResponse> response) {
                Log.d("Success", "Con éxito");
                ResultadosMesResponse resultadosMesResponse = response.body();
                if (resultadosMesResponse != null) {
                    textViewChofer.setText(resultadosMesResponse.getChofer());
                    textViewTotalEntregas.setText(resultadosMesResponse.getTotal());
                    textViewPorcentajeEntregas.setText(resultadosMesResponse.getPorcentajeEntregas());
                    textViewEntregasOntime.setText(resultadosMesResponse.getEntregasOntime());
                    textViewPorcentajeOntime.setText(resultadosMesResponse.getPorcentajeOntime());
                    textViewEntregasPorHora.setText(resultadosMesResponse.getPromedio());
                }
            }

            @Override
            public void onFailure(Call<ResultadosMesResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "No fue conectar con el sistema.", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnRuta = view.findViewById(R.id.ruta);
        Button btnEntregas = view.findViewById(R.id.entregas);
        Button btnSalir = view.findViewById(R.id.salir);
        btnRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_rutaFragment);
            }
        });
        btnEntregas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_entregasFragment);
            }
        });
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_loginFragment);
            }
        });

    }
}