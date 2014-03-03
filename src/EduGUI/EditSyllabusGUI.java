package EduGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import Operation.SyllabusData;

public class EditSyllabusGUI extends JDialog implements ActionListener {

	JButton SaveBtn;
	JButton CancelBtn;

	JTextArea area = new JTextArea(20, 30);

	ArrayList<String> syllabusInfo = new ArrayList<String>();
	ArrayList<String> stuInfo2 = new ArrayList<String>();
	
	String className;

	public EditSyllabusGUI(JFrame parent, String title, String className) {
		super(parent, title, true);
		if (parent != null) {
			Dimension parentSize = parent.getSize();
			Point p = parent.getLocation();
		}
		
		this.className = className;

		// Add button
		JPanel buttonPane = new JPanel();

		SaveBtn = new JButton("Save Syllabus");
		buttonPane.add(SaveBtn);

		CancelBtn = new JButton("Cancel");
		buttonPane.add(CancelBtn);

		SaveBtn.addActionListener(this);
		CancelBtn.addActionListener(this);

		getContentPane().add(area, BorderLayout.NORTH);

		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setBounds(200, 200, 400, 400);
		
		SyllabusData syllabusData = new SyllabusData();
		area.setText(syllabusData.get(className));
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e1) {

		if (e1.getSource() == SaveBtn) {
			SyllabusData syllabusData = new SyllabusData();
			syllabusData.save(className, area.getText().toString());

			setVisible(false);
			dispose();
		} else if (e1.getSource() == CancelBtn) {
			setVisible(false);
			dispose();

		}

	}

}