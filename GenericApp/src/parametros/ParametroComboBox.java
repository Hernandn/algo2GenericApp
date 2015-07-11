package parametros;

import java.util.ArrayList;

public class ParametroComboBox extends Parametro
{
	private ArrayList<ComboBoxItem> comboBoxItems;
	public ComboBoxItem selectedComboBoxItem;
	
	public ParametroComboBox(String aLabel,String aFlag, inputs anInput, Validation aValidation)
	{
		super(aLabel, aFlag, anInput, aValidation);
		comboBoxItems = new ArrayList<ComboBoxItem>();
		selectedComboBoxItem = null;
	}
	
	public void addComboBoxItem(ComboBoxItem cbItem)
	{
		this.comboBoxItems.add(cbItem);
	}

	
	//getters y setters
	public ArrayList<ComboBoxItem> getComboBoxItems() {
		return comboBoxItems;
	}

	public void setComboBoxItems(ArrayList<ComboBoxItem> comboBoxItems) {
		this.comboBoxItems = comboBoxItems;
	}
}
