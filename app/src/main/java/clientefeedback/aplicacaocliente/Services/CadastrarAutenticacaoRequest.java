package clientefeedback.aplicacaocliente.Services;

import android.content.Context;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.HashMap;
import java.util.Map;

import clientefeedback.aplicacaocliente.MainActivity;


/**
 * Created by Alexandre on 31/03/2016.
 */
public class CadastrarAutenticacaoRequest extends StringRequest {

    Context c = MainActivity.contextOfApplication();
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();

    GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(c)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build();

    public CadastrarAutenticacaoRequest(int method, String url,
                                        Response.Listener<String> listener,
                                        Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public CadastrarAutenticacaoRequest(String url,
                                        Response.Listener<String> listener,
                                        Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
//        String usuario;
//        String senha;
//        SharedPreferences prefs = c.getSharedPreferences("account", Context.MODE_PRIVATE);
//        prefs.getString(c.getString(R.string.id), "");
//        usuario = prefs.getString(c.getString(R.string.login), "");
//        senha = prefs.getString(c.getString(R.string.password), "");
//
//        senha = Base64.encodeToString( //Criptografa apenas a senha
//                (senha).getBytes(),
//                Base64.NO_WRAP);
//        return createBasicAuthHeader(usuario, senha);
        return AutenticacaoHeaders.basicAuthentication();
    }

//    private Map<String, String> createBasicAuthHeader(String username, String password) {
//        Map<String, String> headerMap = new HashMap<String, String>();
//
//        String credentials = username + ":" + password;
//        String encodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//        headerMap.put("Register", "Basic " + encodedCredentials);
//        //headerMap.put("Token", getToken());
//
//        return headerMap;
//    }


}

