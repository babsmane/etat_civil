package sn.architech.etatcivil.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;
import sn.architech.etatcivil.domain.enumeration.Sexe;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "personne")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "personne")
public class Personne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "father_name")
    private String fatherName;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe")
    private Sexe sexe;

    @Column(name = "mother_first_name")
    private String motherFirstName;

    @Column(name = "mother_last_name")
    private String motherLastName;

    @Column(name = "date_of_birthday")
    private Instant dateOfBirthday;

    @Column(name = "hour_of_bithday")
    private Instant hourOfBithday;

    @Column(name = "number_register")
    private Long numberRegister;

    @ManyToOne
    @JsonIgnoreProperties(value = { "personnes", "arrondissement" }, allowSetters = true)
    private Centre centre;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Personne id(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Personne firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Personne lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherName() {
        return this.fatherName;
    }

    public Personne fatherName(String fatherName) {
        this.fatherName = fatherName;
        return this;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public Personne sexe(Sexe sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getMotherFirstName() {
        return this.motherFirstName;
    }

    public Personne motherFirstName(String motherFirstName) {
        this.motherFirstName = motherFirstName;
        return this;
    }

    public void setMotherFirstName(String motherFirstName) {
        this.motherFirstName = motherFirstName;
    }

    public String getMotherLastName() {
        return this.motherLastName;
    }

    public Personne motherLastName(String motherLastName) {
        this.motherLastName = motherLastName;
        return this;
    }

    public void setMotherLastName(String motherLastName) {
        this.motherLastName = motherLastName;
    }

    public Instant getDateOfBirthday() {
        return this.dateOfBirthday;
    }

    public Personne dateOfBirthday(Instant dateOfBirthday) {
        this.dateOfBirthday = dateOfBirthday;
        return this;
    }

    public void setDateOfBirthday(Instant dateOfBirthday) {
        this.dateOfBirthday = dateOfBirthday;
    }

    public Instant getHourOfBithday() {
        return this.hourOfBithday;
    }

    public Personne hourOfBithday(Instant hourOfBithday) {
        this.hourOfBithday = hourOfBithday;
        return this;
    }

    public void setHourOfBithday(Instant hourOfBithday) {
        this.hourOfBithday = hourOfBithday;
    }

    public Long getNumberRegister() {
        return this.numberRegister;
    }

    public Personne numberRegister(Long numberRegister) {
        this.numberRegister = numberRegister;
        return this;
    }

    public void setNumberRegister(Long numberRegister) {
        this.numberRegister = numberRegister;
    }

    public Centre getCentre() {
        return this.centre;
    }

    public Personne centre(Centre centre) {
        this.setCentre(centre);
        return this;
    }

    public void setCentre(Centre centre) {
        this.centre = centre;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Personne)) {
            return false;
        }
        return id != null && id.equals(((Personne) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Personne{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", fatherName='" + getFatherName() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", motherFirstName='" + getMotherFirstName() + "'" +
            ", motherLastName='" + getMotherLastName() + "'" +
            ", dateOfBirthday='" + getDateOfBirthday() + "'" +
            ", hourOfBithday='" + getHourOfBithday() + "'" +
            ", numberRegister=" + getNumberRegister() +
            "}";
    }
}
