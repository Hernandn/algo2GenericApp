package process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import actions.ExecuteAction;

public class MainWindow 
{
	public Display display;
	public Shell shell;
	public Combo combo;
	public int optionSelected;
	public XMLProcess xmlProcess;
	public ArrayList<Aplicacion> aplicaciones;
	
	public MainWindow(ArrayList<Aplicacion> applications)
	{
		display = new Display();
		optionSelected  = -1;
		aplicaciones = applications;
		ArrayList<String> lista = new ArrayList<String>();
		
		Iterator<Aplicacion> iterator_app;
		iterator_app = applications.iterator();
		while (iterator_app.hasNext())
		{
			Aplicacion app = (Aplicacion) iterator_app.next();
			lista.add(app.name);
		}
		
		/* Solo esta porque no me gusta eso de pasarle un new al listener */
		final ExecuteAction executeAction = new ExecuteAction();
		executeAction.setMainWindow(this);
		
		// Solo muestra los botones de cerrar. Oculta el boton Maximizar y Minimizar
		// Se supone que tambien evita que la ventana se agrande (?)
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

	    shell.setText("GenApp"); // Title of ComboBox
	    
	    Label label = new Label(shell, SWT.NULL);
		label.setText("Select your the program to run");
	
		combo = new Combo(shell, SWT.NULL);
		
		Iterator<String> iterator;
		iterator = lista.iterator();
		while (iterator.hasNext())
		{
			String appName = (String)iterator.next(); 
			//System.out.println("Appname iterator = " + appName);
			combo.add(appName);
		}
	
		combo.addSelectionListener(new SelectionListener() 
		{
			public void widgetSelected(SelectionEvent e) 
			{
				optionSelected = combo.getSelectionIndex();
				System.out.println("Selected index: " + combo.getSelectionIndex() + ", selected item: " + combo.getItem(combo.getSelectionIndex()) + ", text content in the text field: " + combo.getText());
			}
			
			public void widgetDefaultSelected(SelectionEvent e) 
			{
				System.out.println("Default selected index: " + combo.getSelectionIndex() + ", selected item: " + (combo.getSelectionIndex() == -1 ? "<null>" : combo.getItem(combo.getSelectionIndex())) + ", text content in the text field: " + combo.getText());
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
		});
		
		GridData gridData_combo = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
		GridData gridData = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
		
		combo.setLayoutData(gridData_combo);
		
		Button executeButton = new Button(shell, SWT.PUSH); 
		executeButton.setText("Execute");
		executeButton.setLayoutData(gridData);

		//TODO: Comentado para que no rompa al hacer click.
		//executeButton.addListener(SWT.Selection, executeAction);

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
	
	
	public MainWindow(XMLProcess xmldata)
	{
		xmlProcess = xmldata;
		//HashSet<String> lista = xmlProcess.getListApps(1);
		ArrayList<String> lista = xmlProcess.getListApps(1);
		
		display = new Display();
		optionSelected  = -1;
		
		/* Solo está porque no me gusta eso de pasarle un new al listener */
		final ExecuteAction executeAction = new ExecuteAction();
		executeAction.setMainWindow(this);

		// Solo muestra los botones de cerrar. Oculta el boton Maximizar y Minimizar
		// Se supone que tambien evita que la ventana se agrande (?)
		shell = new Shell(display, SWT.CLOSE | SWT.TITLE );
		
		//TODO: Opcion 1
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.horizontalSpacing = SWT.CENTER;
		shell.setLayout(gridLayout);
		
		//TODO: Opcion 2
//		FillLayout fillLayout = new FillLayout();
//		fillLayout.type = SWT.VERTICAL;
//		shell.setLayout(fillLayout);
		
		// Este segmento se supone que centra el form a la pantalla
		shell.setSize(200, 200);
		Monitor primary = display.getPrimaryMonitor();
	    Rectangle bounds = primary.getBounds();
	    Rectangle rect = shell.getBounds();
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 2;
	    shell.setLocation(x, y);

	    shell.setText("GenApp"); // Title of ComboBox
		
		Label label = new Label(shell, SWT.NULL);
		label.setText("Select your the program to run");
	
		combo = new Combo(shell, SWT.NULL);
		
		Iterator<String> iterator;
		iterator = lista.iterator();
		while (iterator.hasNext())
		{
			String appName = (String)iterator.next(); 
			//System.out.println("Appname iterator = " + appName);
			combo.add(appName);
		}
	
		combo.addSelectionListener(new SelectionListener() 
		{
			public void widgetSelected(SelectionEvent e) 
			{
				optionSelected = combo.getSelectionIndex();
				System.out.println("Selected index: " + combo.getSelectionIndex() + ", selected item: " + combo.getItem(combo.getSelectionIndex()) + ", text content in the text field: " + combo.getText());
			}
			
			public void widgetDefaultSelected(SelectionEvent e) 
			{
				System.out.println("Default selected index: " + combo.getSelectionIndex() + ", selected item: " + (combo.getSelectionIndex() == -1 ? "<null>" : combo.getItem(combo.getSelectionIndex())) + ", text content in the text field: " + combo.getText());
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
		});
		
		GridData gridData_combo = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
		GridData gridData = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
		
		combo.setLayoutData(gridData_combo);
		
		Button executeButton = new Button(shell, SWT.PUSH); 
		executeButton.setText("Execute");
		executeButton.setLayoutData(gridData);

		executeButton.addListener(SWT.Selection, executeAction);

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
