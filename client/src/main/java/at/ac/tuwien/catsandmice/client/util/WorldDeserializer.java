package at.ac.tuwien.catsandmice.client.util;

import at.ac.tuwien.catsandmice.client.characters.CatRepresentation;
import at.ac.tuwien.catsandmice.client.characters.MouseRepresentation;
import at.ac.tuwien.catsandmice.client.world.SubwayRepresantation;
import at.ac.tuwien.catsandmice.client.world.WorldRepresentation;
import at.ac.tuwien.catsandmice.dto.world.Subway;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WorldDeserializer implements JsonDeserializer<WorldRepresentation> {
    @Override
    public WorldRepresentation deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        WorldRepresentation worldRepresentation = new WorldRepresentation();

        JsonArray jsonArray = jsonObject.getAsJsonArray("subways");
        worldRepresentation.setSubwayRepresantations(getSubwayRepresentations(jsonArray));

        jsonArray = jsonObject.getAsJsonArray("cats");
        worldRepresentation.setCats(getCatRepresentations(jsonArray));

        jsonArray = jsonObject.getAsJsonArray("mice");
        worldRepresentation.setMice(getMouseRepresentations(jsonArray));

        return worldRepresentation;
    }

    private List<CatRepresentation> getCatRepresentations(JsonArray jsonArray) {
        Type targetClassType = new TypeToken<ArrayList<CatRepresentation>>() {}.getType();
        List<CatRepresentation> cats = new GsonBuilder().create().fromJson(jsonArray, targetClassType);
        return cats;
    }

    private List<SubwayRepresantation> getSubwayRepresentations(JsonArray jsonArray) {
        List<SubwayRepresantation> subways = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            subways.add(ClientConstants.getGson().fromJson(jsonArray.get(i), SubwayRepresantation.class));
        }
        return subways;
    }

    private List<MouseRepresentation> getMouseRepresentations(JsonArray jsonArray) {
        Type targetClassType = new TypeToken<ArrayList<MouseRepresentation>>() {}.getType();
        List<MouseRepresentation> mice = new GsonBuilder().create().fromJson(jsonArray, targetClassType);
        return mice;
    }
}
