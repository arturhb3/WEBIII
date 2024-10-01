package org.example.livros.models;

import java.time.LocalDate;

public class Livro {

    private Integer Id;
    private String name;
    private String autor;
    private LocalDate dataLancamento;
    private Autor status;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name.toUpperCase();
    }

    public void setName(String name) { this.name = name;}

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }


    public Autor getStatus() {
        return status;
    }

    public void setStatus(Autor status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", email='" + autor + '\'' +
                ", birthDate=" + dataLancamento +
                '}';
    }
}
