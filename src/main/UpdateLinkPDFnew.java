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
public class UpdateLinkPDFnew {
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateLinkPDFnew.class);
	private static DBConfig dbConfig = new DBConfig();
//	static String Series="B001";
//	static String Series=
	static List<String> uuid = new ArrayList<String>();
	static List<String> uuidNo = new ArrayList<String>();
	static List<String> uuidA = new ArrayList<String>();
	static int count=0;
	
//	static prueba p=new prueba("03","resources");
	
	public static void main(String[] args){
		
		boolean conector=true; // PROD
//		boolean conector=false; // PRUEBAS
		
		 File archivos = new File("/tmp/log-query-status.txt");
		 File archivos2 = new File("/tmp/log-query-stogareNo.txt");
		 File archivos3 = new File("/tmp/log-query-statusA.txt");
		 FileWriter fichero=null;
		 PrintWriter pw=null;
		 FileWriter fichero2=null;
		 PrintWriter pw2=null;
		 FileWriter fichero3=null;
		 PrintWriter pw3=null;
//		 pe.com.rackspace.storage.test
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
		
		 // Para actualizar LINK PDF el pdf debe ubicarse en la ruta /opt/efact/ose/storage_smb/PDF/INVOICE
		 
		List<String> identifiers = new ArrayList<String>();
	

		identifiers.add("10d48ad8-9bfd-47ed-b6fe-949b08caa72a");
//		identifiers.add("17dd8f07-3728-4192-b16e-d8c1307e9745");



//		identifiers.add("F001-00000881");
		MongoDatabase dbW=null;
		MongoDatabase dbO=null;
		if(conector){
//			dbW = dbConfig.connectionWeb();
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
	}
	
	public static String findOne (String UUID, MongoDatabase db) {
		
		Document document=null;
		String respuesta="";
		
		Bson filter = new Document("UUID",UUID);
		Bson fMigration = Filters.and(Filters.exists("Storage.PDF", true));
		filter = Filters.and(filter, fMigration);
		
//		String link="https://7c49413b1c22c814e97e-3ea8c335857d761126421d6b6cb03647.ssl.cf1.rackcdn.com/pdf/41012069-fddd-4f1b-a1a7-a328de59bf6e";
		String link="https://s3-api.dal-us-geo.objectstorage.softlayer.net/ose-prd-boleta-xml/10d48ad8-9bfd-47ed-b6fe-949b08caa72a";
		
		MongoCursor<Document> result = db.getCollection("TRANSACTION").find(filter).iterator();
		
		
		boolean exits=false;
		while (result.hasNext()) {
//			document = result.next();
			exits=true;
			break;
		}
		
		
		if (exits) {

			try {

//				link = p.proceso(UUID);

			} catch (Exception e) {
				System.out.println(e);
			}

		} else {
			uuidNo.add(UUID);
		}
		

		if (link != null && !link.equals("")) {
			
			boolean resp = updateStatus(UUID, link, db);
			if (resp) {
				respuesta = UUID;
				System.out.println("Actualizado " + UUID);
			} else {
				System.out.println("ERROR " + UUID);
			}
		}
		
		return respuesta;
		
	}
	
	
	
	public static boolean updateStatus(String UUID, String link,MongoDatabase db) {
		Bson filter = new Document("UUID", UUID);
		
		Bson newValue = new Document("Storage.XML", link);
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
