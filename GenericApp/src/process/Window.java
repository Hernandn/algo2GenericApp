package process;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Window 
{
	private static final Logger logger = Logger.getLogger(Window.class.getName());
	
	public static void main(String[] args) 
    {
		//Configuro el logger global (afecta a los loggers de todas las clases) - También podría setearle un handler para que loguee en un archivo
		LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(Level.FINE);
		
		String FileName = "config.xml";
        //Log.setLogInfo(Log.INFO);
        //Log.writeLogMessage(Log.INFO, "Archivo = " + FileName);
		logger.info("Archivo = " + FileName);
		
		XMLProcess xmlProcess = new XMLProcess(FileName);
        ArrayList<Aplicacion> applications = xmlProcess.parseDocument();
        new MainWindow(applications);
    }

}
