package process;

import java.util.ArrayList;

import java.util.Iterator;

import listeners.ComboApplicationListener;

import org.eclipse.swt.SWT;
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
	public Aplicacion applicationSelected;
	
	public MainWindow(ArrayList<Aplicacion> applications)
	{
		display = new Display();
		optionSelected  = -1;
		applicationSelected = null;
		aplicaciones = applications;
		ArrayList<String> lista = new ArrayList<String>();
		
		Iterator<Aplicacion> iterator_app;
		iterator_app = aplicaciones.iterator();
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
			combo.add(appName);
		}
		
		// Le cree una implementacion porque no soporto pasarle toda la especificacion de los metodos :D
		ComboApplicationListener selectionListener = new ComboApplicationListener(this);
		combo.addSelectionListener(selectionListener);
		
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
