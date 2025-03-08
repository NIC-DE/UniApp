package uniapp.gui;

import java.awt.Color;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import uniapp.models.University;
import uniapp.DataBase.UniversityAPIClient;
import uniapp.DataBase.DatabaseUse;
import uniapp.DataBase.UniApp;

//Φόρμα αναζήτησης πανεπιστημίων
public class UniversitySearchForm extends javax.swing.JFrame {

    //Το μοντέλο της λίστας
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private int FORMS_COLOR_R=123;
    private int FORMS_COLOR_G=150;
    private int FORMS_COLOR_B=150;
   
            
    //Constructor
    public UniversitySearchForm() {
        System.out.println("🔍 Εκκίνηση UniversitySearchForm...");
        initComponents();

        //Αρχικοποίηση της φόρμας 
        //Τίτλος φόρμας
        this.setTitle("Εφαρμογή Προβολής Πληροφοριών Πανεπιστημίων: Αναζητήσεις");

        //Συμπεριφορά του Χ του παραθύρου
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //Μέγεθος φόρμας
        this.setSize(1000, 750);

        //Δεν αλλάζει μέγεθος
        this.setResizable(false);

        //Κεντράρισμα/θέση της φόρμας στην οθόνη χρήστη
        setLocationRelativeTo(null);

        //Εικονίδιο εφαρμογής
        String appIcon = (UniApp.MY_MEDIA_PATH + "IconEap.png");
        ImageIcon icon = new ImageIcon(appIcon);
        this.setIconImage(icon.getImage());

        //Χρώμα φόντου
        this.getContentPane().setBackground(
                new Color(FORMS_COLOR_R, FORMS_COLOR_G, FORMS_COLOR_B));

        //επιπλέον μορφοποίηση στην φόρμα
        jTextField1.setText("");
        jTextField2.setText("");
        label3.setVisible(true);
        label3.setText("");
        
        //Ορισμός μοντέλου λίστας
        jList1.setModel(listModel);
        
        System.out.println("✅ Η φόρμα αναζήτησης δημιουργήθηκε επιτυχώς.");
        
        
    }                       //τέλος κατασκευαστή
    
    //Μέθοδος καθαρισμού της τρέχουσας φόρμας
    // καταστρέφει την τρέχουσα και δημιουργεί νέα
    private void closeAndReopen() {
        System.out.println("🔄 Επαναφόρτωση φόρμας αναζήτησης...");
        dispose();
        new UniversitySearchForm().setVisible(true);
    }
        
    //Βρίσκει τα ονόματα πανεπιστημίων της αναζήτησης
    //Επιστρέφει false αν δεν βρεθεί κάποιο αποτέλεσμα ή true αν βρεθεί
    public boolean getUniversities() {
        System.out.println("🔍 Εκτέλεση αναζήτησης πανεπιστημίων...");
        
        //Καθαρίζμός προηγούμενων αποτελεσμάτων 
        listModel.clear();

        //Αν δεν υπάρχουν κριτήρια αναζήτησης, επιστρέφει false
        if (jTextField1.getText().trim().isBlank() 
                && jTextField2.getText().trim().isBlank()) {
            System.out.println("⚠️ Δεν δόθηκαν κριτήρια αναζήτησης.");
            return false;
        }
        
        //Ανάκτηση δεδομένων από API και βάση δεδομένων
        //Βρίσκει όλα τα ονόματα πανεπιστημίων από το API και 
        // από τη βάση δεδομένων που ταιριάζουν στα κριτήρια αναζήτησης
        List<String> namesFromAPI = UniversityAPIClient.getNamesFromAPI(jTextField1.getText().trim(), jTextField2.getText().trim());
        List<String> namesFromDB = DatabaseUse.getNames(jTextField1.getText().trim(), jTextField2.getText().trim());
        List<String> namesAll = new ArrayList<>(namesFromAPI);
        
        for (String n : namesFromDB) {
            if (namesFromAPI.isEmpty() || !namesFromAPI.contains(n)) {
                namesAll.add(n);
            }
        }
        
        //Αν δεν βρεθεί κάποιο αποτέλεσμα επιστρέφει false
        if (namesAll.isEmpty()) {
            System.out.println("❌ Δεν βρέθηκαν πανεπιστήμια με τα δοθέντα κριτήρια.");
            return false;
        }
        
        //Ταξινόμηση και εμφάνιση αποτελεσμάτων
        Collections.sort(namesAll);
        listModel.addAll(namesAll);
        System.out.println("📋 Βρέθηκαν " + namesAll.size() + " πανεπιστήμια.");
        return true;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        label3 = new java.awt.Label();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Αποτελέσματα αναζήτησης", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Unicode MS", 2, 14))); // NOI18N

        jList1.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jList1);

        label3.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        label3.setForeground(new java.awt.Color(0, 0, 255));
        label3.setText("label3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
                    .addComponent(label3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Αναζήτηση με το όνομα ΠΑΝΕΠΙΣΤΗΜΙΟΥ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Unicode MS", 2, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        jLabel1.setText("ΟΝΟΜΑ ΠΑΝΕΠΙΣΤΗΜΙΟΥ");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jTextField1.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        jTextField1.setText("jTextField1");
        jTextField1.setToolTipText("δεν υπάρχει διάκριση σε ΚΕΦ - μικρά");

        jButton4.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        jButton4.setText("ΚΑΘΑΡΙΣΜΟΣ");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(25, 25, 25))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Αναζήτηση με όνομα ΧΩΡΑΣ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Unicode MS", 2, 14))); // NOI18N

        jTextField2.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        jTextField2.setText("jTextField2");
        jTextField2.setToolTipText("ολόκληρο το όνομα της χώρας, όχι μέρος της");

        jLabel2.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        jLabel2.setText("ΧΩΡΑ ΠΑΝΕΠΙΣΤΗΜΙΟΥ");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jButton5.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        jButton5.setText("ΚΑΘΑΡΙΣΜΟΣ");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jButton5)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jTextField2});

        jButton1.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        jButton1.setText("ΑΝΑΖΗΤΗΣΗ");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        jButton2.setText("ΕΠΙΛΟΓΗ & ΠΡΟΒΟΛΗ");
        jButton2.setToolTipText("μετάβαση στην επόμενη οθόνη για περισσότερα στοιχεία");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        jButton3.setText("ΠΙΣΩ");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        jButton6.setText("ΚΑΘΑΡΙΣΜΟΣ ΦΟΡΜΑΣ");
        jButton6.setToolTipText("");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton2, jButton3});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2, jButton3, jButton6});

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //Κλείνει την φόρμα αναζήτησης πανεπιστημίων και εμφανίζει την αρχική φόρμα
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        System.out.println("🔙 Επιστροφή στην αρχική φόρμα.");
        dispose();
        UniApp.mainForm.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    //Επιλογή πανεπιστημίου για προβολή
    //Κλείνει την φόρμα αναζήτησης πανεπιστημίων και εμφανίζει 
    // την φόρμα στοιχείων πανεπιστημίου για το επιλεγμένο πανεπιστήμιο
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         System.out.println("📌 Επιλογή πανεπιστημίου από τη λίστα...");
        //Εμφανίζει κατάλληλο μήνυμα αν δεν έχει επιλεγεί κάποιο πανεπιστήμιο
        String universityName = jList1.getSelectedValue();
        if (universityName == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Δεν έχετε επιλέξει κάποιο πανεπιστήμιο",
                    "Επιλογή πανεπιστημίου",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        System.out.println("✅ Επιλέχθηκε πανεπιστήμιο: " + universityName);
        
        //Ανάκτηση πανεπιστημίου από API ή βάση
        //Αν το όνομα πανεπιστημίου υπάρχει μόνο στο API μας φέρνει το
        // πανεπιστήμιο από το API, ενώ αν το όνομα πανεπιστημίου υπάρχει 
        // και στη βάση δεδομένων, μας το φέρνει από τη βάση
        University u = UniversityAPIClient.getUniversityFromAPI(universityName);
        University universityFromDB = DatabaseUse.getUniversity(universityName);
        if (universityFromDB != null) {
            u = universityFromDB;
        }
        UniversitySelectionForm universityForm = UniApp.mainForm.getUniversityForm();
        universityForm.setUniversity(u);
        universityForm.showUniversityData();

        //Αποθήκευση στη βάση ή ενημέρωση των προβολών
        //Αν δεν είναι αποθηκευμένο στη βάση το αποθηκεύει,
        // διαφορετικά αυξάνει κατά μία τις προβολές του
        if (DatabaseUse.getUniversity(u.getName()) == null) {
            System.out.println("💾 Αποθήκευση πανεπιστημίου στη βάση...");
            DatabaseUse.storeUniversity(u);
        } else {
            System.out.println("📈 Αύξηση προβολών πανεπιστημίου...");
            DatabaseUse.updateUniversityViews(u);
        }

        //Μετάβαση στη φόρμα πανεπιστημίου
        //Κλείνει τη φόρμα αναζήτησης και εμφανίζει τη φόρμα
        // στοιχείων πανεπιστημίου
        this.dispose();
        universityForm.setVisible(true);
     
        
    }//GEN-LAST:event_jButton2ActionPerformed

    //Εμφανίζει τα αποτελέσματα της αναζήτησης
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        boolean resultsFound = getUniversities();
        
        if (!resultsFound) {
            System.out.println("ℹ️ Κανένα πανεπιστήμιο δεν βρέθηκε.");
            label3.setVisible(false);
            JOptionPane.showMessageDialog(
                    this,
                    "Κανένα πανεπιστήμιο δεν βρέθηκε.",
                    "Αναζήτηση πανεπιστημίου",
                    JOptionPane.INFORMATION_MESSAGE
            );
            
        } else{
        // εμφανίζει πόσα πανεπιστήμια βρέθηκαν
        label3.setVisible(true);
        label3.setText("Βρέθηκαν συνολικά " + 
                (String.valueOf(listModel.getSize()))+" εγγραφές");
        System.out.println("✅ Αναζήτηση ολοκληρώθηκε. Βρέθηκαν " + 
                listModel.getSize() + " πανεπιστήμια.");
        }
       
        
    }//GEN-LAST:event_jButton1ActionPerformed
    
    // Καθαρισμός πεδίου ονόματος πανεπιστημίου
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        System.out.println("Καθαρισμός πεδίου ονόματος πανεπιστημίου.");
        this.jTextField1.setText("");
        jButton1.setEnabled(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    // Καθαρισμός πεδίου χώρας πανεπιστημίου
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        System.out.println("Καθαρισμός πεδίου χώρας πανεπιστημίου.");
        this.jTextField2.setText("");
        jButton1.setEnabled(true);
    }//GEN-LAST:event_jButton5ActionPerformed
    //Καλεί την μέθοδο καθαρισμού φόρμας
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        System.out.println("🔄 Καθαρισμός φόρμας και ανανέωση.");
        closeAndReopen(); 
    }//GEN-LAST:event_jButton6ActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private java.awt.Label label3;
    // End of variables declaration//GEN-END:variables
}
