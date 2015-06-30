package process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Consola
{
	Shell shell;
	
	public Consola(Display display, final String command)
	{
		shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setLocation(new Point(80, 80));
		final Text text = new Text(shell, SWT.MULTI | SWT.V_SCROLL | SWT.READ_ONLY);
		shell.setText("Output of the command");
		shell.open();
		Display.getDefault().asyncExec(new Runnable() {
			public void run() 
			{
				Process p;
				try 
				{
					p = Runtime.getRuntime().exec("cmd /c "+ command);
					BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream())); 
					String line = null; 
					while ((line = in.readLine()) != null) 
					{  
		                text.append(line + "\n");
		            }
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		
	}
}
