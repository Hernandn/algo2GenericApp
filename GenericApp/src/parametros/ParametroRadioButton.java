package parametros;

import java.util.ArrayList;

public class ParametroRadioButton extends Parametro
{
	private ArrayList<RadioButtonItem> radioButtonItems;
	
	public ParametroRadioButton(String aLabel,String aFlag, inputs anInput, Validation aValidation)
	{
		super(aLabel, aFlag, anInput, aValidation);
		radioButtonItems = new ArrayList<RadioButtonItem>();
	}
	
	public void addRadioButtonItem(RadioButtonItem rbItem)
	{
		this.radioButtonItems.add(rbItem);
	}
	
  public ArrayList<RadioButtonItem> getRadioButtonItems() {
    return radioButtonItems;
  }
	
}
