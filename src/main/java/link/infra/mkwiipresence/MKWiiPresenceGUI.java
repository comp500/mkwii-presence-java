package link.infra.mkwiipresence;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import link.infra.mkwiipresence.WiimmMessages.WiimmMember;
import link.infra.mkwiipresence.WiimmMessages.WiimmRoom;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class MKWiiPresenceGUI {

	private JFrame frmSuperCoolRich;
	private JTextField tbxFC1;
	private JTextField tbxFC2;
	private JTextField tbxFC3;
	private JPanel pnlUpdateRate;
	private JPanel pnlUpdateLabels;
	private JLabel lblSlowSecs;
	private JLabel lblMediumSecs;
	private JLabel lblFastSecs;
	private JLabel lblVeryFast;
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
		frmSuperCoolRich.setResizable(false);
		frmSuperCoolRich.setBounds(100, 100, 445, 600);
		frmSuperCoolRich.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSuperCoolRich.getContentPane().setLayout(null);

		JLabel lblUserSettings = new JLabel("User Settings");
		lblUserSettings.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserSettings.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUserSettings.setBounds(10, 11, 180, 31);
		frmSuperCoolRich.getContentPane().add(lblUserSettings);

		JPanel pnlFriendCode = new JPanel();
		pnlFriendCode
				.setBorder(new TitledBorder(null, "Friend Code", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlFriendCode.setBounds(10, 50, 180, 43);
		frmSuperCoolRich.getContentPane().add(pnlFriendCode);
		pnlFriendCode.setLayout(new GridLayout(0, 3, 5, 0));

		tbxFC1 = new JTextField();
		tbxFC1.setHorizontalAlignment(SwingConstants.CENTER);
		tbxFC1.setColumns(10);
		((PlainDocument) tbxFC1.getDocument()).setDocumentFilter(new FriendCodeFilter());
		pnlFriendCode.add(tbxFC1);

		tbxFC2 = new JTextField();
		tbxFC2.setHorizontalAlignment(SwingConstants.CENTER);
		tbxFC2.setColumns(10);
		((PlainDocument) tbxFC2.getDocument()).setDocumentFilter(new FriendCodeFilter());
		pnlFriendCode.add(tbxFC2);

		tbxFC3 = new JTextField();
		tbxFC3.setHorizontalAlignment(SwingConstants.CENTER);
		tbxFC3.setColumns(10);
		((PlainDocument) tbxFC3.getDocument()).setDocumentFilter(new FriendCodeFilter());
		pnlFriendCode.add(tbxFC3);
		
		tbxFC1.getDocument().addDocumentListener(new FriendCodeListener(tbxFC2));
		tbxFC2.getDocument().addDocumentListener(new FriendCodeListener(tbxFC3));
		tbxFC3.getDocument().addDocumentListener(new FriendCodeListener(null));

		pnlUpdateRate = new JPanel();
		pnlUpdateRate.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Update Rate",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlUpdateRate.setBounds(10, 104, 180, 271);
		frmSuperCoolRich.getContentPane().add(pnlUpdateRate);
		pnlUpdateRate.setLayout(null);

		sliderUpdateRate = new JSlider();
		sliderUpdateRate.setValue(3);
		sliderUpdateRate.setBounds(6, 16, 60, 244);
		sliderUpdateRate.setMaximum(4);
		sliderUpdateRate.setMinimum(1);
		sliderUpdateRate.setMinorTickSpacing(1);
		sliderUpdateRate.setMajorTickSpacing(1);
		sliderUpdateRate.setPaintTicks(true);
		sliderUpdateRate.setSnapToTicks(true);
		sliderUpdateRate.setOrientation(SwingConstants.VERTICAL);
		sliderUpdateRate.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				updatedSettings();
			}
		});
		pnlUpdateRate.add(sliderUpdateRate);

		pnlUpdateLabels = new JPanel();
		pnlUpdateLabels.setBorder(null);
		pnlUpdateLabels.setBounds(76, 16, 97, 244);
		pnlUpdateRate.add(pnlUpdateLabels);
		pnlUpdateLabels.setLayout(null);

		lblSlowSecs = new JLabel("Slow (20 secs)");
		lblSlowSecs.setBounds(0, 0, 71, 14);
		pnlUpdateLabels.add(lblSlowSecs);

		lblMediumSecs = new JLabel("Default (15 secs)");
		lblMediumSecs.setBounds(0, 77, 87, 14);
		pnlUpdateLabels.add(lblMediumSecs);

		lblFastSecs = new JLabel("Fast (10 secs)");
		lblFastSecs.setBounds(0, 153, 87, 14);
		pnlUpdateLabels.add(lblFastSecs);

		lblVeryFast = new JLabel("Very Fast (5 secs)");
		lblVeryFast.setBounds(0, 230, 87, 14);
		pnlUpdateLabels.add(lblVeryFast);

		lblRichPresenceSettings = new JLabel("Rich Presence Settings");
		lblRichPresenceSettings.setHorizontalAlignment(SwingConstants.CENTER);
		lblRichPresenceSettings.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRichPresenceSettings.setBounds(200, 11, 229, 31);
		frmSuperCoolRich.getContentPane().add(lblRichPresenceSettings);

		pnlDetailsLine = new JPanel();
		pnlDetailsLine.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Details Line",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlDetailsLine.setBounds(200, 50, 229, 92);
		frmSuperCoolRich.getContentPane().add(pnlDetailsLine);
		pnlDetailsLine.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		cbxMiiName = new JCheckBox("Display Mii name");
		cbxMiiName.addActionListener(saveSettingsListener);
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
		pnlStateLine.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "State Line",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlStateLine.setBounds(200, 153, 229, 119);
		frmSuperCoolRich.getContentPane().add(pnlStateLine);
		pnlStateLine.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		cbxVRBR = new JCheckBox("Display VR/BR");
		cbxVRBR.addActionListener(saveSettingsListener);
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
		pnlElapsedTimer.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Elapsed Timer",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlElapsedTimer.setBounds(200, 283, 229, 92);
		frmSuperCoolRich.getContentPane().add(pnlElapsedTimer);
		pnlElapsedTimer.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		rbtTimeInRoom = new JRadioButton("Display time in room (changes per room)");
		rbtTimeInRoom.addActionListener(saveSettingsListener);
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

		JButton btnBeginRichPresence = new JButton("Begin Rich Presence");
		btnBeginRichPresence.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainInst.setCurrentSettings(getSettings());
				mainInst.requestNewPresence();
			}
		});
		btnBeginRichPresence.setBounds(10, 492, 419, 43);
		frmSuperCoolRich.getContentPane().add(btnBeginRichPresence);

		JLabel lblStateIdle = new JLabel("State: Idle");
		lblStateIdle.setHorizontalAlignment(SwingConstants.CENTER);
		lblStateIdle.setBounds(10, 546, 419, 14);
		frmSuperCoolRich.getContentPane().add(lblStateIdle);

		JLabel lblPreview = new JLabel("Preview");
		lblPreview.setHorizontalAlignment(SwingConstants.CENTER);
		lblPreview.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPreview.setBounds(10, 386, 419, 31);
		frmSuperCoolRich.getContentPane().add(lblPreview);

		JLabel lblDiscordIcon = new JLabel("");
		lblDiscordIcon.setBounds(45, 428, 46, 14);
		frmSuperCoolRich.getContentPane().add(lblDiscordIcon);

		JLabel lblPreviewImage = new JLabel("");
		lblPreviewImage.setToolTipText("Mario Kart Wii");
		BufferedImage wPic = ImageIO.read(this.getClass().getResource("resources/previewIcon.png"));
		lblPreviewImage.setIcon(new ImageIcon(wPic));
		lblPreviewImage.setBounds(55, 417, 64, 64);
		frmSuperCoolRich.getContentPane().add(lblPreviewImage);

		lblPreviewText = new JLabel("PreviewText");
		lblPreviewText.setVerticalAlignment(SwingConstants.TOP);
		lblPreviewText.setBounds(129, 428, 285, 53);
		frmSuperCoolRich.getContentPane().add(lblPreviewText);

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
