package test;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class SWTExample 
{
public static void main (String [] args) 
{
    Display display = new Display ();
    Shell shell = new Shell (display);
    shell.setLayout (new RowLayout ());

    
    //calendario
    final DateTime calendar = new DateTime (shell, SWT.CALENDAR);
    calendar.addSelectionListener (new SelectionAdapter() {
        public void widgetSelected (SelectionEvent e) {
            System.out.println ("calendar date changed");
        }
    });

    LocalDate dateAux = LocalDate.of(calendar.getYear(),calendar.getMonth(),calendar.getDay());
    System.out.println(dateAux);
	
	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
	  String text = ""+calendar.getYear()+calendar.getMonth()+calendar.getDay();
	  final LocalDate date = LocalDate.parse(text, formatter);
    
    //boton
    
    Button okBtn = new Button(shell, SWT.PUSH);
	okBtn.setText("Execute");
	okBtn.addSelectionListener (new SelectionAdapter () {
        public void widgetSelected (SelectionEvent e) {
            System.out.println (calendar.getDay()+"/"+calendar.getMonth()+"/"+calendar.getYear());
            System.out.println(date);
        }
    });
	
	//checkbox
	Button checkBox = new Button(shell, SWT.CHECK);
	checkBox.setText("CheckBox");
	checkBox.addSelectionListener(new SelectionAdapter() {

        @Override
        public void widgetSelected(SelectionEvent event) {
            Button btn = (Button) event.getSource();
            System.out.println(btn.getSelection());
        }
    });

    shell.pack ();
    shell.open ();
    while (!shell.isDisposed ()) {
        if (!display.readAndDispatch ()) display.sleep ();
    }
    display.dispose ();
}

}