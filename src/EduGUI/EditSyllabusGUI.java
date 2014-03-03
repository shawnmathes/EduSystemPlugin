package EduGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import DomainServices.SyllabusService;

@SuppressWarnings("serial")
public class EditSyllabusGUI extends JDialog implements ActionListener {

	JButton SaveBtn;
	JButton CancelBtn;

	JTextArea area = new JTextArea(20, 30);

	String className;

	public EditSyllabusGUI(JFrame parent, String title, String className) {
		super(parent, title, true);

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

		area.setText(SyllabusService.get(className));
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e1) {
		if (e1.getSource() == SaveBtn) {
			SyllabusService.save(className, area.getText().toString());
			setVisible(false);
			dispose();
		} else if (e1.getSource() == CancelBtn) {
			setVisible(false);
			dispose();
		}
	}
}