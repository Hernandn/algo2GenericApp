package process;

public class Parametro 
{
	public enum inputs
	{
		fileInput,
		folderInput,
		comboBox,
		checkBox,
		dateTimePicker
	} 
	
	public String label;
	public String flag;
	public inputs inputType;
	
	public Parametro(String aLabel,String aFlag, inputs anInput)
	{
		label = aLabel;
		flag = aFlag;
		inputType = anInput;
	}
}
