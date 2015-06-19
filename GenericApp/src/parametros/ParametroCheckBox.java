package parametros;

import java.util.ArrayList;

public class ParametroCheckBox extends Parametro
{
	private ArrayList<Parametro> subParametros;
 	
	public ParametroCheckBox(String aLabel,String aFlag, inputs anInput, Validation aValidation)
	{
		super(aLabel, aFlag, anInput, aValidation);
		subParametros = new ArrayList<Parametro>();
	}
	
	public void addSubparametros(ArrayList<Parametro> parametros)
	{
		if(parametros != null)
		{
			subParametros.addAll(parametros);
		}
	}
}
