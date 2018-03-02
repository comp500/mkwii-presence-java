package link.infra.mkwiipresence;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

public class WiimmMessages {
	public static class MessageDeserializer implements JsonDeserializer<WiimmMessage> {
		@Override
		public WiimmMessage deserialize(final JsonElement json, final Type typeOfT,
				final JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();

			JsonElement jsonType = jsonObject.get("type");
			String type = jsonType.getAsString();

			if ("room".equals(type)) {
				return context.deserialize(json, WiimmRoom.class);
			}

			return null;
		}
	}

	public class WiimmMessage {
		@SerializedName("type")
		public String messageType;
	}

	public class WiimmRoom extends WiimmMessage {

	}
	
	public static WiimmMessage deserialize(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(WiimmMessage.class, new MessageDeserializer());
		Gson gson = gsonBuilder.create();

		WiimmMessage message = gson.fromJson(json, WiimmMessage.class);
		return message;
	}
}
