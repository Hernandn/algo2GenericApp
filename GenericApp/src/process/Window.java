package process;

import java.util.ArrayList;

public class Window 
{
	public static void main(String[] args) 
    {
        XMLProcess xmlProcess = new XMLProcess("src/process/config.xml");
        ArrayList<Aplicacion> applications = xmlProcess.parseDocument();
        MainWindow mainWindow = new MainWindow(applications);
    }

    public static void printMessage(String message)
    {
    	System.out.println(message);
    	return;
    }
}
