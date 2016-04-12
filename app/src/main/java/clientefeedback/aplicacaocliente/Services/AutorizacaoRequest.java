package clientefeedback.aplicacaocliente.Services;

import android.accounts.Account;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import clientefeedback.aplicacaocliente.MainActivity;
import clientefeedback.aplicacaocliente.R;

/**
 * Created by Alexandre on 30/03/2016.
 */
public class AutorizacaoRequest extends StringRequest {
    Context c = MainActivity.contextOfApplication();
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();

    GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(c)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build();

    public AutorizacaoRequest(int method, String url,
                              Response.Listener<String> listener,
                              Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public AutorizacaoRequest(String url,
                              Response.Listener<String> listener,
                              Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

//    public AutorizacaoRequest(int method, String url, JSONObject jsonRequest,
//                              Response.Listener<JSONObject> listener,
//                              Response.ErrorListener errorListener) {
//        super(method, url, jsonRequest, listener, errorListener);
//    }
//
//    public AutorizacaoRequest(String url, JSONObject jsonRequest,
//                              Response.Listener<JSONObject> listener,
//                              Response.ErrorListener errorListener) {
//        super(url, jsonRequest, listener, errorListener);
//    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

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

    private Map<String, String> createBasicAuthHeader(String username, String password) {
        Map<String, String> headerMap = new HashMap<String, String>();

        String credentials = username + ":" + password;
        String encodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headerMap.put("Authorization", "Basic " + encodedCredentials);
        //headerMap.put("Token", getToken());

        return headerMap;
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }


    private String getToken() {
        String token = null;
        SharedPreferences prefs = c.getSharedPreferences("account", Context.MODE_PRIVATE);
        int AUTH_REQUEST_CODE = 5000;
        Account account = new Account("alexandremaesato","");

        try {
            token = GoogleAuthUtil.getToken(c, account, "");
            return token;

        } catch (UserRecoverableAuthException e) {
            e.printStackTrace();
        } catch (GoogleAuthException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }
}