package process;

import java.util.ArrayList;

import log.Log;

public class Window 
{
	public static void main(String[] args) 
    {
		String FileName = "config.xml";
        Log.setLogInfo(Log.INFO);
        Log.writeLogMessage(Log.INFO, "Archivo = " + FileName);
		
		XMLProcess xmlProcess = new XMLProcess(FileName);
        ArrayList<Aplicacion> applications = xmlProcess.parseDocument();
        new MainWindow(applications);
    }

}
