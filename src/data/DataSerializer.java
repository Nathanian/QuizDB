package data;

import java.io.*;
import java.util.List;

import data.entities.Frage;

/**
 * Die Klasse {@code DataSerializer} bietet Methoden zum Speichern und Laden einer Liste von
 * {@link Frage}-Objekten mittels Java-Objektserialisierung.
 * <p>
 * Die Daten werden in einer Datei gespeichert, deren Name durch {@value #FILE_NAME} festgelegt ist.
 * </p>
 */
public class DataSerializer {

    /**
     * Der Dateiname, in dem die {@code Frage}-Objekte gespeichert werden.
     */
    private static final String FILE_NAME = "fragen.txt"; 

    /**
     * Speichert die übergebene Liste von {@link Frage}-Objekten in die Datei {@value #FILE_NAME}.
     * <p>
     * Es wird ein {@link ObjectOutputStream} verwendet, um die Liste zu serialisieren.
     * </p>
     *
     * @param fragen die Liste der {@link Frage}-Objekte, die gespeichert werden soll
     */
    public static void saveFragen(List<Frage> fragen) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(fragen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lädt und gibt die Liste der {@link Frage}-Objekte aus der Datei {@value #FILE_NAME} zurück.
     * <p>
     * Wenn die Datei nicht existiert, wird eine entsprechende Meldung ausgegeben und {@code null} zurückgegeben.
     * </p>
     *
     * @return die Liste der {@link Frage}-Objekte, sofern das Laden erfolgreich war, andernfalls {@code null}
     */
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
