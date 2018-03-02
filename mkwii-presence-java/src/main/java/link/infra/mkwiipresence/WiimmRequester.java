package link.infra.mkwiipresence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WiimmRequester {
	private final static String WiimmApiEndpoint = "https://wiimmfi.de/mkw/room/p";
	private final static String WiimmApiQuery = "?m=json";

	public static String requestRoomInfo(String playerId) throws IOException {
		if (playerId.length() != 9) {
			throw new RuntimeException("Player ID invalid!");
		}

		URL url = new URL(WiimmApiEndpoint + playerId + WiimmApiQuery);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		int status = con.getResponseCode();
		if (status >= 300) {
			throw new IOException("Unexpected response code from Wiimm Api");
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder content = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		con.disconnect();
		
		return content.toString();
	}
}
