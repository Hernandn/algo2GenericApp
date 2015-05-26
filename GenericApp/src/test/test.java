package test;

import java.util.ArrayList;
import java.util.Iterator;

import process.Aplicacion;
import process.MainWindow;
import process.Parametro;
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
			
			iterator_parametros = app.parametros.iterator();
			while(iterator_parametros.hasNext())
			{
				Parametro parametro = (Parametro) iterator_parametros.next();
				System.out.println("parametro.label = " + parametro.label);
				System.out.println("parametro.flag = " + parametro.flag);
				System.out.println("parametro.inputType = " + parametro.inputType);
			}
		}
		
		MainWindow mainWindow = new MainWindow(applications);
    }
}
