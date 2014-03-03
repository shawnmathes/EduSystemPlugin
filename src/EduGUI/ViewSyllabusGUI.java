package EduGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import Operation.SyllabusData;

public class ViewSyllabusGUI extends JDialog implements ActionListener {

	JTextArea area = new JTextArea(20, 30);
	ArrayList<String> syllabusInfo = new ArrayList<String>();

	public ViewSyllabusGUI(JFrame parent, String title, String className) {
		super(parent, title, true);
		if (parent != null) {
			Dimension parentSize = parent.getSize();
			Point p = parent.getLocation();
		}

		SyllabusData syllabusData = new SyllabusData();
		area.setText(syllabusData.get(className));
		area.setEditable(false);

		JScrollPane jsp = new JScrollPane(area);
		jsp.setSize(100, 100);

		getContentPane().add(jsp, BorderLayout.CENTER);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setBounds(200, 200, 400, 400);

		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e1) {

	}
}
