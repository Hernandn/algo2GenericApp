package process;

import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import process.Parametro.inputs;

public class ApplicationWindow 
{
	public static Display display;
	public static Shell shell;
	public static Aplicacion actualApplication;
	
	
	public ApplicationWindow(Display actualDisplay, Aplicacion app)
	{
		actualApplication = app;
		display = actualDisplay;
	}
	
	public void run()
	{
		System.out.println("ApplicationWindow.run()");
		showWindow();
	}
	
	public void showWindow()
	{
		shell = new Shell(display, SWT.CLOSE | SWT.TITLE );
		
		//shell = new Shell(display);
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		gridLayout.makeColumnsEqualWidth = true;

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
	    
	    GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 4;
        //gridData.horizontalAlignment = GridData.FILL;
	    
	    Label label = new Label(shell, SWT.SINGLE);
		label.setText(actualApplication.description);
		label.setLayoutData(gridData);
		
		Iterator<Parametro> iterator_parametros;
		iterator_parametros = actualApplication.parametros.iterator();
		while(iterator_parametros.hasNext())
		{
			Parametro parametro = (Parametro) iterator_parametros.next();
			
			Label aLabel = new Label(shell, SWT.SINGLE);
			aLabel.setText(parametro.label);
			aLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			if(parametro.inputType.equals(inputs.folderInput))
			{
				final Text text = new Text(shell, SWT.PUSH);
				text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				
				Button button = new Button(shell, SWT.PUSH);
			    button.setText("Browse...");
			    button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			    button.addSelectionListener(new SelectionAdapter() {
			        public void widgetSelected(SelectionEvent event) {
			          DirectoryDialog dlg = new DirectoryDialog(shell);

			          // Set the initial filter path according
			          // to anything they've selected or typed in
			          dlg.setFilterPath(text.getText());

			          // Change the title bar text
			          dlg.setText("SWT's DirectoryDialog");

			          // Customizable message displayed in the dialog
			          dlg.setMessage("Select a directory");

			          // Calling open() will open and run the dialog.
			          // It will return the selected directory, or
			          // null if user cancels
			          String dir = dlg.open();
			          if (dir != null) {
			            // Set the text box to the new selection
			            text.setText(dir);
			          }
			        }
			      });
				//System.out.println("Incorporar Widget para folderInput");
				continue;
			}
			
			if(parametro.inputType.equals(inputs.textBox))
			{
				Text text = new Text(shell, SWT.SINGLE);
				continue;
			}
			
			if(parametro.inputType.equals(inputs.fileInput))
			{
				Text text = new Text(shell, SWT.SINGLE);
				System.out.println("Incorporar Widget para fileInput");
				continue;
			}
			
			if(parametro.inputType.equals(inputs.comboBox))
			{
				Text text = new Text(shell, SWT.SINGLE);
				System.out.println("Incorporar Widget para comboBox");
				continue;
			}
			
			if(parametro.inputType.equals(inputs.checkBox))
			{
				Text text = new Text(shell, SWT.SINGLE);
				System.out.println("Incorporar Widget para checkBox");
				continue;
			}
			
			if(parametro.inputType.equals(inputs.dateTimePicker))
			{
				Text text = new Text(shell, SWT.SINGLE);
				System.out.println("Incorporar Widget para dateTimePicker");
				continue;
			}
			
			System.out.println("[ERROR] - No se reconoce el input.");
		}
		
		GridData gridData_button = new GridData(GridData.END, GridData.CENTER, false, false);
		gridData_button.horizontalSpan = 3;
		
		
		Button okBtn = new Button(shell, SWT.PUSH);
		okBtn.setText("Execute");
		okBtn.setLayoutData(gridData_button);

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

