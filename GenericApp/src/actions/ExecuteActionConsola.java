package actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import process.Consola;

public class ExecuteActionConsola implements Runnable 
{
	public static Consola laConsola;
	
	public ExecuteActionConsola(Consola consola)
	{
		laConsola = consola;
	}
	
	@Override
	public void run() 
	{
		Process p;
		try 
		{
			p = Runtime.getRuntime().exec("cmd /c "+ laConsola.Command);
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream())); 
			String line = null;
			laConsola.text.append("$ " + laConsola.Command + "\n");
			while ((line = in.readLine()) != null) 
			{  
				laConsola.text.append(line + "\n");
            }
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
