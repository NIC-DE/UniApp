package uniapp.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

//POJO κλάση για τον πίνακα Department

@Entity
@Table(name = "DEPARTMENT")
@NamedQueries({
    @NamedQuery(name = "Department.findAll", query = "SELECT d FROM Department d"),
    @NamedQuery(name = "Department.findByDepartmentid", query = "SELECT d FROM Department d WHERE d.departmentid = :departmentid"),
    @NamedQuery(name = "Department.findByName", query = "SELECT d FROM Department d WHERE d.name = :name")})
public class Department implements Serializable {

    @JoinColumn(name = "UNIVERSITYID", referencedColumnName = "UNIVERSITYID")
    @ManyToOne(optional = false)
    private University universityid;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DEPARTMENTID")
    private Integer departmentid;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;

    public Department() {
    }

    public Department(Integer departmentid) {
        this.departmentid = departmentid;
    }

    public Department(Integer departmentid, String name) {
        this.departmentid = departmentid;
        this.name = name;
    }

    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (departmentid != null ? departmentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Department)) {
            return false;
        }
        Department other = (Department) object;
        if ((this.departmentid == null && other.departmentid != null) || (this.departmentid != null && !this.departmentid.equals(other.departmentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniapp.Department[ departmentid=" + departmentid + " ]";
    }

    public University getUniversityid() {
        return universityid;
    }

    public void setUniversityid(University universityid) {
        this.universityid = universityid;
    }
    
}
