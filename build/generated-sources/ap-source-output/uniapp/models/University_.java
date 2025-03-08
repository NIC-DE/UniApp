package uniapp.models;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uniapp.models.Department;
import uniapp.models.Domain;
import uniapp.models.Webpage;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-03-08T01:45:39", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(University.class)
public class University_ { 

    public static volatile ListAttribute<University, Webpage> webpageList;
    public static volatile SingularAttribute<University, String> area;
    public static volatile SingularAttribute<University, Integer> universityid;
    public static volatile SingularAttribute<University, String> country;
    public static volatile SingularAttribute<University, String> comments;
    public static volatile ListAttribute<University, Domain> domainList;
    public static volatile SingularAttribute<University, String> countrycode;
    public static volatile SingularAttribute<University, String> name;
    public static volatile ListAttribute<University, Department> departmentList;
    public static volatile SingularAttribute<University, String> communication;
    public static volatile SingularAttribute<University, Integer> views;

}