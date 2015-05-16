package process;

public class Window 
{
	public static void main(String[] args) 
    {
        printMessage("Hello World!");
        printMessage("Hola Mundo!");
        XMLProcess xmlProcess = new XMLProcess("src/process/config.xml");
		//HashSet<String> lista = xmlProcess.getListApps(1);
		
		//MainWindow mainWindow = new MainWindow(xmlProcess);
		MainWindow mainWindow = new MainWindow(xmlProcess);
    }

    public static void printMessage(String message)
    {
    	System.out.println(message);
    	return;
    }
}
