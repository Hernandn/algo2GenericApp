package log;

public class Log 
{
	public static final int INFO = 1;
	public static final int DEBUG = 2;
	public static final int ERROR = 3;
	public static int LoggingInfo = 0;
	
	public static void setLogInfo(int Number)
	{
		LoggingInfo = Number;
	}
	
	public static void writeLogMessage(int LogInfo, String Message)
	{
		if(LogInfo >= LoggingInfo)
		{
			switch(LogInfo)
			{
				case INFO: System.out.println("[INFO] - " + Message); break;
				case DEBUG: System.out.println("[DEBUG] - " + Message); break;
				case ERROR: System.out.println("[ERROR] - " + Message); break;
			}
		}		
	}
}
