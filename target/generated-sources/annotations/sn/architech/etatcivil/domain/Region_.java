package sn.architech.etatcivil.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Region.class)
public abstract class Region_ {

	public static volatile SingularAttribute<Region, Integer> regionCode;
	public static volatile SingularAttribute<Region, String> regionName;
	public static volatile SingularAttribute<Region, Long> id;
	public static volatile SetAttribute<Region, Department> departments;

	public static final String REGION_CODE = "regionCode";
	public static final String REGION_NAME = "regionName";
	public static final String ID = "id";
	public static final String DEPARTMENTS = "departments";

}

