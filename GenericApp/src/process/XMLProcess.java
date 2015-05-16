package process;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLProcess 
{
	public String filename;
	Document document;
	String fullCommand;
	
	public XMLProcess(String FileName)
	{
		filename = FileName;
		fullCommand = "";
		System.out.println("Filename = " + filename);
		System.out.println("XML Process creado");
		//parse the xml file and get the dom object
		parseXmlFile();
	}
	
	private void parseXmlFile()
	{
		//get the factory
		DocumentBuilderFactory documentBuilderFactory;
		DocumentBuilder documentBuilder;
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		
		try 
		{
			//Using factory get an instance of document builder
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			
			//parse using builder to get DOM representation of the XML file
			document = documentBuilder.parse(filename);

		}
		catch(ParserConfigurationException pce) 
		{
			pce.printStackTrace();
		}
		catch(SAXException se) 
		{
			se.printStackTrace();
		}
		catch(IOException ioe) 
		{
			ioe.printStackTrace();
		}
	}
	
	private void parseDocument(int appNum)
	{
		NodeList nodeList;
		Element element,singleElement;
		String text;
		
		nodeList = document.getElementsByTagName("application");
		if(nodeList == null)
		{
			System.out.println("[ERROR] - No puede no tener tag application");
			return;
		}
		//get the application
		element = (Element)nodeList.item(appNum-1);
		
		//agrega el path (si no lo usa, agrega un string vacio)
		text = element.getAttribute("exePath");
		fullCommand += text;
		//agrega el comando (idem anterior)
		text = element.getAttribute("command");
		fullCommand += text;

		//get the application parameters
		nodeList = element.getElementsByTagName("parameter");
		if(nodeList != null && nodeList.getLength() > 0) 
		{
			for(int i = 0 ; i < nodeList.getLength();i++) 
			{	
				singleElement = (Element)nodeList.item(i);
				String flag = getTextValue(singleElement, "flag");
				
				if(!flag.equals(""))
					fullCommand += " " + flag;
				
				text = getTextValue(singleElement, "id");
				fullCommand += " " + text;
			}
		}
	}
	
	public NodeList getWindowElement(int appNum)
	{
		NodeList nodeList;
		Element element;
		
		System.out.println("getWindowElement(" + appNum + ")");
		nodeList = document.getElementsByTagName("application");
		if(nodeList == null)
		{
			System.out.println("[ERROR] - No puede no tener tag application");
			return null;
		}
		//get the application
		element = (Element)nodeList.item(appNum);
		nodeList = element.getElementsByTagName("window");
		
		if(nodeList == null)
		{
			System.out.println("[ERROR] - No puede no tener tag window");
			return null;
		}
		
		//text = element.getAttribute("command");
		//System.out.println("Text = " + text);
		
		return nodeList;
		//		Element element;
//		NodeList nodeList;
//		
//		System.out.println("getWindowElement()");
//		System.out.println("appNum = " + appNum);
//		
//		nodeList = document.getElementsByTagName("application");
//		if(nodeList == null)
//		{
//			System.out.println("[ERROR] - No puede no tener tag application");
//			return null;
//		}
//		//get the application
//		System.out.println("1");
//		element = (Element)nodeList.item(appNum);
//		System.out.println("2");
//		System.out.println(element.getAttribute("exePath"));
		
		
		//agrega el path (si no lo usa, agrega un string vacio)
		//return element.getElementsByTagName("window");
	}
	
	public String getTextValue(Element element, String tagName) 
	{
		String textVal = null;
		NodeList nodeList = element.getElementsByTagName(tagName);
		if(nodeList != null && nodeList.getLength() > 0) 
		{
			Element el = (Element)nodeList.item(0);
			try
			{
				textVal = el.getFirstChild().getNodeValue();
			}
			catch(NullPointerException e)
			{
				textVal = "";
			}
		}
		return textVal;
	}
	
	//obtener listado (en string) de aplicaciones para ejecutar
	public String getListApps()
	{
		Element element;
		NodeList nodeList = document.getElementsByTagName("application");
		String output = "";
		for(int i = 0 ; i < nodeList.getLength();i++) 
		{	
			element = (Element)nodeList.item(i);
			String appName = element.getAttribute("name");
			output += (i+1) + ") " + appName + "\n";
		}
		return output;
	}
	
	//Redefinicion de getListApps()
	//public HashSet<String> getListApps(int number)
	public ArrayList<String> getListApps(int number)
	{
		Element element;
		NodeList nodeList = document.getElementsByTagName("application");
		//HashSet<String> collection = new HashSet<String> ();
		ArrayList<String> collection = new ArrayList<String>();
		for(int i = 0 ; i < nodeList.getLength();i++) 
		{	
			element = (Element)nodeList.item(i);
			String appName = element.getAttribute("name");
			//System.out.println("Appname = " + appName);
			//output += (i+1) + ") " + appName + "\n";
			collection.add(appName);
		}
		return collection;
	}
	
	public void run(int appNum) 
	{
		//get each employee element and create a Employee object
		parseDocument(appNum);
		//printData();
		System.out.println(fullCommand);
	}
	
	public String getFullCommand()
	{
		return fullCommand;
	}
	
	public static void main(String[] args){
		//create an instance
		//XMLProcess xmlProcess = new XMLProcess("../GenericApp/src/process/configuracion2.xml");
		
		//call run
		//xmlProcess.run();
	}
}
