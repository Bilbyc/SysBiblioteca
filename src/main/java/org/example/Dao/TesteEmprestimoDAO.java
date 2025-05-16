package org.example.Dao;

import org.example.Domain.Aluno;
import org.example.Domain.Emprestimo;
import org.example.Domain.Publicacao;
import org.h2.tools.Server;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.SQLException;
import java.util.*;

public class TesteEmprestimoDAO {

    public static void main(String[] args) {
        Server webServer = null;

        List<Aluno> alunosCriados = new ArrayList<>();
        List<Publicacao> publicacoesCriadas = new ArrayList<>();
        List<Emprestimo> emprestimosSalvos = new ArrayList<>();

        try {
            PersistenceUtil.getEntityManagerFactory();
            webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8084");
            webServer.start();
            System.out.println("H2 Console (EmprestimoDAOTeste) iniciado: http://localhost:8084");

            System.out.println("JDBC URL para H2 Console/Ferramenta SQL: jdbc:h2:mem:bibliotecadb ou jdbc:h2:tcp://localhost:9094/mem:bibliotecadb");
            System.out.println("Usuário: sa, Senha: <em branco>");

            limparTabelas();

            AlunoDAO alunoDAO = new AlunoDAO();
            PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
            EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

            System.out.println("\n--- GERANDO DADOS DE ALUNOS ---");
            for (int i = 1; i <= 5; i++) {
                Aluno aluno = new Aluno(300 + i, "Aluno teste " + i);
                alunoDAO.insert(aluno);
                alunosCriados.add(aluno);
            }
            System.out.println(alunosCriados.size() + " alunos criados.");

            String[] tipos = {"Livro Didático", "Revista Científica", "Artigo Conferência", "Tese Doutorado", "Manual Técnico"};
            for (int i = 1; i <= 5; i++) {
                Publicacao pub = new Publicacao(400 + i, tipos[i-1] + " Essencial " + i, 2020 + i, "Autor Conhecido " + i, tipos[i-1]);
                publicacaoDAO.insert(pub);
                publicacoesCriadas.add(pub);
            }
            System.out.println(publicacoesCriadas.size() + " publicações criadas.");

            System.out.println("\n--- TESTES EMPRESTIMODAO ---");

            System.out.println("\n1. Teste INSERT Emprestimo");
            Emprestimo emp1 = new Emprestimo(criarData(2025, 5, 10), null, alunosCriados.get(0), publicacoesCriadas.get(0));
            Emprestimo emp2 = new Emprestimo(criarData(2025, 5, 12), null, alunosCriados.get(1), publicacoesCriadas.get(2));
            emprestimoDAO.insert(emp1);
            emprestimosSalvos.add(emp1);
            System.out.println("Emprestimo 1 inserido, ID: " + emp1.getId());
            emprestimoDAO.insert(emp2);
            emprestimosSalvos.add(emp2);
            System.out.println("Emprestimo 2 inserido, ID: " + emp2.getId());

            System.out.println("\n2. Teste FIND_BY_ID Emprestimo");
            if (emp1.getId() != null) {
                Emprestimo encontrado = emprestimoDAO.findById(emp1.getId());
                System.out.println("Emprestimo ID " + emp1.getId() + " encontrado: " + (encontrado != null && encontrado.getId().equals(emp1.getId())));
            }

            System.out.println("\n3. Teste FIND_ALL Emprestimos");
            List<Emprestimo> todosEmprestimos = emprestimoDAO.findAll();
            System.out.println("Total de empréstimos encontrados: " + todosEmprestimos.size());
            for(Emprestimo e : todosEmprestimos) {
                System.out.println(" > ID: " + e.getId() + ", Aluno: " + e.getAluno().getMatriculaAluno() + ", Publicação: " + e.getPublicacao().getCodigoPub());
            }

            System.out.println("\n4. Teste UPDATE Emprestimo");
            if (!emprestimosSalvos.isEmpty()) {
                Emprestimo paraAtualizar = emprestimoDAO.findById(emprestimosSalvos.get(0).getId());
                if (paraAtualizar != null) {
                    System.out.println("Original dataDevolucao: " + paraAtualizar.getDataDevolucao());
                    paraAtualizar.setDataDevolucao(criarData(2025, 5, 20));
                    paraAtualizar.setAluno(alunosCriados.get(2));
                    emprestimoDAO.update(paraAtualizar);
                    System.out.println("Emprestimo ID " + paraAtualizar.getId() + " atualizado.");
                    Emprestimo atualizado = emprestimoDAO.findById(paraAtualizar.getId());
                    System.out.println("Nova dataDevolucao: " + atualizado.getDataDevolucao());
                    System.out.println("Novo Aluno ID: " + atualizado.getAluno().getMatriculaAluno());
                }
            }

            System.out.println("\n5. Teste DELETE Emprestimo");
            if (emprestimosSalvos.size() > 1) {
                Emprestimo paraDeletar = emprestimoDAO.findById(emprestimosSalvos.get(1).getId());
                if (paraDeletar != null) {
                    emprestimoDAO.delete(paraDeletar);
                    System.out.println("Emprestimo ID " + paraDeletar.getId() + " deletado.");
                }
            }

            System.out.println("\n--- Lista Final de Empréstimos ---");
            List<Emprestimo> finalEmprestimos = emprestimoDAO.findAll();
            System.out.println("Total de empréstimos final: " + finalEmprestimos.size());
            for(Emprestimo e : finalEmprestimos) {
                System.out.println(" > ID: " + e.getId() + ", Aluno: " + e.getAluno().getMatriculaAluno() + ", Publicação: " + e.getPublicacao().getCodigoPub());
            }


            System.out.println("\n--- TESTES CONCLUÍDOS ---");
            System.out.println("Pressione Enter para finalizar a aplicação e parar os servidores H2...");
            new Scanner(System.in).nextLine();

        } catch (SQLException e) {
            System.err.println("Erro SQL (H2 Servers): " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro geral na aplicação: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Finalizando...");
            if (webServer != null) webServer.stop();
            System.out.println("Aplicação finalizada.");
        }
    }

    private static void limparTabelas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("DELETE FROM Emprestimo").executeUpdate();
            em.createQuery("DELETE FROM Aluno").executeUpdate();
            em.createQuery("DELETE FROM Publicacao").executeUpdate();
            tx.commit();
            System.out.println("Tabelas limpas.");
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Falha ao limpar tabelas: " + e.getMessage());
        } finally {
            if (em != null) em.close();
        }
    }

    private static Date criarData(int ano, int mes, int dia) {
        Calendar cal = Calendar.getInstance();
        cal.set(ano, mes - 1, dia, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
