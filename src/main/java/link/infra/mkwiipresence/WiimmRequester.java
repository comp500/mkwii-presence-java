package link.infra.mkwiipresence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WiimmRequester {
	private final static String WiimmApiEndpoint = "https://wiimmfi.de/mkw/room/p";
	private final static String WiimmApiQuery = "?m=json";

	public static String requestRoomInfo(int playerId) throws IOException {
		URL url = new URL(WiimmApiEndpoint + Integer.toString(playerId) + WiimmApiQuery);
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

	public static int convertFriendCodeToPID(long fc) {
		String hex;
		int pid;
		
		hex = Long.toHexString(fc);
		hex = hex.substring(hex.length() - 8, hex.length());
		pid = Integer.parseInt(hex, 16);
		return pid;
	}
}
