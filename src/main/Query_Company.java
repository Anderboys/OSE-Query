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

public class Query_Company {
//	Query_Company
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Query_Company.class);
	private static DBConfig dbConfig = new DBConfig();
	
	
	static String RUC="20100304636";
	
//						20600958578
//						20551093035
	
//	static String type="01";
	
//	static String Series="B001";
	
//	static String FechaEmision="2019-01-02 05:00:00.000Z";
	
		
//	static String fecha="2019-01-02T05:00:00.000Z";	
	static String fecha="2019-01-01T00:00:00.000Z";

	
//	static String Status="ACCEPTED";
	
	static List<String> uuid = new ArrayList<String>();
	static List<String> uuidNo = new ArrayList<String>();
	static List<String> uuidA = new ArrayList<String>();
	
	static List<String> lista = new ArrayList<String>();
	
	static int count=0;
	public static void main(String[] args){
		
		boolean conector=true;

		 MongoDatabase dbW=null;
		MongoDatabase dbO=null;
//			if(conector){
//		 		dbW = dbConfig.connectionWeb();
				dbO = dbConfig.connectionOdin();
//			}else{
//				dbW = dbConfig.connectionWebDev();
//				dbO = dbConfig.connectionOseDev();
//			}
				////////////////////////////////////////////////////////////
		 		
		 	Document document=null;
		 
			String respuesta="";
			
			
			Bson filter=null;
			
			Bson fRuc = new Document("CompanyParty.PartyIdentification.ID",RUC);
			
			filter=Filters.and(fRuc);
			MongoCursor<Document> result = dbO.getCollection("COMPANY").find(filter).iterator();		
//			
						
			while (result.hasNext()) {				
				
				
				document = result.next();
			
				String ID  = ((Document) ((Document) document.get("CompanyParty")).get("PartyIdentification")).getString("ID");
				String Name  = ((Document) ((Document) document.get("CompanyParty")).get("PartyName")).getString("Name");
			
				
//				String tipo=(((Document) document.get("DocumentType")).getString("ExtensionDocument"));
			
				
				ArrayList array= ( ArrayList ) document.get( "DocumentType" );
						
				for(int i=0;i<array.size();i++) {
					
						
					Document document2=(Document)array.get(i);
					String extensionDocument=document2.getString("ExtensionDocument");
					
					boolean sign=document2.getBoolean("Sign");
					String PSE=document2.getString("PSE");
					
					System.out.println("ExtensionDocument "+ extensionDocument);
					System.out.println("sign: "+sign);
					System.out.println("PSE: "+PSE);
					
					
				}
				
//				Services.PDF.Create
				
				
				
				Boolean PDFcreate;
				Boolean PDFgeneric;
				ArrayList array2= ( ArrayList ) document.get( "Services");
				
				int dato = array2.size();
				
				for(int i=0;i<array2.size();i++) {					
						
					Document document2=(Document)array2.get(i);
					
					if(i==11){
						
						
						 PDFcreate= ((Document) document2.get("PDF")).getBoolean("Create");
						 if(PDFcreate){
							 System.out.println("Si genera PDF: "+PDFcreate);
						 }else{
							 System.out.println("No genera PDF: "+PDFcreate); 
						 }
						 
						
						
						 PDFgeneric= ((Document) document2.get("PDF")).getBoolean("Generic");
						 
						 if(PDFgeneric){
							 System.out.println("PDF Generico: "+PDFcreate);
						 }else{
							 System.out.println("PDF Personalizado "+PDFcreate); 
						 }
						 
						
						
					}
					
					
					
//					String extensionDocument= ((Document) document2.get("PDF")).getString("Create");
					
										
				
					
//					System.out.println("ExtensionDocument "+ extensionDocument);
				
					
					
				}
				
				
//				for(int i=0;i<lista.size();i++){
//					
//					
//					Document document2=(Document)lista.get(i);
//					
//					 tipo =  document2.getString("ExtensionDocument");
//					 sign = ""+document2.getBoolean("Sign");
//					 PSE = document2.getString("PSE");
//													
//				}
				
				
				
//				System.out.println(tipo);
				
				System.out.println(document.toJson().toString());
				System.out.println(result+" ==> Existe");
//				lista.add(identifier+" "+Description);
//				lista.add(UUID);
				
				
	
				count++;
		
			
			
			}

			 File archivos = new File("/home/acalcina/Escritorio/QueryAllODIN.txt");
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
