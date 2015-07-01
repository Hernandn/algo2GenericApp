package process;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MessageBoxCustom 
{
	public static Display display;
	public static Shell shell;
	
	public MessageBoxCustom(Shell parentShell, Display parentDisplay)
	{
		display = parentDisplay;
		shell = parentShell;
	}
	
	public static void main(String[] args)
	{
		Display display = new Display();
		Shell shell = new Shell(display);
		
		MessageBoxCustom messageBoxCustom = new MessageBoxCustom(shell, display);
		messageBoxCustom.MessageBoxError("¿Qué estás haciendo?");	
		
		int respuesta = MessageBoxOptions("Pregunta", "¿Seguro?");
		if (respuesta == 0)
			System.out.println("Respuesta fue si");
		else
			System.out.println("Respuesta fue no");
		
		return;
	}
	
	public void MessageBoxError(String message)
	{

		int style = SWT.ICON_ERROR;
		
		
		MessageBox messageBox = new MessageBox(shell, style);
	    messageBox.setMessage(message);
	    messageBox.open();
	}
	
	public static int MessageBoxOptions(String Title, String message)
	{
		int opcion;
		
		opcion = JOptionPane.showConfirmDialog(null, message, Title, JOptionPane.YES_NO_OPTION);

		return opcion;
	}
}
