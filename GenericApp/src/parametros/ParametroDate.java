package parametros;

public class ParametroDate extends Parametro
{
	private String format;
	
	public ParametroDate(String aLabel, String aFlag, inputs anInput, Validation aValidation, String aFormat) {
		super(aLabel, aFlag, anInput, aValidation);
		this.format=aFormat;
	}
	
	public String getFormat()
	{
		return this.format;
	}

}
