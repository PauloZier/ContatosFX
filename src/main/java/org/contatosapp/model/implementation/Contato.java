package org.contatosapp.model.implementation;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import org.contatosapp.model.definition.BaseModel;

@NamedQueries({

    @NamedQuery(
        name = "Contato.findAll",
        query = "SELECT new org.contatosapp.model.implementation.Contato(c.id, c.nome) FROM Contato c ORDER BY nome"
    ),

    @NamedQuery(
        name = "Contato.findByName",
        query = "SELECT new org.contatosapp.model.implementation.Contato(c.id, c.nome) FROM Contato c WHERE LOWER(c.nome) LIKE LOWER(:nome) ORDER BY nome"
    )
})

/**
 *
 * @author zier
 */
@Entity
@SequenceGenerator(name="contato_seq", sequenceName = "contato_seq", allocationSize = 1)
public class Contato implements BaseModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contato_seq")
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    private String observacoes;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "contato", orphanRemoval = true)
    private Set<Email> emails;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "contato", orphanRemoval = true)
    private Set<Fone> fones;

    public Contato() {
    }

    public Contato(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Contato(Long id, String nome, String observacoes, Set<Email> emails, Set<Fone> fones) {
        this.id = id;
        this.nome = nome;
        this.observacoes = observacoes;
        this.emails = emails;
        this.fones = fones;
    }
    
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Set<Email> getEmails() {
        if (emails == null)
            emails = new HashSet<>();
            
        return emails;
    }

    public void setEmails(Set<Email> emails) {
        this.emails = emails;
    }

    public Set<Fone> getFones() {
        if (fones == null)
            fones = new HashSet<>();
        
        return fones;
    }

    public void setFones(Set<Fone> fones) {
        this.fones = fones;
    }   
}