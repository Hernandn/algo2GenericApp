package parametros;

import java.util.ArrayList;

public class ParametroComboBox extends Parametro
{
	private ArrayList<ComboBoxItem> comboBoxItems;
	private String itemSelected;
	public int indexOfItemSelected;
	
	public ParametroComboBox(String aLabel,String aFlag, inputs anInput, Validation aValidation)
	{
		super(aLabel, aFlag, anInput, aValidation);
		comboBoxItems = new ArrayList<ComboBoxItem>();
		itemSelected = "";
		indexOfItemSelected = -1;
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
	/*
	TODO: Borrar sino tira errores de compilacion
	
	public void setItemSelected(String item)
	{
		itemSelected = item;
	}
	
	public String getItemSelected()
	{
		return itemSelected;
	}
	
	*/
	
	public int getIndexOfItemSelected()
	{
		return indexOfItemSelected;
	}
	
	public void setIndexOfItemSelected(int value)
	{
		indexOfItemSelected = value;
	}
	
	
}
