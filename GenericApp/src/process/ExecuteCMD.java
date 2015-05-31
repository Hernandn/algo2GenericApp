package process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/*
 * Esta clase sirve para simular lo que haria la UI.
 * 1- Hace el parse del XML usando la clase XMLProcess.
 * 2- Obtengo el string con el comando.
 * 3- Solicito el ingreso de los ID y los flags para reemplazarlos en el comando.
 * 4- Ejecuto el comando y muestro la salida por consola.
 */

public class ExecuteCMD
{
	public static void main(String[] args)
	{
		//create an instance
		XMLProcess xmlProcess = new XMLProcess("../GenericApp/src/process/config.xml");
		Scanner scanner = new Scanner(System.in);
		String id;
		String flag;
		int ini,fin; //indices
				
		System.out.println("\nListado de aplicaciones:");
		//System.out.println(xmlProcess.getListApps());		//comentado para que no de error
		System.out.println("Ingrese una opcion: ");
		int opcion=scanner.nextInt();
		
		System.out.println("\nComando:");
		//call run
		//xmlProcess.run(opcion);	//comentado para que no de error
		
		String command = xmlProcess.getFullCommand();

		//creo el buffer para usar los metodos de manejo de cadenas
		StringBuffer sb=new StringBuffer();
		sb.append(command);
		
		System.out.print("\nIngrese id del flag (# para finalizar): ");
		id=scanner.next();
		
		while(!id.equals("#"))
		{
			System.out.print("Ingrese el flag: ");
			flag=scanner.next();
			//esto simula lo que seria obtener el contenido de algun componente de la UI (ej: textBox, fileInput, etc)
			
			//elimino el "id" y lo reemplazo por el "flag"
			ini=sb.indexOf(id);
			fin=ini+id.length();
			sb.delete(ini,fin);
			sb.insert(ini,flag);
			
			System.out.print("Ingrese id del flag (# para finalizar): ");
			id=scanner.next();
		}
		
		command=sb.toString();
		
		
		System.out.println("\n\nSalida en consola:");
		
		try {  
            //ingresa el string en el "ejecutar" de windows
			Process p = Runtime.getRuntime().exec("cmd /c "+command);  //"cmd" abre la consola y el "/c" es para que se ejecute y se cierre sola
            //captura el output de la consola (aca podemos pasarlo en una consola dentro del UI para que el usuario vea la salida)
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));  
            String line = null;  
            while ((line = in.readLine()) != null) {  
                System.out.println(line);
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
	}
}
