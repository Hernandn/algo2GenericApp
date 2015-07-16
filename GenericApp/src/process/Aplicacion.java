package process;

import java.util.ArrayList;

import parametros.Parametro;

public class Aplicacion 
{
	public ArrayList<Parametro> parametros;
	public String name;
	public String description;
	public String exePath;
	public String command;
	public CustomValidationClass customValidationClass;
	
	public Aplicacion(String appName)
	{
		name = appName;
		parametros = new ArrayList<Parametro>();
		customValidationClass = null;
	}
	
	public void SetExePath(String exepath)
	{
		exePath = exepath;
	}
	
	public void SetCommand(String aCommand)
	{
		command = aCommand;
	}
	
	public void SetDescription(String aDescription)
	{
		description = aDescription;
	}
	
	public void agregarParametro(Parametro parametro)
	{
		parametros.add(parametro);
	}
	
	public void SetCustomValidationClass(CustomValidationClass aCustomValidationClass)
	{
		customValidationClass = aCustomValidationClass;
	}
	
	public CustomValidationClass GetCustomValidationClass()
	{
		return customValidationClass;
	}

}
