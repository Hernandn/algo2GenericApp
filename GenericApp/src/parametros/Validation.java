package parametros;

import java.io.File;
import java.util.logging.Logger;

public class Validation
{
	private static final Logger logger = Logger.getLogger(Validation.class.getName());
	
	private int minSize;
	private boolean hasMinSize = false;
	
	private int maxSize;
	private boolean hasMaxSize = false;
	
	private boolean nullable;
	private boolean hasNullable = false;
	
	private boolean numeric;
	private boolean hasNumeric = false;
	
	private boolean exists;
	private boolean hasExists = false;
	
	
	public boolean hasMinSize()
	{
		return this.hasMinSize;
	}
	public boolean hasMaxSize()
	{
		return this.hasMaxSize;
	}
	public boolean hasNullable()
	{
		return this.hasNullable;
	}
	public boolean hasNumeric()
	{
		return this.hasNumeric;
	}
	public boolean hasExists()
	{
		return this.hasExists;
	}
	
	//getters y setters
	public int getMinSize() {
		return minSize;
	}
	public void setMinSize(int minSize) {
		this.minSize = minSize;
		this.hasMinSize = true;
	}
	public int getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
		this.hasMaxSize = true;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
		this.hasNullable = true;
	}
	public boolean isNumeric() {
		return numeric;
	}
	public void setNumeric(boolean numeric) {
		this.numeric = numeric;
		this.hasNumeric = true;
	}
	public boolean isExists() {
		return exists;
	}
	public void setExists(boolean exists) {
		this.exists = exists;
		this.hasExists = true;
	}
	
	public boolean validateInput(String aString)
	{
		if(hasMinSize && aString.length() < minSize)
			return false;
			
		if(hasMaxSize && aString.length() > maxSize)
			return false;
		
		if(hasNullable && !nullable && aString.length() == 0)
			return false;
		
		if(hasNumeric)
		{
			try
			{
				Integer.parseInt(aString);
			}
			catch(NumberFormatException e)
			{
				return false;
			}
		}
		
		if(hasExists)
		{
			//Log.writeLogMessage(Log.INFO, "hasExists");
			logger.info("hasExists");
			
			File f = new File(aString);
			if(!f.exists())
				return false;
		}
				
		return true;
	}
}
