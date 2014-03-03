package EduGUISWT;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import DomainServices.SyllabusService;

public class EditSyllabusGUI extends EduDialog {

	Text syllabusText;

	public EditSyllabusGUI(Shell parent, int style) {
		super(parent, style);
	}

	public EditSyllabusGUI(Shell parent) {
		this(parent, 0);
	}

	public EditSyllabusGUI(Shell parent, String title, String message) {
		super(parent, title, new String[] { message });
	}

	private String getClassName() {
		String[] message = getMessage();
		return message == null || message.length == 0 ? "" : message[0];
	}

	@Override
	protected void createContents(final Shell shell) {
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);

		syllabusText = new Text(shell, SWT.MULTI | SWT.BORDER);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		syllabusText.setLayoutData(gridData);
		syllabusText.setSize(400, 400);

		Button saveSyllabusButton = new Button(shell, SWT.PUSH);
		saveSyllabusButton.setText("Save Syllabus");
		saveSyllabusButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Save Syllabus pressed");
				SyllabusService.save(getClassName(), syllabusText.getText()
						.toString());
				shell.close();
			}
		});

		Button cancelButton = new Button(shell, SWT.PUSH);
		cancelButton.setText("Cancel");
		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Cancel pressed");
				shell.close();
			}
		});
	}
}
