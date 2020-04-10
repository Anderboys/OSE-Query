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

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

//import config.RabbitMQConfig;
//import pe.com.efact.broker.config.BrokerConfigType;
//import util.UtilRabbit;
import config.DBConfig;

public class Query_OSE_IN {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(query.class);
	private static DBConfig dbConfig = new DBConfig();
	
	
	static String RUC="20525512267";
	
	static String type="20";
	
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
			
			Bson fRuc = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);
			
			Bson fType = new Document("Authorization.Services.SUNATClearing.DocumentCode",type);			
			
			
			
			ArrayList<String> datos= new ArrayList<>();
			
			datos.add("R001-1404");
			datos.add("R001-1405");
			
			
			
			Bson fID = Filters.and(Filters.in("ID",datos));
			
//			Bson fStatus = new Document("Status.Status","ERROR");				
	
		
//			Bson Status = new Document("Status.Status","SENT");
			
//			Instant instant = Instant.parse(FechaEmision); //Pass your date.
//			Date fechaEmision = Date.from(instant);	
//			Bson SFechaEmision = new Document("IssueDate",fechaEmision);
			
//			SimpleDateFormat sdfPeru = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");			
//			sdfPeru.setTimeZone(TimeZone.getTimeZone("America/Peru/Lima"));			
//			String fecha = sdfPeru.format(SFechaEmision);
			
//			Bson Spdf = Filters.and(Filters.exists("Storage.PDF", false));
			
			
//			Bson ValidationCode = new Document("Validation.ValidationCode","0");
																	

			
//			 filter=Filters.and(Status);
			
//			filter=Filters.and(fFECHA,Spdf,pdfGeneric,ValidationCode,pdfCreate);
			filter=Filters.and(fRuc,fType,fID);
						
//			MongoCursor<Document> result = dbO.getCollection("TRANSACTION").find(filter).sort(new BasicDBObject("$natural", -1)).limit(10).iterator();
			MongoCursor<Document> result = dbO.getCollection("TRANSACTION").find(filter).iterator();
			boolean exist=false;
			
			
			
			while (result.hasNext()) {				
				
				
				document = result.next();
				
				String UUID=document.getString("UUID");
				String identifier=document.getString("ID");				

//				Authorization.Timestamp
//				String Timestamp= ((Document) document.get("Authorization")).getString("Timestamp");
		
				
				
//				String  rucEmisor=( (Document)((Document) ( (Document) document.get("Authorization")).get("AccountingSupplierParty")) .get("PartyIdentification") ).getString("ID");
//				
				
				//LLAMA FECHA
//				Date date=document.getDate("IssueDate");	
//				SimpleDateFormat sdfPeru = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");				
//				sdfPeru.setTimeZone(TimeZone.getTimeZone("America/Peru/Lima"));			
//				String fecha = sdfPeru.format(date);
				
				
				String status= ((Document) document.get("Status")).getString("Status");	
				
//				String SUNATProces = ((Document) document.get("Status")).getString("SUNATProcess"); 
				
//				Status.Response.Code
				String Code  = ((Document) ((Document) document.get("Status")).get("Response")).getString("Code"); 
				
				//Descricpcion sunat
				String Description = ((Document) ((Document) document.get("Status")).get("Response")).getString("Description"); 
				
//				DocumentCode
//			
//				String  DocumentCode=( (Document)((Document) ( (Document) document.get("Authorization")).get("Services")) .get("SUNATClearing") ).getString("DocumentCode");
				
				
//				Validation.Description OK
//				String Description= ((Document) document.get("Validation")).getString("Description");
				
				
				
				System.out.println(document.toJson().toString());
				System.out.println(result+" ==> Existe");
				
				
//				lista.add(identifier+"\t"+ AccountingSupplierParty+"\t"+Timestamp +"\n"+status);
//				lista.add(identifier+"\t"+ AccountingSupplierParty +"\t"+status);
//				lista.add(identifier + " "+DocumentCode +" "+ fecha );
//				lista.add(identifier + " "+ fecha );
//				lista.add(identifier);
				lista.add(identifier +" " + status +" " +Code +" "+Description );
				
				
				exist=true;
				
				count++;
		
			
			
			}

			 File archivos = new File("/home/acalcina/Escritorio/Query_OSE_IN.txt");
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
