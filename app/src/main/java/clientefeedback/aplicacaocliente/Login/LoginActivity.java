package clientefeedback.aplicacaocliente.Login;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import clientefeedback.aplicacaocliente.Helpers.DialogFragmentActivity;
import clientefeedback.aplicacaocliente.MainActivity;
import clientefeedback.aplicacaocliente.R;

public class    LoginActivity extends AppCompatActivity {
    EditText email;
    EditText senha;
    TextView naoCadastrado;
    TextView esqueceuSenha;
    Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.editEmail);
        senha = (EditText) findViewById(R.id.editPassword);
        naoCadastrado = (TextView) findViewById(R.id.textNaoCadastrado);
        esqueceuSenha = (TextView) findViewById(R.id.textEsqueceuSenha);
        btnEnviar = (Button) findViewById(R.id.buttonLoginEntrar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragmentActivity teste = new DialogFragmentActivity();

            }
        });


    }
}



