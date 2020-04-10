package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

import config.DBConfig;
import pe.com.efact.db.constants.ICollections;

public class UpadteLinkDirecto {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(update2.class);
	private static DBConfig dbConfig = new DBConfig();
	static List<String> uuid = new ArrayList<String>();
	static List<String> uuidNo = new ArrayList<String>();
	static List<String> uuidA = new ArrayList<String>();
	
	
	static int count=0;
	
	
//	static String tipo="XZP";
	static String status="";
	
	public static void main(String[] args){
		
//		boolean conector=false;// PRUEBAS
		boolean conector=true;// PROD
		
		 File archivos2 = new File("/tmp/log-query-stogareNo.txt");
		
		 FileWriter fichero2=null;
		 PrintWriter pw2=null;
		
		 try {
			if(archivos2.createNewFile()){
				LOGGER.info("txt creado");
			 }
		} catch (IOException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		
		List<String> identifiers = new ArrayList<String>();
	
		identifiers.add("a4e4a382-2466-4408-8dba-9772e57a05dc");
//		identifiers.add("56d6e558-6cc2-4b4d-a36b-f87ee05b2104");
//		identifiers.add("9440e949-b174-4227-833f-070fcd4e14ad");
//		identifiers.add("13a35aa3-98ea-4083-8841-a7b6672e7c35");

		
		MongoDatabase dbW=null;
		MongoDatabase dbO=null;
		
		if(conector){  //PRODUCCION
			
			dbW = dbConfig.connectionWeb();
			dbO = dbConfig.connectionOdin();
			
		}else{		//PRUEBAS
			
			dbW = dbConfig.connectionWebDev();
			dbO = dbConfig.connectionOseDev();
		}
		
		for (int i = 0; i < identifiers.size(); i++) {
			try {
//				work(identifiers.get(i),conector,dbW);  // PROD WEb
				work(identifiers.get(i),conector,dbO);  // PROD OSE
			} catch (Exception e) {
				// TODO Auto-generated catch block
				 LOGGER.info(e.toString());
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
		System.out.println(count);
		
	}
	
	public static void work(String identifier,boolean conector,MongoDatabase dbO) {
		
		LOGGER.info("---CONEXION A ODIN EXITOSA---");
		String fin=findOne(identifier,dbO);
		
		if(!fin.equals("")){
			uuid.add(fin);
		}else{
			LOGGER.info("---El ticket no existe---");
		}
	}
	
	public static String findOne (String UUID, MongoDatabase db) {
		
		String respuesta="";
	
		//factura
		
//		STATUS
//		VOIDED
//		ERROR
//		ACCEPTED
				
		String link="https://e10ec2c01e1df51993fa-5e992d1db7853edd0e1f9e5de88957f3.ssl.cf1.rackcdn.com/pdf/2e012c5f-fa70-490b-b6f4-04e608f2d5c9";
	
//		if (link != null && !link.equals("")) {

			boolean resp = updateStatus(UUID, link, db);
			if (resp) {
				respuesta = UUID;
				System.out.println("link Actualizado " + link);
			} else {
				System.out.println("ERROR CDR: " + UUID);
			}
//		}
	
			
	
		return respuesta;
	}

	public static boolean updateStatus(String UUID, String link,MongoDatabase db) {
		Bson filter = new Document("UUID", UUID);
		
		Bson newValue = new Document("Storage.PDF", link);
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
	
	}//fin


