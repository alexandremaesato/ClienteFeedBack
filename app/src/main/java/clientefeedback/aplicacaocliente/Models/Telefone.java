package clientefeedback.aplicacaocliente.Models;

/**
 * Created by Guilherme on 01/04/2016.
 */
public class Telefone {

    private int telefoneid;
    private String tipoTelefone;
    private String numero;

    public Telefone(String tipoTelefone, String numero) {
        this.tipoTelefone = tipoTelefone;
        this.numero = numero;
    }

    public int getTelefoneid() {
        return telefoneid;
    }

    public void setTelefoneid(int telefoneid) {
        this.telefoneid = telefoneid;
    }

    public String getTipoTelefone() {
        return tipoTelefone;
    }

    public void setTipoTelefone(String tipoTelefone) {
        this.tipoTelefone = tipoTelefone;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

}
