package br.pro.ramon.dcs.entities;

public class Professor {

    private Integer id;
    private String nome;
    private String email;
    private String registro;
    private String tipo;

    public Professor(Integer id, String nome, String email, String registro, String tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.registro = registro;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getRegistro() {
        return registro;
    }

    public String getTipo() {
        return tipo;
    }

}
