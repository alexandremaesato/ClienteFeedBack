package clientefeedback.aplicacaocliente.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.util.HashMap;
import java.util.Map;

import clientefeedback.aplicacaocliente.MainActivity;
import clientefeedback.aplicacaocliente.R;

/**
 * Created by Alexandre on 31/03/2016.
 */
public class AutenticacaoHeaders {

    private static Context c = MainActivity.contextOfApplication();

    public static Map<String,String> basicAuthentication(){
        String usuario;
        String senha;
        SharedPreferences prefs = c.getSharedPreferences("account", Context.MODE_PRIVATE);
        prefs.getString(c.getString(R.string.id), "");
        usuario = prefs.getString(c.getString(R.string.login), "");
        senha = prefs.getString(c.getString(R.string.password), "");

        senha = Base64.encodeToString( //Criptografa apenas a senha
                (senha).getBytes(),
                Base64.NO_WRAP);

        return createBasicAuthHeader(usuario, senha);
    }

    private static Map<String, String> createBasicAuthHeader(String username, String password) {
        Map<String, String> headerMap = new HashMap<String, String>();
        String credentials = username + ":" + password;
        String encodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headerMap.put("Register", "Basic " + encodedCredentials);
        return headerMap;
    }
}
