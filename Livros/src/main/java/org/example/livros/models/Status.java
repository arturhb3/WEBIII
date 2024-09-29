package org.example.livros.models;

public enum Status {
    EMPRESTADO(1),
    DISPONIVEL(2),
    INDISPONIVEL(3);
    private final int id;


    Status(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public static Status getById(int id) {
        for (Status status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("ID inválido: " + id);
    }
}
