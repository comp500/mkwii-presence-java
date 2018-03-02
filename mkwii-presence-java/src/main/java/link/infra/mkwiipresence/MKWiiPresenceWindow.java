package link.infra.mkwiipresence;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class MKWiiPresenceWindow {

	private JFrame frame;
	private JTextField txtFriendcode;
	private JTextField txtFriendcode_1;
	private JTextField txtFriendcode_2;

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
					MKWiiPresenceWindow window = new MKWiiPresenceWindow();
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
	public MKWiiPresenceWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 513, 376);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		JLabel lblUserSettings = new JLabel("User Settings");
		lblUserSettings.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUserSettings.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblUserSettings.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.add(lblUserSettings);
		
		JLabel lblFriendCode = new JLabel("Friend Code");
		lblFriendCode.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(lblFriendCode);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		
		txtFriendcode = new JTextField();
		panel_4.add(txtFriendcode);
		txtFriendcode.setColumns(4);
		
		JLabel label = new JLabel("-");
		panel_4.add(label);
		
		txtFriendcode_1 = new JTextField();
		panel_4.add(txtFriendcode_1);
		txtFriendcode_1.setColumns(4);
		
		JLabel label_1 = new JLabel("-");
		panel_4.add(label_1);
		
		txtFriendcode_2 = new JTextField();
		panel_4.add(txtFriendcode_2);
		txtFriendcode_2.setColumns(4);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		JSlider slider = new JSlider();
		slider.setMinimum(5);
		slider.setMaximum(20);
		slider.setMinorTickSpacing(1);
		slider.setMajorTickSpacing(5);
		slider.setOrientation(SwingConstants.VERTICAL);
		slider.setSnapToTicks(true);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
	    table.put(20, new JLabel("Slow (20 seconds)"));
	    table.put(15, new JLabel("Normal (15 seconds)"));
	    table.put(10, new JLabel("Fast (10 seconds)"));
	    table.put(5, new JLabel("Very Fast (5 seconds)"));
	    slider.setLabelTable(table);
		panel_3.add(slider);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblRichPresenceSettings = new JLabel("Rich Presence Settings");
		lblRichPresenceSettings.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRichPresenceSettings.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblRichPresenceSettings.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel_1.add(lblRichPresenceSettings);
		
		JButton btnNewButton_1 = new JButton("New button");
		panel_1.add(btnNewButton_1);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.SOUTH);
		
		JButton btnBeginRichPresence = new JButton("Begin Rich Presence");
		panel_2.add(btnBeginRichPresence);
	}

}
