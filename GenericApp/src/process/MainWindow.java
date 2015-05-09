package process;

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

public class MainWindow 
{
	public Display display;
	public Shell shell;
	public Combo combo;
	
	public MainWindow(HashSet<String> lista) 
	{
		display = new Display();
		
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
			combo.add((String)iterator.next());
		}
	
		combo.addSelectionListener(new SelectionListener() 
		{
			public void widgetSelected(SelectionEvent e) 
			{
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
		
		/* Solo está porque no me gusta eso de pasarle un new al listener */
		ExecuteAction executeAction = new ExecuteAction();
		executeAction.setMainWindow(this);
		
		executeButton.addListener(SWT.Selection, executeAction);
		/*executeButton.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) 
		      {
		    	  switch (e.type) {
		          case SWT.Selection:
		        	  shell.setVisible(false);
		          	MessageBoxCustom messageBoxCustom = new MessageBoxCustom(shell, display);
		          	messageBoxCustom.MessageBoxError("Qué estas haciendo?");
		            System.out.println("***Button pressed");
		            break;
		          }
		        }
		      });*/

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
