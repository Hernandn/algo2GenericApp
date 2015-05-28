package process;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ApplicationWindow 
{
	public NodeList nodeList;
	public static Display display;
	public static Shell shell;
	public static Aplicacion actualApplication;
	
	
	public ApplicationWindow(Display actualDisplay, Aplicacion app)
	{
		actualApplication = app;
		display = actualDisplay;
//		shell = mainWindow.shell;
	}
	
	public void run()
	{
		System.out.println("ApplicationWindow.run()");
		showWindow();
	}
	
	public void showWindow()
	{
		shell = new Shell(display, SWT.CLOSE | SWT.TITLE );
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.horizontalSpacing = SWT.CENTER;
		shell.setLayout(gridLayout);
		
		// Este segmento se supone que centra el form a la pantalla
		
		shell.setSize(200, 200);
		Monitor primary = display.getPrimaryMonitor();
	    Rectangle bounds = primary.getBounds();
	    Rectangle rect = shell.getBounds();
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 2;
	    shell.setLocation(x, y);
	    
	    shell.setText(actualApplication.name);
	    
	    Label label = new Label(shell, SWT.NULL);
		label.setText(actualApplication.description);

	    shell.pack();
		shell.open();
		//textUser.forceFocus();
		// Set up the event loop.
		while (!shell.isDisposed()) 
		{
			
			if (!display.readAndDispatch()) 
			{
				// If no more entries in event queue
				display.sleep();
			}
		}
		
		
		display.dispose();
	}
}
