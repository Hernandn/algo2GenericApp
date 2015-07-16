package process;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import listeners.ExecuteButtonListener;
import log.Log;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import parametros.ComboBoxItem;
import parametros.Parametro;
import parametros.Parametro.inputs;
import parametros.ParametroComboBox;
import parametros.ParametroDate;
import parametros.ParametroRadioButton;
import parametros.RadioButtonItem;

public class ApplicationWindow 
{
	private static final Logger logger = Logger.getLogger(ApplicationWindow.class.getName());
	
	public static Display display;
	public static Shell shell;
	public static Aplicacion actualApplication;
	public static Button okBtn;
	public static ArrayList<Widget> parametrosCargados;
	
	
	public ApplicationWindow(Display actualDisplay, Aplicacion app)
	{
		actualApplication = app;
		display = actualDisplay;	
		parametrosCargados = new ArrayList<Widget>();
	}
	
	public void run()
	{
		showWindow();
	}
	
	public void initializeWindow()
	{
		shell = new Shell(display, SWT.CLOSE | SWT.TITLE );
		
		//shell = new Shell(display);
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		gridLayout.makeColumnsEqualWidth = true;
		gridLayout.horizontalSpacing = 15;
		gridLayout.marginWidth = 15;
		gridLayout.marginHeight = 15;

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
		//para dejar un espacio abajo del titulo
		Label label2 = new Label(shell, SWT.SINGLE);
		label2.setText("");
		label2.setLayoutData(gridData); 
	}
	
	public void addExecuteButton()
	{
		GridData gridData_button = new GridData(GridData.END, GridData.CENTER, false, false);
		gridData_button.horizontalSpan = 4;

		//para dejar un espacio arriba del boton
		Label label3 = new Label(shell, SWT.SINGLE);
		label3.setText("");
		label3.setLayoutData(gridData_button); 
		
		okBtn = new Button(shell, SWT.PUSH);
		okBtn.setText("Execute");
		okBtn.setLayoutData(gridData_button);
		Listener listener = new ExecuteButtonListener(this);
		okBtn.addListener(SWT.Selection, listener);
	};
	
	public String getCommandString(Widget widget, Parametro parametro)
	{
		if(parametro.inputType.equals(inputs.comboBox))
		{
			Combo comboBox = (Combo) widget;
			String text = comboBox.getText();
			
			if( parametro.validation != null && !parametro.validation.validateInput(text))
			{
				logger.severe("Error de validacion");
				
				MessageBoxCustom messageBoxCustom = new MessageBoxCustom(shell, display);
				messageBoxCustom.MessageBoxError("Error de Validacion en comboBox");
								
				return "";
			}
			
			return text;
		}
		
		
		if(parametro.inputType.equals(inputs.checkBox))
		{
			Button checkBox = (Button) widget;
			if(checkBox.getSelection())	//agrega el flag si el checkBox esta tildado
			{
				return " " + parametro.flag;
			}
			
			return "";
		}
		
		if(parametro.inputType.equals(inputs.dateTimePicker))
		{
			
			DateTime calendar = (DateTime) widget;
			LocalDate date = LocalDate.of(calendar.getYear(),calendar.getMonth()+1,calendar.getDay());	//el month+1 es por un tema del LocalDate
			String pattern = ((ParametroDate) parametro).getFormat();
			DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
			return " " + date.format(format);
		}
		
		if(parametro.inputType.equals(inputs.textBox))
		{
			Text text = (Text) widget;
			//TODO: Terminar el tema de Validacion. Mucho test
			if( parametro.validation != null && !parametro.validation.validateInput(text.getText()))
			{
				logger.severe("Error de validación");
				
				MessageBoxCustom messageBoxCustom = new MessageBoxCustom(shell, display);
				messageBoxCustom.MessageBoxError("Error de Validacion en textBox");
				return "";
			}
			
			if(parametro.flag == null)
				return " " + text.getText();
			else
				return " "+ parametro.flag + " " + text.getText();
			
		}
		
		if(parametro.inputType.equals(inputs.fileInput))
		{
			Text text = (Text) widget;
			if( parametro.validation != null && !parametro.validation.validateInput(text.getText()))
			{	
				logger.severe("Error de Validacion");
				MessageBoxCustom messageBoxCustom = new MessageBoxCustom(shell, display);
				messageBoxCustom.MessageBoxError("Error de Validacion en fileInput");
				return "";
			}			
			return " \"" + text.getText() + "\"";	//se le agrega comillas para nombres de archivos con espacios
		}
		
		if(parametro.inputType.equals(inputs.folderInput))
		{
			Text text = (Text) widget;
			if( parametro.validation != null && !parametro.validation.validateInput(text.getText()))
			{
				logger.severe("Error de Validacion");
				MessageBoxCustom messageBoxCustom = new MessageBoxCustom(shell, display);
				messageBoxCustom.MessageBoxError("Error de Validacion en folderInput");
				return "";
			} 
			return " \"" + text.getText() + "\"";	//se le agrega comillas para nombres de carpetas con espacios
		}
		
		return "";
	}
		
	public void addParameter(Parametro parametro, GridData gridData)
	{
		final GridData gridData2 = gridData;
	
		if(!parametro.tieneInput())
		{
			System.out.println("Parametro sin input");
			parametrosCargados.add(null);
		}
		
		//valido primero para el checkbox porque tiene el label "pegado"
		if(parametro.inputType.equals(inputs.checkBox))
		{
			Button checkBox = new Button(shell, SWT.CHECK);
			checkBox.setText(parametro.label);
			checkBox.setLayoutData(gridData2);
			parametrosCargados.add(checkBox);
			return;
		}
		
		if(parametro.inputType.equals(inputs.folderInput))
		{
			Label aLabel = new Label(shell, SWT.SINGLE);
			aLabel.setText(parametro.label);
			aLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      	
			int SWT_TYPE;
			
			if(parametro.validation != null && parametro.validation.hasExists())
			{
				SWT_TYPE = SWT.PUSH;
			}
			else
			{				
				SWT_TYPE = SWT.SINGLE;
			}
			
			final Text text = new Text(shell, SWT_TYPE );
			
			GridData gridData3 = new GridData(GridData.FILL_HORIZONTAL);
			gridData3.horizontalSpan = 2;
			text.setLayoutData(gridData3);
			
			Button button = new Button(shell, SWT.PUSH);
			button.setText("Browse...");
			button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			button.addSelectionListener(new SelectionAdapter() { public void widgetSelected(SelectionEvent event) {
				DirectoryDialog dlg = new DirectoryDialog(shell);

				//Set the initial filter path according
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
		        if (dir != null) 
		        {
		        	// Set the text box to the new selection
		            text.setText(dir);
		        }
			}});
		    parametrosCargados.add(text);
		    return;
		}
      
		if(parametro.inputType.equals(inputs.fileInput))
		{
			Label aLabel = new Label(shell, SWT.SINGLE);
			aLabel.setText(parametro.label);
			aLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			int SWT_TYPE;
			
			if(parametro.validation != null && parametro.validation.hasExists())
			{
				SWT_TYPE = SWT.PUSH;
			}
			else
			{				
				SWT_TYPE = SWT.SINGLE;
			}
			
			final Text text = new Text(shell, SWT_TYPE );
			GridData gridData3 = new GridData(GridData.FILL_HORIZONTAL);
	        gridData3.horizontalSpan = 2;
			text.setLayoutData(gridData3);
			
			Button button = new Button(shell, SWT.PUSH);
		    button.setText("Browse...");
		    button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		    button.addSelectionListener(new SelectionAdapter() {
		        public void widgetSelected(SelectionEvent event) 
		        {
		        	FileDialog dlg = new FileDialog(shell);

		        	// Set the initial filter path according
		        	// to anything they've selected or typed in
		        	dlg.setFilterPath(text.getText());

		        	// Change the title bar text
		        	dlg.setText("SWT's FileDialog");

		        	// Customizable message displayed in the dialog

		        	// Calling open() will open and run the dialog.
		        	// It will return the selected directory, or
		        	// null if user cancels
		        	String dir = dlg.open();
		        	if (dir != null) 
		        	{
		        		//Set the text box to the new selection
		        		text.setText(dir);
		        	}
		        }
		    });
		    parametrosCargados.add(text);
		    return;
		}
		
        final StyledText text1 = new StyledText(shell, SWT.WRAP);
        text1.setText(parametro.label);
        text1.setBackground(shell.getBackground());
        text1.setTopMargin(15);
        text1.setBottomMargin(5);
        //Agregado porque sino se puede hacer tab y se puede editar
        text1.setEditable(false);
        text1.setEnabled(false);

		if(parametro.inputType.equals(inputs.textBox))
		{
			Text text = new Text(shell, SWT.SINGLE);
			text.setLayoutData(gridData2);
			parametrosCargados.add(text);
			return;
		}
		
		if(parametro.inputType.equals(inputs.comboBox)) 
		{
			  
			final Combo combo = new Combo(shell, SWT.SINGLE);
			combo.setLayoutData(gridData2);
			final ParametroComboBox parametroComboBox = (ParametroComboBox) parametro;
			final ArrayList<ComboBoxItem> items = parametroComboBox.getComboBoxItems();
			
			if(items.isEmpty())
				logger.severe("No se inicializco correctamente el parametroComboBox");
				
			Iterator<ComboBoxItem> iterator_items;
			iterator_items = items.iterator();
			while (iterator_items.hasNext())
			{
				ComboBoxItem comboBoxItem = (ComboBoxItem) iterator_items.next();
				combo.add(comboBoxItem.getTag());
				if(parametroComboBox.selectedComboBoxItem != null)
					if(parametroComboBox.selectedComboBoxItem.equals(comboBoxItem))
					{
						//Log.writeLogMessage(Log.ERROR, "Encontre el item seleccionado");
						combo.setText(comboBoxItem.getTag());
					}
			}
			
			combo.addSelectionListener(new SelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) 
				{
					logger.fine("Default selected index: " + combo.getSelectionIndex() + ", selected item: " + (combo.getSelectionIndex() == -1 ? "<null>" : items.get(combo.getSelectionIndex()).getFlag()) + ", text content in the text field: " + combo.getText());
					parametroComboBox.selectedComboBoxItem = items.get(combo.getSelectionIndex());
					
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
									logger.fine("parametro.label = " + parametro.label);
									addParameter(parametro,gridData2);
								}
							}
							
							Button ExecuteButton = okBtn;
							//GridData data = (GridData) ExecuteButton.getLayoutData();
							ExecuteButton.dispose();
							
							addExecuteButton();
							shell.layout(false);
							shell.pack();
							shell.open();
							
							previousShell.dispose();
							break;
						}
							
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) 
				{
					// No es necesario implementar
				}
				
			});
			parametrosCargados.add(combo);
			return;
		}
		
		if(parametro.inputType.equals(inputs.radioButton))
		{
			final ParametroRadioButton parametroRadioButton = (ParametroRadioButton) parametro;

			int itemsCount = parametroRadioButton.getRadioButtonItems().size();
			int i = 0;
    
			final Button[] radioButtonComponent; // = null;
			
			if(itemsCount > 0) 
			{
				radioButtonComponent = new Button[itemsCount];
          
				for(final RadioButtonItem radioButtonItem : parametroRadioButton.getRadioButtonItems()) 
				{
					
					final ArrayList<RadioButtonItem> items = parametroRadioButton.getRadioButtonItems();
					radioButtonComponent[i] = new Button(shell, SWT.RADIO);
					radioButtonComponent[i].setText(radioButtonItem.getTag());
					radioButtonComponent[i].setData(radioButtonItem.getFlag());
					
					if(parametroRadioButton.selectedRadioButtonItem != null)
					{
						if(parametroRadioButton.selectedRadioButtonItem.equals(radioButtonItem))
						{
							radioButtonComponent[i].setSelection(true);
						}
					}
					
					if(radioButtonItem.subParametros.size() > 0)
					{
						radioButtonComponent[i].addFocusListener(new FocusListener()
						{
							@Override
							public void focusGained(FocusEvent e) 
							{
								Log.writeLogMessage(Log.ERROR, "focusGained");
						
								parametroRadioButton.selectedRadioButtonItem = radioButtonItem;
								Shell previousShell = shell;
								initializeWindow();
								addParameters();
							
								Iterator<RadioButtonItem> iterator_items;
								iterator_items = items.iterator();
								while (iterator_items.hasNext())
								{
									Log.writeLogMessage(Log.ERROR, "Dentro del while iterator_items");
									RadioButtonItem radioButtonItem = (RadioButtonItem) iterator_items.next();
									ArrayList<Parametro> subParametros = radioButtonItem.getSubParametros();
									Iterator<Parametro> iterator_parametros;
									iterator_parametros = subParametros.iterator();
										
									while (iterator_parametros.hasNext())
									{
										Log.writeLogMessage(Log.ERROR, "Agregue un subparametro");
										Parametro parametro = (Parametro) iterator_parametros.next();
										logger.fine("parametro.label = " + parametro.label);
										addParameter(parametro, gridData2);
									}
								}
							
								Button ExecuteButton = okBtn;
								//GridData data = (GridData) ExecuteButton.getLayoutData();
								ExecuteButton.dispose();
								addExecuteButton();
								shell.layout(false);
								shell.pack();
								shell.open();
								previousShell.dispose();
							}

							@Override
							public void focusLost(FocusEvent e) 
							{
								//No es necesario implementar, o si?
								Log.writeLogMessage(Log.ERROR, "focusLost");
							}});
					}
					else
					{
						radioButtonComponent[i].addFocusListener(new FocusListener(){

							@Override
							public void focusGained(FocusEvent arg0) {
								// TODO Auto-generated method stub
								
								parametroRadioButton.selectedRadioButtonItem = radioButtonItem;
								Shell previousShell = shell;
								
								initializeWindow();
								addParameters();
								
								Button ExecuteButton = okBtn;
								//GridData data = (GridData) ExecuteButton.getLayoutData();
								ExecuteButton.dispose();
								addExecuteButton();
								shell.layout(false);
								shell.pack();
								shell.open();
								previousShell.dispose();
								
							}

							@Override
							public void focusLost(FocusEvent arg0) {
								// TODO Auto-generated method stub
								
							}});
						
					}
					parametrosCargados.add(radioButtonComponent[i]);
					// Por que no se sumaba?
					i++;
				} 
			}
			return;
		}
		
		if(parametro.inputType.equals(inputs.dateTimePicker))
		{
			final DateTime calendar = new DateTime (shell, SWT.CALENDAR);
			calendar.setLayoutData(gridData2);
			//calendar.getParent().getBackground()
			calendar.setBackground(null);
			calendar.setForeground(null);
			parametrosCargados.add(calendar);
			return;
		}
		
		logger.severe("No se reconoce el input o el input es NULL");
		return;
	}
	
	public void addParameters()
	{
		parametrosCargados.clear();
		Iterator<Parametro> iterator_parametros;
		iterator_parametros = actualApplication.parametros.iterator();
		
		Parametro parametro;
		
		while(iterator_parametros.hasNext())
		{
			parametro = (Parametro) iterator_parametros.next();

			//para que el input complete la linea actual y quede cada parametro en una linea
			final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
	    
			gridData.horizontalSpan = 4;
			addParameter(parametro, gridData);
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

