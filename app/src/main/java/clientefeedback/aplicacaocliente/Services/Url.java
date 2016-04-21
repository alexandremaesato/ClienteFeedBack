package clientefeedback.aplicacaocliente.Services;

import android.content.Context;

import clientefeedback.aplicacaocliente.BD.AutenticacaoDao;

/**
 * Created by Guilherme on 23/02/2016.
 */
public class Url {

    //private static String url = "http://192.168.25.15:8080/ServidorAplicativo/webresources/";
//    private static String url = "http://172.20.82.32:8080/ServidorAplicativo/webresources/";
    private static String url = "http://192.168.1.5:8080/ServidorAplicativo/webresources/";


    public static String getUrl() {
        return url;
    }

    public static String cadastrarEmpresaUrl() {return url+"Empresa/cadastrarEmpresa";}

    public static String autenticacao(Context c){
        String res;
        AutenticacaoDao dao = new AutenticacaoDao(c);

        res = dao.getAutenticacao();
        //dao.close();
        return res;
    }
}
