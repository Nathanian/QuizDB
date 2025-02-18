package data;

import data.entities.Frage;
import data.entities.Thema;
import data.entities.Kategorie;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
       
        Kategorie kategorie = new Kategorie(1, "Mathematik");
        Thema thema = new Thema(1, "Algebra", kategorie);

       
        List<Frage> fragen = new ArrayList<>();
        fragen.add(new Frage(1, "Was ist 2+2?", "3", 0, "4", 1, "5", 0, "6", 0, false, thema));
        fragen.add(new Frage(2, "Was ist 3x3?", "6", 0, "8", 0, "9", 1, "10", 0, false, thema));

       
        DataSerializer.saveFragen(fragen);

       
        List<Frage> loadedFragen = DataSerializer.loadFragen();

       
        if (loadedFragen != null) {
            for (Frage f : loadedFragen) {
                System.out.println("Geladene Frage: " + f.getText());
            }
        }
    }
}
