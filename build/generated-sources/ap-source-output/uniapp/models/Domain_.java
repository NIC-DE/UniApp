package uniapp.models;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uniapp.models.University;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-03-08T01:45:39", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Domain.class)
public class Domain_ { 

    public static volatile SingularAttribute<Domain, University> universityid;
    public static volatile SingularAttribute<Domain, String> name;
    public static volatile SingularAttribute<Domain, Integer> domainid;

}