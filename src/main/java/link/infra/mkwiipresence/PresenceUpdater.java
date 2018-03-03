package link.infra.mkwiipresence;

import com.github.psnrigner.discordrpcjava.DiscordRichPresence;
import com.github.psnrigner.discordrpcjava.DiscordRpc;

public class PresenceUpdater {

	private static final String APP_ID = "412637273683918850";
	private DiscordRpc discordRpc;

	public PresenceUpdater() {
		discordRpc = new DiscordRpc();

		discordRpc.init(APP_ID, null, true, null);
		System.out.println("called init");
	}

	public void update(PresenceMessage msg) {
		DiscordRichPresence discordRichPresence = new DiscordRichPresence();
		discordRichPresence.setState(msg.stateLine);
		discordRichPresence.setDetails(msg.detailsLine);
		discordRichPresence.setStartTimestamp(msg.startTimestamp.getTime() / 1000);
		discordRichPresence.setLargeImageKey("mkwii_large");
		discordRichPresence.setLargeImageText("Mario Kart Wii");
		discordRichPresence.setSmallImageKey("wiimmfi_small");
		discordRichPresence.setSmallImageText("Wiimmfi");
		discordRichPresence.setInstance(false);
		
		System.out.println("sent update");
		
		discordRpc.updatePresence(discordRichPresence);
	}

}
