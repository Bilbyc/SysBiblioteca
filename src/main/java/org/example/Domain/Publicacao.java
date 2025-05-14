package org.example.Domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Publicacao")
public class Publicacao {

    @Id
    private int codigoPub;

    @Column(nullable = false)
    private String titulo;
    private int ano;
    private String autor;
    private String tipo;

    @OneToMany(mappedBy = "publicacao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Emprestimo> emprestimos = new ArrayList<>();

    public Publicacao() {
    }

    public Publicacao(int codigoPub, String titulo, int ano, String autor, String tipo) {
        this.codigoPub = codigoPub;
        this.titulo = titulo;
        this.ano = ano;
        this.autor = autor;
        this.tipo = tipo;
    }

    public int getCodigoPub() {
        return codigoPub;
    }

    public void setCodigoPub(int codigoPub) {
        this.codigoPub = codigoPub;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publicacao that = (Publicacao) o;
        return codigoPub == that.codigoPub;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoPub);
    }

    @Override
    public String toString() {
        return "Publicacao{" +
                "codigoPub=" + codigoPub +
                ", titulo='" + titulo + '\'' +
                ", ano=" + ano +
                ", autor='" + autor + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}

