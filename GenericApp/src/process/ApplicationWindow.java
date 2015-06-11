package process;

import java.util.ArrayList;
import java.util.Iterator;

import listeners.ComboBoxItemListener;
import log.Log;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import parametros.ComboBoxItem;
import parametros.Parametro;
import parametros.ParametroComboBox;
import parametros.Parametro.inputs;

public class ApplicationWindow 
{
	public static Display display;
	public static Shell shell;
	public static Aplicacion actualApplication;
	public static Button okBtn;
	public static Boolean restartWindowFlag;
	
	
	public ApplicationWindow(Display actualDisplay, Aplicacion app)
	{
		actualApplication = app;
		display = actualDisplay;
		restartWindowFlag = false;
	}
	
	public void run()
	{
		showWindow();
	}
	
	public void initializeWindow()
	{
		shell = new Shell(display, SWT.CLOSE | SWT.TITLE );
		
		//shell = new Shell(display);
		final Boolean restartWindow = false;
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		gridLayout.makeColumnsEqualWidth = true;

		shell.setLayout(gridLayout);
		
		// Este segmento se supone que centra el form a la pantalla
		shell.setSize(400, 200);
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
	}
	
	public void addExecuteButton()
	{
		GridData gridData_button = new GridData(GridData.END, GridData.CENTER, false, false);
		gridData_button.horizontalSpan = 3;

		okBtn = new Button(shell, SWT.PUSH);
		okBtn.setText("Execute");
		okBtn.setLayoutData(gridData_button);
	};
	
	public Combo getCombo(String itemSelected)
	{
		Control[] children = shell.getChildren();
		Combo combo = null;
	    for (int i = 0; i < children.length; i++) 
	    {
	      Control child = children[i];
	      if(child instanceof Combo)
	      {
	    	  combo = (Combo) child;
	    	  
	    	  String[] items = combo.getItems();
	    	  for(int j=0; j < items.length; j++)
	    	  {
	    		  if(items[j].equals(itemSelected))
	    			  break;	    		
	    	  }
	    	  
	    	  break;
	      }
	    }
	    return combo;
	}
	
	public void addParameters()
	{
		Iterator<Parametro> iterator_parametros;
		iterator_parametros = actualApplication.parametros.iterator();
		while(iterator_parametros.hasNext())
		{
			Parametro parametro = (Parametro) iterator_parametros.next();
			
			Label aLabel = new Label(shell, SWT.SINGLE);
			aLabel.setText(parametro.label);
			aLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			//para que el input complete la linea actual y quede cada parametro en una linea
			final GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
	        gridData2.horizontalSpan = 2;
	        
	        if(!parametro.tieneInput())
	        {
	        	System.out.println("Parametro sin input");
	        	continue;
	        }
	        
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
			
			else if(parametro.inputType.equals(inputs.textBox))
			{
				Text text = new Text(shell, SWT.SINGLE);
				text.setLayoutData(gridData2);
				continue;
			}
			
			else if(parametro.inputType.equals(inputs.fileInput))
			{
				Text text = new Text(shell, SWT.SINGLE);
				text.setLayoutData(gridData2);
				System.out.println("Incorporar Widget para fileInput");
				continue;
			}
			
			else if(parametro.inputType.equals(inputs.comboBox))
			{
				//Text text = new Text(shell, SWT.SINGLE);
				final Combo combo = new Combo(shell, SWT.SINGLE);
				combo.setLayoutData(gridData2);
				ParametroComboBox parametroComboBox = (ParametroComboBox) parametro;
				final ArrayList<ComboBoxItem> items = parametroComboBox.getComboBoxItems();
				if(items.isEmpty())
					Log.writeLogMessage(Log.ERROR, "No se inicializco correctamente el parametroComboBox");
				
				Iterator<ComboBoxItem> iterator_items;
				iterator_items = items.iterator();
				while (iterator_items.hasNext())
				{
					ComboBoxItem comboBoxItem = (ComboBoxItem) iterator_items.next();
					combo.add(comboBoxItem.getTag());
				}
				
				combo.addSelectionListener(new SelectionListener(){

					@Override
					public void widgetSelected(SelectionEvent e) 
					{
						Log.writeLogMessage(Log.DEBUG, "Default selected index: " + combo.getSelectionIndex() + ", selected item: " + (combo.getSelectionIndex() == -1 ? "<null>" : combo.getItem(combo.getSelectionIndex())) + ", text content in the text field: " + combo.getText());
						
						String itemSelected = combo.getItem(combo.getSelectionIndex());
						
						Shell previousShell = shell;
						initializeWindow();
						addParameters();
						
						Iterator<ComboBoxItem> iterator_items;
						iterator_items = items.iterator();
						while (iterator_items.hasNext())
						{
							ComboBoxItem comboBoxItem = (ComboBoxItem) iterator_items.next();
							if(comboBoxItem.getTag().equals(combo.getText()))
							{
								if(comboBoxItem.getSubParametros().size() > 0)
								{
									ArrayList<Parametro> subParametros = comboBoxItem.getSubParametros();
									Iterator<Parametro> iterator_parametros;
									iterator_parametros = subParametros.iterator();
									
									while (iterator_parametros.hasNext())
									{
								
										Parametro parametro = (Parametro) iterator_parametros.next();
										Log.writeLogMessage(Log.DEBUG, "parametro.label = " + parametro.label);
										
										Label aLabel = new Label(shell, SWT.SINGLE);
										aLabel.setText(parametro.label);
										aLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
					
										Text text = new Text(shell, SWT.SINGLE);
										text.setLayoutData(gridData2);
										
										
									}
								}
								
								Button ExecuteButton = okBtn;
								GridData data = (GridData) ExecuteButton.getLayoutData();
								ExecuteButton.dispose();
								
								addExecuteButton();
								
								Combo elCombo = getCombo(itemSelected);
								if(elCombo == null)
									Log.writeLogMessage(Log.ERROR, "No encontre el Combo, despues de reconstruir la pantalla");
								
								elCombo.setText(itemSelected);
								shell.layout(false);
								shell.pack();
								shell.open();
								
								previousShell.dispose();
								break;
							}
								
						}
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						
					}
					
				});
				
				continue;
			}
			
			else if(parametro.inputType.equals(inputs.checkBox))
			{
				Text text = new Text(shell, SWT.SINGLE);
		        text.setLayoutData(gridData2);
				System.out.println("Incorporar Widget para checkBox");
				continue;
			}
			
			else if(parametro.inputType.equals(inputs.dateTimePicker))
			{
				Text text = new Text(shell, SWT.SINGLE);
				text.setLayoutData(gridData2);
				System.out.println("Incorporar Widget para dateTimePicker");
				continue;
			}
			
			else if(parametro.inputType.equals(inputs.radioButton))
			{
				Text text = new Text(shell, SWT.SINGLE);
				text.setLayoutData(gridData2);
				System.out.println("Incorporar Widget para radioButton");
				continue;
			}
			
			else	//(parametro.inputType == null)
			{
				
				continue;
			}
			//System.out.println("[ERROR] - No se reconoce el input.");
		}
	}
	
	public void showWindow()
	{

		initializeWindow();
		addParameters();
		addExecuteButton();

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

