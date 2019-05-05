package at.ac.tuwien.catsandmice.server.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServerConstants {
    private static Gson gson;

    public static Gson getGson() {
        if(gson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
        }
        return gson;
    }
}
