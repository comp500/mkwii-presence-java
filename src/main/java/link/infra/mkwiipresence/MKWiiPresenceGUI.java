package link.infra.mkwiipresence;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import link.infra.mkwiipresence.WiimmMessages.WiimmMember;
import link.infra.mkwiipresence.WiimmMessages.WiimmRoom;

public class MKWiiPresenceGUI {

	private JFrame frmSuperCoolRich;
	private JTextField tbxFC1;
	private JTextField tbxFC2;
	private JTextField tbxFC3;
	private JPanel pnlUpdateRate;
	private JLabel lblRichPresenceSettings;
	private JPanel pnlDetailsLine;
	private JPanel pnlElapsedTimer;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JCheckBox cbxDiscrepancyVRBR;
	private JCheckBox cbxPlayersInRoom;
	private JCheckBox cbxDisplayNumRaces;
	private JCheckBox cbxMiiName;
	private JCheckBox cbxFriendCode;
	private JCheckBox cbxRegion;
	private JCheckBox cbxVRBR;
	private JRadioButton rbtTimeInRoom;
	private JRadioButton rbtTimeInRace;
	private JRadioButton rbtTimeOverall;
	private JSlider sliderUpdateRate;

	private MKWiiPresence mainInst;
	private JLabel lblPreviewText;
	private JPanel panel_3;

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public MKWiiPresenceGUI(MKWiiPresence mainInst) throws IOException {
		this.mainInst = mainInst;
		initialize();
		frmSuperCoolRich.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		ActionListener saveSettingsListener = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updatedSettings();
			}
		};

		frmSuperCoolRich = new JFrame();
		frmSuperCoolRich.setTitle("super cool rich presence lul");
		frmSuperCoolRich.setBounds(100, 100, 425, 515);
		frmSuperCoolRich.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSuperCoolRich.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		frmSuperCoolRich.getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel lblUserSettings = new JLabel("User Settings");
		lblUserSettings.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblUserSettings);
		lblUserSettings.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserSettings.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JPanel pnlFriendCode = new JPanel();
		panel.add(pnlFriendCode);
		pnlFriendCode
				.setBorder(new TitledBorder(null, "Friend Code", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		tbxFC1 = new JTextField();
		tbxFC1.setHorizontalAlignment(SwingConstants.CENTER);
		tbxFC1.setColumns(4);
		((PlainDocument) tbxFC1.getDocument()).setDocumentFilter(new FriendCodeFilter());
		pnlFriendCode.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlFriendCode.add(tbxFC1);

		tbxFC2 = new JTextField();
		tbxFC2.setHorizontalAlignment(SwingConstants.CENTER);
		tbxFC2.setColumns(4);
		((PlainDocument) tbxFC2.getDocument()).setDocumentFilter(new FriendCodeFilter());
		pnlFriendCode.add(tbxFC2);

		tbxFC3 = new JTextField();
		tbxFC3.setHorizontalAlignment(SwingConstants.CENTER);
		tbxFC3.setColumns(4);
		((PlainDocument) tbxFC3.getDocument()).setDocumentFilter(new FriendCodeFilter());
		pnlFriendCode.add(tbxFC3);

		tbxFC1.getDocument().addDocumentListener(new FriendCodeListener(tbxFC2));
		tbxFC2.getDocument().addDocumentListener(new FriendCodeListener(tbxFC3));
		tbxFC3.getDocument().addDocumentListener(new FriendCodeListener(null));

		pnlUpdateRate = new JPanel();
		panel.add(pnlUpdateRate);
		pnlUpdateRate.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Update Rate",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		sliderUpdateRate = new JSlider();
		sliderUpdateRate.setValue(15);
		sliderUpdateRate.setMaximum(20);
		sliderUpdateRate.setMinimum(5);
		sliderUpdateRate.setMinorTickSpacing(5);
		sliderUpdateRate.setMajorTickSpacing(5);
		sliderUpdateRate.setPaintTicks(true);
		sliderUpdateRate.setSnapToTicks(true);
		sliderUpdateRate.setPaintLabels(true);
		sliderUpdateRate.setOrientation(SwingConstants.VERTICAL);
		Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
		labels.put(20, new JLabel("Slow (20 secs)"));
		labels.put(15, new JLabel("Normal (15 secs)"));
		labels.put(10, new JLabel("Fast (10 secs)"));
		labels.put(5, new JLabel("Very Fast (5 secs)"));
		sliderUpdateRate.setLabelTable(labels);
		sliderUpdateRate.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				updatedSettings();
			}
		});
		pnlUpdateRate.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlUpdateRate.add(sliderUpdateRate);

		JPanel panel_1 = new JPanel();
		frmSuperCoolRich.getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
				lblRichPresenceSettings = new JLabel("Rich Presence Settings");
				panel_1.add(lblRichPresenceSettings);
				lblRichPresenceSettings.setHorizontalAlignment(SwingConstants.CENTER);
				lblRichPresenceSettings.setFont(new Font("Tahoma", Font.PLAIN, 18));

		pnlDetailsLine = new JPanel();
		panel_1.add(pnlDetailsLine);
		pnlDetailsLine.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Details Line",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		cbxMiiName = new JCheckBox("Display Mii name");
		cbxMiiName.addActionListener(saveSettingsListener);
		pnlDetailsLine.setLayout(new BoxLayout(pnlDetailsLine, BoxLayout.Y_AXIS));
		cbxMiiName.setSelected(true);
		pnlDetailsLine.add(cbxMiiName);

		cbxFriendCode = new JCheckBox("Display friend code");
		cbxFriendCode.addActionListener(saveSettingsListener);
		cbxFriendCode.setSelected(true);
		pnlDetailsLine.add(cbxFriendCode);

		cbxRegion = new JCheckBox("Display your region");
		cbxRegion.addActionListener(saveSettingsListener);
		pnlDetailsLine.add(cbxRegion);

		JPanel pnlStateLine = new JPanel();
		panel_1.add(pnlStateLine);
		pnlStateLine.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "State Line",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		cbxVRBR = new JCheckBox("Display VR/BR");
		cbxVRBR.addActionListener(saveSettingsListener);
		pnlStateLine.setLayout(new BoxLayout(pnlStateLine, BoxLayout.Y_AXIS));
		cbxVRBR.setSelected(true);
		pnlStateLine.add(cbxVRBR);

		cbxDiscrepancyVRBR = new JCheckBox("Display discrepancy in VR/BR");
		cbxDiscrepancyVRBR.addActionListener(saveSettingsListener);
		pnlStateLine.add(cbxDiscrepancyVRBR);

		cbxPlayersInRoom = new JCheckBox("Display number of players in room");
		cbxPlayersInRoom.addActionListener(saveSettingsListener);
		cbxPlayersInRoom.setSelected(true);
		pnlStateLine.add(cbxPlayersInRoom);

		cbxDisplayNumRaces = new JCheckBox("Display number of races played");
		cbxDisplayNumRaces.addActionListener(saveSettingsListener);
		pnlStateLine.add(cbxDisplayNumRaces);

		pnlElapsedTimer = new JPanel();
		panel_1.add(pnlElapsedTimer);
		pnlElapsedTimer.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Elapsed Timer",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		rbtTimeInRoom = new JRadioButton("Display time in room (changes per room)");
		rbtTimeInRoom.addActionListener(saveSettingsListener);
		pnlElapsedTimer.setLayout(new BoxLayout(pnlElapsedTimer, BoxLayout.Y_AXIS));
		buttonGroup.add(rbtTimeInRoom);
		pnlElapsedTimer.add(rbtTimeInRoom);

		rbtTimeInRace = new JRadioButton("Display time for each race");
		rbtTimeInRace.addActionListener(saveSettingsListener);
		buttonGroup.add(rbtTimeInRace);
		rbtTimeInRace.setSelected(true);
		pnlElapsedTimer.add(rbtTimeInRace);

		rbtTimeOverall = new JRadioButton("Display overall play time");
		rbtTimeOverall.addActionListener(saveSettingsListener);
		buttonGroup.add(rbtTimeOverall);
		pnlElapsedTimer.add(rbtTimeOverall);

		JPanel panel_2 = new JPanel();
		frmSuperCoolRich.getContentPane().add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));

		JLabel lblPreview = new JLabel("Preview");
		lblPreview.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(lblPreview);
		lblPreview.setHorizontalAlignment(SwingConstants.CENTER);
		lblPreview.setFont(new Font("Tahoma", Font.PLAIN, 18));

		panel_3 = new JPanel();
		panel_2.add(panel_3);

		JLabel lblPreviewImage = new JLabel("");
		panel_3.add(lblPreviewImage);
		lblPreviewImage.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPreviewImage.setToolTipText("Mario Kart Wii");
		BufferedImage wPic = ImageIO.read(this.getClass().getResource("resources/previewIcon.png"));
		lblPreviewImage.setIcon(new ImageIcon(wPic));

		lblPreviewText = new JLabel("PreviewText");
		panel_3.add(lblPreviewText);
		lblPreviewText.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPreviewText.setVerticalAlignment(SwingConstants.TOP);

		JLabel lblDiscordIcon = new JLabel("");
		lblDiscordIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(lblDiscordIcon);

		JButton btnBeginRichPresence = new JButton("Begin Rich Presence");
		btnBeginRichPresence.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(btnBeginRichPresence);
		btnBeginRichPresence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainInst.setCurrentSettings(getSettings());
				mainInst.requestNewPresence();
			}
		});

		JLabel lblStateIdle = new JLabel("State: Idle");
		lblStateIdle.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(lblStateIdle);
		lblStateIdle.setHorizontalAlignment(SwingConstants.CENTER);

		// Set up settings and preview
		PresenceSettings settings = getSettings();
		WiimmRoom room = createPreviewRoom(settings);
		PresenceMessage previewMessage = new PresenceMessage(room, settings);
		lblPreviewText.setText("<html>" + previewMessage.detailsLine + "<br>" + previewMessage.stateLine);

		mainInst.setCurrentSettings(getSettings());
	}

	class FriendCodeListener implements DocumentListener {
		JTextField nextField;

		public FriendCodeListener(JTextField next) {
			nextField = next;
		}

		public void changedUpdate(DocumentEvent e) {
			updatedSettings();
			if (e.getDocument().getLength() >= 4) {
				if (nextField != null) {
					nextField.setText("");
					nextField.grabFocus();
				}
			}
		}

		public void removeUpdate(DocumentEvent e) {
			updatedSettings();
		}

		public void insertUpdate(DocumentEvent e) {
			updatedSettings();
			if (e.getDocument().getLength() >= 4) {
				if (nextField != null) {
					nextField.setText("");
					nextField.grabFocus();
				}
			}
		}
	}

	class FriendCodeFilter extends DocumentFilter {
		@Override
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
				throws BadLocationException {

			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.insert(offset, string);

			if (test(sb.toString())) {
				super.insertString(fb, offset, string, attr);
			} else {
				// warn the user and don't allow the insert
			}
		}

		private boolean test(String text) {
			if (text.length() > 4)
				return false;
			// Allow null or ""
			if (text == null || text.length() == 0)
				return true;

			try {
				Integer.parseInt(text);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		@Override
		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
				throws BadLocationException {

			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.replace(offset, offset + length, text);

			if (test(sb.toString())) {
				super.replace(fb, offset, length, text, attrs);
			} else {
				// warn the user and don't allow the insert
			}
		}

		@Override
		public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.delete(offset, offset + length);

			if (test(sb.toString())) {
				super.remove(fb, offset, length);
			} else {
				// warn the user and don't allow the insert
			}

		}
	}

	private void updatedSettings() {
		PresenceSettings settings = getSettings();
		WiimmRoom room = createPreviewRoom(settings);
		PresenceMessage previewMessage = new PresenceMessage(room, settings);
		lblPreviewText.setText("<html>" + previewMessage.detailsLine + "<br>" + previewMessage.stateLine);
		// TODO timer

		mainInst.setCurrentSettings(getSettings());
		// Save
	}

	private WiimmRoom createPreviewRoom(PresenceSettings settings) {
		WiimmRoom room = new WiimmRoom();
		WiimmMember member = new WiimmMember();

		room.roomName = "ROOM";
		room.roomStart = new Date();
		room.raceStart = new Date();
		room.numberOfRaces = 1;
		room.numberOfMembers = 1;
		room.numberOfPlayers = 1;

		member.friendCode = settings.friendCode;
		member.names = new String[2];
		member.names[0] = "Player"; // TODO use room from cache?
		member.names[1] = null;
		member.gameRegion = "vs";
		member.userRegion = "Eur/1";
		member.battlePoints = 2357;
		member.versusPoints = 4325;
		member.userRaces = 1;

		room.members = new WiimmMember[1];
		room.members[0] = member;

		return room;
	}

	public PresenceSettings getSettings() {
		PresenceSettings settings = new PresenceSettings();

		settings.displayMiiName = cbxMiiName.isSelected();
		settings.displayFriendCode = cbxFriendCode.isSelected();
		settings.displayRegion = cbxRegion.isSelected();
		settings.displayVRBR = cbxVRBR.isSelected();
		settings.displayDiscrepancyVRBR = cbxDiscrepancyVRBR.isSelected();
		settings.displayNumPlayers = cbxPlayersInRoom.isSelected();
		settings.displayNumRaces = cbxDisplayNumRaces.isSelected();

		if (rbtTimeInRoom.isSelected()) {
			settings.timerSetting = PresenceSettings.TimerSettingType.TIMEINROOM;
		} else if (rbtTimeInRace.isSelected()) {
			settings.timerSetting = PresenceSettings.TimerSettingType.TIMEINRACE;
		} else if (rbtTimeOverall.isSelected()) {
			settings.timerSetting = PresenceSettings.TimerSettingType.OVERALLTIME;
		}

		String friendCode1 = tbxFC1.getText();
		String friendCode2 = tbxFC2.getText();
		String friendCode3 = tbxFC3.getText();
		// TODO sanitise
		settings.friendCode = friendCode1 + "-" + friendCode2 + "-" + friendCode3;

		settings.updateRate = sliderUpdateRate.getValue();
		return settings;
	}

	public void loadSettings(PresenceSettings settings) {
		cbxMiiName.setSelected(settings.displayMiiName);
		cbxFriendCode.setSelected(settings.displayFriendCode);
		cbxRegion.setSelected(settings.displayRegion);
		cbxVRBR.setSelected(settings.displayVRBR);
		cbxDiscrepancyVRBR.setSelected(settings.displayDiscrepancyVRBR);
		cbxPlayersInRoom.setSelected(settings.displayNumPlayers);
		cbxDisplayNumRaces.setSelected(settings.displayNumRaces);

		rbtTimeInRoom.setSelected(false);
		rbtTimeInRace.setSelected(false);
		rbtTimeOverall.setSelected(false);
		switch (settings.timerSetting) {
		case TIMEINROOM:
			rbtTimeInRoom.setSelected(true);
			break;
		case OVERALLTIME:
			rbtTimeOverall.setSelected(true);
			break;
		case TIMEINRACE:
			rbtTimeInRace.setSelected(true);
			break;
		default:
			break;
		}

		String[] friendCodeSplit = settings.friendCode.split("-");
		if (friendCodeSplit.length >= 3) {
			tbxFC1.setText(friendCodeSplit[0]);
			tbxFC2.setText(friendCodeSplit[1]);
			tbxFC3.setText(friendCodeSplit[2]);
		}

		sliderUpdateRate.setValue(settings.updateRate);
	}
}
