package link.infra.mkwiipresence;

import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import link.infra.mkwiipresence.WiimmMessages.WiimmMessage;
import link.infra.mkwiipresence.WiimmMessages.WiimmRoom;

public class MKWiiPresence {
	public PresenceSettings currentSettings = new PresenceSettings();
	public MKWiiPresenceGUI guiInstance;
	
	public static void main(String[] args) {
		System.out.println("hi");
		new MKWiiPresence();
	}
	
	public MKWiiPresence() {
		// is this a bad idea?
		MKWiiPresence reference = this;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
					guiInstance = new MKWiiPresenceGUI(reference);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void showError(Exception e) {
		JOptionPane.showMessageDialog(null, "Exception thrown: \n" + e.getMessage(), "There was a problem.", JOptionPane.ERROR_MESSAGE);
	}
	
	public void processResponse(String json) {
		WiimmMessage[] messages = WiimmMessages.deserialize(json);
		for (int i = 0; i < messages.length; i++) {
			if (messages[i] instanceof WiimmRoom) {
				PresenceUpdater.update(new PresenceMessage((WiimmRoom) messages[i], currentSettings));
			}
		}
	}
	
	public void setCurrentSettings(PresenceSettings settings) {
		this.currentSettings = settings;
	}
	
	public void requestNewPresence() {
		try {
			if (currentSettings.friendCode != null && currentSettings.friendCode.length() == 14) {
				String friendCodeNumbers = currentSettings.friendCode.replace("-", "");
				long friendCode = Long.parseLong(friendCodeNumbers);
				int playerId = PresenceMessage.convertFriendCodeToPID(friendCode);
				String json = WiimmRequester.requestRoomInfo(playerId);
				processResponse(json);
			} else {
				System.out.println("WARN: friend code not set");
			}
		} catch (Exception e) {
			showError(e);
		}
	}
}
