package process;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ExecuteAction implements Listener{

	public MainWindow mainWindow; 
	
	public void setMainWindow(MainWindow parent)
	{
		mainWindow = parent;
	}
	
	@Override
	public void handleEvent(Event event) 
	{
		System.out.println("Button pressed = " + event.type);
		switch (event.type) 
        {
        	case SWT.Selection:
        		mainWindow.shell.setVisible(false);
	          	MessageBoxCustom messageBoxCustom = new MessageBoxCustom(mainWindow.shell, mainWindow.display);
	          	messageBoxCustom.MessageBoxError("Qué estas haciendo?");
        		break;
        }
		
	}

}
