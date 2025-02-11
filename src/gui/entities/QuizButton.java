package gui.entities;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class QuizButton extends JButton {

	public QuizButton() {
		// TODO Auto-generated constructor stub
	}

	public QuizButton(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public QuizButton(String text) {
        super(text);
        
        setBackground(new Color(0, 120, 215));
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
       
    }

	public QuizButton(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public QuizButton(String text, Icon icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}

}
