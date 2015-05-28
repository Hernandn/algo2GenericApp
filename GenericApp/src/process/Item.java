package process;

public class Item {
	
	public enum inputs
	{
		fileInput,
		folderInput,
		comboBox,
		checkBox,
		dateTimePicker
	} 
	
	
	public String Label;
	public int Type;
	
	public Item (String alabel, inputs input)
	{
		Label = alabel;
		switch(input)
		{
			case fileInput:
				Type = 0; break;
			case folderInput:
				Type = 1; break;
			case comboBox:
				Type = 2; break;
			case checkBox:
				Type = 3; break;
			case dateTimePicker:
				Type = 4; break;
			default: 
				Type = -1;
		}
	}

}
