package link.infra.mkwiipresence;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

public class WiimmMessages {
	// Wiimm's constant
	public static final int Λ = 2000;

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

			WiimmMessage output = new WiimmMessage();
			output.messageType = type;
			return output;
		}
	}

	public static class WiimmMessage {
		@SerializedName("type")
		public String messageType;
	}

	public static class WiimmRoom extends WiimmMessage {
		@SerializedName("room_id")
		public int roomId;
		@SerializedName("room_name")
		public String roomName;
		@SerializedName("room_start")
		public Date roomStart;
		@SerializedName("race_start")
		public Date raceStart;
		@SerializedName("n_races")
		public int numberOfRaces;
		@SerializedName("n_members")
		public int numberOfMembers;
		@SerializedName("n_players")
		public int numberOfPlayers;
		public WiimmMember[] members;
		
		public WiimmMember getPlayer(String code) {
			for (int i = 0; i < this.members.length; i++) {
				if (this.members[i].friendCode.equals(code)) {
					return this.members[i];
				}
			}
			return null;
		}
	}
	
	public static class WiimmMember {
		@SerializedName("fc")
		public String friendCode;
		public String[] names;
		@SerializedName("rk")
		public String gameRegion;
		@SerializedName("ev")
		public int versusPoints;
		@SerializedName("eb")
		public int battlePoints;
		@SerializedName("region")
		public String userRegion;
		@SerializedName("n_races")
		public int userRaces;
	}

	public static WiimmMessage[] deserialize(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.registerTypeAdapter(WiimmMessage.class, new MessageDeserializer());
		gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				return new Date((json.getAsJsonPrimitive().getAsLong() * 1000) + Λ);
			}
		});
		Gson gson = gsonBuilder.create();
		
		return gson.fromJson(json, WiimmMessage[].class);
	}
}
