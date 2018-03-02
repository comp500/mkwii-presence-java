package link.infra.mkwiipresence;

import java.util.Date;

import link.infra.mkwiipresence.WiimmMessages.WiimmMember;
import link.infra.mkwiipresence.WiimmMessages.WiimmRoom;

public class PresenceMessage {
	public String detailsLine;
	public String stateLine;
	public Date startTimestamp;
	

	public PresenceMessage(WiimmRoom room, PresenceSettings settings) {
		WiimmMember member = room.getPlayer(settings.friendCode);
		
		if (member == null) {
			throw new RuntimeException("could not find player"); 
		}
		
		if (settings.displayMiiName == true) {
			detailsLine = String.format("%d", member.names[0]);
		}
		if (settings.displayFriendCode == true) {
			detailsLine += String.format(" (%d)", member.friendCode);
		}
		if (settings.displayRegion == true) {
			detailsLine += String.format(" (%d)", member.userRegion);
		}
		
		if (settings.displayVRBR == true) {
			if (member.gameRegion.contains("bt")) {
				stateLine = String.format("%d BR", member.battlePoints);
			} else {
				stateLine = String.format("%d VR", member.versusPoints);
			}
		}
		if (settings.displayDiscrepancyVRBR == true) {
			
		}

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
