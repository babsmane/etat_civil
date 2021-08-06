package sn.architech.etatcivil.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sn.architech.etatcivil.domain.enumeration.Sexe;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Personne.class)
public abstract class Personne_ {

	public static volatile SingularAttribute<Personne, String> firstName;
	public static volatile SingularAttribute<Personne, String> lastName;
	public static volatile SingularAttribute<Personne, String> fatherName;
	public static volatile SingularAttribute<Personne, String> motherFirstName;
	public static volatile SingularAttribute<Personne, Long> numberRegister;
	public static volatile SingularAttribute<Personne, Instant> dateOfBirthday;
	public static volatile SingularAttribute<Personne, String> motherLastName;
	public static volatile SingularAttribute<Personne, Centre> centre;
	public static volatile SingularAttribute<Personne, Sexe> sexe;
	public static volatile SingularAttribute<Personne, Long> id;
	public static volatile SingularAttribute<Personne, Instant> hourOfBithday;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String FATHER_NAME = "fatherName";
	public static final String MOTHER_FIRST_NAME = "motherFirstName";
	public static final String NUMBER_REGISTER = "numberRegister";
	public static final String DATE_OF_BIRTHDAY = "dateOfBirthday";
	public static final String MOTHER_LAST_NAME = "motherLastName";
	public static final String CENTRE = "centre";
	public static final String SEXE = "sexe";
	public static final String ID = "id";
	public static final String HOUR_OF_BITHDAY = "hourOfBithday";

}

