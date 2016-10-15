package br.pro.ramon.dcs.resources.respostas;

import br.pro.ramon.dcs.entities.Evento;
import java.util.List;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RespostaEventos {

    private Status status;
    private List<Evento> eventos;

    protected RespostaEventos() {
    }

    public RespostaEventos(Status status, List<Evento> eventos) {
        this.status = status;
        this.eventos = eventos;
    }

    @XmlAttribute
    public Integer getStatus() {
        return status.getStatusCode();
    }

    @XmlElement
    public List<Evento> getEventos() {
        return eventos;
    }

}
