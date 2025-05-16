package org.example.Teste;

import org.example.Domain.Aluno;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class TesteJpa {
    public static void main(String[] args) {
        EntityManager entityMgr = Persistence
                .createEntityManagerFactory("bibliotecaPU")
                .createEntityManager();
        System.out.println("Ok Conexao ----- JPA");

        Query query = entityMgr.createQuery("select c from Aluno c", Aluno.class);
        List<Aluno> clientes = query.getResultList();
        System.out.println("Qtde de clientes - " + clientes.size());
        for (Aluno c1 : clientes) {
            System.out.println(c1.getNome() + " 		     ID=" + c1.getMatriculaAluno());
        }
    }
}
