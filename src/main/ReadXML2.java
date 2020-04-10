package main;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class ReadXML2 {
	static Logger logger = Logger.getLogger(ReadXML2.class);
	

	public static void main(String[] args) {

		
		 
		 String RUC="20299759068";	
		 
//		 Bajas
//		 String tipo="17";
//		 String tipo="16";
		 
		 // RESUMEN DE BOLETAS consultar CPE_SUMMARY RC
		 String tipo="15";
		 
		 String TipoExt=".xml";
		 
		 String rutaRead = "/home/acalcina/Escritorio/"+RUC+"/";
		
		ArrayList<String> identifiers = new ArrayList<String>();
			
		
		identifiers.add("20299759068-RC-20190611-00003");
		identifiers.add("20299759068-RC-20190611-00004");
//		identifiers.add("RA-20190612-4");
//		identifiers.add("RA-20190612-3");
//		identifiers.add("RA-20190612-2");
//		identifiers.add("RA-20190612-1");

		
		int f = 0;
		

		for(int i=0;i< identifiers.size();i++){
			
			try {
				
				
				// Example RC-20190405-1
				
				
//				File xmlFile = new File(rutaRead + RUC + "-" + tipo + "-" +identifiers.get(i)+ TipoExt);
				
				
				//For RESUMEN DE BOLETAS
//				File xmlFile = new File(rutaRead + RUC + "-" +identifiers.get(i)+ TipoExt);
				File xmlFile = new File(rutaRead +identifiers.get(i)+ TipoExt);
				
//				System.out.println(xmlFile);
				
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(xmlFile);
				

				doc.getDocumentElement().normalize();
				
				
				logger.info("Root element :" + doc.getDocumentElement().getNodeName()); // lee tipo de documento en general
				
				
				// LEER POR INDICES
//				int cant = ((NodeList) doc.getElementsByTagName("cbc:ID")).getLength();				
//				for (int j = 0; j < cant; j++) {												
//					String info = doc.getElementsByTagName("cbc:ID").item(4).getTextContent();					
//					System.out.println("Info: " +info );
//				}
//							
				
				
				
					if (tipo == "17") {
					
						
						
						int cant = ((NodeList) doc.getElementsByTagName("sac:DocumentNumberID")).getLength();							
						for (int j = 0; j < cant; j++) {
							
							String info1 = doc.getElementsByTagName("sac:DocumentSerialID").item(j).getTextContent();	
							String info2 = doc.getElementsByTagName("sac:DocumentNumberID").item(j).getTextContent();
							
//							System.out.println("Info : " +info1 +"-"+info2 );
							
						}
						
						
					String identifier= doc.getElementsByTagName("cbc:ID").item(0).getTextContent().toString();					
					String IssueDate= doc.getElementsByTagName("cbc:IssueDate").item(0).getTextContent().toString();					
					String DocumentTypeCode= doc.getElementsByTagName("cbc:DocumentTypeCode").item(0).getTextContent().toString();
					String DocumentSerialID= doc.getElementsByTagName("sac:DocumentSerialID").item(0).getTextContent().toString();
					String DocumentNumberID= doc.getElementsByTagName("sac:DocumentNumberID").item(0).getTextContent().toString();
					String ReferenceDate= doc.getElementsByTagName("cbc:ReferenceDate").item(0).getTextContent().toString();
					
								
//					
					
					System.out.println("identifier:  "+	identifier+"\n"+					
										"IssueDate: "+IssueDate +"\n"+									
										"DocumentType: "+DocumentTypeCode +"\n"+
										"Serie y Correlativo: "+DocumentSerialID +"-"+DocumentNumberID+"\n"+										
										"ReferenceDate: "+ReferenceDate+"\n"
							);
								
					
					
					
					}else if(tipo == "16"){
							
						
						// Tipo 1
//						String identifier= doc.getElementsByTagName("cbc:ID").item(0).getTextContent().toString();
//						String IssueDate= doc.getElementsByTagName("cbc:IssueDate").item(0).getTextContent().toString();						
//						String ReferenceDate= doc.getElementsByTagName("cbc:ReferenceDate").item(0).getTextContent().toString();						
//						String DocumentNumberID= doc.getElementsByTagName("cbc:ID").item(4).getTextContent().toString();						
//						String DocumentTypeCode= doc.getElementsByTagName("cbc:DocumentTypeCode").item(0).getTextContent().toString();
//										
//												
//						System.out.println(
//										"identifier:  "+	identifier+"\n"+
////											"IssueDate: "+IssueDate +"\n"+
////											"DocumentType: "+DocumentTypeCode +"\n"+
//											"DocumentNumberID: "+DocumentNumberID+"\n"
////											+
////											"ReferenceDate: "+ReferenceDate+"\n"
							
											
						// Tipo 2 Envian  XML
//						String identifier= doc.getElementsByTagName("cbc:ID").item(0).getTextContent().toString();
//						String IssueDate= doc.getElementsByTagName("cbc:IssueDate").item(0).getTextContent().toString();						
//						String ReferenceDate= doc.getElementsByTagName("cbc:ReferenceDate").item(0).getTextContent().toString();						
//						String DocumentNumberID= doc.getElementsByTagName("cbc:ID").item(3).getTextContent().toString();						
//						String DocumentTypeCode= doc.getElementsByTagName("cbc:DocumentTypeCode").item(0).getTextContent().toString();
//										
												
//						System.out.println(
//										"identifier:  "+	identifier+"\n"+
//											"IssueDate: "+IssueDate +"\n"+
//											"DocumentType: "+DocumentTypeCode +"\n"+
//											"DocumentNumberID: "+DocumentNumberID+"\n"
//											+
//											"ReferenceDate: "+ReferenceDate+"\n"										
								
								
//								);
						
						int cant = ((NodeList) doc.getElementsByTagName("cbc:ID")).getLength();							
						for (int j = 0; j < cant; j++) {
							
							String info = doc.getElementsByTagName("cbc:ID").item(j).getTextContent();	
							
							System.out.println("Info : " +info );
						
						}
						
						System.out.println("\n");
						
						// Resumen de boletas
						
					}else if(tipo == "15"){
						
						
						
						int cant = ((NodeList) doc.getElementsByTagName("cbc:ID")).getLength();							
						for (int j = 0; j < cant; j++) {
							
							String info = doc.getElementsByTagName("cbc:ID").item(j).getTextContent();	
							
							System.out.println("Info : " +info );
							
						}
						
						
						
						
						
						
						System.out.println("\n");
						
				}
				
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			f++;
			
			System.out.println("Total Documentos :" + f);
			
		}
		
		
		
		
		
		
		
		
		
		
	}

}
