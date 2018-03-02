package link.infra.mkwiipresence;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
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
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

public class MKWiiPresenceGUI {

	private JFrame frame;
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
					MKWiiPresenceGUI window = new MKWiiPresenceGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MKWiiPresenceGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 445, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUserSettings = new JLabel("User Settings");
		lblUserSettings.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUserSettings.setBounds(45, 11, 115, 31);
		frame.getContentPane().add(lblUserSettings);
		
		JPanel pnlFriendCode = new JPanel();
		pnlFriendCode.setBorder(new TitledBorder(null, "Friend Code", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlFriendCode.setBounds(10, 50, 180, 43);
		frame.getContentPane().add(pnlFriendCode);
		pnlFriendCode.setLayout(new GridLayout(0, 3, 5, 0));
		
		tbxFC1 = new JTextField();
		tbxFC1.setHorizontalAlignment(SwingConstants.CENTER);
		tbxFC1.setColumns(10);
		pnlFriendCode.add(tbxFC1);
		
		tbxFC2 = new JTextField();
		tbxFC2.setHorizontalAlignment(SwingConstants.CENTER);
		tbxFC2.setColumns(10);
		pnlFriendCode.add(tbxFC2);
		
		tbxFC3 = new JTextField();
		tbxFC3.setHorizontalAlignment(SwingConstants.CENTER);
		tbxFC3.setColumns(10);
		pnlFriendCode.add(tbxFC3);
		
		pnlUpdateRate = new JPanel();
		pnlUpdateRate.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Update Rate", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlUpdateRate.setBounds(10, 104, 180, 271);
		frame.getContentPane().add(pnlUpdateRate);
		pnlUpdateRate.setLayout(null);
		
		JSlider slider = new JSlider();
		slider.setValue(3);
		slider.setBounds(6, 16, 60, 244);
		slider.setMaximum(4);
		slider.setMinimum(1);
		slider.setMinorTickSpacing(1);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setSnapToTicks(true);
		slider.setOrientation(SwingConstants.VERTICAL);
		pnlUpdateRate.add(slider);
		
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
		lblRichPresenceSettings.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRichPresenceSettings.setBounds(227, 11, 187, 31);
		frame.getContentPane().add(lblRichPresenceSettings);
		
		pnlDetailsLine = new JPanel();
		pnlDetailsLine.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Details Line", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlDetailsLine.setBounds(200, 50, 229, 92);
		frame.getContentPane().add(pnlDetailsLine);
		pnlDetailsLine.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		JCheckBox cbxMiiName = new JCheckBox("Display Mii name");
		cbxMiiName.setSelected(true);
		pnlDetailsLine.add(cbxMiiName);
		
		JCheckBox cbxFriendCode = new JCheckBox("Display friend code");
		cbxFriendCode.setSelected(true);
		pnlDetailsLine.add(cbxFriendCode);
		
		JCheckBox cbxRegion = new JCheckBox("Display your region");
		pnlDetailsLine.add(cbxRegion);
		
		JPanel pnlStateLine = new JPanel();
		pnlStateLine.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "State Line", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlStateLine.setBounds(200, 153, 229, 119);
		frame.getContentPane().add(pnlStateLine);
		pnlStateLine.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		JCheckBox cbxVRBR = new JCheckBox("Display VR/BR");
		cbxVRBR.setSelected(true);
		pnlStateLine.add(cbxVRBR);
		
		JCheckBox cbxDiscrepencyVRBR = new JCheckBox("Disply discrepancy in VR/BR");
		pnlStateLine.add(cbxDiscrepencyVRBR);
		
		JCheckBox cbxPlayersInRoom = new JCheckBox("Display number of players in room");
		cbxPlayersInRoom.setSelected(true);
		pnlStateLine.add(cbxPlayersInRoom);
		
		JCheckBox chckbxDisplayNumberOf = new JCheckBox("Display number of races played");
		pnlStateLine.add(chckbxDisplayNumberOf);
		
		pnlElapsedTimer = new JPanel();
		pnlElapsedTimer.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Elapsed Timer", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlElapsedTimer.setBounds(200, 283, 229, 92);
		frame.getContentPane().add(pnlElapsedTimer);
		pnlElapsedTimer.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		JRadioButton rbtTimeInRoom = new JRadioButton("Display time in room (changes per room)");
		buttonGroup.add(rbtTimeInRoom);
		pnlElapsedTimer.add(rbtTimeInRoom);
		
		JRadioButton rbtTimeInRace = new JRadioButton("Display time for each race");
		buttonGroup.add(rbtTimeInRace);
		rbtTimeInRace.setSelected(true);
		pnlElapsedTimer.add(rbtTimeInRace);
		
		JRadioButton rbtTimeOverall = new JRadioButton("Display overall play time");
		buttonGroup.add(rbtTimeOverall);
		pnlElapsedTimer.add(rbtTimeOverall);
		
		JButton btnBeginRichPresence = new JButton("Begin Rich Presence");
		btnBeginRichPresence.setBounds(10, 386, 419, 43);
		frame.getContentPane().add(btnBeginRichPresence);
		
		JLabel lblStateIdle = new JLabel("State: Idle");
		lblStateIdle.setHorizontalAlignment(SwingConstants.CENTER);
		lblStateIdle.setBounds(10, 440, 419, 14);
		frame.getContentPane().add(lblStateIdle);
	}
}
