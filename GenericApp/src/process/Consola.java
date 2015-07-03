package process;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import actions.ExecuteActionConsola;

public class Consola
{
	Shell shell;
	public String Command;
	public Text text;
	
	public Consola(Display display, final String command)
	{
		shell = new Shell(display);
		Command = command;
		shell.setLayout(new FillLayout());
		shell.setLocation(new Point(80, 80));
		text = new Text(shell, SWT.MULTI | SWT.V_SCROLL | SWT.READ_ONLY);
		shell.setText("Command Output");
		shell.open();
		ExecuteActionConsola executeActionConsola = new ExecuteActionConsola(this);
		Display.getDefault().asyncExec(executeActionConsola);
		while (!shell.isDisposed()) 
		{
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();
		
	}
}
