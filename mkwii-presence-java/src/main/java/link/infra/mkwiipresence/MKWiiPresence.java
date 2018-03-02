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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
					guiInstance = new MKWiiPresenceGUI();
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
		WiimmMessage[] messages = WiimmMessages.deserialize("[{\n" + 
				"    \"type\": \"room\",\n" + 
				"    \"room_id\": 499734,\n" + 
				"    \"room_name\": \"KF34\",\n" + 
				"    \"game_id4\": \"RMCJ\",\n" + 
				"    \"is_mkw\": 1,\n" + 
				"    \"ol_status\": 373,\n" + 
				"    \"ol_status_x\": \"o-G-wAR-h----\",\n" + 
				"    \"room_start\": 1518472562,\n" + 
				"    \"race_start\": 1518475418,\n" + 
				"    \"n_races\": 11}]");
		
		for (int i = 0; i < messages.length; i++) {
			if (messages[i] instanceof WiimmRoom) {
				PresenceUpdater.update(new PresenceMessage((WiimmRoom) messages[i], currentSettings));
			}
		}
	}
	
	public void requestNewPresence() {
		try {
			if (currentSettings.friendCode != null && currentSettings.friendCode.length() == 12) {
				String friendCodeNumbers = currentSettings.friendCode.replace("-", "");
				long friendCode = Long.parseLong(friendCodeNumbers);
				int playerId = PresenceMessage.ConvertFriendCodeToPID(friendCode);
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
