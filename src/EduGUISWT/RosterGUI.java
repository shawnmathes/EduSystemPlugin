package EduGUISWT;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class RosterGUI extends EduDialog {

	public RosterGUI(Shell parent, int style) {
		super(parent, style);
	}

	public RosterGUI(Shell parent) {
		this(parent, 0);
	}

	public RosterGUI(Shell parent, String title, String[] message) {
		super(parent, title, message);
	}

	@Override
	protected void createContents(final Shell shell) {
		GridLayout layout = new GridLayout(1, false);
		shell.setLayout(layout);
		List list = new List(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		list.setItems(getMessage());
		GridData grid = new GridData(GridData.FILL, GridData.CENTER, true,
				false);
		list.setLayoutData(grid);
	}
}
