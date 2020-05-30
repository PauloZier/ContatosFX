package org.contatosapp.model.definition;

import org.contatosapp.util.Pair;

import java.util.List;

/**
 *
 * @author zier
 */
public interface CrudRepository<T extends BaseModel> {
        
    T save(T t);
    Boolean delete(T t);
    Boolean delete(Long id);
    T find(Long id);
    List<T> findAll();
    List<T> executeNamedQuery(String namedQuery, Pair<String, Object>... pairs);
}
