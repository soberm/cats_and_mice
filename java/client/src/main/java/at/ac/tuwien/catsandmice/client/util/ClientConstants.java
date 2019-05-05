package at.ac.tuwien.catsandmice.client.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ClientConstants {
    private static Gson gson;

    public static Gson getGson() {
        if(gson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
        }
        return gson;
    }

    public static final String url = "localhost";
    public static final int port = 2222;
}
