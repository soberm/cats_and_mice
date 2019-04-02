package at.ac.tuwien.catsandmice.client.util;

import at.ac.tuwien.catsandmice.client.world.SubwayRepresantation;
import at.ac.tuwien.catsandmice.client.world.WorldRepresentation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ClientConstants {
    private static Gson gson;

    public static Gson getGson() {
        if(gson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(WorldRepresentation.class, new WorldDeserializer());
            gsonBuilder.registerTypeAdapter(SubwayRepresantation.class, new SubwayDeserializer());
            gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
        }
        return gson;
    }

    public static final String url = "localhost";
    public static final int port = 2222;
}
