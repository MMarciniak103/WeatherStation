package app.util;

import app.models.Measurement;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.List;

/**
 * Class that provides static method for saving session data in json format.
 */
public final class SessionSaver {

    /**
     * Saves current session data into file of json format.
     * @param jsonData List of measurements that we want to save.
     * @param file Chosen file that would contain saved data.
     */
    public static void saveToJson(List jsonData, File file){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){

            Gson gson = new Gson();
            writer.write(gson.toJson(jsonData));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads saved session data containing measurements.
     * @param file Chosen file in json format.
     * @return List of measurements.
     */
    public static List loadFromJson(File file){
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){

            Gson gson = new Gson();
            List data = gson.fromJson(reader, new TypeToken<List<Measurement>>() {}.getType());

            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
