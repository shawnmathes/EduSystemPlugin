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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Confirmation.ConfirmationBox;
import DomainServices.StudentClassService;
import Exception.WarningBox;

@SuppressWarnings("serial")
public class StudentGUIMyList extends JDialog implements ActionListener {

	JLabel studentIDLabel;

	JList<String> list;
	JButton DropBtn;

	ArrayList<String> stuInfo = new ArrayList<String>();
	ArrayList<String> stuInfo2 = new ArrayList<String>();
	String msg = "";

	public StudentGUIMyList(JFrame parent, String title, String message) {
		super(parent, title, true);

		String[] data = { "" };
		list = new JList<String>(data);

		msg = message;
		list = new JList<String>(StudentClassService.getClassList(message));
		studentIDLabel = new JLabel("Student ID is " + message);

		// Add Student ID Text
		JPanel messagePane = new JPanel();
		messagePane.add(studentIDLabel);
		messagePane.setBounds(200, 200, 800, 490);

		// Add list
		list.addListSelectionListener(new SelectionHandler());
		JScrollPane jsp = new JScrollPane(list);
		jsp.setSize(100, 100);

		// Add button
		JPanel buttonPane = new JPanel();
		DropBtn = new JButton("Drop Class");
		buttonPane.add(DropBtn);
		DropBtn.addActionListener(this);

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
	public void actionPerformed(ActionEvent e1) {

		if (e1.getSource() == DropBtn) {
			String courseEntry = (String) (list.getSelectedValue());
			if ((courseEntry == "") || (courseEntry == null)) {
				WarningBox NoClassWarning = new WarningBox(new JFrame(),
						"No Class Warning", "You have no class to drop");
				return;
			} else {
				StudentClassService.dropClass(msg, courseEntry);
				list = new JList<String>(StudentClassService.getClassList(msg));

				ConfirmationBox confirmation = new ConfirmationBox(
						new JFrame(), "Drop Message",
						"Course Dropped Successfully");

			}// end of normal drop

		}
	}

	private class SelectionHandler implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				System.out.println((String) (list.getSelectedValue()));
			}
		}
	}

}