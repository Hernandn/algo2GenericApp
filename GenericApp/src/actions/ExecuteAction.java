package actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import process.ApplicationWindow;
import process.MainWindow;
import process.MessageBoxCustom;

public class ExecuteAction implements Listener{

	public MainWindow mainWindow;
	
	public void setMainWindow(MainWindow parent)
	{
		mainWindow = parent;
	}
	
	@Override
	public void handleEvent(Event event) 
	{
		if(mainWindow.applicationSelected == null)
		{
			MessageBoxCustom messageBoxCustom = new MessageBoxCustom(mainWindow.shell, mainWindow.display);
			messageBoxCustom.MessageBoxError("Por favor seleccione una aplicacion.");
			return;
		} 

		switch (event.type) 
        {
        	case SWT.Selection:
        		mainWindow.shell.setVisible(false);
        		ApplicationWindow applicationWindow = new ApplicationWindow(mainWindow.display, mainWindow.applicationSelected);
        		applicationWindow.run();
        		break;
        }
		
	}

}
