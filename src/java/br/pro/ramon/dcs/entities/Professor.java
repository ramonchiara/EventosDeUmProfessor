package br.pro.ramon.dcs.entities;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Professor {

    private Integer id;
    private String nome;
    private String email;
    private String registro;
    private String tipo;

    protected Professor() {
    }

    public Professor(Integer id, String nome, String email, String registro, String tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.registro = registro;
        this.tipo = tipo;
    }

    @XmlAttribute
    public Integer getId() {
        return id;
    }

    @XmlElement
    public String getNome() {
        return nome;
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

    @XmlElement
    public String getRegistro() {
        return registro;
    }

    @XmlElement
    public String getTipo() {
        return tipo;
    }

}
