package test;

import java.util.ArrayList;
import java.util.Iterator;

import parametros.Parametro;
import process.Aplicacion;
import process.MainWindow;
import process.XMLProcess;

public class test 
{
	
	public static void main(String[] args) 
    {
		XMLProcess xmlProcess = new XMLProcess("src/process/config.xml");
		ArrayList<Aplicacion> applications = xmlProcess.parseDocument();
		
		System.out.println("applications.size() = " + applications.size());
		
		Iterator<Aplicacion> iterator_app;
		Iterator<Parametro> iterator_parametros;
		iterator_app = applications.iterator();
		while (iterator_app.hasNext())
		{
			Aplicacion app = (Aplicacion) iterator_app.next();
			System.out.println("app.name = " + app.name);
			System.out.println("app.command = " + app.command);
			System.out.println("app.exePath = " + app.exePath);
			
			System.out.println("app.parametros.size() = " + app.parametros.size());
		}
		
		MainWindow mainWindow = new MainWindow(applications);
    }
}
