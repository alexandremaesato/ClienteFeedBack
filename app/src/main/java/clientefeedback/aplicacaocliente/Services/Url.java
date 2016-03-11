package clientefeedback.aplicacaocliente.Services;

/**
 * Created by Guilherme on 23/02/2016.
 */
public class Url {

    //private String url = "http://192.168.25.15:8080/ServidorAplicativo/webresources";
    private static String url = "http://192.168.1.7:8080/ServidorAplicativo/webresources/";

    public static String getUrl() {
        return url;
    }
    public static String cadastrarEmpresaUrl() {return url+"/Services/cadastrarEmpresa";}
}
