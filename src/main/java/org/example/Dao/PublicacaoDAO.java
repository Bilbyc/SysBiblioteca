package org.example.Dao;

import org.example.Domain.Publicacao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class PublicacaoDAO {
    public Publicacao findById(Integer id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Publicacao publicacao = null;
        try {
            publicacao = em.find(Publicacao.class, id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar Publicacao por ID: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar Publicacao.", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return publicacao;
    }

    
    public List<Publicacao> findAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        List<Publicacao> publicacoes = null;
        try {
            CriteriaQuery<Publicacao> cq = em.getCriteriaBuilder().createQuery(Publicacao.class);
            cq.select(cq.from(Publicacao.class));
            publicacoes = em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar todas as Publicacoes: " + e.getMessage());
            throw new RuntimeException("Erro ao listar Publicacoes.", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return publicacoes;
    }

    
    public void insert(Publicacao entity) {
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
            System.err.println("Erro ao inserir Publicacao: " + e.getMessage());
            throw new RuntimeException("Erro ao inserir Publicacao.", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
    public void update(Publicacao entity) {
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
            System.err.println("Erro ao atualizar Publicacao: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar Publicacao.", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
    public void delete(Publicacao entity) {
        EntityManager em = PersistenceUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Publicacao publicacaoParaRemover = em.find(Publicacao.class, entity.getCodigoPub());
            if (publicacaoParaRemover != null) {
                em.remove(publicacaoParaRemover);
            } else {
                System.out.println("Publicacao não encontrada para remoção com código: " + entity.getCodigoPub());
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erro ao remover Publicacao: " + e.getMessage());
            throw new RuntimeException("Erro ao remover Publicacao.", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}

