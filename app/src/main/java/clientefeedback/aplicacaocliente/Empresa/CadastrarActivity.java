package clientefeedback.aplicacaocliente.Empresa;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import clientefeedback.aplicacaocliente.Models.Empresa;
import clientefeedback.aplicacaocliente.R;
import clientefeedback.aplicacaocliente.Services.Url;
import clientefeedback.aplicacaocliente.Services.WebService;

public class CadastrarActivity extends AppCompatActivity {

    EditText nome;
    EditText cnpj;
    EditText descricao;
    Button botao;
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        botao = (Button)findViewById(R.id.buttonSave);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        nome = (EditText)findViewById(R.id.editTextNomeEmpresa);
        cnpj = (EditText)findViewById(R.id.editTextCnpj);
        descricao = (EditText)findViewById(R.id.editTextDescricao);


        botao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);

                new Thread(){
                    public void run() {

                        WebService ws = new WebService(Url.cadastrarEmpresaUrl());
                        Map params = new HashMap();

                        Empresa emp = new Empresa();
                        emp.setNome(nome.getText().toString());
                        emp.setCnpj(cnpj.getText().toString());
                        emp.setDescricao(descricao.getText().toString());

                        Gson g = new Gson();
                        String empresa  = g.toJson(emp);

//                List<NameValuePair> params2 = new ArrayList<NameValuePair>();
//                params2.add(new BasicNameValuePair("empresa", empresa));

                        try{
                            String response = ws.doPost("", empresa);
//                    String response = ws.webGet("pegarEmpresas", params);
                            JSONObject json = new JSONObject(response);
//
//                    JSONObject jsonUsuario = new JSONObject(json.getString("usuario")); //Pega o Json e faz um load apenas dos dados do Usuario em um novo Json
//                    Usuario u = new Usuario();
//                    u.jsonToUsuario(jsonUsuario); //Converte o json em usuario
//                    Bundle b = new Bundle();
//                    //final ProgressDialog mprogressDialog = ProgressDialog.show(MainActivity.this, "Aguarde", "Processando...");
//                    if(u != null){
//                        b.putString("id", String.valueOf(u.getIdUsuario()));
//                        b.putString("nome", u.getNome());
//                    }else {
//                        b.putString("message", "Algo deu errado!!!");
//                    }
//
                    Bundle b = new Bundle();
                            b.putString("message", "Foi de boa");
                    Message msg = new Message();
                    msg.setData(b);
//
////                    handler.sendMessage(msg);
                    handler.sendMessageAtTime(msg,3000);
//
//
                        }catch (JSONException e1){
//                    Message msg = new Message();
//                    Bundle b= new Bundle();
//                    b.putString("msg", "erro");
//                    msg.setData(b);
                        handler.sendMessage(new Message());
//                    //e1.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }
    public Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg){
            spinner.setVisibility(View.GONE);

//            if( msg != null ) {
//                String idUsuario = msg.getData().getString("id");
//                String nome = msg.getData().getString("nome");
                //tostando(idUsuario);
//                if(idUsuario != null) {
//                    iniciaDashboard(idUsuario, nome);
//                }else
//                {
//                    tostando("Usuario ou senha incorreto!");
//                }
            //}
        }
    };
}
