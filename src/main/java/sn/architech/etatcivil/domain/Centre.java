package sn.architech.etatcivil.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Centre.
 */
@Entity
@Table(name = "centre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "centre")
public class Centre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "centre_name")
    private String centreName;

    @Column(name = "centre_chief")
    private String centreChief;

    @OneToMany(mappedBy = "centre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "centre" }, allowSetters = true)
    private Set<Personne> personnes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "centres", "commune" }, allowSetters = true)
    private Arrondissement arrondissement;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Centre id(Long id) {
        this.id = id;
        return this;
    }

    public String getCentreName() {
        return this.centreName;
    }

    public Centre centreName(String centreName) {
        this.centreName = centreName;
        return this;
    }

    public void setCentreName(String centreName) {
        this.centreName = centreName;
    }

    public String getCentreChief() {
        return this.centreChief;
    }

    public Centre centreChief(String centreChief) {
        this.centreChief = centreChief;
        return this;
    }

    public void setCentreChief(String centreChief) {
        this.centreChief = centreChief;
    }

    public Set<Personne> getPersonnes() {
        return this.personnes;
    }

    public Centre personnes(Set<Personne> personnes) {
        this.setPersonnes(personnes);
        return this;
    }

    public Centre addPersonne(Personne personne) {
        this.personnes.add(personne);
        personne.setCentre(this);
        return this;
    }

    public Centre removePersonne(Personne personne) {
        this.personnes.remove(personne);
        personne.setCentre(null);
        return this;
    }

    public void setPersonnes(Set<Personne> personnes) {
        if (this.personnes != null) {
            this.personnes.forEach(i -> i.setCentre(null));
        }
        if (personnes != null) {
            personnes.forEach(i -> i.setCentre(this));
        }
        this.personnes = personnes;
    }

    public Arrondissement getArrondissement() {
        return this.arrondissement;
    }

    public Centre arrondissement(Arrondissement arrondissement) {
        this.setArrondissement(arrondissement);
        return this;
    }

    public void setArrondissement(Arrondissement arrondissement) {
        this.arrondissement = arrondissement;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Centre)) {
            return false;
        }
        return id != null && id.equals(((Centre) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Centre{" +
            "id=" + getId() +
            ", centreName='" + getCentreName() + "'" +
            ", centreChief='" + getCentreChief() + "'" +
            "}";
    }
}
