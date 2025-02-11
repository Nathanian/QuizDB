package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import data.DataManager;
import data.entities.Frage;
import data.entities.Kategorie;
import data.entities.Thema;
import gui.entities.QuizButton;

public class QuizApp extends JFrame {
    private JTextField txtFrage, A1, A2, A3, A4, Ap1, Ap2, Ap3, Ap4;
    private JButton btnSave;
    private JComboBox<String> categoryComboBox, themaComboBox;
    private JCheckBox chckbMehrfachwahl;  
    // New checkboxes for activating additional answers in two steps:
    private JCheckBox cbAnswer3;  // Activates answer 3 (A3/Ap3)
    private JCheckBox cbAnswer4;  // Activates answer 4 (A4/Ap4)

    public QuizApp() {
        setTitle("Quiz Manager");
        setSize(500, 550);  // increased height to accommodate new checkboxes/rows
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize text fields
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

        // Initialize the save button with a vibrant blue design.
        btnSave = new QuizButton("Speichern");
        
        // Initialize category and thema combo boxes.
        categoryComboBox = new JComboBox<>();
        themaComboBox = new JComboBox<>();

        // Initialize checkbox for Mehrfachwahl.
        chckbMehrfachwahl = new JCheckBox("Mehrfachwahl");

        // NEW: Checkbox to activate additional answer 3.
        cbAnswer3 = new JCheckBox("Antwort 3 aktivieren");
        cbAnswer3.setSelected(false);
        cbAnswer3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean enable = cbAnswer3.isSelected();
                A3.setEnabled(enable);
                Ap3.setEnabled(enable);
                if (!enable) {
                    // If answer 3 is disabled, clear its fields and disable answer 4 checkbox and fields.
                    A3.setText("");
                    Ap3.setText("");
                    cbAnswer4.setSelected(false);
                    cbAnswer4.setEnabled(false);
                    A4.setEnabled(false);
                    Ap4.setEnabled(false);
                    A4.setText("");
                    Ap4.setText("");
                } else {
                    // Enable the checkbox for answer 4.
                    cbAnswer4.setEnabled(true);
                }
            }
        });

        // NEW: Checkbox to activate additional answer 4.
        cbAnswer4 = new JCheckBox("Antwort 4 aktivieren");
        cbAnswer4.setSelected(false);
        cbAnswer4.setEnabled(false); // remains disabled until answer 3 is activated.
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
        // Row 0: Frage
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Frage:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(txtFrage, gbc);
        
        // Row 1: Antwort 1 and Punkte 1
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
        
        // Row 2: Antwort 2 and Punkte 2
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Antwort 2:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(A2, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Punkte 2:"), gbc);
        
        gbc.gridx = 3;
        formPanel.add(Ap2, gbc);
        
        // Row 3: Checkbox to activate additional answer 3.
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        formPanel.add(cbAnswer3, gbc);
        gbc.gridwidth = 1; // reset
        
        // Row 4: Antwort 3 and Punkte 3 (initially disabled)
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Antwort 3:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(A3, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Punkte 3:"), gbc);
        
        gbc.gridx = 3;
        formPanel.add(Ap3, gbc);
        
        // Row 5: Checkbox to activate additional answer 4.
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        formPanel.add(cbAnswer4, gbc);
        gbc.gridwidth = 1; // reset
        
        // Row 6: Antwort 4 and Punkte 4 (initially disabled)
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Antwort 4:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(A4, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Punkte 4:"), gbc);
        
        gbc.gridx = 3;
        formPanel.add(Ap4, gbc);
        
        // Row 7: Kategorie
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(new JLabel("Kategorie:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(categoryComboBox, gbc);
        
        // Row 8: Thema
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(new JLabel("Thema:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(themaComboBox, gbc);
        
        // Row 9: Mehrfachwahl checkbox
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(chckbMehrfachwahl, gbc);
        
        // Row 10: Save button
        gbc.gridx = 1;
        gbc.gridy = 10;
        formPanel.add(btnSave, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Populate the category combo box and preinitialize themaComboBox.
        populateCategories();
        if (categoryComboBox.getItemCount() > 0) {
            categoryComboBox.setSelectedIndex(0);  // default selection
            updateThemaComboBox();                 // preload themas for the selected category
        }
        
        // Button action listener to save the question.
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFrage();
            }
        });
        
        // Update the thema combo box when the category selection changes.
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
        // Fetch all categories and add them to the category combo box.
        List<Kategorie> categories = DataManager.getInstance().getAllCategories();
        for (Kategorie category : categories) {
            categoryComboBox.addItem(category.getBezeichnung());
        }
    }
    
    private void updateThemaComboBox() {
        // Get the selected category name.
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        if (selectedCategory == null) {
            return;
        }
        // Fetch the themas based on the selected category.
        List<Thema> themas = DataManager.getInstance().getThemaByKategorie(selectedCategory);
        
        // Clear existing items in the thema combo box and add new ones.
        themaComboBox.removeAllItems();
        for (Thema thema : themas) {
            themaComboBox.addItem(thema.getBezeichnung());
        }
    }
    
    private void saveFrage() {
        // First, check that non-points text fields do not exceed 400 characters.
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
        
        // Build an error message for missing fields.
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
        // Only validate A3/Ap3 and A4/Ap4 if their checkboxes are selected.
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
        
        // Validate that the points fields contain valid integers between 0 and 10.
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
        
        // Create the Frage object and set its values.
        Frage frage = new Frage();
        frage.setText(txtFrage.getText());
        frage.setA1(A1.getText());
        frage.setAp1(point1);
        frage.setA2(A2.getText());
        frage.setAp2(point2);
        // Set additional answers only if enabled; otherwise, set empty.
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
        
        // Get the selected Kategorie and Thema.
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        String selectedThema = (String) themaComboBox.getSelectedItem();
        Kategorie category = DataManager.getInstance().getCategoryByName(selectedCategory);
        Thema thema = DataManager.getInstance().getThemaByName(selectedThema);
        frage.setThema(thema);
        
        // Set the Mehrfachwahl flag.
        frage.setWahl(chckbMehrfachwahl.isSelected());
        
        // Save the Frage object.
        DataManager.getInstance().saveFrage(frage);
        
        JOptionPane.showMessageDialog(this, "Frage gespeichert!");
        
        // Reset all fields to empty/default values.
        clearFields();
    }
    
    /**
     * Clears all input fields, resets checkboxes, and reinitializes the comboboxes.
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
