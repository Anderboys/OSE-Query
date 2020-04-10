package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

public class UPDATE_STATUS {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(update2.class);
	private static DBConfig dbConfig = new DBConfig();
	static List<String> uuid = new ArrayList<String>();
	static List<String> uuidNo = new ArrayList<String>();
	static List<String> uuidA = new ArrayList<String>();
	
	
	static int count=0;
	
	
//	static String tipo="XZP";
	static String status="";
	
	public static void main(String[] args) throws IOException{
		
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
	
		
//	FileReader fileData= new FileReader("/home/acalcina/Escritorio/Query_WEB_Ticket.txt");
//		
//		BufferedReader bf = new BufferedReader(fileData);
//		String sCadena="";
//		 while ((sCadena = bf.readLine())!=null) {
//			
//			 identifiers.add(sCadena);			 
//			 System.out.println(sCadena);
//		 }
		
			
		identifiers.add("29356f7e-f662-4f3e-b540-b82d8be2fe6d");
//		identifiers.add("9f850693-f76e-44c8-88e6-6d6af062b9b8");
//		identifiers.add("c5242e10-6835-4a24-a0d0-a2421c5d58c5");



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
//				work(identifiers.get(i),conector,dbO);  // PROD OSE
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
	
		String Info="";
		
		// STATUS
		
//		String Status="ACCEPTED";
		String Status="ERROR";
//		String Status="REVERSED";
//		String Status="VOIDED";
		
		
//		String validationCode="0";
				
		//UBLVersionID
//		String UBLVersionID="2.0";		
//		String CustomizationID="1.0";
		
		
//		String Valdescrip="OK";
		
		//Montos
//		Double PayableAmount=80.00;
	
//		if (Status != null && !Status.equals("")) {

			boolean resp = updateStatus(UUID, Status, db);
			if (resp) {
				respuesta = UUID;
				System.out.println("Status Actualizado " + Status);
			} else {
				System.out.println("ERROR XML: " + UUID);
			}
//		}
	
			
	
		return respuesta;
	}

	
//	public static boolean updateStatus(String UUID, Double Status,MongoDatabase db) {
//	public static boolean updateStatus(String UUID, String Status,MongoDatabase db) {   // para montos ODIN
//	public static boolean updateStatus(String UUID, String UBLVersionID,MongoDatabase db) {   // UBLVersionID
//	public static boolean updateStatus(String UUID, String CustomizationID,MongoDatabase db) { 
		public static boolean updateStatus(String UUID, String Status,MongoDatabase db) {
			
//	public static boolean updateStatus(String UUID, String Valdescrip,MongoDatabase db) {
			
//	public static boolean updateStatus(String UUID, String validationCode,MongoDatabase db) {
		
		
		Bson filter = new Document("UUID", UUID);	
		
		Bson newValue = new Document("Status.Status", Status);	
//		Bson newValue = new Document("Validation.Description", Valdescrip);
		
		
		
//		Bson newValue = new Document("Validation.ValidationCode", validationCode);  //ValidationCode
		
//		Bson newValue = new Document("UBLVersionID", UBLVersionID);  //UBLVersionID
		
//		Bson newValue = new Document("CustomizationID", CustomizationID);  //CustomizationID
		
		// montos
//		Bson newValue = new Document("PayableAmount", PayableAmount);
		
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


