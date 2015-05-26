package process;

import java.util.ArrayList;

public class Aplicacion 
{
	public ArrayList<Parametro> parametros;
	public String name;
	public String exePath;
	public String command;
	
	public Aplicacion(String appName)
	{
		name = appName;
		parametros = new ArrayList<Parametro>();
	}
	
	public void SetExePath(String exepath)
	{
		exePath = exepath;
	}
	
	public void SetCommand(String aCommand)
	{
		command = aCommand;
	}
	
	public void agregarParametro(Parametro parametro)
	{
		parametros.add(parametro);
	}

}
