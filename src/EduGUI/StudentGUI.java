package EduGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Confirmation.ConfirmationBox;
import DomainServices.StudentClassService;
import DomainServices.SyllabusService;
import DomainServices.WarningException;
import Exception.WarningBox;

@SuppressWarnings("serial")
public class StudentGUI extends JDialog implements ActionListener {

	JLabel studentIDLabel;

	JTextField studentIDText;

	JTextArea messagesTextArea;
	JList<String> list;
	JButton AddBtn;
	JButton MyListBtn;
	JButton viewSyllabusBtn;

	ArrayList<String> StudentEnrollList = new ArrayList<String>();

	public StudentGUI(JFrame parent, String title, String[] message) {
		super(parent, title, true);

		studentIDLabel = new JLabel("Student ID: ");
		studentIDText = new JTextField(15);

		// Add Student ID Text
		JPanel messagePane = new JPanel();
		messagePane.add(studentIDLabel);
		messagePane.add(studentIDText);
		messagePane.setBounds(200, 200, 800, 490);

		// Add list
		// String[] data =
		// {"CS5590/490-Software Method and Tools","CS5520-Network ArchitectureI",
		// "CS5521-Network ArchitectureII",
		// "CS5551-Advanced Software Engineering",
		// "CS5567-Machine Learning in Bilinformatics","CS5570-Architecture Of Database Management System","CS5573-Information Security and Assurance","CS5560-Knowledge Discovery and Management"};
		list = new JList<String>(message);
		list.addListSelectionListener(new SelectionHandler());
		JScrollPane jsp = new JScrollPane(list);
		jsp.setSize(100, 100);

		// Add button
		JPanel buttonPane = new JPanel();
		AddBtn = new JButton("Add Class");
		MyListBtn = new JButton("My Class List");
		viewSyllabusBtn = new JButton("View Syllabus");
		buttonPane.add(AddBtn);
		buttonPane.add(MyListBtn);
		buttonPane.add(viewSyllabusBtn);
		AddBtn.addActionListener(this);
		MyListBtn.addActionListener(this);
		viewSyllabusBtn.addActionListener(this);

		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		getContentPane().add(jsp, BorderLayout.CENTER);
		getContentPane().add(messagePane, BorderLayout.NORTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setBounds(200, 200, 400, 400);
		setVisible(true);

	}

	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == AddBtn) {
			System.out.println("you have chosen Add Class Button");

			String courseEntry = (String) (list.getSelectedValue());
			try {
				StudentClassService.addClass(studentIDText.getText(), courseEntry);
			} catch (WarningException e1) {
				WarningBox warningBox = new WarningBox(new JFrame(), e1.getTitle(),
						e1.getMessage());
				return;
			}
			
			ConfirmationBox confirmation = new ConfirmationBox(
					new JFrame(), "Success Message",
					"Course Added Successfully");
		}

		if (e.getSource() == MyListBtn) {
			if (studentIDText.getText().toString().length() == 0) {
				WarningBox idWarning1 = new WarningBox(new JFrame(),
						"ID Warning", "Please input ID");
				return;
			} else if ((studentIDText.getText().toString().length() != 8)
					&& (studentIDText.getText().toString().length() != 0)) {
				WarningBox idWarning2 = new WarningBox(new JFrame(),
						"ID Warning", "Student ID must be 8 digit");
				return;
			}

			System.out.println("you have chosen My List Button");
			JFrame frame = new JFrame("My List Access");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			StudentGUIMyList mylist = new StudentGUIMyList(new JFrame(),
					"My List", studentIDText.getText().toString());

		}

		if (e.getSource() == viewSyllabusBtn) {
			String courseEntry = (String) (list.getSelectedValue());
			if ((courseEntry == "") || (courseEntry == null)) {
				WarningBox NoClassWarning = new WarningBox(new JFrame(),
						"No Class Warning", "Must choose one class to view");
				return;
			}

			String syllabus = SyllabusService.get(courseEntry);
			if (syllabus.length() == 0) {
				WarningBox noSyllabusWarning = new WarningBox(new JFrame(),
						"No Syllabus Warning",
						"This class does not have syllabus yet");
				return;
			} else {
				ViewSyllabusGUI dlg = new ViewSyllabusGUI(new JFrame(),
						"ViewSyllabus", courseEntry);

			}

		}

	}

	private class SelectionHandler implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				System.out.println(list.getSelectedValue());
			}
		}
	}

}