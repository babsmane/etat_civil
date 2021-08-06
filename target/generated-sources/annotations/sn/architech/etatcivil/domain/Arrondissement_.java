package sn.architech.etatcivil.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Arrondissement.class)
public abstract class Arrondissement_ {

	public static volatile SingularAttribute<Arrondissement, Commune> commune;
	public static volatile SingularAttribute<Arrondissement, Integer> arrondissementCode;
	public static volatile SingularAttribute<Arrondissement, Long> id;
	public static volatile SingularAttribute<Arrondissement, String> arrondissementName;
	public static volatile SetAttribute<Arrondissement, Centre> centres;

	public static final String COMMUNE = "commune";
	public static final String ARRONDISSEMENT_CODE = "arrondissementCode";
	public static final String ID = "id";
	public static final String ARRONDISSEMENT_NAME = "arrondissementName";
	public static final String CENTRES = "centres";

}

