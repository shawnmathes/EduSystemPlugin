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
import org.eclipse.swt.widgets.Text;

import DomainServices.StudentClassService;
import DomainServices.SyllabusService;
import DomainServices.WarningException;

public class StudentGUI extends EduDialog {

	List courseList;
	Text studentIDText;

	public StudentGUI(Shell parent, int style) {
		super(parent, style);
	}

	public StudentGUI(Shell parent) {
		this(parent, 0);
	}

	public StudentGUI(Shell parent, String title, String[] message) {
		super(parent, title, message);
	}

	@Override
	protected void createContents(final Shell shell) {
		GridLayout layout = new GridLayout(3, false);
		shell.setLayout(layout);

		Label studentIdLabel = new Label(shell, SWT.NONE);
		studentIdLabel.setText("Student ID:");

		studentIDText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		studentIDText.setLayoutData(gridData);

		courseList = new List(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		courseList.setItems(getMessage());
		GridData grid = new GridData(GridData.FILL, GridData.CENTER, true,
				false);
		grid.horizontalSpan = 3;
		courseList.setLayoutData(grid);
		courseList.setSize(400, 400);

		Button addClassButton = new Button(shell, SWT.PUSH);
		addClassButton.setText("Add Class");
		addClassButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Add Class pressed");
				String[] selections = courseList.getSelection();
				String courseEntry = selections.length == 0 ? ""
						: selections[0];
				try {
					StudentClassService.addClass(studentIDText.getText(),
							courseEntry);
				} catch (WarningException e1) {
					MessageDialog.openWarning(getParent(), e1.getTitle(),
							e1.getMessage());
					return;
				}

				MessageDialog.openInformation(getParent(), "Success Message",
						"Course Added Successfully");
			}
		});

		Button myClassListButton = new Button(shell, SWT.PUSH);
		myClassListButton.setText("My Class List");
		myClassListButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("My Class List pressed");
				if (studentIDText.getText().toString().length() == 0) {
					MessageDialog.openWarning(getParent(), "ID Warning",
							"Please input ID");
					return;
				} else if ((studentIDText.getText().toString().length() != 8)
						&& (studentIDText.getText().toString().length() != 0)) {
					MessageDialog.openWarning(getParent(), "ID Warning",
							"Student ID must be 8 digit");
					return;
				}

				StudentGUIMyList mylist = new StudentGUIMyList(getParent(),
						"My List", studentIDText.getText().toString());
				mylist.open();
			}
		});

		Button viewSyllabusButton = new Button(shell, SWT.PUSH);
		viewSyllabusButton.setText("View Syllabus");
		viewSyllabusButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("View Syllabus pressed");
				String[] selections = courseList.getSelection();
				String courseEntry = selections.length == 0 ? ""
						: selections[0];
				if ((courseEntry == "") || (courseEntry == null)) {
					MessageDialog.openWarning(getParent(), "No Class Warning",
							"Must choose one class to view");
					return;
				}

				String syllabus = SyllabusService.get(courseEntry);
				if (syllabus.length() == 0) {
					MessageDialog.openWarning(getParent(),
							"No Syllabus Warning",
							"This class does not have syllabus yet");
					return;
				} else {
					ViewSyllabusGUI dlg = new ViewSyllabusGUI(getParent(),
							"ViewSyllabus", courseEntry);
					dlg.open();
				}
			}
		});
	}
}
