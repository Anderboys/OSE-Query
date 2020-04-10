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
public class CPE {
	private static final Logger LOGGER = LoggerFactory.getLogger(CPE.class);
	private static DBConfig dbConfig = new DBConfig();
	
	static String RUC="20269376806";
	static String type="01";
	static String Series="F003";	
	
//	static String RUC="20100134455";
//	static String type="03";
//	static String Series="B001";
	
	static List<String> uuid = new ArrayList<String>();
	static List<String> ListPadronesNO = new ArrayList<String>();
	public static void main(String[] args){
		boolean conector=true;
		 File archivos = new File("/home/acalcina/Escritorio/CPE.txt");
		 File archivos2 = new File("/home/acalcina/Escritorio/CPE_No.txt");
		 FileWriter fichero=null;
		 PrintWriter pw=null;	
		 FileWriter fichero2=null;
		 PrintWriter pw2=null;
		 try {
			if(archivos.createNewFile()){
				LOGGER.info("txt creado");
			 }
			if(archivos2.createNewFile()){
				LOGGER.info("txt creado");
			 }
		} catch (IOException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		List<String> identifiers = new ArrayList<String>();
		
		identifiers.add("42");
		identifiers.add("43");
//		identifiers.add("534223");
//		identifiers.add("856656");
//		identifiers.add("856657");


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
			for(int it = 0; it < ListPadronesNO.size(); it++){
				pw2.println(ListPadronesNO.get(it));
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
	
	public static void work(String identifier,boolean conector,MongoDatabase dbW,MongoDatabase dbO) {

		
		LOGGER.info("---CONEXION A ODIN EXITOSA---");
		
		String ticket=findOneTicket(identifier,dbO);
		
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
		String valRespuesta="";
		Bson filter=null;
		
		Bson fRus = new Document("PartyIdentification",RUC);
		Bson fType = new Document("DocumentType",type);
		Bson fID = new Document("Series",Series);
		Bson fNumber = new Document("Number",identifier+"");
		

		filter = Filters.and(fRus, fType,fID,fNumber);
		


		boolean exist=false;
		MongoCursor<Document> resulta = db.getCollection("CPE").find(filter).iterator();
		
		while (resulta.hasNext()) {
					
			
			document = resulta.next();
			

			String DocumentType= document.getString("DocumentType");
			String PartyIdentification= document.getString("PartyIdentification");
			String Status= document.getString("Status");
			String IssueDate= document.getString("IssueDate");
			Double PayableAmount= document.getDouble("PayableAmount");
			
			String CurrencyCode="";
			
			if(document.getString("CurrencyCode") != null ){
				 CurrencyCode= document.getString("CurrencyCode");
			}else{
				 CurrencyCode="TIPO MONEDA NO ESPECIFICADO";
			}

			
			System.out.println(document.toJson().toString());
			
			System.out.println(Series+"-"+identifier+" ==> Existe");
			
			valRespuesta=PartyIdentification+"\t" +DocumentType+"\t" +Status+"\t"+Series+"-"+identifier +"\t"+IssueDate+"\t"+PayableAmount +"\t"+CurrencyCode ;
			 
			exist=true;
			break;
		}
		if(!exist){
			
			System.out.println(Series+"-"+identifier+" ==>NO Existe");
			ListPadronesNO.add(Series+"-"+identifier);
		}
		return valRespuesta;
		}
		
	}

//}
