package org.contatosapp.repository;

import java.util.List;
import java.util.OptionalLong;

import org.contatosapp.model.definition.BaseModel;
import org.contatosapp.model.definition.CrudRepository;
import org.contatosapp.util.Pair;
import org.contatosapp.util.HibernateHelper;
import javax.persistence.Query;

public class Repository<T extends BaseModel> implements CrudRepository<T> {

    private Class<T> cls;

    public Repository(Class<T> persistentClass) {
        this.cls = persistentClass;
    }

    @Override
    public T save(T t) {

        return HibernateHelper.runInTransaction(em -> {

            if (t.getId() == null) {
                
                em.persist(t);
                
            } else {
                
                em.merge(t);
                
            }

            return t;
        });

    }

    @Override
    public Boolean delete(T t) {

        return HibernateHelper.runInTransaction(em -> {

            em.remove(t);

            return true;
        });

    }

    @Override
    public Boolean delete(Long id) {

        return HibernateHelper.runInTransaction(em -> {

            T t = em.find(cls, id);

            em.remove(t);

            return true;
        });

    }

    @Override
    public T find(Long id) {

        return HibernateHelper.runInSession(em -> {
            
            return em.find(cls, id);
            
        });

    }

    @Override
    public List<T> findAll() {

        return HibernateHelper.runInSession(em -> {
            
            return em.createQuery("from " + cls.getSimpleName()).getResultList();
            
        });

    }

    @Override
    public List<T> executeNamedQuery(String namedQuery, Pair<String, Object>... params) {

        return HibernateHelper.runInSession(em -> {

            Query query = em.createNamedQuery(namedQuery);

            if (params != null && params.length > 0)
                for(Pair<String, Object> pair : params)
                    query.setParameter(pair.getKey(), pair.getValue());

            return query.getResultList();

        });

    }

}
