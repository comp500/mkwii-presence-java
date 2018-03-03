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
	public PresenceUpdater presenceUpdater;
	
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
					presenceUpdater = new PresenceUpdater();
					guiInstance = new MKWiiPresenceGUI(reference);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void showError(Exception e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "Exception thrown: \n" + e.getMessage(), "There was a problem.", JOptionPane.ERROR_MESSAGE);
	}
	
	public boolean processResponse(String json) {
		WiimmMessage[] messages = WiimmMessages.deserialize(json);
		boolean wasUpdated = false;
		for (int i = 0; i < messages.length; i++) {
			System.out.println(messages[i].messageType);
			if (messages[i] instanceof WiimmRoom) {
				presenceUpdater.update(new PresenceMessage((WiimmRoom) messages[i], currentSettings));
				wasUpdated = true;
			}
		}
		if (!wasUpdated) {
			// Update presence with previous (cached) info
			// TODO remove testing code
			presenceUpdater.update(null);
		}
		return wasUpdated;
	}
	
	public void setCurrentSettings(PresenceSettings settings) {
		this.currentSettings = settings;
	}
	
	public void requestNewPresence() {
		if (currentSettings.friendCode != null && currentSettings.friendCode.length() == 14) {
			String friendCodeNumbers = currentSettings.friendCode.replace("-", "");
			int playerId;
			try {
				long friendCode = Long.parseLong(friendCodeNumbers);
				playerId = WiimmRequester.convertFriendCodeToPID(friendCode);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Invalid friend code", "There was a problem.", JOptionPane.ERROR_MESSAGE);
				return;
			}
			System.out.println(playerId);
			try {
				String json = WiimmRequester.requestRoomInfo(playerId);
				if (processResponse(json)) {
					// Set status to done
				} else {
					// Set status depending on whether cache was used/available
				}
			} catch (Exception e) {
				showError(e);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Invalid friend code", "There was a problem.", JOptionPane.ERROR_MESSAGE);
		}
	}
}
