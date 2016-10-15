package br.pro.ramon.dcs.resources.respostas;

import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RespostaErro {

    private Status status;
    private String erro;

    protected RespostaErro() {
    }

    public RespostaErro(Status status, String erro) {
        this.status = status;
        this.erro = erro;
    }

    @XmlAttribute
    public Integer getStatus() {
        return status.getStatusCode();
    }

    @XmlElement
    public String getErro() {
        return erro;
    }

}
