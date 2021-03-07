package persistence;

import org.json.JSONObject;
import java.io.*;

import model.GameList;

// Citation: Based on the CPSC 210 JsonSerializationDemo
// Represents a writer that writes JSON representation of a list of games to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: if destination file cannot be opened for writing, throws FileNotFoundException
    //          otherwise, opens writer
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of main menu to file
    public void write(GameList gl) {
        JSONObject json = gl.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writers string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
