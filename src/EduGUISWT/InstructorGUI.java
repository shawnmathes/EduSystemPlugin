package EduGUISWT;

import javax.swing.JFrame;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import DomainServices.StudentClassService;

public class InstructorGUI extends EduDialog {

	List courseList;

	public InstructorGUI(Shell parent, int style) {
		super(parent, style);
	}

	public InstructorGUI(Shell parent) {
		this(parent, 0);
	}

	public InstructorGUI(Shell parent, String title, String[] message) {
		super(parent, title, message);
	}

	@Override
	protected void createContents(final Shell shell) {
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);

		courseList = new List(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		courseList.setItems(getMessage());
		GridData grid = new GridData(GridData.FILL, GridData.CENTER, true,
				false);
		grid.horizontalSpan = 2;
		courseList.setLayoutData(grid);

		Button viewRosterButton = new Button(shell, SWT.PUSH);
		viewRosterButton.setText("View Roster");
		viewRosterButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("View Roster pressed");
				String[] selections = courseList.getSelection();
				String courseEntry = selections.length == 0 ? ""
						: selections[0];
				if ((courseEntry.equals("[]")) || (courseEntry.equals(""))) {
					MessageDialog.openWarning(getParent(), "No Class Warning",
							"You have to choose a class");
					return;
				} else {
					RosterGUI dlg = new RosterGUI(getParent(), "Roster",
							StudentClassService.viewRoster(courseEntry));
					dlg.open();
				}
			}
		});

		Button editSyllabusButton = new Button(shell, SWT.PUSH);
		editSyllabusButton.setText("Edit Syllabus");
		editSyllabusButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Edit Syllabus pressed");
				String[] selections = courseList.getSelection();
				String courseEntry = selections.length == 0 ? ""
						: selections[0];
				if ((courseEntry.equals("[]")) || (courseEntry.equals(""))) {
					MessageDialog.openWarning(getParent(), "No Class Warning",
							"You have to choose a class");
					return;
				} else {
					JFrame frame = new JFrame("Instructor Access");
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

					EditSyllabusGUI dlg = new EditSyllabusGUI(getParent(),
							"EidtSyllabus", courseEntry);
					dlg.open();
				}
			}
		});
	}
}
