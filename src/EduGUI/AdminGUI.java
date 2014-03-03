package EduGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Confirmation.ConfirmationBox;
import DomainServices.CommonClassService;
import DomainServices.WarningException;
import Exception.WarningBox;

@SuppressWarnings("serial")
public class AdminGUI extends JDialog implements ActionListener {

	JTextArea area = new JTextArea("Please enter the new course name:", 1, 33);

	JList<String> list;
	JButton AddBtn;
	// JButton DropBtn;
	JButton DeleteBtn;

	public AdminGUI(JFrame parent, String title, String[] message) {
		super(parent, title, true);

		// Add text and label
		JPanel textPane = new JPanel();
		area.selectAll();
		area.setEditable(true);
		area.setBounds(200, 150, 200, 50);
		textPane.add(area);

		// Add list
		list = new JList<String>(message);
		list.addListSelectionListener(new SelectionHandler());
		JScrollPane jsp = new JScrollPane(list);
		jsp.setBounds(200, 200, 400, 300);

		// Add button
		JPanel buttonPane = new JPanel();
		AddBtn = new JButton("Add Class");
		// DropBtn = new JButton("Delete Class");
		DeleteBtn = new JButton("Delete Class");
		buttonPane.add(AddBtn);
		// buttonPane.add(DropBtn);
		buttonPane.add(DeleteBtn);
		AddBtn.addActionListener(this);
		// DropBtn.addActionListener(this);
		DeleteBtn.addActionListener(this);

		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		getContentPane().add(jsp, BorderLayout.CENTER);
		getContentPane().add(textPane, BorderLayout.NORTH);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setBounds(200, 200, 400, 300);
		setVisible(true);

	}

	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == AddBtn) {
				System.out.println("you have chosen Add Class Button");

				CommonClassService.addClass(area.getText().toString());

				list.setListData(getClassListAsArray());

				area.setText("");
				ConfirmationBox confirmation = new ConfirmationBox(
						new JFrame(), "Success Message",
						"Course Created Successfully");
			}

			if (e.getSource() == DeleteBtn) {
				System.out.println("you have chosen Delete Class Button");

				CommonClassService.deleteClass(list.getSelectedValue());

				list.setListData(getClassListAsArray());

				ConfirmationBox confirmation = new ConfirmationBox(
						new JFrame(), "Success Message",
						"Course Deleted Successfully");
			}
		} catch (WarningException e1) {
			WarningBox warningBox = new WarningBox(new JFrame(), e1.getTitle(),
					e1.getMessage());
			return;
		}
	}

	private String[] getClassListAsArray() {
		ArrayList<String> commonfilelistArray = CommonClassService.getList();
		return commonfilelistArray.toArray(new String[commonfilelistArray
				.size()]);
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