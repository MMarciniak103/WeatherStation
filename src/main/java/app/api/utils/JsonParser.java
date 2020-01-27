package app.api.utils;

import app.models.Measurement;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

public class JsonParser {

    /**
     * Method that converts json in string format into array of measurements.
     * Written to work with openaq API
     * @param jsonText api's response
     * @return List of measurements
     */
    public static List<Measurement> getMeasurements(String jsonText) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Map m = gson.fromJson(jsonText,Map.class);

        JsonArray jsonArray = gson.toJsonTree(m.get("results")).getAsJsonArray();

        if(jsonArray.size() == 0)
            throw new InvalidParameterException("Provided url is invalid!");

        Type typeToken = new TypeToken<List<Measurement>>(){}.getType();

        List<Measurement> map = gson.fromJson(jsonArray,typeToken);

        return map;
    }
}
