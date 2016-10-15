package br.pro.ramon.dcs.entities;

import java.util.Date;

public class Evento {

    private Integer id;
    private String descricao;
    private Date data;
    private String tipoEvento;
    private String status;
    private String identificador;

    public Evento(Integer id, String descricao, Date data, String tipoEvento, String status, String identificador) {
        this.id = id;
        this.descricao = descricao;
        this.data = data;
        this.tipoEvento = tipoEvento;
        this.status = status;
        this.identificador = identificador;
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Date getData() {
        return data;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public String getStatus() {
        return status;
    }

    public String getIdentificador() {
        return identificador;
    }

}
