package sn.architech.etatcivil.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Centre.class)
public abstract class Centre_ {

	public static volatile SingularAttribute<Centre, String> centreName;
	public static volatile SingularAttribute<Centre, String> centreChief;
	public static volatile SingularAttribute<Centre, Arrondissement> arrondissement;
	public static volatile SingularAttribute<Centre, Long> id;
	public static volatile SetAttribute<Centre, Personne> personnes;

	public static final String CENTRE_NAME = "centreName";
	public static final String CENTRE_CHIEF = "centreChief";
	public static final String ARRONDISSEMENT = "arrondissement";
	public static final String ID = "id";
	public static final String PERSONNES = "personnes";

}

