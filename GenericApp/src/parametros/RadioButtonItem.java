package parametros;

import java.util.ArrayList;

public class RadioButtonItem
{
	private String tag;
	private String flag;
	public ArrayList<Parametro> subParametros;
	
	public RadioButtonItem(String tag, String flag)
	{
		this.tag = tag;
		this.flag = flag;
		subParametros = new ArrayList<Parametro>();
	}
	
	public void addSubParametros(ArrayList<Parametro> parametros)
	{
		if(parametros != null)
		{
			subParametros.addAll(parametros);
		}
	}

	
	//getters y setters
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public ArrayList<Parametro> getSubParametros() {
		return subParametros;
	}

	public void setSubParametros(ArrayList<Parametro> subParametros) {
		this.subParametros = subParametros;
	}
}
