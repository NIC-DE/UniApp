package uniapp.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//Kλάση για την σύνδεση με την βάση δεδομένων και την δημιουργία των πινάκων εφόσον δεν υπάρχουν
public class DatabaseCreation {

    //Το String για την σύνδεση με τη βάση δεδομένων
    //Η βάση δεδομένων (UniAppDataBase) βρίσκεται μέσα στο φάκελο του project και αν δεν υπάρχει ήδη την δημιουργούμε
    public static final String CONNECTION_STRING
            = "jdbc:derby:UniAppDataBase;user=uni;password=uni;create=true";
   // System.out.println("Το String για την σύνδεση με τη βάση δεδομένων\n jdbc:derby:UniAppDataBase;user=uni;password=uni;create=true");

    // Σταθερές για τη δημιουργία των πινάκων
    //String για τη δημιουργία του πίνακα University
    private static final String CREATE_TABLE_UNIVERSITY = """
        CREATE TABLE university (
            universityId INT NOT NULL GENERATED ALWAYS AS IDENTITY,
            name VARCHAR(255) NOT NULL,
            area VARCHAR(255),
            country VARCHAR(255) NOT NULL,
            countryCode VARCHAR(2) NOT NULL,
            comments VARCHAR(2000),
            views INT,
            communication VARCHAR(255),
            PRIMARY KEY (universityId)
        )""";

    //String για τη δημιουργία του πίνακα Domain
    private static final String CREATE_TABLE_DOMAIN = """
        CREATE TABLE domain (
            domainId INT NOT NULL GENERATED ALWAYS AS IDENTITY,
            name VARCHAR(255) NOT NULL,
            universityId INT NOT NULL,
            FOREIGN KEY (universityId) REFERENCES university (universityId),
            PRIMARY KEY (domainId)
        )""";

    //String για τη δημιουργία του πίνακα Webpage    
    private static final String CREATE_TABLE_WEBPAGE = """
        CREATE TABLE webPage (
            webPageId INT NOT NULL GENERATED ALWAYS AS IDENTITY,
            url VARCHAR(255) NOT NULL,
            universityId INT NOT NULL,
            FOREIGN KEY (universityId) REFERENCES university (universityId),
            PRIMARY KEY (webPageId)
        )""";

    //String για τη δημιουργία του πίνακα Department
    private static final String CREATE_TABLE_DEPARTMENT = """
        CREATE TABLE department (
            departmentId INT NOT NULL GENERATED ALWAYS AS IDENTITY,
            name VARCHAR(255) NOT NULL,
            universityId INT NOT NULL,
            FOREIGN KEY (universityId) REFERENCES university (universityId),
            PRIMARY KEY (departmentId)
        )""";

    //Δημιουργία των πινάκων
    //Δημιουργεί τη βάση δεδομένων και τους πίνακες με for-each
    //Αποφεύγει τέσσερις κλήσεις createTable() ξεχωριστά.
    //Αν χρειαστεί νέο πίνακα, απλά τον προσθέτουμε
    //στο String[] πίνακα tables.
    public static void createDatabaseTables() {
        String[] tables = {CREATE_TABLE_UNIVERSITY, CREATE_TABLE_DOMAIN, CREATE_TABLE_WEBPAGE, CREATE_TABLE_DEPARTMENT};

        for (String tableSQL : tables) {
            createTable(tableSQL);
        }
        System.out.println("Ολοκληρώθηκε η διαδικασία δημιουργίας πινάκων στη βάση δεδομένων.");
    }

    //Δημιουργεί έναν πίνακα με try-with-resources
    
    //Συνδέεται στη βάση δεδομένων και δημιουργεί πίνακα με βάση τον κώδικα
    // που έχει το String createTableSQL (αν ο πίνακας δεν υπάρχει)
    //Ανοίγει/κλείνει τη σύνδεση με την βάση, χρησιμοποιεί try-with-resources
    // για αυτόματη διαχείριση πόρων.
    //Οι κλάσεις Connection, Statement υποστηρίζουν το AutoCloseable ή Closeable
    // και έτσι δεν υπάρχει finally για το κλείσιμο των πόρων. 
    public static void createTable(String createTableSQL) {

        //try-with-resources
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING); Statement statement = connection.createStatement()) {

            statement.executeUpdate(createTableSQL);
            //Προσθήκη logging (εκτύπωση αποτελεσμάτων).
            System.out.println("Table created successfully.");

        } catch (SQLException e) {
            //Τα σφάλματα που δεν σχετίζονται με "table already exists" 
            // εμφανίζονται στην κονσόλα με 
            // System.err.println() και e.printStackTrace().
            
            // Σφάλμα για "table already exists" στη Derby
            if (e.getSQLState().equals("X0Y32")) { 
                System.out.println("Table already exists: " + e.getMessage());
            } else {
                System.err.println("❌ Σφάλμα κατά τη δημιουργία του πίνακα: " + e.getMessage());
                // Εκτυπώνει λεπτομέρειες αν είναι άλλο σφάλμα
                e.printStackTrace();
            }
        }

    }

}
