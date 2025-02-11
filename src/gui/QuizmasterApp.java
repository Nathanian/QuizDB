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
import data.DataManager;
import data.entities.Frage;
import gui.entities.QuizButton;

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

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Quizmaster App", SwingConstants.CENTER);
        frame.add(titleLabel, BorderLayout.NORTH);

        // Panel for Buttons with a GridLayout. You can adjust this layout as needed.
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

        // Create buttons using the new QuizButton class.
        QuizButton addQuestionButton = new QuizButton("Neue Frage erstellen");
        QuizButton browseQuestionsButton = new QuizButton("Fragen durchsuchen");

        // Add Buttons to Panel
        buttonPanel.add(addQuestionButton);
        buttonPanel.add(browseQuestionsButton);

        frame.add(buttonPanel, BorderLayout.CENTER);

        // Button Actions
        addQuestionButton.addActionListener(e -> new QuizApp());
        browseQuestionsButton.addActionListener(e -> {
            List<Frage> fragen = DataManager.getInstance().loadAllFragen();
            if (fragen == null) {
                fragen = new ArrayList<>(); // If null, create an empty list
            }
            new FrageListGUI(fragen);
        });

        // Center the frame on the screen and display it
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
