package clientefeedback.aplicacaocliente.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import clientefeedback.aplicacaocliente.BD.AutenticacaoDao;
import clientefeedback.aplicacaocliente.BD.AutenticacaoOpenHelper;
import clientefeedback.aplicacaocliente.Empresa.CadastrarActivity;
import clientefeedback.aplicacaocliente.Models.Autenticacao;
import clientefeedback.aplicacaocliente.R;
import clientefeedback.aplicacaocliente.Services.Url;
import clientefeedback.aplicacaocliente.Services.WebService;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText senha;
    TextView naoCadastrado;
    TextView esqueceuSenha;
    Button btnEnviar;
    private Resources resources;
    WebService ws;
    Context c;
    String autenticacao;
    SharedPreferences prefs;
    String novaSenha;
    String novoLogin;

    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        initViews();

    }

    private void initViews() {
        resources = getResources();
        prefs = getSharedPreferences("autenticacao", Context.MODE_PRIVATE);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                callClearErrors(s);
            }
        };

        c = this;
        email = (EditText) findViewById(R.id.editEmail);
        email.setText(prefs.getString(getString(R.string.login),""));
        senha = (EditText) findViewById(R.id.editPassword);
        senha.setText(prefs.getString(getString(R.string.senha),""));
        naoCadastrado = (TextView) findViewById(R.id.textNaoCadastrado);
        esqueceuSenha = (TextView) findViewById(R.id.textEsqueceuSenha);
        btnEnviar = (Button) findViewById(R.id.buttonLoginEntrar);
        spinner = (ProgressBar) findViewById(R.id.progressBarLogin);
        spinner.setVisibility(View.GONE);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (validateFields()) {
                    spinner.setVisibility(View.VISIBLE);
                    new Thread() {
                        public void run() {

                            novoLogin = email.getText().toString();
                            novaSenha = senha.getText().toString();

                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString(getString(R.string.login), novoLogin);
                            editor.putString(getString(R.string.senha), novaSenha);
                            editor.commit();
                            ws = new WebService(Url.getUrl(), novoLogin+":"+novaSenha);
                            String response = ws.doPost("autenticacao/logar","");

                            if("".equals(response)){
                                Intent intent = new Intent(c,CadastrarActivity.class);
                                startActivity(intent);
                                handler.sendMessage(new Message());
                            }
                        }
                    }.start();
                }

            }
        });

        naoCadastrado.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(c, "Clicou em nao cadastrado", Toast.LENGTH_SHORT).show();
            }
        });

        esqueceuSenha.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(c, "Clicou em Esqueceu Senha", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void callClearErrors(Editable s) {
        if (!s.toString().isEmpty()) {
            clearErrorFields(email);
        }
    }

    private boolean validateFields() {
        String user = email.getText().toString().trim();
        String pass = senha.getText().toString().trim();
        return (!isEmptyFields(user, pass) && hasSizeValid(user, pass));
    }

    private boolean isEmptyFields(String user, String pass) {
        if (TextUtils.isEmpty(user)) {
            email.requestFocus(); //seta o foco para o campo user
            email.setError(resources.getString( R.string.email_required));
            return true;
        } else if (TextUtils.isEmpty(pass)) {
            senha.requestFocus(); //seta o foco para o campo password
            senha.setError(resources.getString(R.string.password_required));
            return true;
        }
        return false;
    }

    private boolean hasSizeValid(String user, String pass) {

        if (!(user.length() > 3)) {
            email.requestFocus();
            email.setError(resources.getString(R.string.email_required_size_invalid));
            return false;
        } else if (!(pass.length() > 5)) {
            senha.requestFocus();
            senha.setError(resources.getString(R.string.password_required_size_invalid));
            return false;
        }
        return true;
    }

    private void clearErrorFields(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setError(null);
        }
    }

    public Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg){
            spinner.setVisibility(View.GONE);
        }
    };

}



