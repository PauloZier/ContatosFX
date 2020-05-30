package persistenceTests;

import org.contatosapp.factory.RepositoryFactory;
import org.contatosapp.model.definition.CrudRepository;
import org.contatosapp.model.implementation.Contato;
import org.contatosapp.model.implementation.Email;
import org.contatosapp.model.implementation.Fone;
import org.contatosapp.util.PairImpl;
import org.junit.Test;
import java.util.List;

public class CrudTests {

    @Test
    public void contatoTest() {

        CrudRepository<Contato> repository = RepositoryFactory.create(Contato.class);

        repository.findAll().forEach(System.out::println);

        List<Contato> contatos = repository.executeNamedQuery("Contato.findAll");

        Contato contato = repository.find(1L);
    }

    @Test
    public void emailTest() {

        CrudRepository<Email> repository = RepositoryFactory.create(Email.class);

        repository.findAll().forEach(System.out::println);

        repository.executeNamedQuery("Email.findAll", new PairImpl<>("id", 1L));
    }

    @Test
    public void foneTest() {

        CrudRepository<Fone> repository = RepositoryFactory.create(Fone.class);

        repository.findAll().forEach(System.out::println);

        repository.executeNamedQuery("Fone.findAll", new PairImpl<>("id", 1L));
    }

}

