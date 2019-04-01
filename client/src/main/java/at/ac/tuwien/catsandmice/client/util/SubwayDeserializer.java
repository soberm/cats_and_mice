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

public class SubwayDeserializer implements JsonDeserializer<SubwayRepresantation> {
    @Override
    public SubwayRepresantation deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Subway subway = ClientConstants.getGson().fromJson(jsonElement, Subway.class);
        SubwayRepresantation subwayRepresantation = new SubwayRepresantation(subway);

        return subwayRepresantation;
    }
}
