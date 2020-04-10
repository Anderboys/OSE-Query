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

public class PDF_Link_Null {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(query.class);
	private static DBConfig dbConfig = new DBConfig();
	
	
	static String RUC="20566030188";

//	static String type="01";
//	static String type="03";
//	static String type="07";
	static String type="08";
//	static String type="20";
	
//	static String Series="B001";
	
//	static String FechaEmision="2019-01-02 05:00:00.000Z";
	static String fecha="2019-02-01T00:00:00.000Z";	
					 

	
//	static String fecha="2018-12-31T05:00:00.000Z";
//	2019-01-05T05:00:00.000Z
//	2018-12-31T05:00:00.000Z
//	sacar reporte
//	2018-12-31 00:00:00.000Z
	
//	2018-12-31 05:00:00.000Z
	
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
		
		
			if(conector){
		 		dbW = dbConfig.connectionWeb();
				dbO = dbConfig.connectionOdin();
			}else{
				dbW = dbConfig.connectionWebDev();
				dbO = dbConfig.connectionOseDev();
			}
				////////////////////////////////////////////////////////////
		 		
		 	Document document=null;
		 
			String respuesta="";
			
			
			Bson filter=null;
			
			Bson fRus = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);
			
			Bson fType = new Document("Authorization.Services.SUNATClearing.DocumentCode",type);			
			
						
//			Bson fStatus = new Document("Status.Status","ERROR");				
			
			
//			Validation.ValidationCode
			Bson ValidationCode = new Document("Validation.ValidationCode","0");
			
			
//			Bson Validation = new Document("Validation.Description","OK");
			
//			Bson Status = new Document("Status.Status","SENT");
			
//			Instant instant = Instant.parse(FechaEmision); //Pass your date.
//			Date fechaEmision = Date.from(instant);	
//			Bson SFechaEmision = new Document("IssueDate",fechaEmision);
			
//			SimpleDateFormat sdfPeru = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");			
//			sdfPeru.setTimeZone(TimeZone.getTimeZone("America/Peru/Lima"));			
//			String fecha = sdfPeru.format(SFechaEmision);
			
			
//			Authorization.Services.PDF.Generic
			
			Bson pdfGeneric = new Document("Authorization.Services.PDF.Generic", true); // PDF generico
//			Bson pdfGeneric = new Document("Authorization.Services.PDF.Generic", false); // PDF personalizado
							
			
			// No Generan PDF = false
			Bson pdfCreate = new Document("Authorization.Services.PDF.Create", true);
			
//			Storage.CDR
//			Bson Spdf = new Document("Storage.PDF","SENT");
			Bson Spdf = Filters.and(Filters.exists("Storage.CDR", false));
			
			
//			Bson fMigration = Filters.and(Filters.exists("migrated", true));
//			Bson migrated = Filters.and(Filters.exists("migration.migrated", false));
			
			
			// ṔOR FECHA EMISION   OR   FECHA DE ENVÍO
			Instant instant = Instant.parse(fecha); //Pass your date.
			Date timestamp = Date.from(instant);				
			Bson fFECHA = Filters.gte("IssueDate", timestamp);
//			Bson fFECHA = Filters.gte("IssueDate", timestamp);
			
		
			
//			filter=Filters.and(Status);
			
//			filter=Filters.and(fFECHA,Spdf,pdfGeneric,ValidationCode,pdfCreate);
			
//			filter=Filters.and(fFECHA,Spdf,pdfGeneric,ValidationCode,pdfCreate,fType);
			
			filter=Filters.and(fRus,fType,ValidationCode,Spdf);
			
			
//			filter=Filters.and(Spdf);
						
			
			MongoCursor<Document> result = dbO.getCollection("TRANSACTION").find(filter).iterator();
			boolean exist=false;			
			
			
			while (result.hasNext()) {				
				
				
				document = result.next();
				
				String UUID=document.getString("UUID");
				String identifier=document.getString("ID");				

//				Authorization.Timestamp
//				Date Timestamp= ((Document) document.get("Authorization")).getDate("Timestamp");
		
				
//				
				String  rucEmisor=( (Document)((Document) ( (Document) document.get("Authorization")).get("AccountingSupplierParty")) .get("PartyIdentification") ).getString("ID");
//				
				
				
				// FECHA EMISION
//				Date date=document.getDate("IssueDate");	
//				SimpleDateFormat sdfPeru = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
//				
//				sdfPeru.setTimeZone(TimeZone.getTimeZone("America/Peru/Lima"));			
//				String fechaEmisión = sdfPeru.format(date);
				
				
				// FECHA DE ENVÍO
				
				
//				String status= ((Document) document.get("Status")).getString("Status");	
				
//				String SUNATProces = ((Document) document.get("Status")).getString("SUNATProcess"); 
				
//				Status.Response.Code
//				String Code  = ((Document) ((Document) document.get("Status")).get("Response")).getString("Code"); 
				
				//Descricpcion sunat
//				String Description = ((Document) ((Document) document.get("Status")).get("Response")).getString("Description"); 
				
//							
//				String  DocumentCode=( (Document)((Document) ( (Document) document.get("Authorization")).get("Services")) .get("SUNATClearing") ).getString("DocumentCode");
				
				
				
//				Validation.Description OK
//				String Description= ((Document) document.get("Validation")).getString("Description");
				
				
				System.out.println("Imprimiendo resultado document Json() ");
				
				System.out.println(document.toJson().toString());
				System.out.println(result+" ==> Existe");
				
				
//				lista.add(identifier+"\t"+ AccountingSupplierParty+"\t"+Timestamp +"\n"+status);
//				lista.add(identifier+"\t"+ AccountingSupplierParty +"\t"+status);
//				lista.add(identifier + " "+rucEmisor +" "+ fecha );
//				lista.add(rucEmisor+" "+ UUID);
				
				lista.add(identifier);
				
//				lista.add(UUID+" "+fechaEmisión);
//				lista.add(UUID+" "+DocumentCode + " "+ fechaEmisión);
				
				
				exist=true;
				
				count++;
		
			
			
			}

			 File archivos = new File("/home/acalcina/Escritorio/PDF-NULL.txt");
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
