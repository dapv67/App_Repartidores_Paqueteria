package logisticdelsur.com.mx.logisticgps;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import logisticdelsur.com.mx.api.interfaces.ISalida;
import logisticdelsur.com.mx.api.modelo.Entrega;
import logisticdelsur.com.mx.api.responses.StandardResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntregasPendientesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntregasPendientesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Entrega> entregasPendientes = new ArrayList<>();

    public EntregasPendientesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RutaPendientesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EntregasPendientesFragment newInstance(String param1, String param2) {
        EntregasPendientesFragment fragment = new EntregasPendientesFragment();
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
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String pendientes = preferences.getString("pendientes", "");

        Log.d("TAG", "onCreateView: " + pendientes.toString());
        Type listType = new TypeToken<ArrayList<Entrega>>() {
        }.getType();
        if (!pendientes.equals("")) {
            pendientes = "[" + pendientes + "]";
            Gson gson = new Gson();
            try {
                entregasPendientes = new Gson().fromJson(pendientes, listType);
            } catch (Exception e) {
                Log.d("TAG", "onCreateViewError: ");
            }
        }

        return inflater.inflate(R.layout.fragment_entregas_pendientes, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnSincronizarEntregasPendientes = view.findViewById(R.id.btn_SincronizarEntregasPendientes);
        btnSincronizarEntregasPendientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar progressBar = view.findViewById(R.id.progressBarPendientes);
                progressBar.setVisibility(View.VISIBLE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.logisticexpressdelsur.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ISalida iSalida = retrofit.create(ISalida.class);

                Call<StandardResponse> call = iSalida.guardarEntregasBatch(entregasPendientes);
                call.enqueue(new Callback<StandardResponse>() {
                    @Override
                    public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                        Log.d("Success", "Con éxito");
                        Toast.makeText(getActivity(), "Entrega registrada!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("pendientes", "");
                        editor.commit();
                    }

                    @Override
                    public void onFailure(Call<StandardResponse> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "No fue posible guardar en el sistema intente otra vez.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });

                Navigation.findNavController(v).navigate(R.id.action_entregasPendientesFragment_to_homeFragment);
            }
        });

        if (!entregasPendientes.isEmpty()) {
            TextView textView = view.findViewById(R.id.numero_paquetes_pendientes_text);
            textView.setText("Paquetes: " + entregasPendientes.size());

            LinearLayout linearLayout = view.findViewById(R.id.entregas_pendientes_scrolllayout);
            for (Entrega entrega : entregasPendientes) {
                TextView entregaView = new TextView(EntregasPendientesFragment.this.getContext());
                entregaView.setText(entrega.getPaquete() + " - " + entrega.getStatus());
                linearLayout.addView(entregaView);
            }
        }
    }
}