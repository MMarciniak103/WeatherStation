package app.util;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Class that provides static method for saving session data in json format.
 */
public final class SessionSaver {

    public static void saveToJson(List jsonData, File file){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){

            Gson gson = new Gson();
            writer.write(gson.toJson(jsonData));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
