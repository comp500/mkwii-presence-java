package link.infra.mkwiipresence;

import java.util.Date;

import link.infra.mkwiipresence.WiimmMessages.WiimmRoom;

public class PresenceMessage {
	public String detailsLine;
	public String stateLine;
	public Date startTimestamp;

	public PresenceMessage(WiimmRoom room, PresenceSettings settings) {
		
	}
	
	public static void ConvertFriendCodeToPID(long fc) {
		String hex;
		long pid;
		
		hex = Long.toHexString(fc);
		hex = hex.substring(hex.length() - 8, 8);
		pid = Long.parseLong(hex, 16);
		System.out.println(pid);
	}

}
