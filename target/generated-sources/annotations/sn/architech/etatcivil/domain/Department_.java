package sn.architech.etatcivil.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Department.class)
public abstract class Department_ {

	public static volatile SingularAttribute<Department, String> departmentName;
	public static volatile SingularAttribute<Department, Integer> departmentCode;
	public static volatile SingularAttribute<Department, Long> id;
	public static volatile SetAttribute<Department, Commune> communes;
	public static volatile SingularAttribute<Department, Region> region;

	public static final String DEPARTMENT_NAME = "departmentName";
	public static final String DEPARTMENT_CODE = "departmentCode";
	public static final String ID = "id";
	public static final String COMMUNES = "communes";
	public static final String REGION = "region";

}

