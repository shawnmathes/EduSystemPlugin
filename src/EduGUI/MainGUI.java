package EduGUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DomainServices.CommonClassService;

@SuppressWarnings("serial")
public class MainGUI extends JFrame implements ActionListener {

	static int c;

	String beginPeriod;
	String endPeriod;
	// String NQFfilename ;

	JButton Button = new JButton("Student");
	JButton Button2 = new JButton("Instructor");
	JButton Button3 = new JButton("Administrator");

	JLabel label1;

	JLabel patientAgeLabel;
	JLabel patientGenderLabel;
	JLabel patientAttriRecordTimeLabel;

	JTextField patientAgeText;
	JTextField patientGenderText;
	JTextField patientAttriRecordTimeText;

	BufferedImage myPicture;

	JMenuBar menu;
	JMenu file;
	JMenuItem closeMenu;

	public MainGUI() {

		menu = new JMenuBar();
		file = new JMenu("Class Enrollment System");
		closeMenu = new JMenuItem("Close");
		closeMenu.addActionListener(this);

		Button.addActionListener(this);
		Button2.addActionListener(this);
		Button3.addActionListener(this);

		JPanel closePanel = new JPanel();
		JPanel closePanel2 = new JPanel();
		closePanel.setLayout(new FlowLayout());

		closePanel.add(Button);
		closePanel.add(Button2);
		closePanel.add(Button3);

		File pic = new File(
				"C:\\Users\\Shawn Mathes\\workspace\\EduSystemPlugin\\umkc.jpg");

		Icon icon = new ImageIcon(pic.toString());

		JLabel picLabel = new JLabel(icon);

		// Font font=new Font("Serief", Font.BOLD, 22);
		// picLabel.setFont(font);
		// picLabel.setForeground(Color.RED);

		closePanel2.add(picLabel);

		this.add(closePanel2, BorderLayout.SOUTH);
		this.add(closePanel, BorderLayout.CENTER);
		this.add(menu, BorderLayout.NORTH);
		menu.add(file);
		file.add(closeMenu);
		this.setBounds(200, 200, 400, 400);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		String[] startInit = getClassListAsArray();

		if (e.getSource() == Button) {
			System.out.println("you have chosen student option");
			JFrame frame = new JFrame("Student Access");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			StudentGUI dlg = new StudentGUI(new JFrame(), "Student Account",
					startInit);

		}
		if (e.getSource() == Button2) {
			System.out.println("you have chosen instructor option");
			JFrame frame = new JFrame("Instructor Access");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			InstructorGUI dlg = new InstructorGUI(new JFrame(),
					"Instructor Account", startInit);
		}
		if (e.getSource() == Button3) {
			System.out.println("you have chosen administrator option");
			JFrame frame = new JFrame("Admin Access");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			AdminGUI dlg = new AdminGUI(new JFrame(), "Admin Account",
					startInit);

		}

	}

	private String[] getClassListAsArray() {
		ArrayList<String> commonfilelistArray = CommonClassService.getList();
		return commonfilelistArray.toArray(new String[commonfilelistArray
				.size()]);
	}

}
