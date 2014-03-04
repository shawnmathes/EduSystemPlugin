package EduGUISWT;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import edusystemplugin.extensions.LoggingService;
import DomainServices.CommonClassService;

public class MainGUI extends EduDialog {
	Object result;

	public MainGUI(Shell parent, int style) {
		super(parent, style);
	}

	public MainGUI(Shell parent) {
		this(parent, 0);
	}

	@Override
	public String getText() {
		return "Class Enrollment System";
	}

	public static void main(String[] args) {
		MainGUI gui = new MainGUI(new Shell());
		gui.open();
	}

	@Override
	protected void createContents(final Shell shell) {
		GridLayout layout = new GridLayout(3, false);
		shell.setLayout(layout);

		Button studentButton = new Button(shell, SWT.PUSH);
		studentButton.setText("Student");
		studentButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LoggingService.writeLog("Student pressed");
				StudentGUI dlg = new StudentGUI(getParent(), "Student Account",
						getClassListAsArray());
				dlg.open();
			}
		});

		Button instructorButton = new Button(shell, SWT.PUSH);
		instructorButton.setText("Instructor");
		instructorButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LoggingService.writeLog("Instructor pressed");
				InstructorGUI dlg = new InstructorGUI(getParent(),
						"Instructor Account", getClassListAsArray());
				dlg.open();
			}
		});

		Button adminButton = new Button(shell, SWT.PUSH);
		adminButton.setText("Administrator");
		adminButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LoggingService.writeLog("Administrator pressed");
				AdminGUI dlg = new AdminGUI(getParent(), "Admin Account",
						getClassListAsArray());
				dlg.open();
			}
		});

		Image image = new Image(shell.getDisplay(),
				"C:\\Users\\Shawn Mathes\\workspace\\EduSystemPlugin\\umkc.jpg");
		Label label = new Label(shell, SWT.BORDER);
		GridData grid = new GridData();
		grid.horizontalSpan = 3;
		label.setLayoutData(grid);
		label.setImage(image);
	}

	private String[] getClassListAsArray() {
		ArrayList<String> commonfilelistArray = CommonClassService.getList();
		return commonfilelistArray.toArray(new String[commonfilelistArray
				.size()]);
	}
}