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

public class Generar_Link {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Generar_Link.class);
	private static DBConfig dbConfig = new DBConfig();
//	static String Series="B001";
//	static String Series=
	static List<String> uuid = new ArrayList<String>();
	static List<String> uuidNo = new ArrayList<String>();
	static List<String> uuidA = new ArrayList<String>();
	static int count=0;
	
	
//	static prueba p=new prueba("07","resources");
//	static prueba p=new prueba("03","resources");		
//	static prueba p=new prueba("07","resources");
//	static prueba p=new prueba("08","resources");
	
	public static void main(String[] args)throws IOException{
//		 prueba p=new prueba("01");
		
				
		boolean conector=true;  // produccion
//		boolean conector=false;	

//		 pe.com.rackspace.storage.test

		
		 // Para actualizar LINK PDF el pdf debe ubicarse en la ruta /opt/efact/ose/storage_smb/PDF/INVOICE
		 
		List<String> identifiers = new ArrayList<String>();
				
//		FileReader fileData= new FileReader("/home/acalcina/Escritorio/xD.txt");
//		
//		BufferedReader bf = new BufferedReader(fileData);
//		String sCadena="";
//		 while ((sCadena = bf.readLine())!=null) {
//			
//			 identifiers.add(sCadena);			 
//			 System.out.println(sCadena);
//		 }
		
		identifiers.add("75af0ced-07ca-4101-8570-2099b308a7c4");
//		identifiers.add("6d50a86e-ec69-4443-adc2-d66e9314cc40");
//		identifiers.add("599f6bff-8f01-4e21-9783-8ab4f778e149");
//		identifiers.add("7e73f119-2869-4f80-8430-c2d0be7c7ad2");
//		identifiers.add("94a71dc3-6aaa-47d3-9a74-63d1b2ab4423");
//		identifiers.add("a1da24cf-5a43-45f3-8a96-48785da70eb7");
//		identifiers.add("cb4277a1-5395-4f14-9b12-d46e8d239ffb");
//		identifiers.add("e3a670cb-62c9-465e-b20c-fa5cd688edcd");

		

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
