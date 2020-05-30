package org.contatosapp.model.implementation;

import javax.persistence.*;
import org.contatosapp.model.definition.BaseModel;

@NamedQueries({

    @NamedQuery(
        name = "Email.findAll",
        query =  "FROM Email WHERE contato.id = :id ORDER BY email"
    )

})

/**
 *
 * @author zier
 */
@Entity
@SequenceGenerator(name = "email_seq", allocationSize = 1, sequenceName = "email_seq")
public class Email implements BaseModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_seq")
    private Long id;
    @Column(nullable = false)
    private String email;

    @Transient
    private Boolean edicao = false;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idContato", referencedColumnName = "id")
    private Contato contato;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEdicao() {
        return edicao;
    }

    public void setEdicao(Boolean edicao) {
        this.edicao = edicao;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

}
