package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;

//import pe.com.rackspace.storage.test.prueba;

//import config.RabbitMQConfig;
//import pe.com.efact.broker.config.BrokerConfigType;
//import util.UtilRabbit;

import config.DBConfig;
import pe.com.efact.db.DBFactory;
import pe.com.efact.db.constants.ICollections;
import pe.com.efact.db.util.DAOUtil;

public class Generar_Link2 {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Generar_Link2.class);
	private static DBConfig dbConfig = new DBConfig();
//	static String Series="B001";
//	static String Series=
	static List<String> uuid = new ArrayList<String>();
	static List<String> uuidNo = new ArrayList<String>();
	static List<String> uuidA = new ArrayList<String>();
	
	
	static int count=0;
			
	
//	static prueba p=new prueba("20","resources");
	
	public static void main(String[] args)throws IOException{
//		 prueba p=new prueba("01");
		
				
		boolean conector=true;
//		boolean conector=false;	

//		 pe.com.rackspace.storage.test

		
		 // Para actualizar LINK PDF el pdf debe ubicarse en la ruta /opt/efact/ose/storage_smb/PDF/INVOICE
		 
		List<String> identifiers = new ArrayList<String>();
				
//		FileReader fileData= new FileReader("/home/acalcina/Escritorio/Regularizar12000.txt");
//		
//		BufferedReader bf = new BufferedReader(fileData);
//		String sCadena="";
//		 while ((sCadena = bf.readLine())!=null) {
//			
//			 identifiers.add(sCadena);			 
//			 System.out.println(sCadena);
//		 }
			
		
		identifiers.add("c7d6666d-3fb4-4f32-b0e7-1b9b051f2192");
//		identifiers.add("7927637d-5390-4930-8205-7a786847ddca");
//		identifiers.add("39f3dc60-49c8-4b37-b179-ab139f37bab0");
//		identifiers.add("69ae21d6-af30-4cd5-9391-b1e92bcc51fa");
//		identifiers.add("83f252ee-8239-4e25-9c1e-ab381d71d60c");
//		identifiers.add("a9f0d98d-f5f1-4828-ab1e-02231ef4491b");
//		identifiers.add("afaaa5d0-2b3c-47b2-9c8b-23438e74b1c0");
//		identifiers.add("b0203c61-31b1-48f8-aae9-c3e93165f511");
//		identifiers.add("bd93d1fc-0b9c-4bf5-8ff8-b82651c3a2d2");
//		identifiers.add("c099975d-55e2-4027-bcc7-cf3933bf4807");
//		identifiers.add("c0bda7de-ab65-4c77-bce0-69de8802dbf2");
//		identifiers.add("c7a7867f-dc43-448b-9dff-7d3dae9814f5");
//		identifiers.add("e02f45fe-7cee-4866-8488-b8815c68acf0");

	
		

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
		


		System.out.println(count);
		
		// end generar Link
		
		
		//star update link
		
		
		
		
		
	}
	
	
	
	
	public static void work(String identifier,boolean conector,MongoDatabase dbW,MongoDatabase dbO) {

		
		LOGGER.info("---CONEXION A ODIN EXITOSA---");
//		String ticket=findOneTicket(identifier,dbW);
		String fin=findOne(identifier,dbO);
		
		
		
		if(!fin.equals("")){
			uuid.add(fin);

		}else{
			LOGGER.info("---El ticket no existe---");
//			error.add(identifier);
		}
		
		
		// ADD METHOD FOR UPDATE LINK
		
		
		
		
	}
	
	// paso 3
	public static String findOne (String UUID, MongoDatabase db) {
		
		
		Document document=null;
		String respuesta="";
		Bson filter = new Document("UUID",UUID);
		
		
//		Bson fMigration = Filters.and(Filters.exists("Storage.PDF", false));
//		Bson fMigration = Filters.and(Filters.exists("Storage.PDF", true));
		
//		filter = Filters.and(filter, fMigration);
		filter = Filters.and(filter);
		
		String link="";
		
		MongoCursor<Document> result = db.getCollection("TRANSACTION").find(filter).iterator();
		
		boolean exits=false;
		
		while (result.hasNext()) {
//			document = result.next();
			exits=true;
			break;
		}
		
		
		
		if(exits){
			
			try {
								
//				link=p.procesoPDF(UUID);
//				link=p.procesoXML(UUID);
//				link=p.proceso(UUID); //CDR
				
				
			} catch (Exception e) {
				System.out.println(e);
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
			
			
		}else{
		uuidNo.add(UUID);
		}
		

		// UPDATE LINK PDF
		
		
		
		
		if(link!=null&&!link.equals("")){	
			
			// pinta el retorno del enlace  de prueba.java
			System.out.println(link);
			
			if(link.contains("rackcdn.com/pdf")){
				boolean resp=updateStatusPDF(UUID,link,db);
				
				if(resp){
					respuesta=UUID;
					System.out.println("Actualizado "+UUID);
				}else{
					System.out.println("ERROR "+UUID);
				}
			}
			
			if(link.contains("rackcdn.com/xml")){
				boolean resp=updateStatusXML(UUID,link,db);
				
				if(resp){
					respuesta=UUID;
					System.out.println("Actualizado "+UUID);
				}else{
					System.out.println("ERROR "+UUID);
				}
			}
		
			
			if(link.contains("rackcdn.com/cdr")){
				boolean resp=updateStatusCDR(UUID,link,db);
				
				if(resp){
					respuesta=UUID;
					System.out.println("Actualizado "+UUID);
				}else{
					System.out.println("ERROR "+UUID);
				}
			}
			
			
		}
		
	
		return respuesta;
	}
	
	public static boolean updateStatusPDF(String UUID, String link,MongoDatabase db) {
		
		Bson filter = new Document("UUID", UUID);		
		Bson newValue = new Document("Storage.PDF", link);
		Bson updateOp = new Document("$set", newValue);		
		return update(filter, updateOp, ICollections.COLLECTION_TRANSACTION,db);
			
	}	
	
	
	public static boolean updateStatusXML(String UUID, String link,MongoDatabase db) {
		Bson filter = new Document("UUID", UUID);
		
		Bson newValue = new Document("Storage.XML", link);
		Bson updateOp = new Document("$set", newValue);
		return update(filter, updateOp, ICollections.COLLECTION_TRANSACTION,db);
	}
	public static boolean updateStatusCDR(String UUID, String link,MongoDatabase db) {
		Bson filter = new Document("UUID", UUID);
		
		Bson newValue = new Document("Storage.CDR", link);
		Bson updateOp = new Document("$set", newValue);
		return update(filter, updateOp, ICollections.COLLECTION_TRANSACTION,db);
	}
	
	
	public static boolean update(Bson filter, Bson update, String collection,MongoDatabase db) {
		MongoCollection<Document> c = db.getCollection(collection);
		UpdateResult result = c.updateOne(filter, update);
		Boolean response = false;
		
		if (result.getModifiedCount() != 0) {
			response = true;
		}
		
		return response;
	}
	
	}

//}
