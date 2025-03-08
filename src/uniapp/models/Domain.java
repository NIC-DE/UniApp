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

//POJO κλάση για τον πίνακα Domain

@Entity
@Table(name = "DOMAIN")
@NamedQueries({
    @NamedQuery(name = "Domain.findAll", query = "SELECT d FROM Domain d"),
    @NamedQuery(name = "Domain.findByDomainid", query = "SELECT d FROM Domain d WHERE d.domainid = :domainid"),
    @NamedQuery(name = "Domain.findByName", query = "SELECT d FROM Domain d WHERE d.name = :name")})
public class Domain implements Serializable {

    @JoinColumn(name = "UNIVERSITYID", referencedColumnName = "UNIVERSITYID")
    @ManyToOne(optional = false)
    private University universityid;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DOMAINID")
    private Integer domainid;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;

    public Domain() {
    }

    public Domain(Integer domainid) {
        this.domainid = domainid;
    }

    public Domain(Integer domainid, String name) {
        this.domainid = domainid;
        this.name = name;
    }

    public Integer getDomainid() {
        return domainid;
    }

    public void setDomainid(Integer domainid) {
        this.domainid = domainid;
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
        hash += (domainid != null ? domainid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Domain)) {
            return false;
        }
        Domain other = (Domain) object;
        if ((this.domainid == null && other.domainid != null) || (this.domainid != null && !this.domainid.equals(other.domainid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniapp.Domain[ domainid=" + domainid + " ]";
    }

    public University getUniversityid() {
        return universityid;
    }

    public void setUniversityid(University universityid) {
        this.universityid = universityid;
    }
    
}
