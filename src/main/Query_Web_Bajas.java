package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.bson.BasicBSONObject;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

//import config.RabbitMQConfig;
//import pe.com.efact.broker.config.BrokerConfigType;
//import util.UtilRabbit;
import config.DBConfig;

public class Query_Web_Bajas {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(query.class);
	private static DBConfig dbConfig = new DBConfig();
	
	
	static String RUC="20117431615";	
	static String type="01";
	static String serie="F015";	
//	static String number="0003165";
	
//	static String Status="ACCEPTED";
	
	static List<String> uuid = new ArrayList<String>();
	static List<String> uuidNo = new ArrayList<String>();
	static List<String> uuidA = new ArrayList<String>();	
	static List<String> lista = new ArrayList<String>();
	
	static int count=0;
	public static void main(String[] args){
		
//		B009-00018236
		
		
		//STRING
//		ArrayList<String> identifier = new ArrayList<String>();				
//		identifier.add("6903");
//		identifier.add("14814");
		
		// INTEGER
		ArrayList<Integer> identifier = new ArrayList<Integer>();
		identifier.add(6903);
		
		// cnx efatc web
		 MongoDatabase dbW=null;
		 MongoDatabase dbO=null;		
 		 dbW = dbConfig.connectionWeb();								 		

				
		for(int i=0;i<identifier.size();i++){
			
			
//			String info1=identifier.get(i);			
//			int correlativo = Integer.parseInt(info1);
	
			 	Document document=null;
			 				
				
				Bson filter=null;
				
//				
				Bson Ruc = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);			
				Bson Type = new Document("Authorization.CPE.DocumentReferences.DocumentTypeCode",type);			
				Bson Serie = new Document("Authorization.CPE.DocumentReferences.DocumentSerialID",serie);
				
				//String 
				Bson Number = new Document("Authorization.CPE.DocumentReferences.DocumentNumberID",identifier.get(i));
				
//				Bson Number = new Document("Authorization.CPE.DocumentReferences.DocumentNumberID",correlativo);
				
				// Int
				
//				Bson Number = new Document("Authorization.CPE.DocumentReferences.DocumentNumberID",6447);
				
				filter=Filters.and(Ruc,Type,Serie,Number);
				
				
				MongoCursor<Document> result = dbW.getCollection("TRANSACTION").find(filter).iterator();		
//				
							
				while (result.hasNext()) {				
					
					
					document = result.next();
				
					String  rucEmisor=( (Document)((Document) ( (Document) document.get("Authorization")).get("AccountingSupplierParty")) .get("PartyIdentification") ).getString("ID");				
					String UUID=document.getString("UUID");
					String identifiersBaja=document.getString("ID");	
					String status= ((Document) document.get("Status")).getString("Status");
					
					
					// 17-16
					String DocumentType = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getString("DocumentType");
					
					//** ****************************************************
					
					//INFO DOCUMENTO RELACIONADO DE LA BAJA									
					
					ArrayList array= ( ArrayList ) ((Document) ( (Document) document.get("Authorization")).get("CPE")) .get("DocumentReferences");				
					int total= array.size();		
					
					
					String DocumentTypeCode="";
					String DocumentSerialID="";
					
					int DocumentNumberID = 0;			
					String  DocumentNumberID2="";
					
					
					for(int j=0;j<array.size();j++) {
						
							
						Document document2=(Document)array.get(i);
						
						
						DocumentTypeCode=document2.getString("DocumentTypeCode");
						DocumentSerialID=document2.getString("DocumentSerialID");
						
						try {
							DocumentNumberID=document2.getInteger("DocumentNumberID");
						} catch (Exception e) {
							// TODO: handle exception
							DocumentNumberID2=document2.getString("DocumentNumberID");
						}
						
									
					}
					//** ****************************************************
					
					 
					String NumberID = DocumentNumberID+"";
					
					String NumberID2 = DocumentNumberID2;
									
					System.out.println(document.toJson().toString());
					System.out.println(result+" ==> Existe");
									
					
//					lista.add(identifiersBaja+ " "+DocumentSerialID+" "+DocumentNumberID);
					
					
					if(DocumentType.equals("17")){
						lista.add(identifiersBaja+ " "+status+" "+DocumentTypeCode+" "+DocumentSerialID+"-"+NumberID);	
						
						
					}else if(DocumentType.equals("16")){
						lista.add(identifiersBaja+ " "+status+" "+DocumentTypeCode+" "+DocumentSerialID+"-"+NumberID2);
					}	
					
		
					count++;
			
				
				
				}
			
		}		
				


			 File archivos = new File("/home/acalcina/Escritorio/QueryWeb_Bajas.txt");
			 FileWriter fichero1=null;
			 PrintWriter pw1=null;		
			 try {
				if(archivos.createNewFile()){
					LOGGER.info("txt creado");
				 }	
					fichero1=new FileWriter(archivos);
					pw1= new PrintWriter(fichero1);
					for(int it = 0; it < lista.size(); it++){
						pw1.println(lista.get(it));
					}
				}catch (Exception e) {
						e.printStackTrace();
				}finally {
					try{
						if(null != fichero1)
							fichero1.close();
					}catch (Exception e2) {
						e2.printStackTrace();
					}
				}
	
		System.out.println(count);
		
	}
	
	
		
	}

//}
