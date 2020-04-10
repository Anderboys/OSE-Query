package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;


import config.DBConfig;
public class REPORTE_ODIN {
	private static final Logger LOGGER = LoggerFactory.getLogger(REPORTE_ODIN.class);
	private static DBConfig dbConfig = new DBConfig();
	
	static String RUC="20327591062";	
	
	static String typeDocument="03";
	
//	static String fecha="2018-10-11T00:00:00.000Z";
//	static String type="03";
//	static String Series="B001";

	static String ValidationCode="2873";
	
	
	static List<String> ListReportODIN = new ArrayList<String>();
	
	static int count=0;
	public static void main(String[] args){
		
		
		
		boolean conector=true;
		

//		List<String> identifiers = new ArrayList<String>();//		
//		identifiers.add("F009-5866");
//		identifiers.add("F101-0015731");

	
		MongoDatabase dbW=null;
		MongoDatabase dbO=null;
		
		if(conector){
			dbW = dbConfig.connectionWeb();
			dbO = dbConfig.connectionOdin();
		}else{
			dbW = dbConfig.connectionWebDev();
			dbO = dbConfig.connectionOseDev();
		}
		
				
		Document document=null;
		String respuesta="";
		Bson filter=null;
		
		
		
		// WHERE  RUC + TYPE DOCUMENT + IDENTIFIER
		
		//RUC
		Bson fRus = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);		
		
		// TYPE DOCUMENT
		Bson fType = new Document("Authorization.Services.SUNATClearing.DocumentCode",typeDocument);	
		
		Bson fMigration = Filters.and(Filters.exists("migration.migrated", false));	
				
		// IDENTIFIERS
//		Bson fID = new Document("ID",identifier);
		
//		Bson VALIDATION_CODE = new Document("Validation.ValidationCode",ValidationCode);	
		
//		Instant instant = Instant.parse(fecha); //Pass your date.
//		Date fechaEmision = Date.from(instant);				
//		Bson fFECHA = Filters.eq("Authorization.CPE.IssueDate", fechaEmision);
		
		
//		 Instant instant = Instant.parse("2018-09-21T00:00:00.000Z"); //Pass your date.		 
//		 Date fecha = (Date) Date.from(instant);		 
//		Bson fFECHA = new Document("Authorization.CPE.IssueDate",instant);
		
//		Bson fFECHA = new Document("Authorization.CPE.DocumentType",type);
		
//		Bson fStatus = new Document("Status.Status","ERROR");
		
		
		//
//		filter=Filters.and(fRus,fType);	
		filter=Filters.and(fRus,fType,fMigration);		
//		filter = Filters.and(filter, fID);
		
//		filter=Filters.and(fMigration,VALIDATION_CODE);
		
		
//		filter = Filters.and(filter, fStatus);
//		LOGGER.info(ruc+"-"+fecha);
				
		MongoCursor<Document> result = dbO.getCollection("TRANSACTION").find(filter).iterator();
				
		
		document = result.next();
				
		System.out.println(document);
		
		boolean exist=false;
		
		while (result.hasNext()) {
			
			document = result.next();
			System.out.println(document.toJson().toString());
//			System.out.println(fRus+" ==> Existe");
			//Status.Response.Description			
			
			//ODIN
				
		
			String uuid=document.getString("UUID");
			
			String id=document.getString("ID");
			
			
			
//			String status= ((Document) document.get("Status")).getString("Status");	
			
//			String SUNATProces = ((Document) document.get("Status")).getString("SUNATProcess"); 
			
//			Status.Response.Code
//			String Code  = ((Document) ((Document) document.get("Status")).get("Response")).getString("Code"); 
			
			//Descricpcion sunat
//			String Description = ((Document) ((Document) document.get("Status")).get("Response")).getString("Description"); 
								
			
//			ValidationCode = 0 ok		
			
//			String ValidationCode= ((Document) document.get("Validation")).getString("ValidationCode");
			
			
//			String AccountingSupplierParty=((Document)((Document) ( (Document) document.get("Authorization")).get("AccountingSupplierParty")) .get("PartyIdentification") ).getString("ID");
			
//			Validation.Description OK
//			String Description= ((Document) document.get("Validation")).getString("Description");
			
//			String Interface=document.getString("Interface");
			
//			Authorization.DocumentType.ExtensionDocument
//			String ExtensionDocument = ((Document) ((Document) document.get("Authorization")).get("DocumentType")).getString("ExtensionDocument");
			
			
//			Authorization.DocumentType.Sign
//			Boolean Sign = ((Document) ((Document) document.get("Authorization")).get("DocumentType")).getBoolean("Sign");
			
//			Authorization.Timestamp
//			String Timestamp= ((Document) document.get("Authorization")).getString("Timestamp");
	
			
//			Authorization.AccountingSupplierParty.PartyIdentification.ID
//			String RUCsupplier =""+((Document) ((Document) document.get("Authorization")).get("AccountingSupplierParty")).get("PartyIdentification");
			
		
			
			// sin no son clientes XML			
//			Boolean Generic=( (Document)((Document) ( (Document) document.get("Authorization")).get("Services")) .get("PDF") ).getBoolean("Generic");
			
			
			String storagePdf="";
			String storageXml="";
			String storageCdr="";		
			
			
//			if (document.toJson().contains("rackcdn.com/pdf")) {		
//			if(document.toJson().contains("rackcdn.com/pdf")||document.toJson().contains(".pdf")){
//				storagePdf = ((Document) document.get("Storage")).getString("PDF");
//				LOGGER.info(storagePdf);
//			}
////
//			if (document.toJson().contains("rackcdn.com/xml")) {
////			if(document.toJson().contains("rackcdn.com/xml")||document.toJson().contains(".xml")){
//
//				storageXml = ((Document) document.get("Storage")).getString("XML");
//				LOGGER.info(storageXml);
//			}
////
//			if (document.toJson().contains("rackcdn.com/cdr")) {
////			if(document.toJson().contains("rackcdn.com/cdr")&& document.toJson().contains(".zip")){
//
//				storageCdr = ((Document) document.get("Storage")).getString("CDR");
//				LOGGER.info(storageCdr);
//			}
//			
//			if( document.toJson().contains(".zip")){
//				storageCdr = ((Document) document.get("Storage")).getString("CDR");
//				LOGGER.info(storageCdr);
//			}
			
			
			
			
			
			
			
			
			
			
//			if(ValidationCode == null){
				ListReportODIN.add(uuid);
//			}
			
			
//			ListReportODIN.add(id);
//			uuidA.add(id+" "+"Interface: "+Interface+ "  ExtensionDocument: "+ExtensionDocument+"  Sign: "+Sign+"  Generico: "+Generic);

			
			
//			String StorageXML= ((Document) document.get("Storage")).getString("XML");
					
			
//			if(storageXml != null){
//				ListReportODIN.add(id+" "+storageXml);
//			}
				
			exist=true;
//			break;
			count++;
			
			LOGGER.info(count+"");
			
		}
	
		
		
		 File archivos1 = new File("/home/acalcina/Escritorio/REPORTE_ODIN.txt");
		 FileWriter fichero1=null;
		 PrintWriter pw1=null;		
		 try {
			if(archivos1.createNewFile()){
				LOGGER.info("txt creado");
			 }	
				fichero1=new FileWriter(archivos1);
				pw1= new PrintWriter(fichero1);
				for(int it = 0; it < ListReportODIN.size(); it++){
					pw1.println(ListReportODIN.get(it));
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
