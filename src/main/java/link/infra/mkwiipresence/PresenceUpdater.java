package link.infra.mkwiipresence;

import com.github.psnrigner.discordrpcjava.DiscordEventHandler;
import com.github.psnrigner.discordrpcjava.DiscordJoinRequest;
import com.github.psnrigner.discordrpcjava.DiscordRichPresence;
import com.github.psnrigner.discordrpcjava.DiscordRpc;
import com.github.psnrigner.discordrpcjava.ErrorCode;

public class PresenceUpdater {

	private static final String APP_ID = "412637273683918850";
	private DiscordRpc discordRpc;

	public PresenceUpdater() {
		discordRpc = new DiscordRpc();

		DiscordEventHandler discordEventHandler = new DiscordEventHandler() {
			@Override
			public void ready() {
				System.err.println("READY");
			}

			@Override
			public void disconnected(ErrorCode errorCode, String message) {
				System.err.println("DISCONNECTED : " + errorCode + " " + message);
			}

			@Override
			public void errored(ErrorCode errorCode, String message) {
				System.err.println("ERRORED : " + errorCode + " " + message);
			}

			@Override
			public void joinGame(String joinSecret) {
				System.err.println("JOIN GAME : " + joinSecret);
			}

			@Override
			public void spectateGame(String spectateSecret) {
				System.err.println("SPECTATE GAME : " + spectateSecret);
			}

			@Override
			public void joinRequest(DiscordJoinRequest joinRequest) {
				System.err.println("JOIN REQUEST : " + joinRequest);
			}
		};

		discordRpc.init(APP_ID, discordEventHandler, true, null);
		System.out.println("called init");
	}

	public void update(PresenceMessage msg) {
		DiscordRichPresence discordRichPresence = new DiscordRichPresence();
		discordRichPresence.setState("Developing");
		discordRichPresence.setDetails("Java | Discord RPC API");
		discordRichPresence.setStartTimestamp(0);
		discordRichPresence.setEndTimestamp(0);
		discordRichPresence.setLargeImageKey("icon-large");
		discordRichPresence.setSmallImageKey("icon-small");
		discordRichPresence.setPartyId("ALONE");
		discordRichPresence.setPartySize(1);
		discordRichPresence.setPartyMax(2);
		discordRichPresence.setMatchSecret("hello");
		discordRichPresence.setJoinSecret("join");
		discordRichPresence.setSpectateSecret("look");
		discordRichPresence.setInstance(false);
		
		System.out.println("sent update");
		
		discordRpc.updatePresence(discordRichPresence);
	}

}
