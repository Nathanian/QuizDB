package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatLightLaf;
import data.DataSerializer;
import data.entities.Frage;
import gui.entities.QuizButton;

/**
 * Die Hauptanwendung der Quizmaster App.
 * <p>
 * Diese Klasse initialisiert das LookAndFeel der Anwendung, erstellt das Hauptfenster und
 * stellt Schaltflächen bereit, über die der Benutzer Quizfragen erstellen oder durchsuchen kann.
 * Beim Klicken auf "Neue Frage erstellen" wird die Eingabeoberfläche für Fragen (QuizApp) geöffnet,
 * während "Fragen durchsuchen" die gespeicherten Fragen (via FrageListGUI) lädt.
 * </p>
 * <p>
 * Hinweis: Der Autor dieser Klasse ist <b>Dein Name</b> (nicht Valentina, welche nur den DBConstants-Code
 * geschrieben hat).
 * </p>
 * 
 * @author Jan
 */
public class QuizmasterApp {
    public static void main(String[] args) {
        // Customize UI Defaults for a Modern, Colorful Look
        UIManager.put("Button.arc", 20);
        UIManager.put("Component.focusWidth", 2);
        UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Label.font", new Font("Segoe UI", Font.BOLD, 24));
        UIManager.put("Button.background", "#0078D7");
        UIManager.put("Button.foreground", "#FFFFFF");
        UIManager.put("Button.hoverBackground", "#005A9E");
        UIManager.put("Panel.background", "#F4F4F4");
        UIManager.put("Label.foreground", "#333333");

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Failed to initialize FlatLaf");
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(QuizmasterApp::createAndShowGUI);
    }

    /**
     * Erstellt und zeigt die grafische Benutzeroberfläche der Quizmaster App.
     */
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Quizmaster App", SwingConstants.CENTER);
        frame.add(titleLabel, BorderLayout.NORTH);

        // Panel for Buttons with a GridLayout.
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

        // Create buttons using the QuizButton class.
        QuizButton addQuestionButton = new QuizButton("Neue Frage erstellen");
        QuizButton browseQuestionsButton = new QuizButton("Fragen durchsuchen");

        // Add buttons to the panel.
        buttonPanel.add(addQuestionButton);
        buttonPanel.add(browseQuestionsButton);

        frame.add(buttonPanel, BorderLayout.CENTER);

        // Button Actions.
        addQuestionButton.addActionListener(e -> new QuizApp());
        browseQuestionsButton.addActionListener(e -> {
            // Load questions from the serialized file.
            List<Frage> fragen = DataSerializer.loadFragen();
            if (fragen == null) {
                fragen = new ArrayList<>();
            }
            new FrageListGUI(fragen);
        });

        // Center the frame on the screen and display it.
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
