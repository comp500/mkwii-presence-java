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
	// TODO STOP ABUSING STATICS
	private static int previousVR;
	private static int previousBR;
	private static Date roomTime = null;
	private static Date gameTime = null;
	
	//	Let's store stuff!
	ArrayList<String> detailsArray = new ArrayList<String>();
	ArrayList<String> stateArray = new ArrayList<String>();
	
	public PresenceMessage(WiimmRoom room, PresenceSettings settings) {
		WiimmMember member = room.getPlayer(settings.friendCode);
		
		if (member == null) {
			throw new RuntimeException("could not find player"); 
		}
		
		if (settings.displayMiiName == true) {
			detailsArray.add(String.format("%s", member.names[0]));
		}
		if (settings.displayFriendCode == true) {
			detailsArray.add(String.format("[%s]", member.friendCode));
		}
		if (settings.displayRegion == true) {
			detailsArray.add(String.format("[%s]", member.userRegion));
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
			if (room.numberOfPlayers == 1) {
				stateArray.add(String.format("[%d player in room]", room.numberOfPlayers));
			} else {
				stateArray.add(String.format("[%d players in room]", room.numberOfPlayers));
			}
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
			System.out.println(startTimestamp.toString());
		}
		
		if (settings.timerSetting == PresenceSettings.TimerSettingType.TIMEINROOM) {
			// If no room, stop timer
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
			if (gameTime == null) {
				gameTime = new Date(System.currentTimeMillis() * 1000);
			} else {
				startTimestamp = gameTime;
			}
		}
		
		detailsLine = String.join(" ", detailsArray);
		stateLine = String.join(" ", stateArray);

	}

}
