package clientefeedback.aplicacaocliente.Services;

/**
 * Created by Guilherme on 23/02/2016.
 */
public class Url {

    //private String url = "http://192.168.25.15:8080/ServidorAplicativo/webresources";
    private static String url = "http://192.168.1.20:8080/ServidorAplicativo/webresources";

    public static String getUrl() {
        return url+"/Services/";
    }
    public static String cadastrarEmpresaUrl() {return url+"/Services/cadastrarEmpresa";}
}
