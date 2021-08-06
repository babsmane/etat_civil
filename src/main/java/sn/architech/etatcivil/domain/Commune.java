package sn.architech.etatcivil.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Commune.
 */
@Entity
@Table(name = "commune")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "commune")
public class Commune implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "commune_code")
    private Integer communeCode;

    @Column(name = "commune_name")
    private String communeName;

    /**
     * A relationship
     */
    @ApiModelProperty(value = "A relationship")
    @OneToMany(mappedBy = "commune")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "centres", "commune" }, allowSetters = true)
    private Set<Arrondissement> arrondissements = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "communes", "region" }, allowSetters = true)
    private Department department;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Commune id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getCommuneCode() {
        return this.communeCode;
    }

    public Commune communeCode(Integer communeCode) {
        this.communeCode = communeCode;
        return this;
    }

    public void setCommuneCode(Integer communeCode) {
        this.communeCode = communeCode;
    }

    public String getCommuneName() {
        return this.communeName;
    }

    public Commune communeName(String communeName) {
        this.communeName = communeName;
        return this;
    }

    public void setCommuneName(String communeName) {
        this.communeName = communeName;
    }

    public Set<Arrondissement> getArrondissements() {
        return this.arrondissements;
    }

    public Commune arrondissements(Set<Arrondissement> arrondissements) {
        this.setArrondissements(arrondissements);
        return this;
    }

    public Commune addArrondissement(Arrondissement arrondissement) {
        this.arrondissements.add(arrondissement);
        arrondissement.setCommune(this);
        return this;
    }

    public Commune removeArrondissement(Arrondissement arrondissement) {
        this.arrondissements.remove(arrondissement);
        arrondissement.setCommune(null);
        return this;
    }

    public void setArrondissements(Set<Arrondissement> arrondissements) {
        if (this.arrondissements != null) {
            this.arrondissements.forEach(i -> i.setCommune(null));
        }
        if (arrondissements != null) {
            arrondissements.forEach(i -> i.setCommune(this));
        }
        this.arrondissements = arrondissements;
    }

    public Department getDepartment() {
        return this.department;
    }

    public Commune department(Department department) {
        this.setDepartment(department);
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commune)) {
            return false;
        }
        return id != null && id.equals(((Commune) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commune{" +
            "id=" + getId() +
            ", communeCode=" + getCommuneCode() +
            ", communeName='" + getCommuneName() + "'" +
            "}";
    }
}
