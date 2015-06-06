package listeners;

import java.util.Arrays;
import java.util.Iterator;

import log.Log;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;

import process.Aplicacion;
import process.MainWindow;

public class ComboApplicationListener implements SelectionListener  
{
	public MainWindow mainWindow;
	
	public ComboApplicationListener(MainWindow parent) 
	{
		mainWindow = parent;		
	}

	@Override
	public void widgetSelected(SelectionEvent e) 
	{
		Combo combo = mainWindow.combo;
		Iterator<Aplicacion> iterator_app;
		iterator_app = mainWindow.aplicaciones.iterator();
		while (iterator_app.hasNext())
		{
			Aplicacion app = (Aplicacion) iterator_app.next();
			if(app.name.equals(combo.getItem(combo.getSelectionIndex())))
			{
				mainWindow.applicationSelected = app;
			}
		}
		mainWindow.optionSelected = combo.getSelectionIndex();
		Log.writeLogMessage(Log.DEBUG, "Selected index: " + combo.getSelectionIndex() + ", selected item: " + combo.getItem(combo.getSelectionIndex()) + ", text content in the text field: " + combo.getText());
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) 
	{
		Combo combo = mainWindow.combo;
		Log.writeLogMessage(Log.DEBUG, "Default selected index: " + combo.getSelectionIndex() + ", selected item: " + (combo.getSelectionIndex() == -1 ? "<null>" : combo.getItem(combo.getSelectionIndex())) + ", text content in the text field: " + combo.getText());
		String text = combo.getText();
		
		if(combo.indexOf(text) < 0) 
		{ 
			// Not in the list yet. 
			combo.add(text);
			// Re-sort
			String[] items = combo.getItems();
			Arrays.sort(items);
			combo.setItems(items);
		}
	}
}
