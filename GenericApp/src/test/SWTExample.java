package test;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
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
    final DateTimeFormatter format = DateTimeFormatter.ofPattern("yy/MM/dd");
    calendar.addSelectionListener (new SelectionAdapter() {
        public void widgetSelected (SelectionEvent e) {
            System.out.println ("date changed");
        }
    });

    //boton
    
    Button okBtn = new Button(shell, SWT.PUSH);
	okBtn.setText("Execute");
	okBtn.addSelectionListener (new SelectionAdapter () {
        public void widgetSelected (SelectionEvent e) {
        	LocalDate date = LocalDate.of(calendar.getYear(),calendar.getMonth()+1,calendar.getDay());
        	System.out.println(date.format(format));
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