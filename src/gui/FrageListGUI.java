package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import data.entities.Frage;
import gui.entities.QuizButton;

public class FrageListGUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private List<Frage> fragenListe;
    private List<Frage> displayedFragen;
    private JCheckBox einzelwahlCheckBox;
    private JCheckBox mehrfachwahlCheckBox;

    public FrageListGUI(List<Frage> fragen) {
        this.fragenListe = fragen;
        initialize();
        setVisible(true);
    }

    private void initialize() {
        setTitle("Fragen Liste");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for search and filters.
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Suche: "));
        searchField = new JTextField(20);
        topPanel.add(searchField);

        einzelwahlCheckBox = new JCheckBox("Einzelwahl");
        einzelwahlCheckBox.setSelected(true);
        mehrfachwahlCheckBox = new JCheckBox("Mehrfachwahl");
        mehrfachwahlCheckBox.setSelected(true);
        topPanel.add(einzelwahlCheckBox);
        topPanel.add(mehrfachwahlCheckBox);

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterTable(searchField.getText());
            }
        });
        einzelwahlCheckBox.addActionListener(e -> filterTable(searchField.getText()));
        mehrfachwahlCheckBox.addActionListener(e -> filterTable(searchField.getText()));

        add(topPanel, BorderLayout.NORTH);

        // Table setup.
        tableModel = new DefaultTableModel(new Object[]{"ID", "Frage", "Punkte", "Kategorie"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setAutoCreateRowSorter(true);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open details dialog on double-click.
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        showQuestionDetails(selectedRow);
                    }
                }
            }
        });
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom panel with buttons.
        JPanel buttonPanel = new JPanel();
        QuizButton createQuizButton = new QuizButton("Quiz erstellen");
        createQuizButton.setBackground(new Color(0, 120, 215));
        createQuizButton.setForeground(Color.WHITE);
        createQuizButton.setEnabled(false);
        createQuizButton.addActionListener(e -> createQuizFile());

        QuizButton helpButton = new QuizButton("?");
        helpButton.setBackground(new Color(0, 120, 215));
        helpButton.setForeground(Color.WHITE);
        helpButton.setToolTipText("Klicke hier für Hilfe");
        helpButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Um ein Quiz zu generieren, mehrere Fragen mit STRG+Mausclick markieren und dann den 'Quiz erstellen' Button nutzen.",
                "Quiz Hilfe",
                JOptionPane.INFORMATION_MESSAGE);
        });

        buttonPanel.add(createQuizButton);
        buttonPanel.add(helpButton);
        add(buttonPanel, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedCount = table.getSelectedRowCount();
                createQuizButton.setEnabled(selectedCount >= 2);
            }
        });

        populateTable(fragenListe);
    }

    private String wrapText(String text, int maxLength) {
        if (text == null) return "";
        StringBuilder wrapped = new StringBuilder();
        int index = 0;
        while (index < text.length()) {
            int endIndex = Math.min(index + maxLength, text.length());
            wrapped.append(text.substring(index, endIndex));
            if (endIndex < text.length()) {
                wrapped.append("\n");
            }
            index += maxLength;
        }
        return wrapped.toString();
    }

    private void showQuestionDetails(int rowIndex) {
        int modelRow = table.convertRowIndexToModel(rowIndex);
        Frage selectedFrage = displayedFragen.get(modelRow);
        String message = "Frage: " + wrapText(selectedFrage.getText(), 40) + "\n" +
                         "Antwort 1: " + wrapText(selectedFrage.getA1(), 40) + " (" + selectedFrage.getAp1() + " Punkte)\n" +
                         "Antwort 2: " + wrapText(selectedFrage.getA2(), 40) + " (" + selectedFrage.getAp2() + " Punkte)\n" +
                         "Antwort 3: " + wrapText(selectedFrage.getA3(), 40) + " (" + selectedFrage.getAp3() + " Punkte)\n" +
                         "Antwort 4: " + wrapText(selectedFrage.getA4(), 40) + " (" + selectedFrage.getAp4() + " Punkte)\n" +
                         "Thema: " + (selectedFrage.getThema() != null ? selectedFrage.getThema().getBezeichnung() : "Unbekannt") + "\n" +
                         "Mehrfachwahl: " + (selectedFrage.isWahl() ? "Ja" : "Nein");

        JOptionPane.showMessageDialog(this, message, "Frage Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void createQuizFile() {
        String quizTitle = JOptionPane.showInputDialog(this, "Gib den Titel des Quizzes ein:", "Quiz Titel", JOptionPane.PLAIN_MESSAGE);
        if (quizTitle == null || quizTitle.trim().isEmpty()) return;

        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Bitte mindestens eine Frage auswählen!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (FileWriter writer = new FileWriter(quizTitle + ".txt")) {
            writer.write("\n\t\t" + quizTitle.toUpperCase() + "\n\n");
            int totalPoints = 0;
            for (int row : selectedRows) {
                int modelRow = table.convertRowIndexToModel(row);
                Frage frage = displayedFragen.get(modelRow);
                int questionPoints = frage.getAp1() + frage.getAp2() + frage.getAp3() + frage.getAp4();
                totalPoints += questionPoints;
                
                writer.write(wrapText(frage.getText(), 40) + (frage.isWahl() ? " (Mehrfachwahl möglich)" : "") + "\n");
                writer.write("1) " + wrapText(frage.getA1(), 40) + "\n");
                writer.write("2) " + wrapText(frage.getA2(), 40) + "\n");
                writer.write("3) " + wrapText(frage.getA3(), 40) + "\n");
                writer.write("4) " + wrapText(frage.getA4(), 40) + "\n\n");
            }
            writer.write("Gesamtpunkte: " + totalPoints + "\n");
            writer.flush();
            JOptionPane.showMessageDialog(this, "Quiz erfolgreich gespeichert!", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Fehler beim Speichern des Quizzes.", "Fehler", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void filterTable(String query) {
        String lowerQuery = query.toLowerCase();
        List<Frage> filtered = fragenListe.stream()
            .filter(f -> (f.getText().toLowerCase().contains(lowerQuery)
                    || (f.getThema() != null 
                        && f.getThema().getKategorie() != null 
                        && f.getThema().getKategorie().getBezeichnung().toLowerCase().contains(lowerQuery)))
                    &&
                    ((f.isWahl() && mehrfachwahlCheckBox.isSelected()) || (!f.isWahl() && einzelwahlCheckBox.isSelected()))
            )
            .collect(Collectors.toList());
        populateTable(filtered);
    }

    private void populateTable(List<Frage> fragen) {
        displayedFragen = fragen;
        tableModel.setRowCount(0);
        for (Frage f : fragen) {
            tableModel.addRow(new Object[]{
                f.getId(),
                f.getText(),
                f.getAp1() + f.getAp2() + f.getAp3() + f.getAp4(),
                (f.getThema() != null && f.getThema().getKategorie() != null) 
                    ? f.getThema().getKategorie().getBezeichnung() : "Unbekannt"
            });
        }
    }
}
