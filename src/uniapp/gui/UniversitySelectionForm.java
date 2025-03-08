package uniapp.gui;

import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import uniapp.models.Department;
import uniapp.models.Domain;
import uniapp.models.University;
import uniapp.models.Webpage;
import uniapp.DataBase.DatabaseUse;
import uniapp.DataBase.UniApp;

//Φόρμα στοιχείων πανεπιστημίου
public class UniversitySelectionForm extends javax.swing.JFrame {

    //Μέγιστα όρια συμβολοσειρών για τα στοιχεία του πανεπιστημίου
    public static int MAX_UNIVERSITY_NAME = 255;
    public static int MAX_UNIVERSITY_AREA = 255;
    public static int MAX_UNIVERSITY_COUNTRY = 255;
    public static int MAX_UNIVERSITY_COUNTRY_CODE = 2;
    public static int MAX_UNIVERSITY_COMMENTS = 2000;
    public static int MAX_UNIVERSITY_COMMUNICATION = 255;
    public static int MAX_WEBPAGE_URL = 255;
    public static int MAX_DOMAIN_NAME = 255;
    public static int MAX_DEPARTMENT_NAME = 255;

    //οι κωδικοί του χρώματος της φόρμας
    private int FORMS_COLOR_R=123;
    private int FORMS_COLOR_G=150;
    private int FORMS_COLOR_B=150;
    
    //Το πανεπιστήμιο, τα αρχικά δεδομένα, έχει τροποποιηθεί ή όχι
    private University university;
    private University original;
    private boolean modified;

    //Τα μοντέλα των λιστών
    private DefaultListModel<String> listModel1 = new DefaultListModel<>();
    private DefaultListModel<String> listModel2 = new DefaultListModel<>();
    private DefaultListModel<String> listModel3 = new DefaultListModel<>();

    //Constructor
    public UniversitySelectionForm() {
        System.out.println("🖥️ Εκκίνηση UniversitySelectionForm...");
        initComponents();
        this.setTitle("Προβολή στοιχείων του επιλεγμένου πανεπιστημίου");

        //Συμπεριφορά του Χ του παραθύρου
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //Δεν αλλάζει μέγεθος η φόρμα
        this.setResizable(false);

        //Κεντράρισμα της φόρμας
        setLocationRelativeTo(null);
        
        //Εικονίδιο εφαρμογής
        String appIcon = (UniApp.MY_MEDIA_PATH + "IconEap.png");
        ImageIcon icon = new ImageIcon(appIcon);
        this.setIconImage(icon.getImage());

        //Χρώμα της φόρμας
        this.getContentPane().setBackground(new Color(FORMS_COLOR_R, FORMS_COLOR_G, FORMS_COLOR_B));
       
        //Κεντράρισμα της φόρμας
        setLocationRelativeTo(null);

        //Η φόρμα δεν μπορεί να αλλάξει μέγεθος
        setResizable(false);

        //Η φόρμα δεν μπορεί να κλείσει ώστε να μην κλείνει η εφαρμογή
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //Θέτουμε τα μοντέλα στις λίστες
        jList1.setModel(listModel1);
        jList2.setModel(listModel2);
        jList3.setModel(listModel3);
        System.out.println("✅ Η φόρμα δημιουργήθηκε επιτυχώς.");
    }

    //Θέτει το επιλεγμένο πανεπίστημιο και κρατάει και τα αρχικά του δεδομένα
    public void setUniversity(University university) {
        this.university = university;
        modified = false;
        this.original = new University(
                0,
                university.getName(),
                university.getArea(),
                university.getCountry(),
                university.getCountrycode(),
                university.getComments(),
                university.getViews(),                
                university.getCommunication());
        original.setDomainList(university.getDomainList());
        original.setWebpageList(university.getWebpageList());
        original.setDepartmentList(original.getDepartmentList());
    }

    //Εμφανίζει στη φόρμα τα στοιχεία του επιλεγμένου πανεπιστημίου
    public void showUniversityData() {
        System.out.println("📌 Εμφάνιση δεδομένων πανεπιστημίου: " 
                            + university.getName());
        jTextField1.setText(university.getName());
        jTextField2.setText(university.getArea());
        jTextField3.setText(university.getCountry());
        jTextField4.setText(university.getCountrycode());
        jTextArea1.setText(university.getCommunication());
        jTextArea3.setText(university.getComments());
        showDomainList();
        showWebpageList();
        showDepartmentList();
    }

    //Εμφανίζει την λίστα των domains
    public void showDomainList() {
        listModel1.clear();
        for (Domain d : university.getDomainList()) {
            listModel1.addElement(d.getName());
        }
        jButton2.setEnabled(!listModel1.isEmpty());
        jButton3.setEnabled(!listModel1.isEmpty());
        System.out.println("🌐 Ενημέρωση λίστας domains: " 
                + listModel1.getSize());
    }

    //Εμφανίζει την λίστα των web pages
    public void showWebpageList() {
        listModel2.clear();
        for (Webpage w : university.getWebpageList()) {
            listModel2.addElement(w.getUrl());
        }
        jButton5.setEnabled(!listModel2.isEmpty());
        jButton6.setEnabled(!listModel2.isEmpty());
        System.out.println("📄 Ενημέρωση λίστας ιστοσελίδων: " 
                                    + listModel2.getSize());
    }

    //Εμφανίζει την λίστα των τμημάτων
    public void showDepartmentList() {
        listModel3.clear();
        for (Department d : university.getDepartmentList()) {
            listModel3.addElement(d.getName());
        }
        jButton8.setEnabled(!listModel3.isEmpty());
        jButton9.setEnabled(!listModel3.isEmpty());
        System.out.println("🏛️ Ενημέρωση λίστας τμημάτων: " 
                            + listModel3.getSize());
     
    }

    //Παίρνει τα στοιχεία από τη φόρμα και ενημερώνει το επιλεγμένο πανεπιστήμιο
    //Κάνει έλεγχο αν τα στοιχεία είναι έγκυρα και βρίσκει αν έχουν τροποποιηθεί
    public String getFormData() {
        modified = false;
        String errorMessage = "";
        if (jTextField1.getText().length() > MAX_UNIVERSITY_NAME) {
            errorMessage += "Το όνομα του πανεπιστημίου πρέπει να είναι μέχρι " + MAX_UNIVERSITY_NAME + " χαρακτήρες\n";
        }
        university.setName(jTextField1.getText());
        if (!university.getName().equals(original.getName())) {
            if (DatabaseUse.getUniversity(university.getName()) != null) {
                errorMessage += "Υπάρχει ήδη αποθηκευμένο πανεπιστήμιο με το ίδιο όνομα. Επιλέξτε διαφορετικό όνομα\n";
            }
            modified = true;
        }
        if (jTextField2.getText().length() > MAX_UNIVERSITY_AREA) {
            errorMessage += "Η περιοχή του πανεπιστημίου πρέπει να είναι μέχρι " + MAX_UNIVERSITY_AREA + " χαρακτήρες\n";
        }
        university.setArea(jTextField2.getText());
        if (!university.getArea().equals(original.getArea())) {
            modified = true;
        }
        if (jTextField3.getText().length() > MAX_UNIVERSITY_COUNTRY) {
            errorMessage += "Η χώρα του πανεπιστημίου πρέπει να είναι μέχρι " + MAX_UNIVERSITY_COUNTRY + " χαρακτήρες\n";
        }
        university.setCountry(jTextField3.getText());
        if (!university.getCountry().equals(original.getCountry())) {
            modified = true;
        }
        if (jTextField4.getText().length() > MAX_UNIVERSITY_COUNTRY_CODE) {
            errorMessage += "O κωδικός χώρας του πανεπιστημίου πρέπει να είναι μέχρι " + MAX_UNIVERSITY_COUNTRY_CODE + " χαρακτήρες\n";
        }
        university.setCountrycode(jTextField4.getText());
        if (!university.getCountrycode().equals(original.getCountrycode())) {
            modified = true;
        }
        if (jTextArea1.getText().length() > MAX_UNIVERSITY_COMMUNICATION) {
            errorMessage += "Η επικοινωνία του πανεπιστημίου πρέπει να είναι μέχρι " + MAX_UNIVERSITY_COMMUNICATION + " χαρακτήρες\n";
        }
        university.setCommunication(jTextArea1.getText());
        if (!university.getCommunication().equals(original.getCommunication())) {
            modified = true;
        }

        if (jTextArea3.getText().length() > MAX_UNIVERSITY_COMMENTS) {
            errorMessage += "Τα σχόλια του πανεπιστημίου πρέπει να είναι μέχρι " + MAX_UNIVERSITY_COMMENTS + " χαρακτήρες\n";
        }
        university.setComments(jTextArea3.getText());
        if (!university.getComments().equals(original.getComments())) {
            modified = true;
        }
        return errorMessage;
    }

    //Επαναφέρει τα αρχικά στοιχεία στο επιλεγμένο πανεπιστήμιο
    public void revertOriginal() {
        university.setName(original.getName());
        university.setArea(original.getArea());
        university.setCountry(original.getCountry());
        university.setCountrycode(original.getCountrycode());
        university.setDomainList(original.getDomainList());
        university.setWebpageList(original.getWebpageList());
        university.setDepartmentList(original.getDepartmentList());
        university.setCommunication(original.getCommunication());
        university.setComments(original.getComments());
    }

    //Ενημερώνει τα στοιχεία του επιλεγμένου πανεπιστημίου
    public void updateUniversity() {
        String errorMessage = getFormData();
        if (errorMessage.isEmpty()) {
            DatabaseUse.updateUniversityData(university);
            setUniversity(university);
            JOptionPane.showMessageDialog(
                    this,
                    "Τα στοιχεία του πανεπιστημίου αποθηκεύτηκαν με επιτυχία",
                    "Αποθήκευση Πανεπιστημίου",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    errorMessage,
                    "Πρόβλημα στην αποθήκευση του πανεπιστημίου",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList<>();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jButton11 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Βασικά πληροφοριακά στοιχεία"));

        jLabel1.setFont(new java.awt.Font("Arial Unicode MS", 1, 14)); // NOI18N
        jLabel1.setText("Όνομα πανεπιστημίου");

        jTextField1.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jTextField1.setText("jTextField1");

        jLabel2.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jLabel2.setText("Περιοχή");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jTextField2.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jTextField2.setText("jTextField2");

        jLabel3.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jLabel3.setText("Χώρα ");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jTextField3.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jTextField3.setText("jTextField3");

        jLabel4.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jLabel4.setText("Κωδικός Χώρας");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jTextField4.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jTextField4.setText("jTextField4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(27, 27, 27))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel3, jLabel4});

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Στοιχεία σε πίνακες"));

        jLabel5.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jLabel5.setText("Domain names ");

        jList1.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.setToolTipText("Εγγραφές σε πίνακα, μία κάθε φορά");
        jScrollPane1.setViewportView(jList1);

        jLabel6.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jLabel6.setText("Web pages ");

        jList2.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList2.setToolTipText("Εγγραφές σε πίνακα, μία κάθε φορά");
        jScrollPane2.setViewportView(jList2);

        jLabel7.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jLabel7.setText("Τμήματα ");

        jList3.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jList3.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList3.setToolTipText("Εγγραφές σε πίνακα, μία κάθε φορά");
        jScrollPane3.setViewportView(jList3);

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jButton3.setText("ΔΙΑΓΡΑΦΗ");
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jButton2.setText("ΤΡΟΠΟΠΟΙΗΣΗ");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jButton1.setText("ΠΡΟΣΘΗΚΗ");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jButton4.setText("ΠΡΟΣΘΗΚΗ");
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jButton5.setText("ΤΡΟΠΟΠΟΙΗΣΗ");
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jButton6.setText("ΔΙΑΓΡΑΦΗ");
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jButton7.setText("ΠΡΟΣΘΗΚΗ");
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jButton8.setText("ΤΡΟΠΟΠΟΙΗΣΗ");
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jButton9.setText("ΔΙΑΓΡΑΦΗ");
        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addComponent(jButton3))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(111, 111, 111)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jButton6)
                                        .addGap(121, 121, 121))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jButton4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton5)
                                        .addGap(65, 65, 65)))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jButton7)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton8))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(48, 48, 48)
                                        .addComponent(jButton9)))
                                .addGap(21, 21, 21))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(205, 205, 205)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(91, 91, 91))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton3, jButton4, jButton6, jButton9});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton2, jButton5, jButton8});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton6)
                    .addComponent(jButton9))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2, jButton3, jButton4, jButton5, jButton6, jButton9});

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Σημειώσεις χρήστη"));

        jLabel8.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        jLabel8.setText("Στοιχεία Επικοινωνίας ");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Arial Unicode MS", 1, 12)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("κάποιο τηλέφωνο, κάποιο όνομα υπευθύνου");
        jTextArea1.setToolTipText("");
        jTextArea1.setWrapStyleWord(true);
        jScrollPane4.setViewportView(jTextArea1);

        jLabel10.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        jLabel10.setText("Σχόλια");

        jTextArea3.setColumns(20);
        jTextArea3.setFont(new java.awt.Font("Segoe Script", 1, 18)); // NOI18N
        jTextArea3.setLineWrap(true);
        jTextArea3.setRows(5);
        jTextArea3.setText("ελεύθερο κείμενο από τον χρήστη");
        jTextArea3.setToolTipText("ελεύθερο κείμενο από τον χρήστη");
        jTextArea3.setWrapStyleWord(true);
        jScrollPane6.setViewportView(jTextArea3);

        jButton11.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        jButton11.setText("ΕΞΟΔΟΣ");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        jButton10.setText("ΑΠΟΘΗΚΕΥΣΗ");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(241, 241, 241))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11)))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Προσθέτει ένα τμήμα
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        String departmentName = JOptionPane.showInputDialog(
                this,
                "Εισάγετε νέο τμήμα",
                "Εισαγωγή Τμήματος",
                JOptionPane.INFORMATION_MESSAGE);
        if (departmentName == null || departmentName.isBlank()) {
            return;
        } else if (departmentName.length() > MAX_DEPARTMENT_NAME) {
            JOptionPane.showMessageDialog(
                    this,
                    "Δώστε τμήμα μέχρι " + MAX_DEPARTMENT_NAME + " χαρακτήρες",
                    "Σφάλμα",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Department department = new Department(0, departmentName);
        department.setUniversityid(university);
        DatabaseUse.storeDepartment(department);
        university.addDepartment(department);
        showDepartmentList();
    }//GEN-LAST:event_jButton7ActionPerformed

    //Προσθέτει ένα domain
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String domainName = JOptionPane.showInputDialog(
                this,
                "Εισάγετε νέο domain",
                "Εισαγωγή Domain",
                JOptionPane.INFORMATION_MESSAGE);
        if (domainName == null || domainName.isBlank()) {
            return;
        } else if (domainName.length() > MAX_DOMAIN_NAME) {
            JOptionPane.showMessageDialog(
                    this,
                    "Δώστε domain μέχρι " + MAX_DOMAIN_NAME + " χαρακτήρες",
                    "Σφάλμα",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Domain domain = new Domain(0, domainName);
        domain.setUniversityid(university);
        DatabaseUse.storeDomain(domain);
        university.addDomain(domain);
        showDomainList();
    }//GEN-LAST:event_jButton1ActionPerformed

    //Αποθηκεύει τις αλλαγές στα στοιχεία του πανεπιστημίου
    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        updateUniversity();
    }//GEN-LAST:event_jButton10ActionPerformed

    //Τροποποιεί το όνομα ενός domain
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int domainIndex = jList1.getSelectedIndex();
        if (domainIndex < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Δεν έχετε επιλέξει domain από την λίστα",
                    "Επιλέξτε domain",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String newDomainName = JOptionPane.showInputDialog(
                this,
                "Εισάγετε νέα ονομασία για το domain",
                "Τροποποίηση Domain",
                JOptionPane.INFORMATION_MESSAGE);
        if (newDomainName == null || newDomainName.isBlank()) {
            return;
        } else if (newDomainName.length() > MAX_DOMAIN_NAME) {
            JOptionPane.showMessageDialog(
                    this,
                    "Δώστε domain μέχρι " + MAX_DOMAIN_NAME + " χαρακτήρες",
                    "Σφάλμα",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Domain domain = university.getDomainList().get(domainIndex);
        DatabaseUse.updateDomain(domain, newDomainName);
        university.getDomainList().get(domainIndex).setName(newDomainName);
        showDomainList();
    }//GEN-LAST:event_jButton2ActionPerformed

    //Διαγράφει ένα domain
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int domainIndex = jList1.getSelectedIndex();
        if (domainIndex < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Δεν έχετε επιλέξει domain από την λίστα",
                    "Επιλέξτε domain",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int response = JOptionPane.showConfirmDialog(
                this,
                "Θέλετε να διαγράψετε το domain;",
                "Διαγραφή Domain",
                JOptionPane.OK_CANCEL_OPTION);
        if (response == JOptionPane.OK_OPTION) {
            Domain domain = university.getDomainList().get(domainIndex);
            DatabaseUse.deleteDomain(domain);
            university.getDomainList().remove(domainIndex);
            showDomainList();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    //Επιστρέφει στην αρχική φόρμα
    //Πριν επιστρέψει, αν έχουν γίνει τροποποιήσεις ρωτάει τον χρήστη αν θέλει να τις αποθηκεύσει
    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        String errorMessage = getFormData();
        if (modified) {
            int response = JOptionPane.showConfirmDialog(
                    this,
                    "Τα στοιχεία του πανεπιστημίου έχουν τροποποιηθεί. Θέλετε να γίνει αποθήκευση πριν επιστρέψετε στην αρχική οθόνη;",
                    "Ερώτηση",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.CANCEL_OPTION) {
                revertOriginal();
            } else {
                if (errorMessage.isEmpty()) {
                    DatabaseUse.updateUniversityData(university);
                    setUniversity(university);
                    JOptionPane.showMessageDialog(
                            this,
                            "Τα στοιχεία του πανεπιστημίου αποθηκεύτηκαν με επιτυχία",
                            "Αποθήκευση Πανεπιστημίου",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            errorMessage,
                            "Πρόβλημα στην αποθήκευση του πανεπιστημίου",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        this.dispose();
        
        //δημιουργείται η αρχική φόρμα εκ νέου,
        //και έτσι δεν έχει αποθηκευμένα data 
        //αντί για την clear
        MainForm mainForm = new MainForm();
        mainForm.setVisible(true);
    }//GEN-LAST:event_jButton11ActionPerformed

    //Προσθέτει ένα web page
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String webpageUrl = JOptionPane.showInputDialog(
                this,
                "Εισάγετε νέο web page",
                "Εισαγωγή Web Page",
                JOptionPane.INFORMATION_MESSAGE);
        if (webpageUrl == null || webpageUrl.isBlank()) {
            return;
        } else if (webpageUrl.length() > MAX_WEBPAGE_URL) {
            JOptionPane.showMessageDialog(
                    this,
                    "Δώστε web page μέχρι " + MAX_WEBPAGE_URL + " χαρακτήρες",
                    "Σφάλμα",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Webpage webpage = new Webpage(0, webpageUrl);
        webpage.setUniversityid(university);
        DatabaseUse.storeWebpage(webpage);
        university.addWebpage(webpage);
        showWebpageList();
    }//GEN-LAST:event_jButton4ActionPerformed

    //Τροποποιεί το url ενός web page
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int webpageIndex = jList2.getSelectedIndex();
        if (webpageIndex < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Δεν έχετε επιλέξει web page από την λίστα",
                    "Επιλέξτε web page",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String newWebpageUrl = JOptionPane.showInputDialog(
                this,
                "Εισάγετε νέo url για το web page",
                "Τροποποίηση Web Page",
                JOptionPane.INFORMATION_MESSAGE);
        if (newWebpageUrl == null || newWebpageUrl.isBlank()) {
            return;
        } else if (newWebpageUrl.length() > MAX_WEBPAGE_URL) {
            JOptionPane.showMessageDialog(
                    this,
                    "Δώστε web page μέχρι " + MAX_WEBPAGE_URL + " χαρακτήρες",
                    "Σφάλμα",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Webpage webpage = university.getWebpageList().get(webpageIndex);
        DatabaseUse.updateWebpage(webpage, newWebpageUrl);
        university.getWebpageList().get(webpageIndex).setUrl(newWebpageUrl);
        showWebpageList();
    }//GEN-LAST:event_jButton5ActionPerformed

    //Διαγράφει ένα web page
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int webpageIndex = jList2.getSelectedIndex();
        if (webpageIndex < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Δεν έχετε επιλέξει web page από την λίστα",
                    "Επιλέξτε web page",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int response = JOptionPane.showConfirmDialog(
                this,
                "Θέλετε να διαγράψετε το web page;",
                "Διαγραφή Web Page",
                JOptionPane.OK_CANCEL_OPTION);
        if (response == JOptionPane.OK_OPTION) {
            Webpage webpage = university.getWebpageList().get(webpageIndex);
            DatabaseUse.deleteWebpage(webpage);
            university.getWebpageList().remove(webpageIndex);
            showWebpageList();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    //Τροποποιεί το όνομα ενός τμήματος
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        int departmentIndex = jList3.getSelectedIndex();
        if (departmentIndex < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Δεν έχετε επιλέξει τμήμα από την λίστα",
                    "Επιλέξτε τμήμα",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String newDepartmentName = JOptionPane.showInputDialog(
                this,
                "Εισάγετε νέα ονομασία για το τμήμα",
                "Τροποποίηση Τμήματος",
                JOptionPane.INFORMATION_MESSAGE);
        if (newDepartmentName == null || newDepartmentName.isBlank()) {
            return;
        } else if (newDepartmentName.length() > MAX_DEPARTMENT_NAME) {
            JOptionPane.showMessageDialog(
                    this,
                    "Δώστε τμήμα μέχρι " + MAX_DEPARTMENT_NAME + " χαρακτήρες",
                    "Σφάλμα",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Department department = university.getDepartmentList().get(departmentIndex);
        DatabaseUse.updateDepartment(department, newDepartmentName);
        university.getDepartmentList().get(departmentIndex).setName(newDepartmentName);
        showDepartmentList();
    }//GEN-LAST:event_jButton8ActionPerformed

    //Διαγράφει ένα τμήμα
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        int departmentIndex = jList3.getSelectedIndex();
        if (departmentIndex < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Δεν έχετε επιλέξει τμήμα από την λίστα",
                    "Επιλέξτε τμήμα",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int response = JOptionPane.showConfirmDialog(
                this,
                "Θέλετε να διαγράψετε το τμήμα;",
                "Διαγραφή Τμήματος",
                JOptionPane.OK_CANCEL_OPTION);
        if (response == JOptionPane.OK_OPTION) {
            Department department = university.getDepartmentList().get(departmentIndex);
            DatabaseUse.deleteDepartment(department);
            university.getDepartmentList().remove(departmentIndex);
            showDepartmentList();
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JList<String> jList3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
