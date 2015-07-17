package listeners;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

import log.Log;
import parametros.Parametro;
import parametros.Parametro.inputs;
import parametros.ParametroComboBox;
import parametros.ParametroRadioButton;
import parametros.RadioButtonItem;
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
		
		Log.writeLogMessage(Log.ERROR, "1");
		
		while(iterator_parametros.hasNext())
		{
			Parametro parametro = (Parametro) iterator_parametros.next();
			Log.writeLogMessage(Log.ERROR, "2");
			
			if(parametro.validation == null)
				logger.info("Raro que no tenga ninguna validation");
			
			if(!parametro.tieneInput())
	        {
				fullCommand += " " + parametro.flag;
				continue;
	        }
			
			Widget widget = (Widget) iterator_widgets.next();
			String string = "";
			
			if(parametro.inputType.equals(inputs.radioButton)) 
			{ 
				//el parámetro es un radio button
				Log.writeLogMessage(Log.ERROR, "3");
				//casteo a parámetro radio button
				ParametroRadioButton parametroRadioButton = (ParametroRadioButton) parametro;
				ArrayList<RadioButtonItem> items = parametroRadioButton.getRadioButtonItems();
				
				
				// obtengo cantidad de opciones para iterar
				int itemsCount = parametroRadioButton.getRadioButtonItems().size();
			  
				//si el item está seleccionado entonces agrego el flag al comando
				if(((Button) widget).getSelection()) 
				{
					parametro.value = (String)((Button) widget).getData();
					fullCommand += " " + ((Button) widget).getData();
					
				}
				
				int i = 0;
			  
				for( ; i < itemsCount - 1; i++) 
				{
					//paso al siguiente widget - IMPORTANTE: estoy pasando al próximo widget porque los radio button son widgets independientes 
					// - Hago esto para no romper la relación parámetro - widget
					widget = (Widget) iterator_widgets.next();
			    
					//si el item está seleccionado entonces agrego el flag al comando
					if(((Button) widget).getSelection()) 
					{
						fullCommand += " " + ((Button) widget).getData();
						
						// El que está seleccionado no es el i, es el i+1						
						for(int j=0;j < items.get(i+1).subParametros.size() ; j++)
						{
							Parametro nuevoParametro = items.get(i+1).subParametros.get(j);
							widget = (Widget) iterator_widgets.next();
							string = applicationWindow.getCommandString(widget, nuevoParametro);
							if(string.equals(""))
								return;
							
							fullCommand += string;
						}
					}
				}
				continue;
			}
			
			if(parametro.inputType.equals(inputs.comboBox)) 
			{
				ParametroComboBox parametroComboBox = (ParametroComboBox) parametro;
				
				if(parametroComboBox.selectedComboBoxItem != null)
				{
					fullCommand += " " + parametroComboBox.selectedComboBoxItem.getFlag();
				}
				else
				{
					string = applicationWindow.getCommandString(widget, parametro);
					if(string.equals(""))
						return;
					
					fullCommand += " " + string;
				}
				
				for(int i=0; i < parametroComboBox.selectedComboBoxItem.subParametros.size(); i++)
				{
					widget = (Widget) iterator_widgets.next();
					Parametro nuevoParametro = parametroComboBox.selectedComboBoxItem.subParametros.get(i);					
					string = applicationWindow.getCommandString(widget, nuevoParametro);
					
					if(string.equals(""))
						return;
					
					fullCommand += string;
				}
				continue;
			}
			
			string = applicationWindow.getCommandString(widget, parametro);
			if(string.equals(""))
				return;
				
			fullCommand += string;
		}
		
		logger.info("fullCommand: " + fullCommand);
		
		if(ApplicationWindow.actualApplication.GetCustomValidationClass() != null)
		{
			Class<?> myClass = null;
			Constructor<?> constructor;
			Object object;
			Method method;
			Boolean customValidated = false;
			
			String classPath = ApplicationWindow.actualApplication.customValidationClass.path;
			String classPackage = ApplicationWindow.actualApplication.customValidationClass.CustomPackage;
			String className = ApplicationWindow.actualApplication.customValidationClass.ClassFile;

			File file = new File(classPath.replace('\\','/'));

			try 
			{
				URL[] urlClassPath = {file.toURI().toURL()};
				URLClassLoader urlClassLoader = new URLClassLoader(urlClassPath);
				myClass = urlClassLoader.loadClass(classPackage + "." + className.replace(".class", ""));
				//constructor = myClass.getConstructor();
				constructor = myClass.getConstructor();
				object = constructor.newInstance();
				method = object.getClass().getMethod("Validate", String.class);
				customValidated = (Boolean) method.invoke(object, fullCommand);
				urlClassLoader.close();
			} 
			catch (MalformedURLException e2)
			{	
				e2.printStackTrace();
				MessageBoxCustom messageBoxCustom = new MessageBoxCustom(ApplicationWindow.shell, ApplicationWindow.display);
				messageBoxCustom.MessageBoxError("Error de CustomValidacion - �Esta bien la ruta del archivo .class en el XML?");
				return;
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
				MessageBoxCustom messageBoxCustom = new MessageBoxCustom(ApplicationWindow.shell, ApplicationWindow.display);
				messageBoxCustom.MessageBoxError("Error de CustomValidacion - �Existe la clase?");
				return;
			}
			catch (NoSuchMethodException | SecurityException e) 
			{
				e.printStackTrace();
				MessageBoxCustom messageBoxCustom = new MessageBoxCustom(ApplicationWindow.shell, ApplicationWindow.display);
				messageBoxCustom.MessageBoxError("Error de CustomValidacion - �Existe el m�todo?");
				return;
			}
			catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
			{
				MessageBoxCustom messageBoxCustom = new MessageBoxCustom(ApplicationWindow.shell, ApplicationWindow.display);
				messageBoxCustom.MessageBoxError("Error de CustomValidacion - �Tiene constructor sin parametros?");
				e.printStackTrace();
				return;
			}
			catch (IOException e)
			{	
				e.printStackTrace();
				return;
			}
			
			if(!customValidated)
			{
				MessageBoxCustom messageBoxCustom = new MessageBoxCustom(ApplicationWindow.shell, ApplicationWindow.display);
				messageBoxCustom.MessageBoxError("Error de CustomValidacion");
				return;
			}
			else
			{
				ApplicationWindow.shell.setVisible(false);
				new Consola(ApplicationWindow.display, fullCommand);
				return;
			}
		}
		
		ApplicationWindow.shell.setVisible(false);
		new Consola(ApplicationWindow.display, fullCommand);
		
	}

}
