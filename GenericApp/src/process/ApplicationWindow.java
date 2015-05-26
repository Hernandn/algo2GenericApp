package process;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ApplicationWindow 
{
	public NodeList nodeList;
	public static Display display;
	public static Shell shell;
	public static MainWindow mainWindow;
	
	public ApplicationWindow(MainWindow parent)
	{
		mainWindow = parent;
		display = mainWindow.display;
//		shell = mainWindow.shell;
	}
	
	public String getApplicationName()
	{
		Element singleElement;
		String head = "";
		//i = 0 tiene que ser el Head
		singleElement = (Element)nodeList.item(0);
		
		NodeList singleNodeList = singleElement.getElementsByTagName("label");
		if(singleNodeList != null && singleNodeList.getLength() > 0) 
		{
			Element el = (Element)singleNodeList.item(0);
			try
			{
				head = el.getFirstChild().getNodeValue();
			}
			catch(NullPointerException e)
			{
				System.out.println("[ERROR] - NullPointerException on ApplicationWindow::getApplicationName");
			}
		}
		
		System.out.println("head = " + head);
		return head;
	}
	
	public void run(int option, XMLProcess xmlprocess)
	{

		System.out.println("Run " + option);
		nodeList = xmlprocess.getWindowElement(option);
		System.out.println("nodeList = " + nodeList.getLength());
		
		
		showWindow();
		
		if(nodeList != null && nodeList.getLength() > 0) 
		{
			for(int i = 1 ; i < nodeList.getLength();i++) 
			{	
				//String head = xmlprocess.getTextValue(singleElement, "head");
				//System.out.println("head = " + head);
				
//				if(!flag.equals(""))
//					fullCommand += " " + flag;
//				
//				text = getTextValue(singleElement, "id");
//				fullCommand += " " + text;
			}
		}
		

	}
	
	public void showWindow()
	{
		shell = new Shell(display, SWT.CLOSE | SWT.TITLE );
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.horizontalSpacing = SWT.CENTER;
		shell.setLayout(gridLayout);
		
		// Este segmento se supone que centra el form a la pantalla
		
		shell.setSize(200, 200);
		Monitor primary = display.getPrimaryMonitor();
	    Rectangle bounds = primary.getBounds();
	    Rectangle rect = shell.getBounds();
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 2;
	    shell.setLocation(x, y);
	    
	    shell.setText(mainWindow.combo.getItem(mainWindow.optionSelected));
	    
	    Label label = new Label(shell, SWT.NULL);
		label.setText(getApplicationName());

	    shell.pack();
		shell.open();
		//textUser.forceFocus();
		// Set up the event loop.
		while (!shell.isDisposed()) 
		{
			
			if (!display.readAndDispatch()) 
			{
				// If no more entries in event queue
				display.sleep();
			}
		}
		
		
		display.dispose();
	}
}
