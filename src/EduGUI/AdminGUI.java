package EduGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
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
import Exception.WarningBox;
import Operation.ReadFile;
import Operation.WriteFile;

public class AdminGUI extends JDialog implements ActionListener {

	JTextArea area = new JTextArea("Please enter the new course name:", 1, 33);

	JList list;
	JButton AddBtn;
	// JButton DropBtn;
	JButton DeleteBtn;

	String studentfilename = "studentManageFile.txt";
	String commonFilename = "commonClassList";

	ArrayList<String> currentClassList = new ArrayList<String>();

	ArrayList<String> stuInfo = new ArrayList<String>();
	ArrayList<String> stuInfo2 = new ArrayList<String>();

	public AdminGUI(JFrame parent, String title, String[] message) {
		super(parent, title, true);
		if (parent != null) {
			Dimension parentSize = parent.getSize();
			Point p = parent.getLocation();
		}

		// Add text and label
		JPanel textPane = new JPanel();
		area.selectAll();
		area.setEditable(true);
		area.setBounds(200, 150, 200, 50);
		textPane.add(area);

		// Add list
		list = new JList(message);
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

	public void actionPerformed(ActionEvent e) {

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
			ReadFile readfile = new ReadFile();
			currentClassList = readfile.ReadFile(commonFilename);
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
				WriteFile writefile = new WriteFile();
				writefile.append(area.getText().toString(), commonFilename);

				ReadFile givevalue = new ReadFile();
				ArrayList<String> refreshlist = new ArrayList<String>();

				refreshlist = givevalue.ReadFile(commonFilename);
				String[] valueInit = new String[refreshlist.size()];
				valueInit = refreshlist.toArray(valueInit);
				list.setListData(valueInit);

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
				File syllabus = new File(selectedCourse);
				if (syllabus.exists()) {
					syllabus.delete();
				}

				// Update info into current common class list
				ReadFile readForcurrent = new ReadFile();
				ArrayList<String> currentList = new ArrayList();
				ArrayList<String> giveValueList = new ArrayList();
				currentList = readForcurrent.ReadFile(commonFilename);

				for (int k = 0; k < currentList.size(); k++) {
					if (selectedCourse.equals(currentList.get(k))) {
						currentList.set(k, "");

					}
				}

				WriteFile writeForcurrent = new WriteFile();
				writeForcurrent.write(currentList, commonFilename);

				ReadFile givevalue = new ReadFile();
				giveValueList = givevalue.ReadFile(commonFilename);
				String[] valueInit = new String[giveValueList.size()];
				valueInit = giveValueList.toArray(valueInit);
				list.setListData(valueInit);

				ConfirmationBox confirmation = new ConfirmationBox(
						new JFrame(), "Success Message",
						"Course Deleted Successfully");

				// Update info to student info file
				ReadFile read2 = new ReadFile();
				stuInfo2 = read2.ReadFile(studentfilename);
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
						} else if (update.equals("")) {
							update = update;
						}

						stuInfo2.set(j, update);

						WriteFile write = new WriteFile();
						write.write(stuInfo2, studentfilename);

					}
				}
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