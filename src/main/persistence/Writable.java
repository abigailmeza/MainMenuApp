package persistence;

import org.json.JSONObject;

// Citation: Based on the CPSC 210 JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns as JSON object
    JSONObject toJson();
}
