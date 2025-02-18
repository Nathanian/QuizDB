package data;

import java.io.*;
import java.util.List;

import data.entities.Frage;

public class DataSerializer {

    private static final String FILE_NAME = "fragen.txt"; 

   
    public static void saveFragen(List<Frage> fragen) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(fragen);
            System.out.println("Fragen wurden erfolgreich gespeichert.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
    @SuppressWarnings("unchecked")
	public static List<Frage> loadFragen() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("Keine gespeicherten Fragen gefunden.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Frage>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

