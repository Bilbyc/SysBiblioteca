package org.example.Dao;

import org.example.Domain.Emprestimo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class EmprestimoDAO {
    
    public Emprestimo findById(Long id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Emprestimo emprestimo = null;
        try {
            emprestimo = em.find(Emprestimo.class, id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar Emprestimo por ID: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar Emprestimo.", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return emprestimo;
    }

    
    public List<Emprestimo> findAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        List<Emprestimo> emprestimos = null;
        try {
            CriteriaQuery<Emprestimo> cq = em.getCriteriaBuilder().createQuery(Emprestimo.class);
            cq.select(cq.from(Emprestimo.class));
            emprestimos = em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar todos os Emprestimos: " + e.getMessage());
            throw new RuntimeException("Erro ao listar Emprestimos.", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return emprestimos;
    }

    
    public void insert(Emprestimo entity) {
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
            System.err.println("Erro ao inserir Emprestimo: " + e.getMessage());
            throw new RuntimeException("Erro ao inserir Emprestimo.", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
    public void update(Emprestimo entity) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            // em.merge irá lidar com entidades desanexadas e suas associações,
            // tentando trazê-las para o contexto de persistência.
            em.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erro ao atualizar Emprestimo: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar Emprestimo.", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
    public void delete(Emprestimo entity) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Emprestimo emprestimoParaRemover = em.find(Emprestimo.class, entity.getId());
            if (emprestimoParaRemover != null) {
                em.remove(emprestimoParaRemover);
            } else {
                System.out.println("Emprestimo não encontrado para remoção com ID: " + entity.getId());
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erro ao remover Emprestimo: " + e.getMessage());
            throw new RuntimeException("Erro ao remover Emprestimo.", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
