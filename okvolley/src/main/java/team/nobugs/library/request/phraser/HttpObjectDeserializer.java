package team.nobugs.library.request.phraser;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by xiayong on 2015/8/12.
 */
public class HttpObjectDeserializer implements JsonDeserializer<HttpObject> {
    @Override
    public HttpObject deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final HttpObject httpObject = new HttpObject();
        final JsonObject jsonObject = jsonElement.getAsJsonObject();

        httpObject.setCode(jsonObject.get("code").getAsInt());
        httpObject.setCodeMsg(jsonObject.get("codeMsg").getAsString());
        httpObject.setData(jsonObject.get("data").getAsString());

        return httpObject;
    }
}
