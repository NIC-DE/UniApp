package uniapp.DataBase;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import uniapp.models.University;
import uniapp.models.Webpage;
import uniapp.models.Domain;
import uniapp.models.Department;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.NoResultException;
import javax.persistence.Query;


//Κλάση με queries για την διαχείριση της βάσης δεδομένων
public class DatabaseUse {

    //O entity manager για την διαχείριση της βάσης δεδομένων
    public static EntityManager em;

    //Δημιουργεί τον entity manager
    public static void createEntityManager() {
        System.out.println("Δημιουργία EntityManager...");
        em = Persistence.createEntityManagerFactory("UniAppPU").createEntityManager();
        System.out.println("EntityManager δημιουργήθηκε επιτυχώς!");
    }

    //Query που επιστρέφει λίστα πανεπιστημίων με όνομα name και χώρα country
    public static List<University> getUniversities(String name, String country) {
        System.out.println("Αναζήτηση πανεπιστημίων με όνομα: " + name + " και χώρα: " + country);
        Query q = em.createQuery("SELECT u FROM University u WHERE LOWER(u.name) LIKE LOWER(:name) AND LOWER(u.country) LIKE LOWER(:country)", University.class);
        q.setParameter("name", "%" + name + "%");
        q.setParameter("country", "%" + country + "%");
        List<University> universities = q.getResultList();
        return universities;
    }

    //Query που επιστρέφει λίστα ονομάτων πανεπιστημίων με όνομα name και χώρα country
    public static List<String> getNames(String name, String country) {
        List<University> universities = getUniversities(name, country);
        List<String> names = new ArrayList<>();
        for (University u : universities) {
            names.add(u.getName());
        }
        return names;
    }

    //Query που επιστρέφει το πανεπιστήμιο με όνομα name
    public static University getUniversity(String name) {
        System.out.println("Αναζήτηση πανεπιστημίου με όνομα: " + name);
        Query q = em.createNamedQuery("University.findByName", University.class);
        q.setParameter("name", name);
        try {
            University university = (University) q.getSingleResult();
            System.out.println("Το πανεπιστήμιο βρέθηκε: " + university.getName());
            return university;
        } catch (NoResultException e) {
            return null;
        }
    }

    //Query που επιστρέφει όλα τα πανεπιστήμια ταξινομημένα 
    // με φθίνουσα διάταξη ως προς τις προβολές τους
    // και αύξουσα διάταξη ως προς το όνομα τους 
    // για όσα πανεπιστήμια έχουν τον ίδιο αριθμό προβολών
    
    public static List<University> getUniversitiesOrderedByViews() {
        Query q = em.createQuery("select u from University u order by u.views desc, u.name");
        List<University> universities = q.getResultList();
        return universities;
    }
       
    //Query που αποθηκεύει ένα πανεπιστήμιο και τα domains, webpages και τμήματα που έχει
    public static void storeUniversity(University university) {
        System.out.println("Αποθήκευση πανεπιστημίου: " + university.getName());
        em.getTransaction().begin();
        em.persist(university);
        for (Domain d : university.getDomainList()) {
            em.persist(d);
        }
        for (Webpage w : university.getWebpageList()) {
            em.persist(w);
        }
        for (Department d : university.getDepartmentList()) {
            em.persist(d);
        }
        em.getTransaction().commit();
        System.out.println("✅ Το πανεπιστήμιο αποθηκεύτηκε επιτυχώς!");
        updateUniversityViews(university);
    }

    //Query που αποθηκεύει ένα domain
    public static void storeDomain(Domain domain) {
        em.getTransaction().begin();
        em.persist(domain);
        em.getTransaction().commit();
    }

    //Query που αποθηκεύει ένα webpage
    public static void storeWebpage(Webpage webpage) {
        em.getTransaction().begin();
        em.persist(webpage);
        em.getTransaction().commit();
    }

    //Query που αποθηκεύει ένα τμήμα
    public static void storeDepartment(Department department) {
        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();
    }

    //Query που τροποποιεί τα στοιχεία ενός πανεπιστημίου
    public static void updateUniversityData(University university) {
        System.out.println("Ενημέρωση στοιχείων για το πανεπιστήμιο: "
                            + university.getName());
        em.getTransaction().begin();
        String queryString
                = "update University u set "
                + "u.name = :p1, "
                + "u.area = :p2, "
                + "u.country = :p3, "
                + "u.countrycode = :p4, "
                + "u.comments = :p5, "
                + "u.communication = :p7 "
                + "where u.universityid = :p8";
        Query q = em.createQuery(queryString);
        q.setParameter("p1", university.getName());
        q.setParameter("p2", university.getArea());
        q.setParameter("p3", university.getCountry());
        q.setParameter("p4", university.getCountrycode());
        q.setParameter("p5", university.getComments());
        q.setParameter("p7", university.getCommunication());
        q.setParameter("p8", university.getUniversityid());
        q.executeUpdate();
        em.getTransaction().commit();
        System.out.println("✅ Τα στοιχεία του πανεπιστημίου" 
                            + " ενημερώθηκαν επιτυχώς!");
    }

    //Αυξάνει κατά ένα το πλήθος προβολών ενός πανεπιστημίου
    public static void updateUniversityViews(University university) {
        System.out.println("Αύξηση προβολών για το πανεπιστήμιο: " 
                + university.getName());
        em.getTransaction().begin();
        university.setViews(university.getViews() + 1);
        em.getTransaction().commit();
        System.out.println("✅ Οι προβολές ενημερώθηκαν σε: "
                                + university.getViews());
    }

    //Query που τροποποιεί το όνομα ενός domain
    public static void updateDomain(Domain domain, String newName) {
        em.getTransaction().begin();
        Query q = em.createQuery("update Domain d set d.name = :p1 where d.domainid = :p2");
        q.setParameter("p1", newName);
        q.setParameter("p2", domain.getDomainid());
        q.executeUpdate();
        em.getTransaction().commit();
    }

    //Query που τροποποιεί το url ενός webpage
    public static void updateWebpage(Webpage webpage, String newUrl) {
        em.getTransaction().begin();
        Query q = em.createQuery("update Webpage w set w.url = :p1 where w.webpageid = :p2");
        q.setParameter("p1", newUrl);
        q.setParameter("p2", webpage.getWebpageid());
        q.executeUpdate();
        em.getTransaction().commit();
    }

    //Query που τροποποιεί το όνομα ενός τμήματος
    public static void updateDepartment(Department department, String newName) {
        em.getTransaction().begin();
        Query q = em.createQuery("update Department d set d.name = :p1 where d.departmentid = :p2");
        q.setParameter("p1", newName);
        q.setParameter("p2", department.getDepartmentid());
        q.executeUpdate();
        em.getTransaction().commit();
    }

    //Query που διαγράφει ένα domain
    public static void deleteDomain(Domain domain) {
        System.out.println("Διαγραφή domain: " + domain.getName());
        em.getTransaction().begin();
        Query q = em.createQuery("delete from Domain where d.domainid = :p");
        q.setParameter("p", domain.getDomainid());
        q.executeUpdate();
        em.getTransaction().commit();
        System.out.println("✅ Το domain διαγράφηκε επιτυχώς!");
    }

    //Query που διαγράφει ένα webpage
    public static void deleteWebpage(Webpage webpage) {
        System.out.println("Διαγραφή ιστοσελίδας: " + webpage.getUrl());
        em.getTransaction().begin();
        Query q = em.createQuery("delete from Webpage where w.webpageid = :p");
        q.setParameter("p", webpage.getWebpageid());
        q.executeUpdate();
        em.getTransaction().commit();
        System.out.println("✅ Η ιστοσελίδα διαγράφηκε επιτυχώς!");
    }

    //Query που διαγράφει ένα τμήμα
    public static void deleteDepartment(Department department) {
        System.out.println("Διαγραφή τμήματος: " + department.getName());
        em.getTransaction().begin();
        Query q = em.createQuery("delete from Department where d.departmentid = :p");
        q.setParameter("p", department.getDepartmentid());
        q.executeUpdate();
        em.getTransaction().commit();
        System.out.println("✅ Το τμήμα διαγράφηκε επιτυχώς!");
    }

}
