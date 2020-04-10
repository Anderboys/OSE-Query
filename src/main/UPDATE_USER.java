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

public class UPDATE_USER {
	
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
	
		
		identifiers.add("20600751167");		
		
		
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
		String fin=findOne(identifier,dbO);
		
		if(!fin.equals("")){
			uuid.add(fin);
		}else{
			LOGGER.info("---El Cliente no existe---");
		}
	}
	
	public static String findOne (String RUC, MongoDatabase db) {
		
		String respuesta="";
	
		//factura
		
		String Nombre="CARRANZA AUTOMOTRIZ SERVICIOS E.I.R.L.";
		
//		Boolean Generic =true;
	
//		if (Status != null && !Status.equals("")) {

			boolean resp = updateStatus(RUC, Nombre, db);
			if (resp) {
				respuesta = RUC;
				
				System.out.println("Social Reason Actualizado " + Nombre);
//			} else {
//				System.out.println("ERROR XML: " + UUID);
//			}
		}
	
			
	
		return respuesta;
	}

	public static boolean updateStatus(String RUC, String nombre,MongoDatabase db) {
		
		
		// conexion efac web y odin COLLECTION_USER
		
		Bson filter = new Document("UserParty.PartyIdentification.ID",RUC);
		
		Bson newValue = new Document("UserParty.PartyName.Name", nombre);		
		Bson updateOp = new Document("$set", newValue);		
		return update(filter, updateOp, ICollections.COLLECTION_USER,db);
		
		
		
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


