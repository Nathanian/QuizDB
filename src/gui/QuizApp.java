package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import data.DataSerializer;
import data.entities.Frage;
import data.entities.Kategorie;
import data.entities.Thema;
import gui.entities.QuizButton;

/**
 * Die Klasse {@code QuizApp} stellt eine grafische Benutzeroberfläche zur Verwaltung von Quizfragen bereit.
 * <p>
 * Mit dieser Anwendung können Quizfragen eingegeben, validiert und gespeichert werden. Der Benutzer kann
 * eine Frage mitsamt Antworten (sowie optional zusätzlichen Antworten) eingeben, Kategorien und Themen auswählen 
 * und die Frage zur weiteren Verarbeitung (hier per Serialisierung) ablegen.
 * </p>
 * <p>
 * Die Oberfläche wird mittels {@link GridBagLayout} aufgebaut. Zusätzliche Checkboxen ermöglichen das dynamische
 * Aktivieren bzw. Deaktivieren der optionalen Antwortfelder.
 * </p>
 * 
 */

public class QuizApp extends JFrame {
    // Eingabefelder für Frage und Antworten
    private JTextField txtFrage, A1, A2, A3, A4, Ap1, Ap2, Ap3, Ap4;
    // Schaltfläche zum Speichern der Frage
    private JButton btnSave;
    // Auswahlfelder für Kategorie und Thema
    private JComboBox<String> categoryComboBox, themaComboBox;
    // Checkbox für Mehrfachwahl
    private JCheckBox chckbMehrfachwahl;  
    // Checkbox, um Antwort 3 zu aktivieren
    private JCheckBox cbAnswer3;  
    // Checkbox, um Antwort 4 zu aktivieren
    private JCheckBox cbAnswer4;  

    /**
     * Erzeugt eine neue Instanz von {@code QuizApp} und initialisiert die grafische Benutzeroberfläche.
     */
    public QuizApp() {
        setTitle("Quiz Manager");
        setSize(500, 550);  // Erhöhte Höhe für zusätzliche Checkboxen/Zeilen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialisierung der Textfelder
        txtFrage = new JTextField();
        A1 = new JTextField();
        A2 = new JTextField();
        A3 = new JTextField();
        A4 = new JTextField();
        Ap1 = new JTextField();
        Ap2 = new JTextField();
        Ap3 = new JTextField();
        Ap4 = new JTextField();

        // Standardmäßig sind nur A1/Ap1 und A2/Ap2 aktiv
        A3.setEnabled(false);
        Ap3.setEnabled(false);
        A4.setEnabled(false);
        Ap4.setEnabled(false);

        // Initialisierung der Speichern-Schaltfläche
        btnSave = new QuizButton("Speichern");
        
        // Initialisierung der ComboBoxen für Kategorie und Thema
        categoryComboBox = new JComboBox<>();
        themaComboBox = new JComboBox<>();

        // Initialisierung der Checkbox für Mehrfachwahl
        chckbMehrfachwahl = new JCheckBox("Mehrfachwahl");

        // Checkbox zur Aktivierung der Antwort 3
        cbAnswer3 = new JCheckBox("Antwort 3 aktivieren");
        cbAnswer3.setSelected(false);
        cbAnswer3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean enable = cbAnswer3.isSelected();
                A3.setEnabled(enable);
                Ap3.setEnabled(enable);
                if (!enable) {
                    A3.setText("");
                    Ap3.setText("");
                    cbAnswer4.setSelected(false);
                    cbAnswer4.setEnabled(false);
                    A4.setEnabled(false);
                    Ap4.setEnabled(false);
                    A4.setText("");
                    Ap4.setText("");
                } else {
                    cbAnswer4.setEnabled(true);
                }
            }
        });

        // Checkbox zur Aktivierung der Antwort 4
        cbAnswer4 = new JCheckBox("Antwort 4 aktivieren");
        cbAnswer4.setSelected(false);
        cbAnswer4.setEnabled(false);
        cbAnswer4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean enable = cbAnswer4.isSelected();
                A4.setEnabled(enable);
                Ap4.setEnabled(enable);
                if (!enable) {
                    A4.setText("");
                    Ap4.setText("");
                }
            }
        });

        txtFrage.setColumns(80);

        // Festlegen der bevorzugten Größen der Textfelder.
        A1.setPreferredSize(new Dimension(100, 25));
        A2.setPreferredSize(new Dimension(100, 25));
        A3.setPreferredSize(new Dimension(100, 25));
        A4.setPreferredSize(new Dimension(100, 25));
        Ap1.setPreferredSize(new Dimension(50, 25));
        Ap2.setPreferredSize(new Dimension(50, 25));
        Ap3.setPreferredSize(new Dimension(50, 25));
        Ap4.setPreferredSize(new Dimension(50, 25));

        // Layout der Komponenten mit GridBagLayout
        // Zeile 0: Frage
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Frage:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(txtFrage, gbc);
        
        // Zeile 1: Antwort 1 und Punkte 1
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Antwort 1:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(A1, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Punkte 1:"), gbc);
        
        gbc.gridx = 3;
        formPanel.add(Ap1, gbc);
        
        // Zeile 2: Antwort 2 und Punkte 2
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Antwort 2:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(A2, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Punkte 2:"), gbc);
        
        gbc.gridx = 3;
        formPanel.add(Ap2, gbc);
        
        // Zeile 3: Checkbox zur Aktivierung von Antwort 3
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        formPanel.add(cbAnswer3, gbc);
        gbc.gridwidth = 1;
        
        // Zeile 4: Antwort 3 und Punkte 3
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Antwort 3:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(A3, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Punkte 3:"), gbc);
        
        gbc.gridx = 3;
        formPanel.add(Ap3, gbc);
        
        // Zeile 5: Checkbox zur Aktivierung von Antwort 4
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        formPanel.add(cbAnswer4, gbc);
        gbc.gridwidth = 1;
        
        // Zeile 6: Antwort 4 und Punkte 4
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Antwort 4:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(A4, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Punkte 4:"), gbc);
        
        gbc.gridx = 3;
        formPanel.add(Ap4, gbc);
        
        // Zeile 7: Kategorie
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(new JLabel("Kategorie:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(categoryComboBox, gbc);
        
        // Zeile 8: Thema
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(new JLabel("Thema:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(themaComboBox, gbc);
        
        // Zeile 9: Checkbox für Mehrfachwahl
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(chckbMehrfachwahl, gbc);
        
        // Zeile 10: Speichern-Schaltfläche
        gbc.gridx = 1;
        gbc.gridy = 10;
        formPanel.add(btnSave, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Befülle die Kategorie-ComboBox und initialisiere die Thema-ComboBox
        populateCategories();
        if (categoryComboBox.getItemCount() > 0) {
            categoryComboBox.setSelectedIndex(0);
            updateThemaComboBox();
        }
        
        // ActionListener für den Speichern-Button
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFrage();
            }
        });
        
        // Aktualisiere die Thema-ComboBox bei Änderung der Kategorieauswahl
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateThemaComboBox();
            }
        });
        
        SwingUtilities.updateComponentTreeUI(this);
        setVisible(true);
    }
    
    /**
     * Befüllt die Kategorie-ComboBox mit statischen Beispieldaten.
     */
    private void populateCategories() {
        List<Kategorie> categories = new ArrayList<>();
        categories.add(new Kategorie(1, "Mathematik"));
        categories.add(new Kategorie(2, "Geschichte"));
        for (Kategorie category : categories) {
            categoryComboBox.addItem(category.getBezeichnung());
        }
    }
    
    /**
     * Aktualisiert die Thema-ComboBox basierend auf der aktuell ausgewählten Kategorie.
     * <p>
     * Für Demonstrationszwecke werden hier statische Beispieldaten verwendet.
     * </p>
     */
    private void updateThemaComboBox() {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        if (selectedCategory == null) {
            return;
        }
        List<Thema> themas = new ArrayList<>();
        if ("Mathematik".equals(selectedCategory)) {
            themas.add(new Thema(1, "Algebra", new Kategorie(1, "Mathematik")));
            themas.add(new Thema(2, "Geometrie", new Kategorie(1, "Mathematik")));
        } else if ("Geschichte".equals(selectedCategory)) {
            themas.add(new Thema(3, "Antike", new Kategorie(2, "Geschichte")));
            themas.add(new Thema(4, "Mittelalter", new Kategorie(2, "Geschichte")));
        }
        
        themaComboBox.removeAllItems();
        for (Thema thema : themas) {
            themaComboBox.addItem(thema.getBezeichnung());
        }
    }
    
    /**
     * Validiert die Eingaben, erstellt ein {@link Frage}-Objekt und speichert es mittels
     * {@link DataSerializer}. Dabei werden auch Fehlermeldungen angezeigt, falls Eingaben
     * fehlen oder ungültig sind.
     */
    private void saveFrage() {
        // Prüfung der Zeichenlängen.
        StringBuilder lengthErrors = new StringBuilder();
        if (txtFrage.getText().trim().length() > 400) {
            lengthErrors.append("- Die Frage darf maximal 400 Zeichen enthalten.\n");
        }
        if (A1.getText().trim().length() > 200) {
            lengthErrors.append("- Antwort 1 darf maximal 200 Zeichen enthalten.\n");
        }
        if (A2.getText().trim().length() > 200) {
            lengthErrors.append("- Antwort 2 darf maximal 200 Zeichen enthalten.\n");
        }
        if (cbAnswer3.isSelected() && A3.getText().trim().length() > 200) {
            lengthErrors.append("- Antwort 3 darf maximal 200 Zeichen enthalten.\n");
        }
        if (cbAnswer4.isSelected() && A4.getText().trim().length() > 200) {
            lengthErrors.append("- Antwort 4 darf maximal 200 Zeichen enthalten.\n");
        }
        if (lengthErrors.length() > 0) {
            JOptionPane.showMessageDialog(this,
                    "Bitte korrigieren Sie die folgenden Eingaben:\n" + lengthErrors.toString(),
                    "Zeichenbegrenzung überschritten",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Prüfung auf fehlende Eingaben.
        StringBuilder missingFields = new StringBuilder();
        if (txtFrage.getText().trim().isEmpty()) {
            missingFields.append("- Frage\n");
        }
        if (A1.getText().trim().isEmpty()) {
            missingFields.append("- Antwort 1\n");
        }
        if (A2.getText().trim().isEmpty()) {
            missingFields.append("- Antwort 2\n");
        }
        if (Ap1.getText().trim().isEmpty()) {
            missingFields.append("- Punkte 1\n");
        }
        if (Ap2.getText().trim().isEmpty()) {
            missingFields.append("- Punkte 2\n");
        }
        if (cbAnswer3.isSelected()) {
            if (A3.getText().trim().isEmpty()) {
                missingFields.append("- Antwort 3\n");
            }
            if (Ap3.getText().trim().isEmpty()) {
                missingFields.append("- Punkte 3\n");
            }
        }
        if (cbAnswer4.isSelected()) {
            if (A4.getText().trim().isEmpty()) {
                missingFields.append("- Antwort 4\n");
            }
            if (Ap4.getText().trim().isEmpty()) {
                missingFields.append("- Punkte 4\n");
            }
        }
        if (categoryComboBox.getSelectedItem() == null) {
            missingFields.append("- Kategorie\n");
        }
        if (themaComboBox.getSelectedItem() == null) {
            missingFields.append("- Thema\n");
        }
        
        if (missingFields.length() > 0) {
            JOptionPane.showMessageDialog(this,
                    "Bitte füllen Sie die folgenden Felder aus:\n" + missingFields.toString(),
                    "Fehlende Eingaben",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Prüfung der Punktefelder auf gültige Zahlenwerte.
        int point1 = 0, point2 = 0, point3 = 0, point4 = 0;
        StringBuilder numericErrors = new StringBuilder();
        
        try {
            point1 = Integer.parseInt(Ap1.getText().trim());
            if (point1 < 0 || point1 > 10) {
                numericErrors.append("- Punkte 1: Zahl muss zwischen 0 und 10 liegen.\n");
            }
        } catch (NumberFormatException e) {
            numericErrors.append("- Punkte 1: Bitte geben Sie nur Zahlen ein.\n");
        }
        try {
            point2 = Integer.parseInt(Ap2.getText().trim());
            if (point2 < 0 || point2 > 10) {
                numericErrors.append("- Punkte 2: Zahl muss zwischen 0 und 10 liegen.\n");
            }
        } catch (NumberFormatException e) {
            numericErrors.append("- Punkte 2: Bitte geben Sie nur Zahlen ein.\n");
        }
        if (cbAnswer3.isSelected()) {
            try {
                point3 = Integer.parseInt(Ap3.getText().trim());
                if (point3 < 0 || point3 > 10) {
                    numericErrors.append("- Punkte 3: Zahl muss zwischen 0 und 10 liegen.\n");
                }
            } catch (NumberFormatException e) {
                numericErrors.append("- Punkte 3: Bitte geben Sie nur Zahlen ein.\n");
            }
        }
        if (cbAnswer4.isSelected()) {
            try {
                point4 = Integer.parseInt(Ap4.getText().trim());
                if (point4 < 0 || point4 > 10) {
                    numericErrors.append("- Punkte 4: Zahl muss zwischen 0 und 10 liegen.\n");
                }
            } catch (NumberFormatException e) {
                numericErrors.append("- Punkte 4: Bitte geben Sie nur Zahlen ein.\n");
            }
        }
        
        if (numericErrors.length() > 0) {
            JOptionPane.showMessageDialog(this,
                    "Es gibt Fehler in den Punkte-Feldern:\n" + numericErrors.toString(),
                    "Ungültige Eingabe",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Erstellen des Frage-Objekts.
        Frage frage = new Frage();
        frage.setText(txtFrage.getText());
        frage.setA1(A1.getText());
        frage.setAp1(point1);
        frage.setA2(A2.getText());
        frage.setAp2(point2);
        if (cbAnswer3.isSelected()) {
            frage.setA3(A3.getText());
            frage.setAp3(point3);
        } else {
            frage.setA3("");
            frage.setAp3(0);
        }
        if (cbAnswer4.isSelected()) {
            frage.setA4(A4.getText());
            frage.setAp4(point4);
        } else {
            frage.setA4("");
            frage.setAp4(0);
        }
        
        // Erstellen von Kategorie- und Thema-Objekten anhand der ComboBox-Auswahl.
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        String selectedThema = (String) themaComboBox.getSelectedItem();
        Kategorie category = new Kategorie(0, selectedCategory);
        Thema thema = new Thema(0, selectedThema, category);
        frage.setThema(thema);
        
        // Setzen des Mehrfachwahl-Flags.
        frage.setWahl(chckbMehrfachwahl.isSelected());
        
        // Speichern der Frage mittels Serialisierung.
        List<Frage> fragenListe = DataSerializer.loadFragen();
        if (fragenListe == null) {
            fragenListe = new ArrayList<>();
        }
        fragenListe.add(frage);
        DataSerializer.saveFragen(fragenListe);
        
        JOptionPane.showMessageDialog(this, "Frage gespeichert!");
        clearFields();
    }
    
    /**
     * Setzt alle Eingabefelder und Auswahlkomponenten zurück.
     */
    private void clearFields() {
        txtFrage.setText("");
        A1.setText("");
        A2.setText("");
        A3.setText("");
        A4.setText("");
        Ap1.setText("");
        Ap2.setText("");
        Ap3.setText("");
        Ap4.setText("");
        chckbMehrfachwahl.setSelected(false);
        cbAnswer3.setSelected(false);
        cbAnswer4.setSelected(false);
        A3.setEnabled(false);
        Ap3.setEnabled(false);
        A4.setEnabled(false);
        Ap4.setEnabled(false);
        
        if (categoryComboBox.getItemCount() > 0) {
            categoryComboBox.setSelectedIndex(0);
        }
        updateThemaComboBox();
    }
}
