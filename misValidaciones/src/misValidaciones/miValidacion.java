package misValidaciones;

import customValidation.CustomValidation;

public class miValidacion implements CustomValidation
{

	@Override
	public boolean Validate(String fullCommand) 
	{
		String[] parts = fullCommand.split(" ");
		Boolean ipv4 = false;
		Boolean ipv6 = false;
		
		for(int i = 0; i < parts.length ; i++)
		{
			System.out.println("parts[" + i + "] = " + parts[i]);
			
			if(parts[i].equals("-4"))
				ipv4 = true;
			
			if(parts[i].equals("-6"))
				ipv6 = true;
		}	
		
		if(ipv4 && ipv6)
			return false;
		
		return true;
	}


}
