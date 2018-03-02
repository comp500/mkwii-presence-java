package link.infra.mkwiipresence;

public class PresenceSettings {
	public enum TimerSettingType {
		TIMEINROOM,
		TIMEFORRACE,
		OVERALLTIME
	}
	
	public boolean displayMiiName;
	public boolean displayFriendCode;
	public boolean displayRegion;
	public boolean displayVRBR;
	public boolean displayDiscrepancyVRBR;
	public boolean displayNumPlayers;
	public boolean displayNumRaces;
	public TimerSettingType timerSetting;
	public String friendCode;
	public int updateRate;
}
