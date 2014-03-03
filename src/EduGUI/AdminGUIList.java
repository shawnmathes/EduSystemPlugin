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

import Exception.WarningBox;
import Operation.CommonClassData;
import Operation.Configuration;
import Operation.ReadFile;
import Operation.StudentClassData;
import Operation.WriteFile;

public class AdminGUIList extends JDialog implements ActionListener {

	JList list;
	JButton DeleteBtn;


	ArrayList<String> stuInfo = new ArrayList<String>();
	ArrayList<String> stuInfo2 = new ArrayList<String>();
	String msg = "";

	public AdminGUIList(JFrame parent, String title, String[] message) {
		super(parent, title, true);
		if (parent != null) {
			Dimension parentSize = parent.getSize();
			Point p = parent.getLocation();
		}

		list = new JList(message);

		// Add list
		list.addListSelectionListener(new SelectionHandler());
		JScrollPane jsp = new JScrollPane(list);
		jsp.setSize(100, 100);

		// Add button
		JPanel buttonPane = new JPanel();
		DeleteBtn = new JButton("Delete Class");
		buttonPane.add(DeleteBtn);
		DeleteBtn.addActionListener(this);

		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		getContentPane().add(jsp, BorderLayout.CENTER);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setBounds(200, 200, 400, 400);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent e1) {

		if (e1.getSource() == DeleteBtn) {
			if ((Arrays.toString(list.getSelectedValues()).equals("[]"))
					|| (Arrays.toString(list.getSelectedValues()).equals(""))) {
				WarningBox NoClassWarning = new WarningBox(new JFrame(),
						"No Class Warning", "You have no class to drop");
				return;
			} else {
				String[] dropClassArray = Arrays.toString(
						list.getSelectedValues()).split("\\[");
				String[] dropClassArray2 = dropClassArray[2].split("\\]");
				String dropClass = "[" + dropClassArray2[0] + "]";

				// Update info into current common class list
				CommonClassData commonClassData = new CommonClassData();
				ArrayList<String> currentList = commonClassData.getList();

				for (int k = 0; k < currentList.size(); k++) {
					if (dropClass.equals(currentList.get(k))) {
						currentList.set(k, "");

					}
				}

				commonClassData.update(currentList);

				list.setListData(getClassListAsArray());

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

							if (myClassArray[u].equals(dropClass)) {
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
						studentClassData.update(stuInfo2);

					}
				}
			}

		}

	}
	
	private String[] getClassListAsArray() {
		CommonClassData commonClassData = new CommonClassData();
		ArrayList<String> commonfilelistArray = commonClassData.getList();
		return commonfilelistArray.toArray(new String[commonfilelistArray.size()]);
	}

	private class SelectionHandler implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				System.out.println(Arrays.toString(list.getSelectedValues()));
			}
		}
	}

}