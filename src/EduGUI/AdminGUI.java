package EduGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import Exception.WarningBox;
import Operation.CommonClassData;
import Operation.StudentClassData;
import Operation.SyllabusData;

@SuppressWarnings("serial")
public class AdminGUI extends JDialog implements ActionListener {

	JTextArea area = new JTextArea("Please enter the new course name:", 1, 33);

	JList<String> list;
	JButton AddBtn;
	// JButton DropBtn;
	JButton DeleteBtn;

	ArrayList<String> currentClassList = new ArrayList<String>();

	ArrayList<String> stuInfo = new ArrayList<String>();
	ArrayList<String> stuInfo2 = new ArrayList<String>();

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

		CommonClassData commonClassData = new CommonClassData();

		if (e.getSource() == AddBtn) {
			System.out.println("you have chosen Add Class Button");

			// Check if you select class or not
			if (area.getText().toString().equals("")) {
				WarningBox NoClassWarning = new WarningBox(new JFrame(),
						"No Class Warning", "Please provide a class name");
				return;
			}

			// Check if name contains invalid value
			String regEx = "[`~!@#$%^&*()+=|{}':;'\\[\\],.<>/?~]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(area.getText().toString());
			if (m.find()) {
				WarningBox NoClassWarning = new WarningBox(new JFrame(),
						"Invalid Class name",
						"Please use only number and letter as name");
				return;
			}

			// Check if selected class already there
			currentClassList = commonClassData.getList();
			int checkFlag = 0;

			for (int i = 0; i < currentClassList.size(); i++) {
				if (!currentClassList.get(i).equals("")) {

					if ((currentClassList.get(i).equals(area.getText()
							.toString()))
							|| (currentClassList.get(i).toLowerCase()
									.equals(area.getText().toString()))
							|| (currentClassList.get(i).toUpperCase()
									.equals(area.getText().toString()))) {
						checkFlag = 1;
						WarningBox repeateClassWarning = new WarningBox(
								new JFrame(), "Repeat Class Warning",
								"This Class already exist");
						return;
					}
				}

			}

			if (checkFlag == 0) {
				currentClassList.add(area.getText().toString());
				commonClassData.update(currentClassList);

				list.setListData(getClassListAsArray());

				area.setText("");
				ConfirmationBox confirmation = new ConfirmationBox(
						new JFrame(), "Success Message",
						"Course Created Successfully");

			}

		}

		if (e.getSource() == DeleteBtn) {
			String selectedCourse = (String) (list.getSelectedValue());
			if ((selectedCourse == null) || (selectedCourse.length() == 0)) {
				WarningBox NoClassWarning = new WarningBox(new JFrame(),
						"No Class Warning", "You have no class to delete");
				return;
			} else {
				// if such class has syllabus, delete it
				SyllabusData syllabusData = new SyllabusData();
				syllabusData.delete(selectedCourse);

				// Update info into current common class list
				ArrayList<String> currentList = commonClassData.getList();

				for (int k = 0; k < currentList.size(); k++) {
					if (selectedCourse.equals(currentList.get(k))) {
						currentList.set(k, "");

					}
				}

				commonClassData.update(currentList);

				list.setListData(getClassListAsArray());

				ConfirmationBox confirmation = new ConfirmationBox(
						new JFrame(), "Success Message",
						"Course Deleted Successfully");

				// Update info to student info file
				StudentClassData studentClassData = new StudentClassData();
				stuInfo2 = studentClassData.getClassList();
				for (int j = 0; j < stuInfo2.size(); j++) {
					String id = "";
					if (!stuInfo2.get(j).equals("")) {
						String[] temptemp = stuInfo2.get(j).split(":");
						id = temptemp[0];
						String[] myClassArray = temptemp[1].split(",");

						for (int u = 0; u < myClassArray.length; u++) {

							if (myClassArray[u].equals(selectedCourse)) {
								myClassArray[u] = "delete";
								break;

							}

						}

						String update = "";
						for (int p = 0; p < myClassArray.length; p++) {
							if (!myClassArray[p].equals("delete")) {
								update = update + myClassArray[p] + ",";
							}
						}

						String[] elimilate = update.split(",");
						update = "";
						for (int n = 0; n < elimilate.length; n++) {
							if (!elimilate[n].equals("")) {
								if (n != elimilate.length - 1)
									update = update + elimilate[n] + ",";
								else if (n == elimilate.length - 1)
									update = update + elimilate[n];
							}
						}

						if (!update.equals("")) {
							update = id + ":" + update;
						}

						stuInfo2.set(j, update);

						studentClassData.update(stuInfo2);

					}
				}
			}

		}

	}

	private String[] getClassListAsArray() {
		CommonClassData commonClassData = new CommonClassData();
		ArrayList<String> commonfilelistArray = commonClassData.getList();
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