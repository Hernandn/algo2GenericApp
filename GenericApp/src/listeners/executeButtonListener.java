package listeners;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

import log.Log;
import parametros.ComboBoxItem;
import parametros.Parametro;
import parametros.ParametroComboBox;
import parametros.Parametro.inputs;
import process.ApplicationWindow;
import process.Consola;

public class executeButtonListener implements Listener 
{
	public static ApplicationWindow applicationWindow;
	
	public executeButtonListener(ApplicationWindow applicationWindowObject)
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
				Log.writeLogMessage(Log.INFO, "Raro que no tenga ninguna validation");
			
			if(!parametro.tieneInput())
	        {
				fullCommand += " " + parametro.flag;
				continue;
	        }
			
			Widget widget = (Widget) iterator_widgets.next();
			
			if(parametro.inputType.equals(inputs.comboBox))
			{
				Combo combo = (Combo) widget;
				ParametroComboBox parametroComboBox = (ParametroComboBox) parametro;
				ArrayList<ComboBoxItem> items = parametroComboBox.getComboBoxItems();
				int index = parametroComboBox.getIndexOfItemSelected();
				fullCommand += " " + items.get(index).getFlag();
				for(int i=0;i < items.get(index).subParametros.size() ; i++)
				{
					widget = (Widget) iterator_widgets.next();
					Parametro nuevoParametro = items.get(index).subParametros.get(i);
					
					fullCommand += applicationWindow.getCommandString(widget, nuevoParametro);
				}
			}
			else
				fullCommand += applicationWindow.getCommandString(widget, parametro);
		}
		Log.writeLogMessage(Log.INFO, fullCommand);
		applicationWindow.shell.setVisible(false);
		new Consola(applicationWindow.display, fullCommand);
	}

}
