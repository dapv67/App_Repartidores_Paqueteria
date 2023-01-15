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
import android.widget.EditText;
import android.widget.Toast;

import logisticdelsur.com.mx.api.interfaces.ISalida;
import logisticdelsur.com.mx.api.modelo.UserModelo;
import logisticdelsur.com.mx.api.requests.LoginRequest;
import logisticdelsur.com.mx.api.services.ServiceHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String username;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        EditText username = view.findViewById(R.id.editTextTextPersonName);
        EditText password = view.findViewById(R.id.editTextTextPassword);
        Button btnLogin = view.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (username.getText().length() == 0) {
                        Toast.makeText(getContext(), "Ingrese el usuario", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (password.getText().length() == 0) {
                        Toast.makeText(getContext(), "Ingrese la contraseña", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ISalida iSalida = ServiceHandler.createService();
                    Call<UserModelo> call = iSalida.verificarUsuario(new LoginRequest(username.getText().toString(),password.getText().toString()));
                    call.enqueue(new Callback<UserModelo>() {
                        @Override
                        public void onResponse(Call<UserModelo> call, Response<UserModelo> response) {
                            if (response.isSuccessful()) {
                                if ("chofer".equals(response.body().getRol()) || "admin".equals(response.body().getRol())) {
                                    Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_homeFragment);
                                } else {
                                    Toast.makeText(getActivity(), "Credenciales incorrectas.", Toast.LENGTH_SHORT).show();
                                }
                                Log.d("error", response.body().toString());
                            } else {
                                Toast.makeText(getActivity(), "Credenciales incorrectas...", Toast.LENGTH_SHORT).show();
                            }
                            Log.d("error", String.valueOf(response.code()));

                        }

                        @Override
                        public void onFailure(Call<UserModelo> call, Throwable t) {
                            Toast.makeText(getActivity(), "Error: No se puede establecer conexión con el sistema", Toast.LENGTH_LONG).show();
                            Log.d("error", t.toString());
                            return;
                        }
                    });
                }catch (Exception e) {
                    Toast.makeText(getActivity(), "Error: Credenciales incorrectas", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}