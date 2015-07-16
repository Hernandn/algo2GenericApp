package parametros;


public class Parametro 
{
	public enum inputs
	{
		fileInput,
		folderInput,
		comboBox,
		checkBox,
		dateTimePicker,
		textBox,
		radioButton
	} 
	
	public String label;
	public String flag;
	public inputs inputType;
	public Validation validation;
	public String value;
	
	public Parametro(String aLabel,String aFlag, inputs anInput, Validation aValidation)
	{
		label = aLabel;
		flag = aFlag;
		inputType = anInput;
		validation = aValidation;
	}
	
	public boolean tieneInput()
	{
		if(inputType != null)
		{
			return true;
		}
		return false;
	}
}
