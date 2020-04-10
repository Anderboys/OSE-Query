package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

//import config.RabbitMQConfig;
//import pe.com.efact.broker.config.BrokerConfigType;
//import util.UtilRabbit;
import config.DBConfig;
public class status {
	private static final Logger LOGGER = LoggerFactory.getLogger(status.class);
	private static DBConfig dbConfig = new DBConfig();
	
	static String RUC="20105887079";
	
	static String type="01";
//	static String type="17";
//	static String type="03";
//	static String type="07";
//	static String type="09";
//	static String Series="B001";
//	static String Series=
	static List<String> uuid = new ArrayList<String>();
	static List<String> uuidNo = new ArrayList<String>();
	static List<String> uuidA = new ArrayList<String>();
	static int count=0;
	public static void main(String[] args){
		
		boolean conector=true;
		
//		 File archivos = new File("/home/acalcina/Escritorio/WEB_UUID.txt");		 
		 File archivos = new File("/home/acalcina/Escritorio/OSE_Ticket.txt");
		 
//		 File archivos = new File("/home/acalcina/Escritorio/UUID_OSE.txt");
		 
		 File archivos2 = new File("/tmp/log-query-statusNo.txt");
		 File archivos3 = new File("/tmp/log-query-statusA.txt");
		 
		 FileWriter fichero=null;
		 PrintWriter pw=null;
		 FileWriter fichero2=null;
		 PrintWriter pw2=null;
		 FileWriter fichero3=null;
		 PrintWriter pw3=null;
		 
		 try {
			if(archivos.createNewFile()){
				LOGGER.info("txt creado");
			 }
			if(archivos2.createNewFile()){
				LOGGER.info("txt creado");
			 }
			if(archivos3.createNewFile()){
				LOGGER.info("txt creado");
			 }
		} catch (IOException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		List<String> identifiers = new ArrayList<String>();
		
		
		identifiers.add("F001-0005900");
		identifiers.add("F001-0005901");
		identifiers.add("F001-0005902");
		identifiers.add("F001-0005903");
		identifiers.add("F001-0005904");
		identifiers.add("F001-0005905");
		identifiers.add("F001-0005906");
		identifiers.add("F001-0005907");
		identifiers.add("F001-0005908");
		identifiers.add("F002-0005516");
		identifiers.add("F002-0005517");
		identifiers.add("F002-0005518");
		identifiers.add("F002-0005519");
		identifiers.add("F002-0005520");
	
	
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
		
		try{
//			
			fichero=new FileWriter(archivos);
			pw= new PrintWriter(fichero);
			for(int it = 0; it < uuid.size(); it++){
				pw.println(uuid.get(it));
			}
			
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				try{
					if(null != fichero)
						fichero.close();
				}catch (Exception e2) {
					e2.printStackTrace();
				}
		}
		
		try{
//			
			fichero2=new FileWriter(archivos2);
			pw2= new PrintWriter(fichero2);
			for(int it = 0; it < uuidNo.size(); it++){
				pw2.println(uuidNo.get(it));
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
		
		try{
//			
			fichero3=new FileWriter(archivos3);
			pw3= new PrintWriter(fichero3);
			for(int it = 0; it < uuidA.size(); it++){
				pw3.println(uuidA.get(it));
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
		String ticket=findOneTicket(identifier,dbW);
		
		//conexion odin
//		String ticket=findOneTicket(identifier,dbO);
		
		
		if(!ticket.equals("")){
			uuid.add(ticket);

		}else{
			LOGGER.info("---El ticket no existe---");
//			error.add(identifier);
		}
	}
	
	public static String findOne (String UUID, MongoDatabase db) {
		Document document=null;
		String respuesta="";
		Bson filter = new Document("UUID",UUID);
		Bson fMigration = Filters.and(Filters.exists("migration.migrated", false));
		filter = Filters.and(filter, fMigration);
		MongoCursor<Document> result = db.getCollection("TRANSACTION").find(filter).iterator();

		while (result.hasNext()) {
			document = result.next();
			
//			boolean v=((Document) ((Document)((Document) document.get("Authorization")).get("Services")).get("PDF")).getBoolean("Generic");
//		if(Generic){
//			((Document) ((Document)((Document) document.get("Authorization")).get("Services")).get("PDF")).replace("Generic", false,true);
//		}else{
//			((Document) ((Document)((Document) document.get("Authorization")).get("Services")).get("PDF")).replace("Generic", true,false);
//		}
			

//			((Document)  document.get("Status")).replace("Status", "VOIDED", "ACCEPTED");
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
		
		
		//tipo doc web
		Bson fType = new Document("Authorization.CPE.DocumentType",type);	
		
		//tipo doc ODIN
//		Bson fType = new Document("Authorization.Services.SUNATClearing.DocumentCode",type);
				
		
		
		Bson fID = new Document("ID",identifier);
//		Bson fStatus = new Document("Status.Status","ERROR");
		
		filter=Filters.and(fRus,fType);		
		filter = Filters.and(filter, fID);
		
		
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
			String status= ((Document) document.get("Status")).getString("Status");
			
			//ID WEB
//			String id=document.getString("ID");
			
			//UUID WEB y ODIN
			String uuid=document.getString("UUID");
			
			//UIID Ticket 
//			String uuid= ((Document) document.get("Status")).getString("Ticket");
			
			//OSE
//			Validation.Description
//			String  validacion = ((Document) document.get("Validation")).getString("Description");
			
			//FECHA de emisión OSE
//			String date = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getDate("IssueDate");
			 
			
//			String date = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getDate("IssueDate");
//			 java.util.Date s=((Document) ((Document) document.get("Authorization")).get("CPE")).getString("IssueDate")
			 //Authorization.CPE.IssueDate
			 
//			DESCRIPCION DE STATUS
//			String descrip = ((Document) ((Document) document.get("Status")).get("Response")).getString("Description");
			
			
//			if(status.equals("ACCEPTED")||status.equals("VOIDED")){	
//			if(status.equals("ACCEPTED")){	
			
//			if(validacion.equals("OK")){	
//			if(status.equals("ERROR")){
			
//			if(status.equals("SENT")){
							
				uuidA.add(identifier);
				
//				respuesta=uuid+"	"+date;
//				respuesta=identifier;
//				respuesta=identifier +" "+ uuid + " "+ status +" "+ descrip ;
				respuesta=uuid;
				
//			}
//			else{
//				 
//				if(descrip!=null){
//					respuesta=identifier+","+uuid+","+descrip.replace("\n", " ");
//				}
//				 
//			}
			
			exist=true;
//			break;
			count++;
			
		}
		if(!exist){
			System.out.println(identifier+" ==> NOOOO Existe");
		uuidNo.add(identifier);
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
