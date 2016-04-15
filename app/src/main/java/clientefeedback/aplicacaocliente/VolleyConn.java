package clientefeedback.aplicacaocliente;

import android.app.Activity;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import clientefeedback.aplicacaocliente.Models.Empresa;

public class VolleyConn {
    private Transaction transaction;
    private RequestQueue requestQueue;


    public VolleyConn(Context c, Transaction t){
        transaction = t;
        //requestQueue = ((CustomApplication) ((Activity) c).getApplication()).getRequestQueue();
    }


    public void execute(){
        transaction.doBefore();
        callByStringRequest(transaction.getRequestData());
    }


    private void callByStringRequest(final RequestData requestData){
        StringRequest request = new StringRequest(Request.Method.POST,
            requestData.getUrl(),
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    transaction.doAfter(response);
                }
            },
            new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    transaction.doAfter(null);
                }
        }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("method", requestData.getMethod());

                if(requestData.getObj() != null){
                    Empresa empresa = (Empresa)requestData.getObj();
                    params.put("lastId", Integer.toString(empresa.getEmpresaId()));
                }

                return(params);
            }
        };

        request.setTag("conn");
        requestQueue.add(request);
    }
}
