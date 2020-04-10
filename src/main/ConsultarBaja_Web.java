package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;


import config.DBConfig;
public class ConsultarBaja_Web {
//	ConsultarBaja_Web
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultarBaja_Web.class);
	private static DBConfig dbConfig = new DBConfig();
	
	static String RUC="20550800030";
	
	static String type="17";
//	static String type="03";
//	static String type="08";
//	static String type="09";
//	static String type="07";
//	static String type="17";   //bajas
//	static String type="20";
//	static String Series="B001";
//	static String Series=	
	
	
	
	static List<String> uuid = new ArrayList<String>();
	static List<String> uuidNo = new ArrayList<String>();
	
	static List<String> ListaReporte = new ArrayList<String>();
	static List<String> ListaNOexiste = new ArrayList<String>();
	
	static int count=0;
	public static void main(String[] args)throws IOException{
		
		boolean conector=true;		
		 		
	
		List<String> identifiers = new ArrayList<String>();
		
		
//		FileReader fileData= new FileReader("/home/acalcina/Escritorio/Query_OSE.txt");
//		
//		BufferedReader bf = new BufferedReader(fileData);
//		String sCadena="";
//		 while ((sCadena = bf.readLine())!=null) {
//			
//			 identifiers.add(sCadena);			 
//			 System.out.println(sCadena);
//		 }
		
		
		identifiers.add("RA-20190523-338");
		identifiers.add("RA-20190523-339");
		identifiers.add("RA-20190523-340");
		identifiers.add("RA-20190523-341");
		identifiers.add("RA-20190523-342");
		identifiers.add("RA-20190523-338");
		identifiers.add("RA-20190523-339");
		identifiers.add("RA-20190523-340");
		identifiers.add("RA-20190523-341");
		identifiers.add("RA-20190523-342");

	

		MongoDatabase dbW=null;
		MongoDatabase dbO=null;
		
		if(conector){
			dbW = dbConfig.connectionWeb();
//			dbO = dbConfig.connectionOdin();
		}else{
			dbW = dbConfig.connectionWebDev();
			dbO = dbConfig.connectionOseDev();
		}
		
		//1.
		for (int i = 0; i < identifiers.size(); i++) {
			try {
				work(identifiers.get(i),conector,dbW,dbO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				 LOGGER.info(e.toString());
			}
		}
		
		
		 File archivos1 = new File("/home/acalcina/Escritorio/Query_WEB_BAJAS.txt");
		 FileWriter fichero1=null;
		 PrintWriter pw1=null;		
		 try {
			if(archivos1.createNewFile()){
				LOGGER.info("txt creado");
			 }	
				fichero1=new FileWriter(archivos1);
				pw1= new PrintWriter(fichero1);
				for(int it = 0; it < ListaReporte.size(); it++){
					pw1.println(ListaReporte.get(it));
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
		
		
		
		 File archivos2 = new File("/home/acalcina/Escritorio/IDENTIFIER_NO_REGISTRADOS.txt");
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
		
		

		System.out.println(count);
		
	}
	//2.
	public static void work(String identifier,boolean conector,MongoDatabase dbW,MongoDatabase dbO) {

		
		LOGGER.info("---CONEXION A ODIN EXITOSA---");
	
		String ticket=findOneTicket(identifier,dbW);
		
		
//		if(!ticket.equals("")){
		if(ticket != ""){
//			uuid.add(ticket);
		LOGGER.info("---Documento EXiste---");

		}else{
			
			LOGGER.info("---El ticket no existe---");
			ListaNOexiste.add(identifier);
		}
	}
	

	
	
	//3.
	public static  String findOneTicket(String identifier, MongoDatabase db) {
		
		Document document=null;
		String respuesta="";
		Bson filter=null;
		
		
		
		// WHERE  RUC + TYPE DOCUMENT + IDENTIFIER
		
		//RUC
		Bson fRus = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);		
		
		// TYPE DOCUMENT
		Bson fType = new Document("Authorization.CPE.DocumentType",type);	
				
		// IDENTIFIERS
		Bson fID = new Document("ID",identifier);
		
		
//		Bson fStatus = new Document("Status.Status","ERROR");
		
		
		//
		filter=Filters.and(fRus,fType, fID);		
//		filter = Filters.and(filter, fID);
		
		
//		filter = Filters.and(filter, fStatus);
		LOGGER.info(RUC+"-"+type+"-"+identifier);
		MongoCursor<Document> result = db.getCollection("TRANSACTION").find(filter).iterator();
		
		boolean exist=false;
		
		while (result.hasNext()) {
			
			document = result.next();
			
			System.out.println(document.toJson().toString());
			System.out.println(identifier+" ==> Existe");
			//Status.Response.Description			
			
			//WEB
				
		
			String uuid=document.getString("UUID");
			String id=document.getString("ID");
			
//			Authorization.Timestamp
//			String Timestamp= ((Document) document.get("Authorization")).getString("Timestamp");
					
			
//			ruc WEB emisor
//			Authorization.AccountingSupplierParty.PartyIdentification.ID//		
//			String  rucEmisor=( (Document)((Document) ( (Document) document.get("Authorization")).get("AccountingSupplierParty")) .get("PartyIdentification") ).getString("ID");
			
			
//			Authorization.AccountingSupplierParty


					
			
//			String Description = ((Document) ((Document) document.get("Status")).get("Response")).getString("Description");			
			//UIID Ticket 
//			String Ticket= ((Document) document.get("Status")).getString("Ticket");
			
//			Authorization.AccountingSupplierParty
//			AccountingSupplierParty
//			String ID= ""+((Document) document.get("Authorization")).get("AccountingSupplierParty");
			
			//COLECCION  CPE
//			String IssueDate = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getDate("IssueDate");
//			Date IssueDate2 = ((Document) ((Document) document.get("Authorization")).get("CPE")).getDate("IssueDate");
			
			
//			String PaymentDueDate = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getDate("PaymentDueDate");			
//			String DocumentType = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getString("DocumentType");			
//			String Serie = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getString("Serie");			
//			String Number = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getInteger("Number");			
//			String CurrencyCode = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getString("CurrencyCode");			
//			String PayableAmount = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getDouble("PayableAmount");
			
//			AccessOSE
//			String Username = ""+((Document) ((Document) document.get("Authorization")).get("AccessOSE")).getString("Username");
//			String Password = ""+((Document) ((Document) document.get("Authorization")).get("AccessOSE")).getString("Password");
			
//			STATUS
			String status= ((Document) document.get("Status")).getString("Status");	
			
			
//			DESCRIPCION DE STATUS Response/Description
//			String descrip = ((Document) ((Document) document.get("Status")).get("Response")).getString("Description");
			
			
			//COVERTIR FORMATO FECHA DE SALIDA
//			SimpleDateFormat sdfPeru = new SimpleDateFormat("dd-MM-yyyy");			
//			SimpleDateFormat sdfPeru = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
//			
//			sdfPeru.setTimeZone(TimeZone.getTimeZone("America/Peru/Lima"));			
//			String fecha = sdfPeru.format(IssueDate2);
			 
		
//			Authorization.CPE.DocumentReferences.DocumentSerialID
//			Authorization.CPE.DocumentReferences.DocumentNumberID
			

			//** ****************************************************
			
			//INFO DOCUMENTO RELACIONADO DE LA BAJA									
			
			ArrayList array= ( ArrayList ) ((Document) ( (Document) document.get("Authorization")).get("CPE")) .get("DocumentReferences");				
			int total= array.size();
			
			
			
			String DocumentTypeCode="";
			String DocumentSerialID="";
			
			int DocumentNumberID = 0;			
			String  DocumentNumberID2="";
			
			for(int i=0;i<array.size();i++) {
				
					
				Document document2=(Document)array.get(i);
				
				
				DocumentTypeCode=document2.getString("DocumentTypeCode");
				DocumentSerialID=document2.getString("DocumentSerialID");
				
				try {
					DocumentNumberID=document2.getInteger("DocumentNumberID");
				} catch (Exception e) {
					// TODO: handle exception
					DocumentNumberID2=document2.getString("DocumentNumberID");
				}
				
				
							
//				System.out.println("DocumentTypeCode: "+DocumentTypeCode);
//				System.out.println("DocumentSerialID2: "+DocumentSerialID);				
//				System.out.println("DocumentNumberID: "+DocumentNumberID);
				
				
				
			}
			//** ****************************************************
			
			
			String NumberID = DocumentNumberID+"";
			
			String NumberID2 = DocumentNumberID2;

			if(type.equals("17")){
				ListaReporte.add(identifier+ " "+status+" "+DocumentTypeCode+" "+DocumentSerialID+" "+NumberID2);
			}else if(type.equals("16")){
				ListaReporte.add(identifier+ " "+status+" "+DocumentTypeCode+" "+DocumentSerialID+" "+NumberID);
			}						
			
			
			
			
			exist=true;
//			break;
			count++;
			
		}
		
		if(!exist){
			System.out.println(identifier+" ==> NOOOO Existe");
			ListaNOexiste.add(identifier);
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
