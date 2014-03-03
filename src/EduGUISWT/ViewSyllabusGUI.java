package EduGUISWT;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import DomainServices.SyllabusService;

public class ViewSyllabusGUI extends EduDialog {

	public ViewSyllabusGUI(Shell parent, int style) {
		super(parent, style);
	}

	public ViewSyllabusGUI(Shell parent) {
		this(parent, 0);
	}

	public ViewSyllabusGUI(Shell parent, String title, String message) {
		super(parent, title, new String[] { message });
	}

	private String getClassName() {
		String[] message = getMessage();
		return message == null || message.length == 0 ? "" : message[0];
	}

	@Override
	protected void createContents(final Shell shell) {
		GridLayout layout = new GridLayout(1, false);
		shell.setLayout(layout);
		String syllabus = SyllabusService.get(getClassName());
		Text syllabusText = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		syllabusText.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		syllabusText.setEditable(false);
		syllabusText.setText(syllabus);
		syllabusText.setBackground(new Color(shell.getDisplay(), 255, 255, 255));
	}
}
