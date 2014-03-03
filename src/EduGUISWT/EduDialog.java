package EduGUISWT;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class EduDialog extends Dialog {
	Object result;

	private String title;
	private String[] message;

	public EduDialog(Shell parent, int style) {
		super(parent, style);
	}

	public EduDialog(Shell parent) {
		this(parent, 0);
	}

	public EduDialog(Shell parent, String title, String[] message) {
		this(parent, 0);
		this.title = title;
		this.setMessage(message);
	}

	public Object open() {
		Shell parent = getParent();
		Shell shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setText(getText());
		shell.setBounds(200, 200, 400, 400);
		createContents(shell);
		shell.open();
		Display display = parent.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}

	@Override
	public String getText() {
		return title;
	}

	protected void createContents(final Shell shell) {

	}

	public String[] getMessage() {
		return message;
	}

	private void setMessage(String[] message) {
		this.message = message;
	}
}
