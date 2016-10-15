package br.pro.ramon.dcs.resources.respostas;

import br.pro.ramon.dcs.entities.Professor;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RespostaProfessor {

    private Status status;
    private Professor professor;

    protected RespostaProfessor() {
    }

    public RespostaProfessor(Status status, Professor professor) {
        this.status = status;
        this.professor = professor;
    }

    @XmlAttribute
    public Integer getStatus() {
        return status.getStatusCode();
    }

    @XmlElement
    public String getNome() {
        return professor.getNome();
    }

}
