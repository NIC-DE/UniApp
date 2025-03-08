package uniapp.DataBase;


import uniapp.gui.MainForm;
import uniapp.gui.AboutForm;
import uniapp.gui.UniversitySearchForm;


import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UniApp {

    //Η αρχική φόρμα της εφαρμογής 
    public static MainForm mainForm;
    public static UniversitySearchForm universitySearchForm;
    public static final String MY_MEDIA_PATH = System.getProperty("user.dir")+
                                "\\src\\uniapp\\media\\" ;

    //Κυρίως πρόγραμμα
    public static void main(String[] args) {
        // μήνυμα κονσόλας
        System.out.println("Ξεκινά η εφαρμογή...");

        //Δημιουργία των πινάκων της βάσης δεδομένων αν δεν υπάρχουν 
        DatabaseCreation.createDatabaseTables();
        // μήνυμα κονσόλας
        System.out.println("Οι πίνακες της βάσης δεδομένων δημιουργήθηκαν (αν δεν υπήρχαν).");

        //Δημιουργία του entity manager που χειρίζεται τη βάση δεδομένων 
        DatabaseUse.createEntityManager();
        // μήνυμα κονσόλας        
        System.out.println("Entity Manager δημιουργήθηκε.");
        
         // Δημιουργία και εμφάνιση της AboutForm
        AboutForm aboutForm = new AboutForm();
        aboutForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Επιτρέπει το κλείσιμο
        aboutForm.disableExitButton();
        aboutForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                // Όταν κλείσει η AboutForm, δημιουργούμε και εμφανίζουμε την MainForm
                // μήνυμα κονσόλας
                System.out.println("AboutForm έκλεισε. Δημιουργία και εμφάνιση της MainForm...");
                SwingUtilities.invokeLater(() -> {
                    mainForm = new MainForm();
                    universitySearchForm = new UniversitySearchForm();
                    mainForm.setVisible(true);
                });
            }
        });

        aboutForm.setVisible(true);
        // μήνυμα κονσόλας
        System.out.println("Η AboutForm είναι τώρα ορατή.");

    }

}
