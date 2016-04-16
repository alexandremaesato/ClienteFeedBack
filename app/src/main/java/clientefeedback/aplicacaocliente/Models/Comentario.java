package clientefeedback.aplicacaocliente.Models;

/**
 * Created by Alexandre on 14/04/2016.
 */
public class Comentario {

    private int comentarioid;
    private String descricao;
    private int pessoaid;
    private int comentarioDependenteid;
    private int comentadoid;
    private int modificado;
    private String tipoComentado;

    public int getComentarioid() {
        return comentarioid;
    }

    public void setComentarioid(int comentarioid) {
        this.comentarioid = comentarioid;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPessoaid() {
        return pessoaid;
    }

    public void setPessoaid(int pessoaid) {
        this.pessoaid = pessoaid;
    }

    public int getComentarioDependenteid() {
        return comentarioDependenteid;
    }

    public void setComentarioDependenteid(int comentarioDependenteid) {
        this.comentarioDependenteid = comentarioDependenteid;
    }

    public int getComentadoid() {
        return comentadoid;
    }

    public void setComentadoid(int comentadoid) {
        this.comentadoid = comentadoid;
    }

    public int getModificado() {
        return modificado;
    }

    public void setModificado(int modificado) {
        this.modificado = modificado;
    }

    public String getTipoComentado() {
        return tipoComentado;
    }

    public void setTipoComentado(String tipoComentado) {
        this.tipoComentado = tipoComentado;
    }

}
