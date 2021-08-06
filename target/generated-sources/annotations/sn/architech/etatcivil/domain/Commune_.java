package sn.architech.etatcivil.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Commune.class)
public abstract class Commune_ {

	public static volatile SingularAttribute<Commune, String> communeName;
	public static volatile SingularAttribute<Commune, Integer> communeCode;
	public static volatile SingularAttribute<Commune, Long> id;
	public static volatile SetAttribute<Commune, Arrondissement> arrondissements;
	public static volatile SingularAttribute<Commune, Department> department;

	public static final String COMMUNE_NAME = "communeName";
	public static final String COMMUNE_CODE = "communeCode";
	public static final String ID = "id";
	public static final String ARRONDISSEMENTS = "arrondissements";
	public static final String DEPARTMENT = "department";

}

