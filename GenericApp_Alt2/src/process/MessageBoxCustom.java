package process;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MessageBoxCustom 
{
	public static void main(String[] args)
	{
		MessageBoxError("¿Qué estás haciendo?");	
				
		int respuesta = MessageBoxOptions("Pregunta", "¿Seguro?");
		if (respuesta == 0)
			System.out.println("Respuesta fue si");
		else
			System.out.println("Respuesta fue no");
		
		return;
	}
	
	public static void MessageBoxError(String message)
	{
		Display display = new Display();
		Shell shell = new Shell(display);
		int style = SWT.ICON_ERROR;
		
		MessageBox messageBox = new MessageBox(shell, style);
	    messageBox.setMessage(message);
	    messageBox.open();
	    
	    display.dispose();
	}
	
	public static int MessageBoxOptions(String Title, String message)
	{
		int opcion;
		
		opcion = JOptionPane.showConfirmDialog(null, message, Title, JOptionPane.YES_NO_OPTION);

		return opcion;
	}
}
