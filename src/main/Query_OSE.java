package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;


//import org.w3c.dom.Document;



import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;


import config.DBConfig;
import oasis.names.specification.ubl.schema.xsd.creditnote_2.CreditNoteType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import sunat.names.specification.ubl.peru.schema.xsd.summarydocuments_1.SummaryDocumentsType;
public class Query_OSE {
//	ConsultarResumen_URL
//	ConsultarBaja
	private static final Logger LOGGER = LoggerFactory.getLogger(Query_OSE.class);
	private static DBConfig dbConfig = new DBConfig();
	
	static String RUC="20551093035";
//	static String RUC="20100166144";
//	20100166144
	
	static String type="03";
	
	
//	static String type="01";
//	static String type="03";
//	static String type="07";
//	static String type="16";
//	static String type="17";
//	static String type="20";
//	static String type="09";
//	static String type="08";d
	
//	static String Series="B001";
//	static String Series=
	
	static List<String> error = new ArrayList<String>();
	static List<String> uuid = new ArrayList<String>();
	static List<String> ListaNOexiste = new ArrayList<String>();
	static List<String> ListaQueryOSE = new ArrayList<String>();
	
	static int count=0;
	public static void main(String[] args)throws IOException{
		
		boolean conector=true;
		 
	
		List<String> identifiers = new ArrayList<String>();
		
	
//		FileReader fileData= new FileReader("/home/acalcina/Escritorio/NC.txt");
//		
//		BufferedReader bf = new BufferedReader(fileData);
//		String sCadena="";
//		 while ((sCadena = bf.readLine())!=null) {
//			
//			 identifiers.add(sCadena);			 
//			 System.out.println(sCadena);
//		 }
		
		
		 identifiers.add("F001-2497");
//		 identifiers.add("F001-2498");
//		 identifiers.add("F001-2499");
//		 identifiers.add("F001-2500");
//		 identifiers.add("F001-2501");
//		 identifiers.add("F001-2502");
//		 identifiers.add("F001-2503");
//		 identifiers.add("F001-2504");
//		 identifiers.add("F001-2505");
//		 identifiers.add("F001-2506");
//		 identifiers.add("F001-2507");
//		 identifiers.add("F001-2508");
//		 identifiers.add("F001-2509");
//		 identifiers.add("F001-2510");
//		 identifiers.add("F001-2511");
//		 identifiers.add("F001-2512");
//		 identifiers.add("F001-2513");
//		 identifiers.add("F001-2514");
//		 identifiers.add("F001-2515");
//		 identifiers.add("F001-2516");
//		 identifiers.add("B001-316");




		MongoDatabase dbW=null;
		MongoDatabase dbO=null;
		
		if(conector){
			dbW = dbConfig.connectionWeb();
			dbO = dbConfig.connectionOdin();
		}else{
			dbW = dbConfig.connectionWebDev();
			dbO = dbConfig.connectionOseDev();
		}
		
		
		for (int i = 0; i < identifiers.size(); i++) {
			try {
				work(identifiers.get(i),conector,dbW,dbO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				 LOGGER.info(e.toString());
			}
		}
		
		
		 File archivos1 = new File("/home/acalcina/Escritorio/Query_OSE.txt");
		 FileWriter fichero1=null;
		 PrintWriter pw1=null;		
		 try {
			if(archivos1.createNewFile()){
				LOGGER.info("txt creado");
			 }	
				fichero1=new FileWriter(archivos1);
				pw1= new PrintWriter(fichero1);
				for(int it = 0; it < ListaQueryOSE.size(); it++){
					pw1.println(ListaQueryOSE.get(it));
				}
			}catch (Exception e) {
					e.printStackTrace();
			}finally {
				try{
					if(null != fichero1)
						fichero1.close();
				}catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		

		
//		 File archivos2 = new File("/home/acalcina/Documentos/AA_NO_REGISTRADOS/NO_REGISTRADOS.txt");
//		 FileWriter fichero2=null;
//		 PrintWriter pw2=null;	
//		 try {
//			if(archivos2.createNewFile()){
//				LOGGER.info("txt creado");
//			 }	
//			fichero2=new FileWriter(archivos2);
//			pw2= new PrintWriter(fichero2);
//				for(int it = 0; it < ListaNOexiste.size(); it++){
//					pw2.println(ListaNOexiste.get(it));
//				}
//			}catch (Exception e) {
//					e.printStackTrace();
//			}finally {
//				try{
//					if(null != fichero2)
//						fichero2.close();
//				}catch (Exception e2) {
//					e2.printStackTrace();
//				}
//			}
		

		System.out.println(count);
		
	}
	
	
	//METODOS
	

	
	
	
	public static void work(String identifier,boolean conector,MongoDatabase dbW,MongoDatabase dbO) throws MalformedURLException {

		
//		LOGGER.info("---CONEXION A ODIN EXITOSA---");
		//conexion web
//		String ticket=findOneTicket(identifier,dbW);
		
		//conexion odin
		String ticket=findOneTicket(identifier,dbO);
		
		
		if(!ticket.equals("")){
			uuid.add(ticket);

		}else{
			
			LOGGER.info("---El ticket no existe---");
			ListaNOexiste.add(identifier);
			
			
//			 File archivos2 = new File("/home/acalcina/Documentos/AA_NO_REGISTRADOS/NO_REGISTRADOS.txt");
			 File archivos2 = new File("/home/acalcina/Escritorio/NO_REGISTRADOS.txt");
			 FileWriter fichero2=null;
			 PrintWriter pw2=null;	
			 try {
				if(archivos2.createNewFile()){
					LOGGER.info("txt creado");
				 }	
				fichero2=new FileWriter(archivos2);
				pw2= new PrintWriter(fichero2);
					for(int it = 0; it < ListaNOexiste.size(); it++){
						pw2.println(ListaNOexiste.get(it));
					}
				}catch (Exception e) {
						e.printStackTrace();
				}finally {
					try{
						if(null != fichero2)
							fichero2.close();
					}catch (Exception e2) {
						e2.printStackTrace();
					}
				}
		}
	}
	
	public static String findOne (String UUID, MongoDatabase db) {
		Document document=null;
		String respuesta="";
		
		Bson filter = new Document("UUID",UUID);		
//		Bson fMigration = Filters.and(Filters.exists("migration.migrated", false));
		Bson fMigration = Filters.and(Filters.exists("migration.migrated", true));
		
		
		
		filter = Filters.and(filter, fMigration);
		MongoCursor<Document> result = db.getCollection("TRANSACTION").find(filter).iterator();

		while (result.hasNext()) {
			document = result.next();
			

			
			LOGGER.info(document.toJson().toString());
			document.remove("_id");
			document.remove("migration");
			document.remove("jwt-val");
			LOGGER.info(document.toJson().toString());
		}
		if(document!=null){
			respuesta=document.toJson().toString();
		}
		return respuesta;
	}
	
	public static  String findOneTicket(String identifier, MongoDatabase db) throws MalformedURLException {
		
		Document document=null;
		String respuesta="";
		Bson filter=null;
		
				
		Bson fRus = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);		
		Bson fType = new Document("Authorization.Services.SUNATClearing.DocumentCode",type);		
		Bson fID = new Document("ID",identifier);
		
		
		
//		Bson ValidationCode = new Document("Validation.ValidationCode","0");
		
		// CON ERRORES
//		Bson fStatus = new Document("Status.Status","ERROR");


		
		// CONSULTA ...........	
//		filter=Filters.and(fRus,fType);		
//		filter = Filters.and(filter, fID);
//		filter=Filters.and(fRus,fType,fID,ValidationCode);	
		filter=Filters.and(fRus,fType,fID);
//		filter=Filters.and(fRus,fType,fID,fStatus);
		
//		filter = Filters.and(filter, fStatus);
//		LOGGER.info(RUC+"-"+type+"-"+identifier);
		
		MongoCursor<Document> result = db.getCollection("TRANSACTION").find(filter).iterator();
		
		boolean exist=false;
		
		while (result.hasNext()) {
			
			document = result.next();
//			System.out.println(document.toJson().toString());
			System.out.println(identifier+" ==> Existe");
				
			
			String storagePdf="";
			String storageXml="";
			String storageCdr="";		
					
			
//			if (document.toJson().contains("rackcdn.com/pdf")) {		
//			if(document.toJson().contains("rackcdn.com/pdf")||document.toJson().contains(".pdf")){
//				storagePdf = ((Document) document.get("Storage")).getString("PDF");
//				LOGGER.info(storagePdf);
//			}
			
////		if (document.toJson().contains("rackcdn.com/cdr")) {
//		if(document.toJson().contains("rackcdn.com/cdr")&& document.toJson().contains(".zip")){
//
//			storageCdr = ((Document) document.get("Storage")).getString("CDR");
//			LOGGER.info(storageCdr);
//		}

//			if (document.toJson().contains("rackcdn.com/xml")) {
			
				
				
			
			
			
			// ******************** ONLY READ DATOS URL   XML *******************************
				
			
				// ----------- ONLY READ DATOS URL   XML ------------------------	
//				String storageXml="";
			if (document.toJson().contains("rackcdn.com/xml") || document.toJson().contains(".xml")) {
				storageXml = ((Document) document.get("Storage")).getString("XML");
//				System.out.println(storageXml);
			}				
//			String identifiers = "";
//			try {
//
//				if (type.equals("01")) {
//					
//					 Object object = null;
//					 JAXBContext jaxbContext =
//					 JAXBContext.newInstance(InvoiceType.class);
//					 Unmarshaller unmarshaller =
//					 jaxbContext.createUnmarshaller();
//					 object = unmarshaller.unmarshal(new URL(storageXml));
//					 InvoiceType invoiceType = (InvoiceType) object;
//					
//					 String Payable_AmountXML=
//					 String.valueOf(invoiceType.getLegalMonetaryTotal().getPayableAmount().getValue());
//					 System.out.println(Payable_AmountXML);
//
//				} else if (type.equals("07")) {
//					Object object = null;
//					JAXBContext jaxbContext = JAXBContext.newInstance(CreditNoteType.class);
//					Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//					object = unmarshaller.unmarshal(new URL(storageXml));
//					CreditNoteType creditNoteType = (CreditNoteType) object;
//					// String Payable_AmountXML=
//					// String.valueOf(creditNoteType.getLegalMonetaryTotal().getPayableAmount().getValue());
//					identifiers = String.valueOf(creditNoteType.getID().getValue());
////					System.out.println("URL identifier: "+identifiers);
//
//				}
//
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
			
			// ---------------------END ONLY READ URL ----------------------------------
			
			
			
			
			
			
			
			
			
//			ID
			String id=  document.getString("ID");
			
			//UUID WEB y ODIN
			String uuid=document.getString("UUID");		
			
			String status= ((Document) document.get("Status")).getString("Status");	
			
//			String UBLVersionID=  document.getString("UBLVersionID");
			
//			String SUNATProces = ((Document) document.get("Status")).getString("SUNATProcess"); 
//			
////			Status.Response.Code
//			String Code  = ((Document) ((Document) document.get("Status")).get("Response")).getString("Code"); 
//			
			//Status.Response.Description
//			String Description = ((Document) ((Document) document.get("Status")).get("Response")).getString("Description"); 
//			
			
//			String DescriptionError= ((Document) document.get("Validation")).getString("Description");
			
//			Validation
//			String Validation= ((Document) document.get("Validation")).getString("ValidationCode");	
					
			
//			String id=document.getString("ID");
			
			
			
			//OSE
//			Validation.Description
//			String  validacion = ((Document) document.get("Validation")).getString("Description");
			
			//FECHA de emisión OSE
//			String date = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getDate("IssueDate");
			 
			
//			String date = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getDate("IssueDate");
//			 java.util.Date s=((Document) ((Document) document.get("Authorization")).get("CPE")).getString("IssueDate")
			 //Authorization.CPE.IssueDate
			 
			
			
//			Date date=document.getDate("IssueDate");	
//			SimpleDateFormat sdfPeru = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
//			sdfPeru.setTimeZone(TimeZone.getTimeZone("America/Peru/Lima"));
//			String fecha = sdfPeru.format(date);
////						
		
			
//			DocumentCode - Tipo = 01,03,07,08
//			String  DocumentCode=( (Document)((Document) ( (Document) document.get("Authorization")).get("Services")) .get("SUNATClearing") ).getString("DocumentCode");
			
			
//			Validation.Description OK
			String DescriptionVAL= ((Document) document.get("Validation")).getString("Description");
			
			
			String Code="";
			String DescriptionSunat="";
			
			// VALIDAR

			try {
				 Code  = ((Document) ((Document) document.get("Status")).get("Response")).getString("Code"); 
//				
			} catch (Exception e) {
				// TODO: handle exception
				 Code  = "SIN COD ERROR"; 
//				
			}
			
			try {
				DescriptionSunat = ((Document) ((Document) document.get("Status")).get("Response")).getString("Description"); 
				
			} catch (Exception e) {
				// TODO: handle exception
				DescriptionSunat="SIN DESCRIP";
			}
			
			//Descricpcion sunat
			
			
			
			
			//** .........................................................
			
			//INFO DOCUMENTO RELACIONADO DE LA BAJA									
			
//			ArrayList array= ( ArrayList ) ((Document) ( (Document) document.get("Authorization")).get("CPE")) .get("DocumentReferences");				
//			int total= array.size();
//			
//			String extensionDocument="";
//			
//			
//			
//			for(int i=0;i<array.size();i++) {
//				
//					
//				Document document2=(Document)array.get(i);
//				
//				 extensionDocument=document2.getString("DocumentSerialID");		
//							
//				System.out.println("DocumentSerialID2: "+extensionDocument);
//				
//				
//			}
			//** .........................................................
			
				

				
			
//			if(status.equals("ACCEPTED")||status.equals("VOIDED")){
//			if(status.equals("REJECTED")){	
//			if(Code.equals("0306")){	
//			if(validacion.equals("OK")){	
				
//						respuesta=identifier 						
//						+"\n"+" Status: "+status
//						+"\n"+" SUNATProces: "+ SUNATProces
//						+"\n"+ "Codigo: "+Code 
//						+"\n"+ "Description: "+Description
//						
//						+"\n"
//			
			;
			
			
				respuesta=id+"\t"+ status+"\t"+DescriptionVAL;
				
//				respuesta=id+"\t"+ status+"\n"+DescriptionVAL+"\n";
				
//				respuesta=id;
			
				ListaQueryOSE.add(respuesta);
				
//				ListaQueryOSE.add(identifier+ " "+status);
//				ListaQueryOSE.add(identifier+ "\n"+"Codigo Error:"+Validation+"\n"+DescriptionError);			
//				respuesta=identifier 					
//				+"\n"+" Status: "+status
////			
//				;		
				
//			}
				
			}
//			else{
//				 
//				if(descrip!=null){
//					respuesta=identifier+","+uuid+","+descrip.replace("\n", " ");
//				}
				 
//			}
			
			exist=true;
//			break;
			count++;
			
//		}
			
		if(!exist){
			System.out.println(identifier+" ==> NOOOO Existe");
			uuid.add(identifier);
		}
////			if(count>0){
//				if(count==0){
//					respuesta=ticket;
//				}
//				else{
//					respuesta+="|"+ticket;
//				}
				
//			}
		return respuesta;
		}

	
		
	}

//}
