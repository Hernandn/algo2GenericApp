package parametros;

import java.util.ArrayList;

public class ParametroCheckBox extends Parametro
{
	private ArrayList<Parametro> subparametros;
 	
	public ParametroCheckBox(String aLabel,String aFlag, inputs anInput, Validation aValidation)
	{
		super(aLabel, aFlag, anInput, aValidation);
		subparametros = new ArrayList<Parametro>();
	}
	
	public void addSubparametros(ArrayList<Parametro> parametros)
	{
		subparametros.addAll(parametros);
	}
}
