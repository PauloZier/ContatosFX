package org.contatosapp.factory;

import org.contatosapp.model.definition.BaseModel;
import org.contatosapp.model.definition.CrudRepository;
import org.contatosapp.repository.Repository;

public class RepositoryFactory {

    private RepositoryFactory() {

    }

    public static <T extends BaseModel> CrudRepository<T> create(Class<T> cls) {
        return new Repository<T>(cls);
    }

}
