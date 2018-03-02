package link.infra.mkwiipresence;

import java.util.Date;

import link.infra.mkwiipresence.WiimmMessages.WiimmRoom;

public class PresenceMessage {
	public String detailsLine;
	public String stateLine;
	public Date startTimestamp;

	public PresenceMessage(WiimmRoom room, PresenceSettings settings) {
		
	}
	
	public static int ConvertFriendCodeToPID(long fc) {
		String hex;
		int pid;
		
		hex = Long.toHexString(fc);
		hex = hex.substring(hex.length() - 8, 8);
		pid = Integer.parseInt(hex, 16);
		return pid;
	}

}
