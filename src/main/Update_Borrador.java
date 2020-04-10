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

public class Update_Borrador{
	
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
	
		
		identifiers.add("edeaab65-5c80-4fd4-9dc0-8cbc82abf324");		
		identifiers.add("d58b1ec0-b575-4359-a7eb-9cf29fe50433");		
		identifiers.add("2b53b8b0-afa1-47f8-96b1-a2a3e0b3e515");
		identifiers.add("ad6e3859-3071-4849-981a-dafb05e5f9f6");		
		identifiers.add("759bfbe4-c411-4546-a6fd-f893eda91d01");
		
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
				work(identifiers.get(i),conector,dbW);  // PROD WEb
//				work(identifiers.get(i),conector,dbO);  // ODIN
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
//		String fin=findOne(identifier,dbO);
		String fin=findOne(identifier,dbO);
		
		if(!fin.equals("")){
			uuid.add(fin);
		}else{
			LOGGER.info("---El Cliente ya fue actualizado---");
		}
	}
	
	public static String findOne (String UUID, MongoDatabase db) {
		
		String respuesta="";
	
		//factura
		
		String Nombre="Efact S.A.C.";
		
//		Boolean Generic =true;
	
//		if (Status != null && !Status.equals("")) {

			boolean resp = updateStatus(UUID, Nombre, db);
			if (resp) {
				respuesta = UUID;
				
				System.out.println("userData.UserParty.PartyName.Name Actualizado " + Nombre);
			} else {
				System.out.println("ERROR Al Actualizar: " + UUID);
//			}
		}
	
			
	
		return respuesta;
	}

	public static boolean updateStatus(String UUID, String nombre,MongoDatabase db) {
		
		
		// conexion efac web y odin COLLECTION_USER
		
		Bson filter = new Document("UUID",UUID);		
		Bson newValue = new Document("userData.UserParty.PartyName.Name", nombre);		
		Bson updateOp = new Document("$set", newValue);		
		return update(filter, updateOp, ICollections.COLLECTION_BORRADOR,db);
		
		
		
		// conexion Company dbOdin COLLECTION_COMPANY
		 
//		Bson filter = new Document("CompanyParty.PartyIdentification.ID",RUC);		
//		Bson newValue = new Document("CompanyParty.PartyName.Name", nombre);		
//		Bson updateOp = new Document("$set", newValue);		
//		return update(filter, updateOp, ICollections.COLLECTION_COMPANY,db);	
		
		
		
		// conexion Company dbOdin COLLECTION_COMPANY  PDF generic= false
		 
//		Bson filter = new Document("CompanyParty.PartyIdentification.ID",RUC);		
//		Bson newValue = new Document("Services.PDF.Generic", Generic);		
//		Bson updateOp = new Document("$set", newValue);		
//		return update(filter, updateOp, ICollections.COLLECTION_COMPANY,db);	
		
		
		
		// conexion efact web   CLIENTS
		
		
//		Bson filter = new Document("customerRuc",RUC);		
//		Bson newValue = new Document("customerRazonSocial", name);		
//		Bson updateOp = new Document("$set", newValue);		
//		return update(filter, updateOp, ICollections.COLLECTION_CLIENTS,db);
		
		
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


