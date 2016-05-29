package clientefeedback.aplicacaocliente.Produto;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import clientefeedback.aplicacaocliente.R;

public class CadastrarProdutoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_produto);

        initViews();
    }

    private void initViews(){

        Toolbar cptoolbar = (Toolbar) findViewById(R.id.cptoolbar);
        setTitle(R.string.cadastro_produto);
        setSupportActionBar(cptoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
