package org.contatosapp.model.implementation;

import javax.persistence.*;

import org.contatosapp.model.definition.BaseModel;
import org.contatosapp.util.TextUtils;

import java.util.Objects;

@NamedQueries({

    @NamedQuery(
        name = "Fone.findAll",
        query = "FROM Fone WHERE contato.id = :id ORDER BY numero"
    )

})

/**
 *
 * @author zier
 */
@Entity
@SequenceGenerator(name="fone_seq", sequenceName = "fone_seq", allocationSize = 1)
public class Fone implements BaseModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fone_seq")
    private Long id;
    @Column(nullable = false)
    private String numero;
    @Column(nullable = false)
    private String tipo;

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
    
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public Boolean getEdicao() {
        return edicao;
    }

    public void setEdicao(Boolean edicao) {
        this.edicao = edicao;
    }

    public String getNumeroProxy() {
        return TextUtils.maskFone(this.numero);
    }

    public void setNumeroProxy(String numeroProxy) {
        this.numero = TextUtils.getOnlyNumbers(numeroProxy);
    }
}
