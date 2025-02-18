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

public class QuizApp extends JFrame {
    private JTextField txtFrage, A1, A2, A3, A4, Ap1, Ap2, Ap3, Ap4;
    private JButton btnSave;
    private JComboBox<String> categoryComboBox, themaComboBox;
    private JCheckBox chckbMehrfachwahl;  
    private JCheckBox cbAnswer3;  // Activates answer 3 (A3/Ap3)
    private JCheckBox cbAnswer4;  // Activates answer 4 (A4/Ap4)

    public QuizApp() {
        setTitle("Quiz Manager");
        setSize(500, 550);  // Increased height for additional checkboxes/rows.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize text fields.
        txtFrage = new JTextField();
        A1 = new JTextField();
        A2 = new JTextField();
        A3 = new JTextField();
        A4 = new JTextField();
        Ap1 = new JTextField();
        Ap2 = new JTextField();
        Ap3 = new JTextField();
        Ap4 = new JTextField();

        // Only A1/Ap1 and A2/Ap2 are active by default.
        A3.setEnabled(false);
        Ap3.setEnabled(false);
        A4.setEnabled(false);
        Ap4.setEnabled(false);

        // Initialize the save button.
        btnSave = new QuizButton("Speichern");
        
        // Initialize category and thema combo boxes.
        categoryComboBox = new JComboBox<>();
        themaComboBox = new JComboBox<>();

        // Initialize checkbox for Mehrfachwahl.
        chckbMehrfachwahl = new JCheckBox("Mehrfachwahl");

        // Checkbox to activate additional answer 3.
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

        // Checkbox to activate additional answer 4.
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

        // Set preferred sizes.
        A1.setPreferredSize(new Dimension(100, 25));
        A2.setPreferredSize(new Dimension(100, 25));
        A3.setPreferredSize(new Dimension(100, 25));
        A4.setPreferredSize(new Dimension(100, 25));
        Ap1.setPreferredSize(new Dimension(50, 25));
        Ap2.setPreferredSize(new Dimension(50, 25));
        Ap3.setPreferredSize(new Dimension(50, 25));
        Ap4.setPreferredSize(new Dimension(50, 25));

        // Layout components using GridBagLayout.
        // Row 0: Frage.
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Frage:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(txtFrage, gbc);
        
        // Row 1: Antwort 1 and Punkte 1.
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
        
        // Row 2: Antwort 2 and Punkte 2.
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Antwort 2:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(A2, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Punkte 2:"), gbc);
        
        gbc.gridx = 3;
        formPanel.add(Ap2, gbc);
        
        // Row 3: Checkbox to activate answer 3.
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        formPanel.add(cbAnswer3, gbc);
        gbc.gridwidth = 1;
        
        // Row 4: Antwort 3 and Punkte 3.
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Antwort 3:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(A3, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Punkte 3:"), gbc);
        
        gbc.gridx = 3;
        formPanel.add(Ap3, gbc);
        
        // Row 5: Checkbox to activate answer 4.
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        formPanel.add(cbAnswer4, gbc);
        gbc.gridwidth = 1;
        
        // Row 6: Antwort 4 and Punkte 4.
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Antwort 4:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(A4, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Punkte 4:"), gbc);
        
        gbc.gridx = 3;
        formPanel.add(Ap4, gbc);
        
        // Row 7: Kategorie.
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(new JLabel("Kategorie:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(categoryComboBox, gbc);
        
        // Row 8: Thema.
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(new JLabel("Thema:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(themaComboBox, gbc);
        
        // Row 9: Mehrfachwahl checkbox.
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(chckbMehrfachwahl, gbc);
        
        // Row 10: Save button.
        gbc.gridx = 1;
        gbc.gridy = 10;
        formPanel.add(btnSave, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Populate the category combo box and initialize themaComboBox.
        populateCategories();
        if (categoryComboBox.getItemCount() > 0) {
            categoryComboBox.setSelectedIndex(0);
            updateThemaComboBox();
        }
        
        // Save button action.
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFrage();
            }
        });
        
        // Update themaComboBox when category selection changes.
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateThemaComboBox();
            }
        });
        
        SwingUtilities.updateComponentTreeUI(this);
        setVisible(true);
    }
    
    private void populateCategories() {
        // For demonstration, we use a static list of categories.
        List<Kategorie> categories = new ArrayList<>();
        categories.add(new Kategorie(1, "Mathematik"));
        categories.add(new Kategorie(2, "Geschichte"));
        for (Kategorie category : categories) {
            categoryComboBox.addItem(category.getBezeichnung());
        }
    }
    
    private void updateThemaComboBox() {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        if (selectedCategory == null) {
            return;
        }
        // For demonstration, we use a static list of themas.
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
    
    private void saveFrage() {
        // Validate text length.
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
        
        // Check for missing fields.
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
        
        // Validate points fields.
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
        
        // Create the Frage object.
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
        
        // For demonstration, create new Kategorie and Thema objects based on the combo box values.
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        String selectedThema = (String) themaComboBox.getSelectedItem();
        Kategorie category = new Kategorie(0, selectedCategory);
        Thema thema = new Thema(0, selectedThema, category);
        frage.setThema(thema);
        
        // Set the Mehrfachwahl flag.
        frage.setWahl(chckbMehrfachwahl.isSelected());
        
        // Save the Frage via serialization.
        List<Frage> fragenListe = DataSerializer.loadFragen();
        if (fragenListe == null) {
            fragenListe = new ArrayList<>();
        }
        fragenListe.add(frage);
        DataSerializer.saveFragen(fragenListe);
        
        JOptionPane.showMessageDialog(this, "Frage gespeichert!");
        clearFields();
    }
    
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
