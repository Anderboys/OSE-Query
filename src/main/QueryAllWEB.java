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

public class QueryAllWEB {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(query.class);
	private static DBConfig dbConfig = new DBConfig();
	
	
	static String RUC="20429040583";
	
	static String type="01";
	static String serie="FB01";
	
//	static String Series="B001";
//	static String Series=
//	static String Status="ERROR";
	
//	static String fecha="2018-12-26T00:00:00.000Z";
	
	static String fecha="2019-03-14T00:00:00.000Z";
	
//	2019-03-14 00:00:00.000Z
	
	
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
		 		dbW = dbConfig.connectionWeb();
//				dbO = dbConfig.connectionOdin();
//			}else{
//				dbW = dbConfig.connectionWebDev();
//				dbO = dbConfig.connectionOseDev();
//			}
				////////////////////////////////////////////////////////////
		 		
		 	Document document=null;
		 
			String respuesta="";			
			
			Bson filter=null;
			
			Bson fRuc = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);
			Bson fType = new Document("Authorization.CPE.DocumentType",type);			
			
			
			
			
			//BAJAS TIPO
			Bson Type = new Document("Authorization.CPE.DocumentReferences.DocumentTypeCode",type);			
			Bson Serie = new Document("Authorization.CPE.DocumentReferences.DocumentSerialID",serie);
			
			
			
			// Todo hacia Adelante
//			Bson fID =  Filters.gte("ID","B001-0002658");
			
			
			Bson bStatus = new Document("Status.Status","ACCEPTED");	
			
			
			
//			Bson Validation = new Document("Validation.Description","OK");
			
//			Bson Status = new Document("Status.Status","ERROR");
			
			
			
//			Bson fMigration = Filters.and(Filters.exists("migrated", true));
//			Bson migrated = Filters.and(Filters.exists("migration.migrated", false));
			
			// Filtor por fechas
//			Bson filterDates = Filters.and(Filters.gte("Authorization.Timestamp", "2018-12-07T09:40:00Z"),
//							   Filters.lte("Authorization.Timestamp", "2018-12-07T09:41:00Z")); //622
			
			
			// RANGO DE FECHAS  Authorization.Timestamp
//			Instant instant = Instant.parse("2019-03-15T00:00:00.000Z"); //Pass your date.			
//			Instant instant2 = Instant.parse("2019-03-19T00:00:00.000Z");			
//			Date timestamp = Date.from(instant);
//			Date timestamp2 = Date.from(instant2);			
//			Bson fFECHAS2 = Filters.and(Filters.gte("Authorization.Timestamp", timestamp),Filters.lte("Authorization.Timestamp", timestamp2)); //622		
			
			
			
			
			// Filtro de fecha de envío en Adelante  Filters.gte
			
//			Instant instant = Instant.parse(fecha); //Pass your date.
//			Date timestamp = Date.from(instant);			
//			Bson fFECHA = Filters.gte("Authorization.CPE.IssueDate", timestamp);
			
			
//			 filter=Filters.and(Status); //TODO
//			 filter=Filters.and(fRuc,fType,bStatus);
			 filter=Filters.and(fRuc,bStatus);
//			 filter=Filters.and(fRuc,fType,fID);
//			 filter=Filters.and(fRuc,fType,fFECHAS2);
			
			MongoCursor<Document> result = dbW.getCollection("TRANSACTION").find(filter).iterator();
			boolean exist=false;
			
			
			
			while (result.hasNext()) {				
				
				
				document = result.next();
				
				String  rucEmisor=( (Document)((Document) ( (Document) document.get("Authorization")).get("AccountingSupplierParty")) .get("PartyIdentification") ).getString("ID");

				
				String UUID=document.getString("UUID");
				String identifier=document.getString("ID");				

//				Authorization.Timestamp
//				String Timestamp= ((Document) document.get("Authorization")).getString("Timestamp");
		
//				String Ticket= ((Document) document.get("Status")).getString("Ticket");
				
				
				String status= ((Document) document.get("Status")).getString("Status");	
				
//				String SUNATProces = ((Document) document.get("Status")).getString("SUNATProcess"); 
				
//				Status.Response.Code
//				String Code  = ((Document) ((Document) document.get("Status")).get("Response")).getString("Code"); 
				
				//Descricpcion sunat
//				String Description = ((Document) ((Document) document.get("Status")).get("Response")).getString("Description"); 
				
				// 01,03,07,08
				String DocumentType = ((Document) ((Document) document.get("Authorization")).get("CPE")).getString("DocumentType");			

				
				
//				Authorization.CPE.DocumentType
				
				// Salida de fecha
//				Date IssueDate2 = ((Document) ((Document) document.get("Authorization")).get("CPE")).getDate("IssueDate");	
//				SimpleDateFormat sdfPeru = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");				
//				sdfPeru.setTimeZone(TimeZone.getTimeZone("America/Peru/Lima"));			
//				String fecha = sdfPeru.format(IssueDate2);

				
//				String AccountingSupplierParty=((Document)((Document) ( (Document) document.get("Authorization")).get("AccountingSupplierParty")) .get("PartyIdentification") ).getString("ID");
				
//				Validation.Description OK
//				String Description= ((Document) document.get("Validation")).getString("Description");
				
				
				
				System.out.println(document.toJson().toString());
				System.out.println(result+" ==> Existe");
				
				
//				lista.add(identifier+"\t"+ AccountingSupplierParty+"\t"+Timestamp +"\n"+status);
//				lista.add(identifier+"\t"+ AccountingSupplierParty +"\t"+fecha);
				
//				lista.add(rucEmisor+" "+DocumentType+"\t"+ identifier+"\t"+status);

				lista.add(identifier+" "+DocumentType+" "+status);
				
//				lista.add(identifier);
				 
				exist=true;
				
				count++;	
			
			}
			
			
			

			 File archivos = new File("/home/acalcina/Escritorio/QueryAllWEB.txt");
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
