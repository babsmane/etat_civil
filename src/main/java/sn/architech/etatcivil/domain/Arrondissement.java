package sn.architech.etatcivil.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Arrondissement.
 */
@Entity
@Table(name = "arrondissement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "arrondissement")
public class Arrondissement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "arrondissement_code")
    private Integer arrondissementCode;

    @NotNull
    @Column(name = "arrondissement_name", nullable = false)
    private String arrondissementName;

    @OneToMany(mappedBy = "arrondissement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "personnes", "arrondissement" }, allowSetters = true)
    private Set<Centre> centres = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "arrondissements", "department" }, allowSetters = true)
    private Commune commune;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Arrondissement id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getArrondissementCode() {
        return this.arrondissementCode;
    }

    public Arrondissement arrondissementCode(Integer arrondissementCode) {
        this.arrondissementCode = arrondissementCode;
        return this;
    }

    public void setArrondissementCode(Integer arrondissementCode) {
        this.arrondissementCode = arrondissementCode;
    }

    public String getArrondissementName() {
        return this.arrondissementName;
    }

    public Arrondissement arrondissementName(String arrondissementName) {
        this.arrondissementName = arrondissementName;
        return this;
    }

    public void setArrondissementName(String arrondissementName) {
        this.arrondissementName = arrondissementName;
    }

    public Set<Centre> getCentres() {
        return this.centres;
    }

    public Arrondissement centres(Set<Centre> centres) {
        this.setCentres(centres);
        return this;
    }

    public Arrondissement addCentre(Centre centre) {
        this.centres.add(centre);
        centre.setArrondissement(this);
        return this;
    }

    public Arrondissement removeCentre(Centre centre) {
        this.centres.remove(centre);
        centre.setArrondissement(null);
        return this;
    }

    public void setCentres(Set<Centre> centres) {
        if (this.centres != null) {
            this.centres.forEach(i -> i.setArrondissement(null));
        }
        if (centres != null) {
            centres.forEach(i -> i.setArrondissement(this));
        }
        this.centres = centres;
    }

    public Commune getCommune() {
        return this.commune;
    }

    public Arrondissement commune(Commune commune) {
        this.setCommune(commune);
        return this;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Arrondissement)) {
            return false;
        }
        return id != null && id.equals(((Arrondissement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Arrondissement{" +
            "id=" + getId() +
            ", arrondissementCode=" + getArrondissementCode() +
            ", arrondissementName='" + getArrondissementName() + "'" +
            "}";
    }
}
