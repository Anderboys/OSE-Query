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
public class ConsultarBaja_Odin {
//	ConsultarResumenBoleta
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultarBaja_Odin.class);
	private static DBConfig dbConfig = new DBConfig();
	static String RUC="20100058503";
//	static String type="01";
//	static String type="03";
//	static String type="07";
	static String type="17";
//	static String type="16";
//	static String type="20";
//	static String type="09";
//	static String type="08";
//	static String Series="B001";
//	static String Series=
	
	static List<String> Listaerror = new ArrayList<String>();
	static List<String> uuid = new ArrayList<String>();
	static List<String> ListaNOexiste = new ArrayList<String>();
	static List<String> ListaQueryOSE = new ArrayList<String>();
	
	static int count=0;
	public static void main(String[] args)throws IOException{
		
		boolean conector=true;
		List<String> identifiers = new ArrayList<String>();
	
//		FileReader fileData= new FileReader("/home/acalcina/Escritorio/retenciones.txt");
//		
//		BufferedReader bf = new BufferedReader(fileData);
//		String sCadena="";
//		 while ((sCadena = bf.readLine())!=null) {
//			
//			 identifiers.add(sCadena);			 
//			 System.out.println(sCadena);
//		 }
		
		identifiers.add("RA-20190531-7");
		identifiers.add("RA-20190531-6");
		identifiers.add("RA-20190531-5");
		identifiers.add("RA-20190531-4");
		identifiers.add("RA-20190531-3");

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
				 Listaerror.add(identifiers.get(i));
				 // AGREGAR NO EXISTEN
			}
		}
		
		
		 File archivos1 = new File("/home/acalcina/Escritorio/ConsultarBaja.txt");
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
				try{identifiers.add("RA-20190522-325");
					if(null != fichero1)
						fichero1.close();
				}catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		 File archivos2 = new File("/home/acalcina/Escritorio/NO_EXISTE.txt");
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
		
		 
		 File archivos3 = new File("/home/acalcina/Escritorio/NO_EXISTE_ERROR.txt");
		 FileWriter fichero3=null;
		 PrintWriter pw3=null;	
		 try {
			if(archivos3.createNewFile()){
				LOGGER.info("txt creado");
			 }	
			fichero3=new FileWriter(archivos3);
			pw3= new PrintWriter(fichero3);
				for(int it = 0; it < Listaerror.size(); it++){
					pw3.println(Listaerror.get(it));
				}
			}catch (Exception e) {
					e.printStackTrace();
			}finally {
				try{
					if(null != fichero3)
						fichero3.close();
				}catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		 
		 

		System.out.println(count);
		
	}
	
	public static void work(String identifier,boolean conector,MongoDatabase dbW,MongoDatabase dbO) {

		
		LOGGER.info("---CONEXION A ODIN EXITOSA---");
		
		//conexion web
//		String ticket=findOneTicket(identifier,dbW);
		
		//conexion odin
		String ticket=findOneTicket(identifier,dbO);
		
		
		if(!ticket.equals("")){
			uuid.add(ticket);

		}else{
			
			LOGGER.info("---El ticket no existe---");
			
		}
	}
	
	public static String findOne (String UUID, MongoDatabase db) {
		Document document=null;
		String respuesta="";
		
		Bson filter = new Document("UUID",UUID);		
		Bson fMigration = Filters.and(Filters.exists("migration.migrated", false));
//		Bson fMigration = Filters.and(Filters.exists("migration.migrated", true));
		
		
		
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
	
	public static  String findOneTicket(String identifier, MongoDatabase db) {
		
		Document document=null;
		String respuesta="";
		Bson filter=null;
		
				
		Bson fRus = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);		
		Bson fType = new Document("Authorization.Services.SUNATClearing.DocumentCode",type);		
		Bson fID = new Document("ID",identifier);
		
//		Bson ValidationCode = new Document("Validation.ValidationCode","0");


		
		// CONSULTA ...........	
//		filter=Filters.and(fRus,fType);		
//		filter = Filters.and(filter, fID);
//		filter=Filters.and(fRus,fType,fID,ValidationCode);	
		filter=Filters.and(fRus,fType,fID);
		
//		filter = Filters.and(filter, fStatus);
		LOGGER.info(RUC+"-"+type+"-"+identifier);
		
		MongoCursor<Document> result = db.getCollection("TRANSACTION").find(filter).iterator();
		
		boolean exist=false;
		
		while (result.hasNext()) {
			
			document = result.next();
			System.out.println(document.toJson().toString());
			System.out.println(identifier+" ==> Existe");
						
			
			// ODIN
			
//			ID
			String id=  document.getString("ID");
			
			//UUID WEB y ODINstatus
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
//			Authorization.CPE.DocumentType
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
//						
		
					
			
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
				
				
				System.out.println("Serie " +DocumentSerialID );

				
				try {
					DocumentNumberID=document2.getInteger("DocumentNumberID");
					
					System.out.println("Correlativo: " +DocumentNumberID );
					
				} catch (Exception e) {
					// TODO: handle exception
					DocumentNumberID2=document2.getString("DocumentNumberID");
					
					System.out.println("Correlativo: " +DocumentNumberID2 );
				}
				
				
			
							
			}
			//** ****************************************************
			
			
			String NumberID = DocumentNumberID+"";
			
			String NumberID2 = DocumentNumberID2;

			if(type.equals("17")){
				ListaQueryOSE.add(identifier+ " "+status+" "+DocumentTypeCode+" "+DocumentSerialID+" "+NumberID);
			}else if(type.equals("16")){
				ListaQueryOSE.add(identifier+ " "+status+" "+DocumentTypeCode+" "+DocumentSerialID+" "+NumberID2);
			}						
			
			
			
				
			}

			
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
