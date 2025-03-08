package uniapp.models;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//POJO κλάση για τον πίνακα University

@Entity
@Table(name = "UNIVERSITY")
@NamedQueries({
    @NamedQuery(name = "University.findAll", query = "SELECT u FROM University u"),
    @NamedQuery(name = "University.findByUniversityid", query = "SELECT u FROM University u WHERE u.universityid = :universityid"),
    @NamedQuery(name = "University.findByName", query = "SELECT u FROM University u WHERE u.name = :name"),
    @NamedQuery(name = "University.findByArea", query = "SELECT u FROM University u WHERE u.area = :area"),
    @NamedQuery(name = "University.findByCountry", query = "SELECT u FROM University u WHERE u.country = :country"),
    @NamedQuery(name = "University.findByCountrycode", query = "SELECT u FROM University u WHERE u.countrycode = :countrycode"),
    @NamedQuery(name = "University.findByComments", query = "SELECT u FROM University u WHERE u.comments = :comments"),
    @NamedQuery(name = "University.findByViews", query = "SELECT u FROM University u WHERE u.views = :views"),
    @NamedQuery(name = "University.findByCommunication", query = "SELECT u FROM University u WHERE u.communication = :communication")})
public class University implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universityid")
    private List<Webpage> webpageList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universityid")
    private List<Domain> domainList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universityid")
    private List<Department> departmentList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UNIVERSITYID")
    private Integer universityid;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    @Column(name = "AREA")
    private String area;
    @Basic(optional = false)
    @Column(name = "COUNTRY")
    private String country;
    @Basic(optional = false)
    @Column(name = "COUNTRYCODE")
    private String countrycode;
    @Column(name = "COMMENTS")
    private String comments;
    @Column(name = "VIEWS")
    private Integer views;
    @Column(name = "COMMUNICATION")
    private String communication;

    public University() {
    }

    public University(Integer universityid) {
        this.universityid = universityid;
    }

    public University(Integer universityid, String name, String area, String country, String countrycode, String comments, Integer views, String communication) {
        this.universityid = universityid;
        this.name = name;
        this.area = area;
        this.country = country;
        this.countrycode = countrycode;
        this.comments = comments;
        this.views = views;
        this.communication = communication;
        this.webpageList = new ArrayList<>();
        this.domainList = new ArrayList<>();
        this.departmentList = new ArrayList<>();
    }

    public Integer getUniversityid() {
        return universityid;
    }

    public void setUniversityid(Integer universityid) {
        this.universityid = universityid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }


    public String getCommunication() {
        return communication;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }

    public void addWebpage(Webpage webpage) {
        this.webpageList.add(webpage);
    }
    
    public void addDomain(Domain domain) {
        this.domainList.add(domain);
    }
    
    public void addDepartment(Department department) {
        this.departmentList.add(department);
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (universityid != null ? universityid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof University)) {
            return false;
        }
        University other = (University) object;
        if ((this.universityid == null && other.universityid != null) || (this.universityid != null && !this.universityid.equals(other.universityid))) {
            return false;
        }
        if (this.name.equals(other.name) && this.country.equals(other.country)) {
            return true;
        }        
        return true;
    }

    @Override
    public String toString() {
        return "uniapp.University[ universityid=" + universityid + " ]";
    }

    public List<Webpage> getWebpageList() {
        return webpageList;
    }

    public void setWebpageList(List<Webpage> webpageList) {
        this.webpageList = webpageList;
    }

    public List<Domain> getDomainList() {
        return domainList;
    }

    public void setDomainList(List<Domain> domainList) {
        this.domainList = domainList;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }
    
}
