package link.infra.mkwiipresence;

import java.util.ArrayList;
import java.util.Date;

import link.infra.mkwiipresence.WiimmMessages.WiimmMember;
import link.infra.mkwiipresence.WiimmMessages.WiimmRoom;

public class PresenceMessage {
	public String detailsLine;
	public String stateLine;
	public Date startTimestamp;
	// Abuse of statics
	public static int previousVR;
	public static int previousBR;
	
	//	Let's store stuff!
	ArrayList<String> data = new ArrayList<String>();
	
	public PresenceMessage(WiimmRoom room, PresenceSettings settings) {
		WiimmMember member = room.getPlayer(settings.friendCode);
		
		if (member == null) {
			throw new RuntimeException("could not find player"); 
		}
		
		if (settings.displayMiiName == true) {
			data.add(String.format("%d", member.names[0]));
		}
		if (settings.displayFriendCode == true) {
			data.add(String.format(" (%d)", member.friendCode));
		}
		if (settings.displayRegion == true) {
			data.add(String.format(" (%d)", member.userRegion));
		}
		
		if (settings.displayVRBR == true) {
			if (member.gameRegion.contains("bt")) {
				data.add(String.format("%d BR", member.battlePoints));
			} else {
				data.add(String.format("%d VR", member.versusPoints));
			}
		}
		if (settings.displayDiscrepancyVRBR == true) {
			int versusDiscr = 0;
			int battleDiscr = 0;
			if (previousVR == 0) {
				previousVR = member.versusPoints;
			} else {
				versusDiscr = member.versusPoints - previousVR;
			}
			
			if (previousBR == 0) {
				previousBR = member.battlePoints;
			} else {
				battleDiscr = member.battlePoints - previousBR;
			}
			
			if (member.gameRegion.contains("bt")) {
				data.add(String.format("%d BR", member.battlePoints));
			} else {
				data.add(String.format("%d BR", member.versusPoints));
			}
		}
		if (settings.displayNumPlayers == true) {
			data.add(String.format("%d players in room", room.numberOfPlayers));
		}
		if (settings.displayNumRaces == true) {
			if (member.userRaces == 1) {
				data.add(String.format("%d race played", member.userRaces));
			} else {
				data.add(String.format("%d races played", member.userRaces));
			}
			
		}
		
		if (settings.timerSetting == PresenceSettings.TimerSettingType.TIMEINRACE) {
			startTimestamp = room.raceStart;
		}
		if (settings.timerSetting == PresenceSettings.TimerSettingType.TIMEINROOM) {
			Date roomTime = new Date(0);
			if (room.raceStart == null) {
				startTimestamp = null;
			}
			
			
		}

	}
	
	public static int convertFriendCodeToPID(long fc) {
		String hex;
		int pid;
		
		hex = Long.toHexString(fc);
		hex = hex.substring(hex.length() - 8, 8);
		pid = Integer.parseInt(hex, 16);
		return pid;
	}

}
