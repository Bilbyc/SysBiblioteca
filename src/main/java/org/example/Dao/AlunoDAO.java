package org.example.Dao;

import org.example.Domain.Aluno;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class AlunoDAO  {

    public Aluno findById(Integer id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Aluno aluno = null;
        try {
            aluno = em.find(Aluno.class, id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar Aluno por ID: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar Aluno.", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return aluno;
    }

    public List<Aluno> findAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        List<Aluno> alunos = null;
        try {
            CriteriaQuery<Aluno> cq = em.getCriteriaBuilder().createQuery(Aluno.class);
            cq.select(cq.from(Aluno.class));
            alunos = em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar todos os Alunos: " + e.getMessage());
            throw new RuntimeException("Erro ao listar Alunos.", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return alunos;
    }

    public void insert(Aluno entity) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erro ao inserir Aluno: " + e.getMessage());
            throw new RuntimeException("Erro ao inserir Aluno.", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void update(Aluno entity) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erro ao atualizar Aluno: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar Aluno.", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void delete(Aluno entity) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            // Garante que a entidade está gerenciada antes de remover
            Aluno alunoParaRemover = em.find(Aluno.class, entity.getMatriculaAluno());
            if (alunoParaRemover != null) {
                em.remove(alunoParaRemover);
            } else {
                System.out.println("Aluno não encontrado para remoção com matrícula: " + entity.getMatriculaAluno());
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erro ao remover Aluno: " + e.getMessage());
            throw new RuntimeException("Erro ao remover Aluno.", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
