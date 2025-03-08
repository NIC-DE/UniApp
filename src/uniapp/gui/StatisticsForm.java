package uniapp.gui;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;  //  για το χρώμα της φόρμας
import java.awt.Desktop; // για το desktop
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import javax.swing.JOptionPane;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import uniapp.models.University;
import uniapp.DataBase.DatabaseUse;
import uniapp.DataBase.UniApp;

//Φόρμα στατιστικών
public class StatisticsForm extends javax.swing.JFrame {

    //οι κωδικοί του χρώματος της φόρμας
    private int FORMS_COLOR_R=123;
    private int FORMS_COLOR_G=150;
    private int FORMS_COLOR_B=150;
    
    //path του τελευταίου αρχείου pdf που δημιουργήθηκε
    private String pdfDirPath = (System.getProperty("user.dir") + "\\SavedPDF\\");
    //όνομα του τελευταίου αρχείου pdf που δημιουργήθηκε
    private String generatedPdfFilename;

    //το default directory PNG και JPG
    private String fontDirPath = UniApp.MY_MEDIA_PATH;
    
    //counter για τα συνολικά views του pdf
    private int totalViews=0;
    
    //Το μοντέλο του πίνακα 
    private DefaultTableModel tableModel;

    //Constructor
    public StatisticsForm() {
        System.out.println("Δημιουργία φόρμας στατιστικών...");
        initComponents();
        //Αρχικοποίηση στοιχείων φόρμας
        setTitle("Προβολή στατιστικών για προβολές πανεπιστημίων και εκτύπωση σε αρχείο pdf");

        //απενεργοποιεί τα create και erase buttons
        // το jButton1-κουμπί διαγραφής pdf
        jButton1ErasePDF.setEnabled(false);
        jButton4OpenPDF.setEnabled(false);
        
        //απενεργοποιεί το jPane "διαδρομή αρχείου στον δίσκο"
        jLabel2.setVisible(false);
        jPanel3.setVisible(false);
        
        //Κεντράρισμα της φόρμας
        setLocationRelativeTo(null);
        
        //Η φόρμα δεν μπορεί να αλλάξει μέγεθος
        this.setResizable(false);
       
        //Η φόρμα δεν μπορεί να κλείσει ώστε να μην κλείνει η εφαρμογή
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
 
        //Εικονίδιο εφαρμογής
        String appIcon = (UniApp.MY_MEDIA_PATH + "IconEap.png");
        ImageIcon icon = new ImageIcon(appIcon);
        this.setIconImage(icon.getImage());
        
        //Χρώμα φόρμας
        this.getContentPane().setBackground(new Color(FORMS_COLOR_R, FORMS_COLOR_G, FORMS_COLOR_B));
        
        //Ρύθμιση μοντέλου πίνακα
        tableModel = new DefaultTableModel() {
            //Κάνουμε override αυτή την μέθοδο ώστε να μην μπορούν να τροποποιηθούν τα στοιχεία του πίνακα
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        //Προσθέτουμε τις δύο στήλες του μοντέλου και θέτουμε το μοντέλο στον πίνακα
        tableModel.addColumn("Πανεπιστήμιο");
        tableModel.addColumn("Προβολές");
        jTable1.setModel(tableModel);
        
        //Ορίζουμε το πλάτος της πρώτης στήλης για τα ονόματα των πανεπιστημίων
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(250);
        
        
        //Ορίζει τις δύο γραμματοσειρές που χρησιμοποιεί στο jComboBox 
        jComboBoxFonts.addItem("Arial");
        jComboBoxFonts.addItem("Tahoma");
        jComboBoxFonts.addItem("Courier New");
    }            // τέλος κατασκευαστή
    
    //Ενημερώνει τον πίνακα με τα στατιστικά προβολών των πανεπιστημίων
    public void updateStatistics() {
        System.out.println("Ενημέρωση στατιστικών προβολών...");
        //Παίρνει τη λίστα των πανεπιστημίων από τη βάση δεδομένων 
        // ταξινομημένη με φθίνουσα διάταξη προβολών.
        List<University> universitiesOrderedByViews =
                DatabaseUse.getUniversitiesOrderedByViews();
        
        //Ενημερώνει το μοντέλο του πίνακα με τα ονόματα πανεπιστημίων 
        // και το πλήθος προβολών τους.
        tableModel.setRowCount(0);
        for (University u : universitiesOrderedByViews) {
            tableModel.addRow(new Object[]{u.getName(), u.getViews()});
            totalViews+=u.getViews();
            jLabel3.setText("Συνολικά " + totalViews +" προβολές.");
        }
            System.out.println("Συνολικές προβολές: " + totalViews);
    }

    public int getTotalViews() {
        return totalViews;
    }
    
    //Μέθοδος που σβήνει pdf τα που έχουν δημιουργηθεί
    public void eraseOldPDF(String directoryPath) {

        if (JOptionPane.showConfirmDialog(null, "Επιθυμείτε να διαγράψετε όλα τα προηγούμενα pdf που έχετε δημιουργήσει;",
                "Έξοδος", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            
            //απενεργοποιεί τα create και erase buttons
            jButton1ErasePDF.setEnabled(false);
            jButton4OpenPDF.setEnabled(false);
            
            //απενεργοποιεί το jPane "διαδρομή αρχείου στον δίσκο"
            jLabel2.setVisible(false);
            jPanel3.setVisible(false);
            
            File dir = new File(directoryPath);

            // Έλεγχος αν ο κατάλογος υπάρχει και είναι όντως κατάλογος
            if (!dir.exists() || !dir.isDirectory()) {
                System.out.println("Μη έγκυρος κατάλογος: " + directoryPath);
                return;
            }

            // Λίστα με όλα τα αρχεία PDF στον κατάλογο
            File[] pdfFiles = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".pdf"));

            //Αν δεν υπάρχουν αρχεία ή υπάρχει μόνο ένα, δεν χρειάζεται διαγραφή
            // Μελλοντική αναβάθμιση, ίσως αφήνει ένα pdf .length<=1
            if (pdfFiles == null || pdfFiles.length <= 0) {
                JOptionPane.showMessageDialog(this, 
                "Δεν υπάρχουν αρχεία PDF προς διαγραφή!", 
                "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                
                System.out.println("Δεν υπάρχουν αρχεία PDF προς διαγραφή.");
                return;
            }

            // Ταξινόμηση των αρχείων με βάση την ημερομηνία τελευταίας τροποποίησης (αύξουσα σειρά)
            Arrays.sort(pdfFiles, Comparator.comparingLong(File::lastModified));

            //Διαγραφή όλων των αρχείων 
            // Μελλοντική αναβάθμιση, ίσως αφήνει ένα pdf pdffiles.length -1
            for (int i = 0; i < pdfFiles.length; i++) {
                if (pdfFiles[i].delete()) {
                    System.out.println("Διαγράφηκε: " + pdfFiles[i].getName());
                } else {
                    System.out.println("Αποτυχία διαγραφής: " + pdfFiles[i].getName());
                }
            }
        }
    }
           
    //Δημιουργεί το αρχείο pdf με τις προβολές των πανεπιστημίων
    public void createPDF() {
        System.out.println("Δημιουργία PDF...");
        try {
            
            //Στο σημείο αυτό, θα χρειαστεί όνομα αρχείου pdf
            //θα είναι της μορφής UniAppViews_yyyy.MM.dd_HH.mm.ss.pdf
            
            //Παίρνει ημερομηνία συστήματος
            Date dateNow = Calendar.getInstance().getTime();
            //Φορμά της ημερομηνίας
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
            String nowFormatName = sdf.format(dateNow);

            //Προσδιορίζει το (σύνθετο) όνομα του pdf
            String filename = this.pdfDirPath + "UniAppViews_" + nowFormatName + ".pdf";

            //αποθηκεύει τοπικά το όνομα
            //για να μπορεί να το ανοίξει η jButton4OpenPDFActionPerformed
            this.generatedPdfFilename = filename;

            // Create the File object
            File pdfFile = new File(filename);

            OutputStream outputStream = new FileOutputStream(pdfFile);
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);

            // Ανοίγω το document
            document.open();

            //Χρησιμοποιούμενες γραμματοσειρές του pdf
            //Κάνει register τις fonts (arial.ttf, tahoma.ttf, courier.ttf)
            //οι οποίες βρίσκονται στο root directory του UniApp
            FontFactory.register(fontDirPath + "arial.ttf", "Arial");
            FontFactory.register(fontDirPath + "tahoma.ttf", "Tahoma");
            FontFactory.register(fontDirPath + "cour.ttf", "Cοurier New");

            //Ορίζει την γραμματοσειρά
            String selectedFont = (String) jComboBoxFonts.getSelectedItem();

            //Δημιουργεί την γραμματοειρά δυναμικά
            Font font = FontFactory.getFont(selectedFont, BaseFont.IDENTITY_H, true, 12);

            //Τίτλος παραγράφου
            Paragraph par = new Paragraph("Προβολές Πανεπιστημίων:", font);
            par.setAlignment(Element.ALIGN_CENTER);
            document.add(par);

            // Περνάει στην επόμενη γραμμή
            document.add(new Paragraph("\n"));

            //Δημιουργία πίνακα με 2 στήλες
            PdfPTable table = new PdfPTable(2);
            table.addCell(new Paragraph("Πανεπιστήμιο", font));
            table.addCell(new Paragraph("Προβολές", font));

            // Fetch data and add rows
            for (University university : DatabaseUse.getUniversitiesOrderedByViews()) {
                table.addCell(new Paragraph(university.getName(), font));
                table.addCell(new Paragraph(String.valueOf(university.getViews()), font));
            }

            PdfPCell totalLabelCell = new PdfPCell(new Paragraph("Σύνολο Προβολών", font));
            PdfPCell totalValueCell = new PdfPCell(new Paragraph(String.valueOf(totalViews), font));

            // Ρύθμιση περιγράμματος
            totalLabelCell.setBorderWidth(2f); // Παχύτερο περίγραμμα
            totalValueCell.setBorderWidth(2f);
            totalLabelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            totalValueCell.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Προσθήκη τελευταίας γραμμής στον πίνακα με το σύνολο προβολών
            table.addCell(totalLabelCell);
            table.addCell(totalValueCell);

            // Add table to document
            document.add(table);

            //κλεινει το document κα το output stream
            document.close();
            outputStream.close();
            
            //Παίρνει και το absolute file path
            String filePath = pdfFile.getAbsolutePath();

            jPanel3.setVisible(true);
            jLabel2.setVisible(true);
            jLabel2.setText(filePath);

            //Μύνημα επιτυχίας
            System.out.println("PDF δημιουργήθηκε: " + filename);
            JOptionPane.showMessageDialog(this,
                    "Δημιουργήθηκε το αρχείο PDF:\n" + filePath,
                    "Επιτυχία αποθήκευσης αρχείου", JOptionPane.INFORMATION_MESSAGE
            );
                    
            //ενεργοποιεί τα create και erase buttons
            jButton1ErasePDF.setEnabled(true);
            jButton4OpenPDF.setEnabled(true);

        } catch (Exception e) {
            System.out.println("Σφάλμα στη δημιουργία PDF: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Πρόβλημα στην δημιουργία του αρχείου PDF\n" + e.getMessage(),
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE
            );
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton2EXIT = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton3CreatePDFFonted = new javax.swing.JButton();
        jComboBoxFonts = new javax.swing.JComboBox<>();
        label1 = new java.awt.Label();
        jPanel5 = new javax.swing.JPanel();
        jButton4OpenPDF = new javax.swing.JButton();
        jButton1ErasePDF = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Στατιστικά", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Unicode MS", 0, 14))); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {}
        ));
        jScrollPane2.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Arial Unicode MS", 1, 14)); // NOI18N
        jLabel1.setText("ΤΕΛΙΚΑ VIEWS ΤΩΝ ΠΑΝΕΠΙΣΤΗΜΙΩΝ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 102, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Διαθέσιμες επιλογές", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Unicode MS", 0, 14))); // NOI18N

        jButton2EXIT.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        jButton2EXIT.setText("ΑΡΧΙΚΗ ΟΘΟΝΗ");
        jButton2EXIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2EXITActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Δημιουργία pdf", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 12))); // NOI18N

        jButton3CreatePDFFonted.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        jButton3CreatePDFFonted.setText("ΔΗΜΙΟΥΡΓΙΑ PDF");
        jButton3CreatePDFFonted.setToolTipText("Δημιουργεί νέο pdf της μορφής UniAppViews_yyyy.MM.dd_HH.mm.ss.pdf");
        jButton3CreatePDFFonted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3CreatePDFFontedActionPerformed(evt);
            }
        });

        jComboBoxFonts.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        jComboBoxFonts.setToolTipText("Επιθυμητή γραμματοσειρά");

        label1.setName(""); // NOI18N
        label1.setText("Επιλογή γραμματοσειράς");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButton3CreatePDFFonted, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxFonts, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxFonts, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3CreatePDFFonted, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Προβολή", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 12))); // NOI18N

        jButton4OpenPDF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton4OpenPDF.setText("ΑΝΟΙΓΜΑ ΤΟΥ ΑΡΧΕΙΟΥ");
        jButton4OpenPDF.setToolTipText("Ανοίγει το τελευταίο αρχείο που έχει δημιουργήσει ο χρήστης");
        jButton4OpenPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4OpenPDFActionPerformed(evt);
            }
        });

        jButton1ErasePDF.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1ErasePDF.setText("ΔΙΑΓΡΑΦΗ  PDF ΑΡΧΕΙΩΝ");
        jButton1ErasePDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ErasePDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4OpenPDF, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(jButton1ErasePDF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jButton4OpenPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButton1ErasePDF, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jButton2EXIT, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2EXIT, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Η διαδρομή του αρχείου στον δίσκο", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 12))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 102, 255));
        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 874, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Κλείνει την φόρμα στατιστικών και εμφανίζει την αρχική φόρμα
    private void jButton2EXITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2EXITActionPerformed
        // κάνω τα labels invisible
        // και επιστρέφω στην αρχική
        jLabel2.setVisible(false);
        jPanel3.setVisible(false);
        totalViews =0;
        this.dispose();
        //UniApp.mainForm.setVisible(true);
        
        //δημιουργείται η αρχική φόρμα εκ νέου,
        //και έτσι δεν έχει αποθηκευμένα data 
        //αντί για την clear
        MainForm mainForm = new MainForm();
        mainForm.setVisible(true);
        
        
    }//GEN-LAST:event_jButton2EXITActionPerformed

    private void jButton3CreatePDFFontedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3CreatePDFFontedActionPerformed

        //Μήνυμα ενημέρωσης για τις προβολές, αν είναι άδεια η βάση
        // δεν έχω να τυπώσω τίποτα
        if (DatabaseUse.getUniversitiesOrderedByViews().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Δεν υπάρχει προβολή για εκτύπωση",
                    "Κενή βάση δεδομένων", JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            //αλλιώς δημιουργούμε το αρχείο PDF
            createPDF();
        }

    }//GEN-LAST:event_jButton3CreatePDFFontedActionPerformed

    private void jButton4OpenPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4OpenPDFActionPerformed
        try {
            //το path του αρχείου
            File pdfFile = new File(this.generatedPdfFilename);

            //Ελέγχει αν υπάρχει το αρχείο πριν το ανοίξει
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile); // Open the PDF
            } else {
                JOptionPane.showMessageDialog(this,
                        "Το αρχείο PDF δεν βρέθηκε!",
                        "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Δεν υπάρχει πρόσφατο αρχείο PDF.",
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton4OpenPDFActionPerformed
    
    //button που σβήνει όλα τα pdf
    private void jButton1ErasePDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ErasePDFActionPerformed
        eraseOldPDF(this.pdfDirPath);
    }//GEN-LAST:event_jButton1ErasePDFActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1ErasePDF;
    private javax.swing.JButton jButton2EXIT;
    private javax.swing.JButton jButton3CreatePDFFonted;
    private javax.swing.JButton jButton4OpenPDF;
    private javax.swing.JComboBox<String> jComboBoxFonts;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private java.awt.Label label1;
    // End of variables declaration//GEN-END:variables
}
