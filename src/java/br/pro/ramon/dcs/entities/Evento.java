package br.pro.ramon.dcs.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Evento {

    private Integer id;
    private String descricao;
    private Date data;
    private String tipoEvento;
    private String status;
    private String identificador;

    protected Evento() {
    }

    public Evento(Integer id, String descricao, Date data, String tipoEvento, String status, String identificador) {
        this.id = id;
        this.descricao = descricao;
        this.data = data;
        this.tipoEvento = tipoEvento;
        this.status = status;
        this.identificador = identificador;
    }

    @XmlAttribute
    public Integer getId() {
        return id;
    }

    @XmlElement
    public String getDescricao() {
        return descricao;
    }

    @XmlElement
    public String getData() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(data);
    }

    @XmlElement
    public String getTipoEvento() {
        return tipoEvento;
    }

    @XmlElement

    public String getStatus() {
        return status;
    }

    @XmlElement
    public String getIdentificador() {
        return identificador;
    }

}
