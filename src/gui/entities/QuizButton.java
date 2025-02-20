package gui.entities;

import java.awt.Color;
import java.awt.Font;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * Eine benutzerdefinierte Schaltfläche (Button) für Quiz-Anwendungen.
 * <p>
 * Diese Klasse erweitert {@link JButton} und stellt ein einheitliches Erscheinungsbild
 * für quizbezogene grafische Benutzeroberflächen bereit. Wird die Schaltfläche mit Text erstellt,
 * so werden standardmäßig Hintergrundfarbe, Schriftfarbe und Schriftart festgelegt.
 * Weitere Konstruktoren ermöglichen die Initialisierung mit einem Icon oder einer {@link Action}.
 * </p>
 * <p>
 * Hinweis: Da diese Komponente ausschließlich für die GUI vorgesehen ist, besteht in der Regel
 * keine Notwendigkeit, sie zu serialisieren.
 * </p>
 * 
 */
public class QuizButton extends JButton {

    /** 
     * Serialisierungs-ID (wird hier benötigt, um Warnungen zu unterdrücken).
     */
    private static final long serialVersionUID = 1L;

    /**
     * Erzeugt einen neuen leeren QuizButton.
     */
    public QuizButton() {
        // Standardkonstruktor
    }

    /**
     * Erzeugt einen neuen QuizButton mit dem angegebenen Icon.
     *
     * @param icon das Icon, das auf der Schaltfläche angezeigt werden soll
     */
    public QuizButton(Icon icon) {
        super(icon);
    }

    /**
     * Erzeugt einen neuen QuizButton mit dem angegebenen Text.
     * <p>
     * Dabei werden Hintergrundfarbe, Schriftfarbe und Schriftart so eingestellt, dass ein
     * ansprechendes Design für Quiz-Anwendungen gewährleistet ist.
     * </p>
     *
     * @param text der Text, der auf der Schaltfläche angezeigt werden soll
     */
    public QuizButton(String text) {
        super(text);
        setBackground(new Color(0, 120, 215));
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    /**
     * Erzeugt einen neuen QuizButton basierend auf der angegebenen {@link Action}.
     *
     * @param a die Aktion, die das Verhalten der Schaltfläche definiert
     */
    public QuizButton(Action a) {
        super(a);
    }

    /**
     * Erzeugt einen neuen QuizButton mit dem angegebenen Text und Icon.
     *
     * @param text der Text, der auf der Schaltfläche angezeigt werden soll
     * @param icon das Icon, das auf der Schaltfläche angezeigt werden soll
     */
    public QuizButton(String text, Icon icon) {
        super(text, icon);
    }
}
