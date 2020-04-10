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

public class Update_ISOdate {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(update2.class);
	private static DBConfig dbConfig = new DBConfig();
	static List<String> uuid = new ArrayList<String>();
	static List<String> uuidNo = new ArrayList<String>();
	static List<String> uuidA = new ArrayList<String>();
	static int count=0;
	
	
//	static String tipo="XZP";
	static String status="";
	
	public static void main(String[] args){
		
		boolean conector=false;// PRUEBAS
//		boolean conector=true;// PROD
		
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
	
		identifiers.add("d91ee3c7-3104-42dd-adc9-aa4f4d49814e");
//		identifiers.add("e7cadf37-eda8-42b1-bd65-e223845604d9");
//		identifiers.add("18dd4b8f-688e-44b3-a756-47bdbc29e8f6");
//		identifiers.add("45da1ce9-390f-4b07-9a7d-2e61c5be6c1b");
//		identifiers.add("5a82ce9d-a21c-4a9f-8f4a-0ac3bae34753");
//		identifiers.add("e9ed0b2d-7336-44df-8310-182bb1a1a8df");
//		identifiers.add("81bb5ba8-adda-4434-9d01-783df13826e0");
//		identifiers.add("b9ee2409-44bd-4386-9872-2b519424d7fe");



		
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
				work(identifiers.get(i),conector,dbO);  // PROD WEb
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
		
//		2019-01-08T05:00:00.000Z
		String FechaOSE="2020-01-08 10:00:00.000Z";
	
		if (FechaOSE != null && !FechaOSE.equals("")) {

			boolean resp = updateStatus(UUID, FechaOSE, db);
			if (resp) {
				respuesta = UUID;
				System.out.println("Fecha Actualizado " + FechaOSE);
			} else {
				System.out.println("ERROR XML: " + UUID);
			}
		}
	
			
	
		return respuesta;
	}

	public static boolean updateStatus(String UUID, String FechaOSE,MongoDatabase db) {
		
		Bson filter = new Document("UUID", UUID);		
		Bson newValue = new Document("IssueDate", FechaOSE);
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


