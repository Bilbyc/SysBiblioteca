package org.example.Domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Aluno")
public class Aluno {

    @Id
    private int matriculaAluno;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Emprestimo> emprestimos = new ArrayList<>();

    public Aluno() {
    }

    public Aluno(int matriculaAluno, String nome) {
        this.matriculaAluno = matriculaAluno;
        this.nome = nome;
    }

    public int getMatriculaAluno() {
        return matriculaAluno;
    }

    public void setMatriculaAluno(int matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        Aluno aluno = (Aluno) o;
        return matriculaAluno == aluno.matriculaAluno;
    }

    @Override
    public int hashCode() {
        return Objects.hash(matriculaAluno);
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "matriculaAluno=" + matriculaAluno +
                ", nome='" + nome + '\'' +
                '}';
    }
}