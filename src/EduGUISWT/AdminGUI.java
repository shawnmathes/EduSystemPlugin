package EduGUISWT;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edusystemplugin.extensions.LoggingService;
import DomainServices.CommonClassService;
import DomainServices.WarningException;

public class AdminGUI extends EduDialog {

	Text addCourseText;
	List courseList;

	public AdminGUI(Shell parent, int style) {
		super(parent, style);
	}

	public AdminGUI(Shell parent) {
		this(parent, 0);
	}

	public AdminGUI(Shell parent, String title, String[] message) {
		super(parent, title, message);
	}

	@Override
	protected void createContents(final Shell shell) {
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);

		addCourseText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		addCourseText.setLayoutData(gridData);
		addCourseText.setText("Please enter the new course name:");

		courseList = new List(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		courseList.setItems(getMessage());
		GridData grid =  new GridData(SWT.FILL, SWT.FILL, true, true);
		grid.horizontalSpan = 2;
		courseList.setLayoutData(grid);

		Button addClassButton = new Button(shell, SWT.PUSH);
		addClassButton.setText("Add Class");
		addClassButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LoggingService.writeDebug("Add Class pressed");
				try {
					CommonClassService.addClass(addCourseText.getText()
							.toString());
				} catch (WarningException e1) {
					MessageDialog.openWarning(getParent(), e1.getTitle(),
							e1.getMessage());
					return;
				}

				courseList.setItems(getClassListAsArray());
				addCourseText.setText("");
				MessageDialog.openInformation(getParent(), "Success Message",
						"Course Created Successfully");
			}
		});

		Button myClassListButton = new Button(shell, SWT.PUSH);
		myClassListButton.setText("Delete Class");
		myClassListButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LoggingService.writeDebug("Delete Class pressed");
				String[] selections = courseList.getSelection();
				String courseEntry = selections.length == 0 ? ""
						: selections[0];
				try {
					CommonClassService.deleteClass(courseEntry);
				} catch (WarningException e1) {
					MessageDialog.openWarning(getParent(), e1.getTitle(),
							e1.getMessage());
					return;
				}

				courseList.setItems(getClassListAsArray());
				MessageDialog.openInformation(getParent(), "Success Message",
						"Course Deleted Successfully");
			}
		});
	}

	private String[] getClassListAsArray() {
		ArrayList<String> commonfilelistArray = CommonClassService.getList();
		return commonfilelistArray.toArray(new String[commonfilelistArray
				.size()]);
	}
}