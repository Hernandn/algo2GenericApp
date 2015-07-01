package listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

import parametros.ComboBoxItem;
import parametros.Parametro;
import parametros.Parametro.inputs;
import parametros.ParametroComboBox;
import process.ApplicationWindow;
import process.Consola;
import process.MessageBoxCustom;

public class ExecuteButtonListener implements Listener 
{
	private static final Logger logger = Logger.getLogger(ExecuteButtonListener.class.getName());
	
	public static ApplicationWindow applicationWindow;
	
	public ExecuteButtonListener(ApplicationWindow applicationWindowObject)
	{
		applicationWindow = applicationWindowObject;
	}
	
	@Override
	public void handleEvent(Event arg0) 
	{
		String fullCommand = "";
		
		if(!ApplicationWindow.actualApplication.exePath.equals(""))
			fullCommand += ApplicationWindow.actualApplication.exePath;
		else
			fullCommand += ApplicationWindow.actualApplication.command;
		
		Iterator<Parametro> iterator_parametros;
		iterator_parametros = ApplicationWindow.actualApplication.parametros.iterator();
		
		Iterator<Widget> iterator_widgets = ApplicationWindow.parametrosCargados.iterator();
		
		while(iterator_parametros.hasNext())
		{
			Parametro parametro = (Parametro) iterator_parametros.next();
			
			if(parametro.validation == null)
				//Log.writeLogMessage(Log.INFO, "Raro que no tenga ninguna validation");
				logger.info("Raro que no tenga ninguna validation");
			
			if(!parametro.tieneInput())
	        {
				fullCommand += " " + parametro.flag;
				continue;
	        }
			
			Widget widget = (Widget) iterator_widgets.next();
			String string;
			
			if(parametro.inputType.equals(inputs.comboBox))
			{
				ParametroComboBox parametroComboBox = (ParametroComboBox) parametro;
				ArrayList<ComboBoxItem> items = parametroComboBox.getComboBoxItems();
				int index = parametroComboBox.getIndexOfItemSelected();
				if(index == -1)
				{
					MessageBoxCustom messageBoxCustom = new MessageBoxCustom(ApplicationWindow.shell, ApplicationWindow.display);
					messageBoxCustom.MessageBoxError("Por favor seleccione una de las opciones");
					return;
				}	
				
				fullCommand += " " + items.get(index).getFlag();
				for(int i=0;i < items.get(index).subParametros.size() ; i++)
				{
					widget = (Widget) iterator_widgets.next();
					Parametro nuevoParametro = items.get(index).subParametros.get(i);
					
					string = applicationWindow.getCommandString(widget, nuevoParametro);
					if(string.equals(""))
						return;
					
					fullCommand += string;
				}
			}
			else
			{
				string = applicationWindow.getCommandString(widget, parametro);
				if(string.equals(""))
					return;
				
				fullCommand += string;
			}
		}
		
		//Log.writeLogMessage(Log.INFO, fullCommand);
		logger.info("fullCommand: " + fullCommand);
		
		ApplicationWindow.shell.setVisible(false);
		new Consola(ApplicationWindow.display, fullCommand);
	}

}
