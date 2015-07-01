package process;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Window 
{
	private static final Logger logger = Logger.getLogger(Window.class.getName());
	
	private static final String PROPERTIES_DEFAULT_NAME = "main.application.properties";
	private static final String DEFAULT_CONFIG_FILE_NAME = "config.xml";
	
	public static void main(String[] args) 
    {
		//Configuro el logger global (afecta a los loggers de todas las clases) - También podría setearle un handler para que loguee en un archivo
		LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(Level.FINE);
		
		//levanto properties si existen
		Properties properties = loadProperties();
		
		String fileName = DEFAULT_CONFIG_FILE_NAME;
		
		//en caso de existir la property, cambio el valor por defecto del nombre del archivo de config
		if(properties.containsKey("config.file")) {
			fileName = (String) properties.get("config.file");
		}
		
        //Log.setLogInfo(Log.INFO);
        //Log.writeLogMessage(Log.INFO, "Archivo = " + FileName);
		logger.info("Archivo = " + fileName);
		
		XMLProcess xmlProcess = new XMLProcess(fileName);
		
        ArrayList<Aplicacion> applications = xmlProcess.parseDocument();
        
        new MainWindow(applications);
    }
	
	private static Properties loadProperties() {
		
		Properties properties = new Properties();
		
		try {
			//abro archivo de properties
			FileInputStream in = new FileInputStream(PROPERTIES_DEFAULT_NAME);
			//cargo properties desde el archivo
			properties.load(in);
			//cierro archivo
			in.close();
		} catch (FileNotFoundException e) {
			
			logger.warning("Archivo de properties: " + PROPERTIES_DEFAULT_NAME + " no encontrado. Se seguirá con configuración por defecto.");
			return properties;
		} catch (IOException e) {
			
			logger.warning("Error al cargar archivo de properties: " + PROPERTIES_DEFAULT_NAME + ". Se seguirá con configuración por defecto.");
			return properties;
		}
		
		logger.info("Properties cargadas correctamente!");
			
		return properties;
	}
}
