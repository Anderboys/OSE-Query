package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

import config.DBConfig;


public class REPORTE_WEB {
	private static final Logger LOGGER = LoggerFactory.getLogger(REPORTE_WEB.class);
	private static DBConfig dbConfig = new DBConfig();
	
	static String RUC="20100905800";	
	
	static String typeDocument="07";
	
	static String status="SENT";
//	static String status="VOIDED";
	
	static String fecha="2019-01-09T00:00:00.000Z";						
	
	static String fecha1="2018-11-30T00:00:00.000Z";
	static String fecha2="2018-12-11T00:00:00.000Z";
	
//	05-09-2017
//	25-09-2017
			
	
	
//	static String type="17";
//	static String type="03";
//	static String type="07";
//	static String type="09";
//	static String Series="F012";
//	static String Series=
	
	static List<String> Lista1 = new ArrayList<String>();
	static List<String> Lista2 = new ArrayList<String>();
	static List<String> Lista3 = new ArrayList<String>();
	
	static List<String> ListaReporte = new ArrayList<String>();
	
	static int count=0;
	
	public static void main(String[] args) throws ParseException{
		
		
		
		boolean conector=true;
		

//		List<String> identifiers = new ArrayList<String>();
		
//		identifiers.add("F101-0015730");
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
		Bson fRuc = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);	
		
		
//		Authorization.AccountingSupplierParty.PartyIdentification.ID
		// TYPE DOCUMENT
		Bson fType = new Document("Authorization.CPE.DocumentType",typeDocument);

		
		Bson fSTATUS = new Document("Status.Status",status);

//		Bson fSerie = new Document("Authorization.CPE.Serie",Series);		
			
		
		// IDENTIFIERS
//		Bson fID = new Document("ID",identifier);
		
		
		
	
		// POR FECHA
		
		Instant instant = Instant.parse(fecha); //Pass your date.
		Date timestamp = Date.from(instant);				
//		Bson fFECHA = Filters.eq("Authorization.CPE.IssueDate", timestamp);
		Bson fFECHA = Filters.gte("Authorization.CPE.IssueDate", timestamp);
		
				
			
		// RANGO DE FECHAS ISODate
		
//		Instant instant1 = Instant.parse(fecha1); //Pass your date.
//		Instant instant2 = Instant.parse(fecha2); //Pass your date.
//		Date timestamp1 = Date.from(instant1);
//		Date timestamp2 = Date.from(instant2);		
//		Bson fFECHAS = Filters.and(Filters.gte("Authorization.CPE.IssueDate", timestamp1),
//									Filters.lte("Authorization.CPE.IssueDate", timestamp2));
		
		
		// RANGO DE FECHAS String Timestamp
		
//		Bson fFECHAS = Filters.and(Filters.gte("Authorization.Timestamp", fecha1),
//						   		   Filters.lte("Authorization.Timestamp", fecha2));
		
		
		
	
		

//		Bson fStatus = new Document("Status.Status","ERROR");
		
		
	
		// QUERY COMPLETO 
//		filter=Filters.and(fRuc);
//		filter=Filters.and(fRuc,fType);
//		filter=Filters.and(fRuc,fSTATUS);		
//		filter=Filters.and(fRuc,fType,fSTATUS,fFECHAS);
//		filter=Filters.and(fRuc,fType,fSTATUS,fFECHA);
//		filter=Filters.and(fRuc,fFECHA);		
//		filter=Filters.and(fRuc,fFECHAS);
		filter=Filters.and(fType,fSTATUS,fFECHA);
		
		
		MongoCursor<Document> result = dbW.getCollection("TRANSACTION").find(filter).iterator();
		
		// ejecuta todo el resultado
//		document = result.next();		
		
//		boolean exist=false;
		
		while (result.hasNext()) {
			
			document = result.next();
			
			System.out.println(document.toJson().toString());
			System.out.println(fRuc+" ==> Existe");
			//Status.Response.Description			
			
			//WEB
				
		
			String uuid=document.getString("UUID");
			String id=document.getString("ID");
			
//			Authorization.Timestamp
//			String Timestamp= ((Document) document.get("Authorization")).getString("Timestamp");
					
			
//			Authorization.AccountingSupplierParty.PartyIdentification.ID
//			String RUCsupplier =""+((Document) ((Document) document.get("Authorization")).get("AccountingSupplierParty")).get("PartyIdentification");
			
//			Authorization.AccountingSupplierParty
			
//			Status.Response.Description
//			String Description = ((Document) ((Document) document.get("Status")).get("Response")).getString("Description");			
			//UIID Ticket 
//			String Ticket= ((Document) document.get("Status")).getString("Ticket");
			
//			Authorization.AccountingSupplierParty
//			AccountingSupplierParty
//			String ID= ""+((Document) document.get("Authorization")).get("AccountingSupplierParty");
			
			
			//COLECCION  CPE
//			String IssueDate = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getDate("IssueDate");			
			Date IssueDate2 = ((Document) ((Document) document.get("Authorization")).get("CPE")).getDate("IssueDate");
			
//			String PaymentDueDate = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getDate("PaymentDueDate");			
			String DocumentType = ((Document) ((Document) document.get("Authorization")).get("CPE")).getString("DocumentType");			
//			String Serie = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getString("Serie");			
//			String Number = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getInteger("Number");			
//			String CurrencyCode = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getString("CurrencyCode");			
//			String PayableAmount = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getDouble("PayableAmount");
			
//			AccessOSE
//			String Username = ""+((Document) ((Document) document.get("Authorization")).get("AccessOSE")).getString("Username");
//			String Password = ""+((Document) ((Document) document.get("Authorization")).get("AccessOSE")).getString("Password");
			
//			STATUS
			String status= ((Document) document.get("Status")).getString("Status");	
			
//			Authorization.CPE.DocumentReferences.DocumentSerialID
			
			
			
			//COVERTIR FORMATO FECHA DE SALIDA
			SimpleDateFormat sdfPeru = new SimpleDateFormat("dd-MM-yyyy");			
//			SimpleDateFormat sdfPeru = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");			
//			
			sdfPeru.setTimeZone(TimeZone.getTimeZone("America/Peru/Lima"));	
			String fecha2 = sdfPeru.format(IssueDate2);
			
			
//			if(status.equals("ACCEPTED")|| status.equals("VOIDED")){
//				ListaReporte.add(id+"\t"+uuid+"\t"+status);
//				ListaReporte.add(uuid);
//				ListaReporte.add(id+" "+uuid+" "+status);
//			}
			
//			ListaReporte.add(Ticket+" "+status+" "+DocumentType);
			
//			ListaReporte.add(id+"\t"+"Tipo Documento: "+DocumentType+"\t"+status+"\t"+fecha2);				
//			ListaReporte.add(id+"\t"+DocumentType + " "+fecha2);
			ListaReporte.add(uuid+" "+DocumentType+" "+status);	
//			ListaReporte.add(id +" "+DocumentType+" " + status+" "+fecha2);
//			ListaReporte.add(id );
//			}	
		
//			exist=true;
//			break;
			count++;
			
		}

		
		
		 File archivos1 = new File("/home/acalcina/Escritorio/REPORTE_WEB.txt");
		 FileWriter fichero1=null;
		 PrintWriter pw1=null;		
		 try {
			if(archivos1.createNewFile()){
				LOGGER.info("txt creado");
			 }	
				fichero1=new FileWriter(archivos1);
				pw1= new PrintWriter(fichero1);
				for(int it = 0; it < ListaReporte.size(); it++){
					pw1.println(ListaReporte.get(it));
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
