package link.infra.mkwiipresence;

import java.util.Date;

import link.infra.mkwiipresence.WiimmMessages.WiimmRoom;

public class PresenceMessage {
	public String detailsLine;
	public String stateLine;
	public Date startTimestamp;

	public PresenceMessage(WiimmRoom room, PresenceSettings settings) {
		// TODO parse data from `room` with settings from `settings`,
		// and put into detailsLine, stateLine and startTimestamp
	}

}
