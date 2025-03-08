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

//POJO κλάση για τον πίνακα Webpage

@Entity
@Table(name = "WEBPAGE")
@NamedQueries({
    @NamedQuery(name = "Webpage.findAll", query = "SELECT w FROM Webpage w"),
    @NamedQuery(name = "Webpage.findByWebpageid", query = "SELECT w FROM Webpage w WHERE w.webpageid = :webpageid"),
    @NamedQuery(name = "Webpage.findByUrl", query = "SELECT w FROM Webpage w WHERE w.url = :url")})
public class Webpage implements Serializable {

    @JoinColumn(name = "UNIVERSITYID", referencedColumnName = "UNIVERSITYID")
    @ManyToOne(optional = false)
    private University universityid;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "WEBPAGEID")
    private Integer webpageid;
    @Basic(optional = false)
    @Column(name = "URL")
    private String url;

    public Webpage() {
    }

    public Webpage(Integer webpageid) {
        this.webpageid = webpageid;
    }

    public Webpage(Integer webpageid, String url) {
        this.webpageid = webpageid;
        this.url = url;
    }

    public Integer getWebpageid() {
        return webpageid;
    }

    public void setWebpageid(Integer webpageid) {
        this.webpageid = webpageid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (webpageid != null ? webpageid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Webpage)) {
            return false;
        }
        Webpage other = (Webpage) object;
        if ((this.webpageid == null && other.webpageid != null) || (this.webpageid != null && !this.webpageid.equals(other.webpageid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uniapp.Webpage[ webpageid=" + webpageid + " ]";
    }

    public University getUniversityid() {
        return universityid;
    }

    public void setUniversityid(University universityid) {
        this.universityid = universityid;
    }
    
}
