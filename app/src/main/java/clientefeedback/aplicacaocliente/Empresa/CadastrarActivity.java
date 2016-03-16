package clientefeedback.aplicacaocliente.Empresa;

import android.content.SharedPreferences;
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

import java.util.HashMap;
import java.util.Map;

import clientefeedback.aplicacaocliente.BD.AutenticacaoDao;
import clientefeedback.aplicacaocliente.Models.Empresa;
import clientefeedback.aplicacaocliente.Models.Pessoa;
import clientefeedback.aplicacaocliente.R;
import clientefeedback.aplicacaocliente.Services.Url;
import clientefeedback.aplicacaocliente.Services.WebService;

public class CadastrarActivity extends AppCompatActivity {

    EditText nome;
    EditText cnpj;
    EditText descricao;
    Button botao;
    ProgressBar spinner;
    WebService ws;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        botao = (Button) findViewById(R.id.buttonSave);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        nome = (EditText) findViewById(R.id.editTextNomeEmpresa);
        cnpj = (EditText) findViewById(R.id.editTextCnpj);
        descricao = (EditText) findViewById(R.id.editTextDescricao);

        botao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);

                new Thread() {
                    public void run() {
                        ws = new WebService(Url.cadastrarEmpresaUrl(),getAutenticacao());
                        Map params = new HashMap();

                        Empresa emp = new Empresa();
                        emp.setNomeEmpresa(nome.getText().toString());
                        emp.setCnpj(cnpj.getText().toString());
                        emp.setDescricao(descricao.getText().toString());

                        //Pegar do Banco de Dados do Android
                        Pessoa pessoa = new Pessoa();
                        pessoa.setNome("Nome de Teste");

                        Gson g = new Gson();

                        params.put("empresa", emp);
                        params.put("pessoa", pessoa);

                        try {
                            String response = ws.doPost("", g.toJson(params));
                            System.out.println("Resultado: " + response);
//                    String response = ws.webGet("pegarEmpresas", params);
                            //JSONObject json = new JSONObject(response);
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
                            Message msg = new Message();
                            do {
                                String res = response;
                                Bundle b = new Bundle();
                                b.putString("msg", response);

                                msg.setData(b);
                            } while (response == null);
//
                            handler.sendMessage(msg);
                            // handler.sendMessageAtTime(msg,3000);
//
//
                        } catch (Exception e) {
                            Message msg = new Message();
                            Bundle b = new Bundle();
                            b.putString("msg", "erro");
                            msg.setData(b);
                            handler.sendMessage(msg);
                        }
                    }
                }.start();
            }
        });
    }

    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            spinner.setVisibility(View.GONE);
            System.out.println("MSG: " + msg);
            Toast.makeText(CadastrarActivity.this, msg.getData().getString("msg"), Toast.LENGTH_SHORT).show();

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

    public String getAutenticacao(){
        sharedPreferences = getSharedPreferences(getString(R.string.SharedPreferences), CONTEXT_RESTRICTED);
        return sharedPreferences.getString(getString(R.string.login)+":"+getString(R.string.senha), "");
    }
}
