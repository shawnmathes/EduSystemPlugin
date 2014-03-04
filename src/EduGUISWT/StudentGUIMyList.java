package EduGUISWT;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import edusystemplugin.extensions.LoggingService;
import DomainServices.StudentClassService;

public class StudentGUIMyList extends EduDialog {

	List courseList;

	public StudentGUIMyList(Shell parent, int style) {
		super(parent, style);
	}

	public StudentGUIMyList(Shell parent) {
		this(parent, 0);
	}

	public StudentGUIMyList(Shell parent, String title, String message) {
		super(parent, title, new String[] { message });
	}

	private String getStudentID() {
		String[] message = getMessage();
		return message == null || message.length == 0 ? "" : message[0];
	}

	@Override
	protected void createContents(final Shell shell) {
		GridLayout layout = new GridLayout(1, false);
		shell.setLayout(layout);

		Label studentIdLabel = new Label(shell, SWT.NONE);
		studentIdLabel.setText("Student ID is " + getStudentID());

		courseList = new List(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		courseList.setItems(StudentClassService.getClassList(getStudentID()));
		GridData grid =  new GridData(SWT.FILL, SWT.FILL, true, true);
		grid.horizontalSpan = 3;
		courseList.setLayoutData(grid);

		Button dropClassButton = new Button(shell, SWT.PUSH);
		dropClassButton.setText("Drop Class");
		dropClassButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LoggingService.writeDebug("Drop Class pressed");
				String[] selections = courseList.getSelection();
				String courseEntry = selections.length == 0 ? ""
						: selections[0];
				if ((courseEntry == "") || (courseEntry == null)) {
					MessageDialog.openWarning(getParent(), "No Class Warning",
							"You have no class to drop");
					return;
				} else {
					StudentClassService.dropClass(getStudentID(), courseEntry);
					courseList.setItems(StudentClassService
							.getClassList(getStudentID()));
					MessageDialog.openInformation(getParent(), "Drop Message",
							"Course Dropped Successfully");
				}
			}
		});
	}
}
