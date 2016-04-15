package clientefeedback.aplicacaocliente.Busca;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.florescu.android.rangeseekbar.RangeSeekBar;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import clientefeedback.aplicacaocliente.R;
import clientefeedback.aplicacaocliente.Services.AutorizacaoRequest;
import clientefeedback.aplicacaocliente.Services.Url;

public class FilterActivity extends AppCompatActivity {
    RangeSeekBar<Integer> rangeSeekBar;
    Spinner spinnerEstados;
    Spinner spinnerCidades;
    Spinner spinnerBairros;
    Spinner spinnerCulinaria;
    ArrayAdapter<String> adapterCidades;
    ArrayAdapter<String> adapterBairros;
    Long sharedCulinaria;
    Long sharedBairro;
    Long sharedCidade;
    Long sharedEstado;
    int sharedValorMaximo;
    int sharedValorMinimo;
    int sharedComentados;
    int sharedBuscados;
    RadioGroup rgComentados;
    RadioGroup rgBuscados;
    ProgressBar progressBar;
    private static final Object TAG = new Object();
    RequestQueue mQueue;
    List listaCidades;
    List listaBairros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(getApplicationContext());
        setContentView(R.layout.activity_filter);
        listaCidades = new ArrayList<String>();
        listaBairros = new ArrayList<String>();
        progressBar = (ProgressBar)findViewById(R.id.progressBarFilter);
        progressBar.setVisibility(View.GONE);
        carregarSharedPreferences();


        spinnerCulinaria = (Spinner) findViewById(R.id.spinnerCulinaria);
        ArrayAdapter<CharSequence> adapterCulinaria = ArrayAdapter.createFromResource(this,
                R.array.tipo_cozinhas, android.R.layout.simple_spinner_item);
        adapterCulinaria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCulinaria.setAdapter(adapterCulinaria);
        spinnerCulinaria.setSelection(sharedCulinaria.intValue());


        rangeSeekBar = new RangeSeekBar<Integer>(this);
        rangeSeekBar.setRangeValues(5, 500);
        rangeSeekBar.setSelectedMinValue(sharedValorMinimo);
        rangeSeekBar.setSelectedMaxValue(sharedValorMaximo);
        LinearLayout layout = (LinearLayout) findViewById(R.id.rangerSeek);
        layout.addView(rangeSeekBar);
        rangeSeekBar.setTextAboveThumbsColorResource(android.R.color.holo_orange_dark);

        rgComentados = (RadioGroup)findViewById(R.id.rgComentados);
        rgComentados.check(sharedComentados);
        rgBuscados = (RadioGroup)findViewById(R.id.rgBuscados);
        rgBuscados.check(sharedBuscados);


        spinnerCidades = (Spinner) findViewById(R.id.spinnerCidade);
        adapterCidades = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCidades);
        adapterCidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCidades.setAdapter(adapterCidades);


        spinnerBairros = (Spinner) findViewById(R.id.spinnerBairro);
        adapterBairros = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaBairros);
        adapterBairros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBairros.setAdapter(adapterBairros);
        spinnerBairros.setSelection(sharedBairro.intValue());


        spinnerEstados = (Spinner) findViewById(R.id.spinnerEstados);
        ArrayAdapter<CharSequence> adapterEstados = ArrayAdapter.createFromResource(this,
                R.array.estados, android.R.layout.simple_spinner_item);
        adapterEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstados.setAdapter(adapterEstados);
        spinnerEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterBairros.clear();
                adapterBairros.clear();
                carregarCidadesNoSpinner(adapterView.getSelectedItem().toString());
                spinnerCidades.setSelection(sharedCidade.intValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerEstados.setSelection(sharedEstado.intValue());

        spinnerCidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                carregarBairrosNoSpinner(adapterView.getSelectedItem().toString());
                spinnerBairros.setSelection(sharedBairro.intValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerCidades.setSelection(sharedCidade.intValue());


        Button btnFiltrar = (Button)findViewById(R.id.btnFiltrar);
        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarSharedPreferences();
                finish();
            }
        });

        Button btnLimparFiltro = (Button)findViewById(R.id.btnLimparFiltro);
        btnLimparFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparFiltro();
            }
        });

    }

    public void carregarCidadesNoSpinner(String estado){
        progressBar.setVisibility(View.VISIBLE);
        adapterCidades.clear();
        adapterCidades.add("Todos");
        requestCidades(estado);
    }

    public void carregarBairrosNoSpinner(String cidade){
        progressBar.setVisibility(View.VISIBLE);
        adapterBairros.clear();
        adapterBairros.add("Todos");
        requestBairros(cidade);
    }

    public void carregarSharedPreferences(){
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("filtro", Context.MODE_PRIVATE);
        sharedCulinaria = sharedPreferences.getLong("culinaria", 0);
        sharedEstado = sharedPreferences.getLong("estado", 0);
        sharedCidade = sharedPreferences.getLong("cidade",0);
        sharedBairro = sharedPreferences.getLong("bairro",0);
        sharedValorMinimo = sharedPreferences.getInt("valorMinimo", 5);
        sharedValorMaximo = sharedPreferences.getInt("valorMaximo", 500);
        sharedComentados = sharedPreferences.getInt("comentados",0);
        sharedBuscados = sharedPreferences.getInt("buscados", 0);
    }

    public void salvarSharedPreferences(){
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("filtro", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("culinaria", spinnerCulinaria.getSelectedItemId());
        editor.putLong("estado", spinnerEstados.getSelectedItemId());
        editor.putLong("cidade", spinnerCidades.getSelectedItemId());
        editor.putLong("bairro", spinnerBairros.getSelectedItemId());
        editor.putInt("valorMinimo", rangeSeekBar.getSelectedMinValue());
        editor.putInt("valorMaximo", rangeSeekBar.getSelectedMaxValue());
        editor.putInt("comentados", rgComentados.getCheckedRadioButtonId());
        editor.putInt("buscados", rgBuscados.getCheckedRadioButtonId());
        editor.commit();
    }

    public void limparFiltro(){
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("filtro", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        finish();
        startActivity(getIntent());
    }

    public void requestCidades(String estado){
        String url = Url.getUrl() + "Endereco/getCidades/" + estado;


        final StringRequest jsonRequest = new AutorizacaoRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    public void onResponse(String result) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            listaCidades = jsonStringToArray(result);
                            adapterCidades.addAll(listaCidades);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                if (error.networkResponse != null) {
                    if (error.networkResponse.statusCode == 401) {
                        Toast.makeText(getApplicationContext(), "Senha ou Usuário incorreto", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Erro de conexao", Toast.LENGTH_SHORT).show();
                }
            }
        });
        jsonRequest.setTag(TAG);
        mQueue.add(jsonRequest);
    }

    public void requestBairros(String cidade){
        String url = Url.getUrl() + "Endereco/getBairros/" + cidade;


        final StringRequest jsonRequest = new AutorizacaoRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    public void onResponse(String result) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            listaBairros = jsonStringToArray(result);
                            adapterBairros.addAll(listaBairros);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                if (error.networkResponse != null) {
                    if (error.networkResponse.statusCode == 401) {
                        Toast.makeText(getApplicationContext(), "Senha ou Usuário incorreto", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Erro de conexao", Toast.LENGTH_SHORT).show();
                }
            }
        });
        jsonRequest.setTag(TAG);
        mQueue.add(jsonRequest);
    }

    public ArrayList<String> jsonStringToArray(String jsonString) throws JSONException {

        ArrayList<String> stringArray = new ArrayList<String>();

        JSONArray jsonArray = new JSONArray(jsonString);
        for (int i = 0; i < jsonArray.length(); i++) {
            stringArray.add(jsonArray.getString(i));
        }

        return stringArray;
    }




}
