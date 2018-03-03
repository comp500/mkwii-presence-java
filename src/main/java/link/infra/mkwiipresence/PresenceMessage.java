package link.infra.mkwiipresence;

import java.util.ArrayList;
import java.util.Date;

import link.infra.mkwiipresence.WiimmMessages.WiimmMember;
import link.infra.mkwiipresence.WiimmMessages.WiimmRoom;

public class PresenceMessage {
	public String detailsLine;
	public String stateLine;
	public Date startTimestamp;
	public String largeImageKey;
	public String smallImageKey;
	
	// Abuse of statics
	public static int previousVR;
	public static int previousBR;
	
	//	Let's store stuff!
	ArrayList<String> detailsArray = new ArrayList<String>();
	ArrayList<String> stateArray = new ArrayList<String>();
	
	@SuppressWarnings("unused")
	public PresenceMessage(WiimmRoom room, PresenceSettings settings) {
		WiimmMember member = room.getPlayer(settings.friendCode);
		
		if (member == null) {
			throw new RuntimeException("could not find player"); 
		}
		
		if (settings.displayMiiName == true) {
			detailsArray.add(String.format("%d", member.names[0]));
		}
		if (settings.displayFriendCode == true) {
			detailsArray.add(String.format("[%d]", member.friendCode));
		}
		if (settings.displayRegion == true) {
			detailsArray.add(String.format("[%d]", member.userRegion));
		}
		
		if (settings.displayVRBR == true) {
			if (member.gameRegion.contains("bt")) {
				stateArray.add(String.format("%d BR", member.battlePoints));
			} else {
				stateArray.add(String.format("%d VR", member.versusPoints));
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
				if (battleDiscr > 0) {
					stateArray.add(String.format("[+%d]", battleDiscr));
				} else {
					stateArray.add(String.format("[%d]", battleDiscr));
				}							
			} else {
				if (versusDiscr > 0) {
					stateArray.add(String.format("[+%d]", versusDiscr));
				} else {
					stateArray.add(String.format("[%d]", versusDiscr));
				}			
			}
		}
		if (settings.displayNumPlayers == true) {
			stateArray.add(String.format("[%d players in room]", room.numberOfPlayers));
		}
		if (settings.displayNumRaces == true) {
			if (member.userRaces == 1) {
				stateArray.add(String.format("[%d race played]", member.userRaces));
			} else {
				stateArray.add(String.format("[%d races played]", member.userRaces));
			}
			
		}
		
		if (settings.timerSetting == PresenceSettings.TimerSettingType.TIMEINRACE) {
			startTimestamp = room.raceStart;
		}
		
		if (settings.timerSetting == PresenceSettings.TimerSettingType.TIMEINROOM) {
			Date roomTime = null;
			
			//If no room, stop timer
			if (room.raceStart == null) {
				startTimestamp = null;
			}
			
			if (roomTime == null) {
				roomTime = new Date(System.currentTimeMillis() * 1000);
			} else {
				startTimestamp = roomTime;
			}
			
		}
		
		if (settings.timerSetting == PresenceSettings.TimerSettingType.OVERALLTIME) {
			Date gameTime = null;
			
			if (gameTime == null) {
				gameTime = new Date(System.currentTimeMillis() * 1000);
			} else {
				startTimestamp = gameTime;
			}
		}
		
		detailsLine = String.join(" ", detailsArray);
		stateLine = String.join(" ", stateArray);

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