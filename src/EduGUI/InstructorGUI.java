package EduGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import DomainServices.StudentClassService;
import Exception.WarningBox;

@SuppressWarnings("serial")
public class InstructorGUI extends JDialog implements ActionListener {

	JLabel studentIDLabel;

	JTextField studentIDText;

	JTextArea messagesTextArea;
	JList<String> list;
	JButton ViewRosterBtn;
	JButton EditSyllabusBtn;

	public InstructorGUI(JFrame parent, String title, String[] message) {
		super(parent, title, true);

		// Add list
		list = new JList<String>(message);
		list.addListSelectionListener(new SelectionHandler());
		JScrollPane jsp = new JScrollPane(list);
		jsp.setSize(100, 100);

		// Add button
		JPanel buttonPane = new JPanel();
		ViewRosterBtn = new JButton("View Roster");
		EditSyllabusBtn = new JButton("Edit Syllabus");

		buttonPane.add(ViewRosterBtn);

		buttonPane.add(EditSyllabusBtn);

		ViewRosterBtn.addActionListener(this);

		EditSyllabusBtn.addActionListener(this);

		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		getContentPane().add(jsp, BorderLayout.CENTER);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setBounds(200, 200, 400, 400);
		setVisible(true);

	}

	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == ViewRosterBtn) {
			String courseEntry = (String) (list.getSelectedValue());

			if ((courseEntry.equals("[]")) || (courseEntry.equals(""))) {
				WarningBox NoClassWarning = new WarningBox(new JFrame(),
						"No Class Warning", "You have to choose a class");
				return;
			} else {
				JFrame frame = new JFrame("Instructor Access");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				RosterGUI dlg = new RosterGUI(new JFrame(), "Roster",
							StudentClassService.viewRoster(courseEntry));
			}
		} else if (e.getSource() == EditSyllabusBtn) {
			String courseEntry = (String) (list.getSelectedValue());

			if ((courseEntry.equals("[]")) || (courseEntry.equals(""))) {
				WarningBox NoClassWarning = new WarningBox(new JFrame(),
						"No Class Warning", "You have to choose a class");
				return;
			} else {
				JFrame frame = new JFrame("Instructor Access");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				EditSyllabusGUI dlg = new EditSyllabusGUI(new JFrame(),
						"EidtSyllabus", courseEntry);
			}

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