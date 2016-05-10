package clientefeedback.aplicacaocliente.Busca;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import clientefeedback.aplicacaocliente.Empresa.PrincipalEmpresaFragment;
import clientefeedback.aplicacaocliente.Empresa.PrincipalEmpresaRequest;
import clientefeedback.aplicacaocliente.Models.Empresa;
import clientefeedback.aplicacaocliente.R;
import clientefeedback.aplicacaocliente.RequestData;
import clientefeedback.aplicacaocliente.Services.Url;
import clientefeedback.aplicacaocliente.Transaction;
import clientefeedback.aplicacaocliente.VolleyConn;

/**
 * Created by Alexandre on 09/05/2016.
 */
public class CarregaEmpresaRequest implements Transaction{
    FragmentManager fragmentManager;
    View v;
    Context c;
    ProgressBar progressBar;
    Empresa empresa;
    Bundle eBundle = new Bundle();
    Intent intent;

    Fragment mFragment = new PrincipalEmpresaFragment();

    public CarregaEmpresaRequest(View v,Context c, FragmentManager fm){
        this.fragmentManager = fm;
        this.v = v;
        this.c = c;
        (new PrincipalEmpresaRequest(c, this)).execute();
    }


    @Override
    public void doBefore() {
        //progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void doAfter(String answer) {
        try{
            Gson gson = new Gson();
            empresa = gson.fromJson(answer,Empresa.class);
            if(empresa != null){

                eBundle.putParcelable("empresa", empresa);
                mFragment.setArguments(eBundle);
                fragmentManager.beginTransaction().replace(R.id.conteudo, mFragment).commit();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            //progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public RequestData getRequestData() {
        return( new RequestData(Url.getUrl()+"Empresa/getEmpresa/1", "", "") );
    }
}
