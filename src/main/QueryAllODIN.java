package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;

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
import oasis.names.specification.ubl.schema.xsd.creditnote_2.CreditNoteType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
public class QueryAllODIN {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryAllODIN.class);
	private static DBConfig dbConfig = new DBConfig();
	
	static String RUC="20551093035";
	
	
//	20566030188  guias
	static String type="01";
//	static String type="03";
//	static String type="07";

//	static String type="08";
//	static String type="16";
//	static String type="17";
//	static String type="20";
	
//	static String Series="B001";
	
//	static String FechaEmision="2019-01-02 05:00:00.000Z";
	
		
//	static String fecha="2019-01-02T05:00:00.000Z";	
	static String fecha="2019-04-01T00:00:00.000Z";

	
//	static String Status="ACCEPTED";
	
	static List<String> uuid = new ArrayList<String>();
	static List<String> uuidNo = new ArrayList<String>();
	static List<String> uuidA = new ArrayList<String>();
	
	static List<String> lista = new ArrayList<String>();
	
	static int count=0;
	public static void main(String[] args) throws MalformedURLException{
		
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
//			Bson UBLVersionID = new Document("UBLVersionID","2.1");			
//			Bson fStatus = new Document("Status.Status","ERROR");				
//			Bson fReferenceDate = new Document("ReferenceDate","2019-05-29");

			
			
//			ArrayList<String> datos= new ArrayList<>();			
//			datos.add("2019-01-17");
//			datos.add("2019-01-25");
//			datos.add("2019-01-29");
//			datos.add("2019-01-26");
//			datos.add("2019-01-26");
//			datos.add("2019-01-29");			
//			Bson fReferenceDate = Filters.and(Filters.in("ReferenceDate",datos));

			// BAJAS
			
			
//			Bson Validation = new Document("Validation.Description","OK");
			
			Bson UBLVersionID = new Document("UBLVersionID","2.1");
			
			
			Bson Status = new Document("Status.Status","REJECTED");
			
//			Instant instant = Instant.parse(FechaEmision); //Pass your date.
//			Date fechaEmision = Date.from(instant);	
//			Bson SFechaEmision = new Document("IssueDate",fechaEmision);
			
//			SimpleDateFormat sdfPeru = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");			
//			sdfPeru.setTimeZone(TimeZone.getTimeZone("America/Peru/Lima"));			
//			String fecha = sdfPeru.format(SFechaEmision);
			
//			Bson Spdf = Filters.and(Filters.exists("Storage.PDF", false));
			
			
			Bson ValidationCode = new Document("Validation.ValidationCode","0");
											
//			200  - Detalle: xxx.xxx.xxx value='ticket: 201802080868812 error: El servicio: http://WLNegocioTributarioClusterContribuyenteoseAutorizado no esta disponible'
//			0130  El sistema no puede responder su solicitud. (No se pudo obtener el ticket de proceso)
//			0200   No se pudo procesar su solicitud. (Ocurrio un error en el batch) - Detalle: xxx.xxx.xxx value='ticket: 201802051186145 error: Se ha producido un error inesperado.'		
			
//			Bson SunatCode = new Document("Status.Response.Code","200");
			
			
			// Filtor por fechas STRING
//			Bson fFECHAS = Filters.and(Filters.gte("IssueDate", "2019-02-01T00:00:00.000Z"),
//							   Filters.lte("IssueDate", "2019-02-28T00:00:00.000Z")); //622
			
			
			
// 			Filtor por fecha de envío en Adelante timestamp ISODATE			
			
//			Instant instant = Instant.parse("2019-07-01T00:00:00.000Z"); //Pass your date.						
//			Date timestamp = Date.from(instant);					
//			Bson fFECHA = Filters.and(Filters.gte("Authorization.Timestamp", timestamp)); //622		
			
			
//			Filtor por fecha de EMISION en Adelante timestamp ISODATE
			
//			Instant instant = Instant.parse("2019-06-16T00:00:00.000Z"); //Pass your date.						
//			Date timestamp = Date.from(instant);					
//			Bson fFECHA = Filters.and(Filters.gte("IssueDate", timestamp)); //622	
			
			
//			Bson ReferenceDate = new Document("ReferenceDate","2019-03-06");
			
			
			// Filtor por fechas ISODATE			
			
			Instant instant = Instant.parse("2019-08-01T00:00:00.000Z"); //Pass your date.			
			Instant instant2 = Instant.parse("2019-08-02T00:00:00.000Z");			
			Date timestamp = Date.from(instant);
			Date timestamp2 = Date.from(instant2);			
			Bson fFECHAS2 = Filters.and(Filters.gte("IssueDate", timestamp),Filters.lte("IssueDate", timestamp2)); //622		
			
					
			
			// Filtor por fechas de envío Timestamp			
			
//			Instant instant = Instant.parse("2019-05-04T00:00:00.000Z"); //Pass your date.			
//			Instant instant2 = Instant.parse("2019-06-26T00:00:00.000Z");			
//			Date timestamp = Date.from(instant);
//			Date timestamp2 = Date.from(instant2);			
//			Bson fFECHAS2 = Filters.and(Filters.gte("Authorization.Timestamp", timestamp),Filters.lte("Authorization.Timestamp", timestamp2)); //622		
//			
						
			
//			Authorization.Services.PDF.Generic
			
//			Bson pdfGeneric = new Document("Authorization.Services.PDF.Generic", false);
									
			// No Generan PDF = false
//			Bson pdfCreate = new Document("Authorization.Services.PDF.Create", true);
			
//			Storage.CDR
//			Bson Spdf = new Document("Storage.PDF","SENT");
			Bson Spdf = Filters.and(Filters.exists("Storage.PDF", false));
			
			
			// migrated web ose
//			Bson fMigration = Filters.and(Filters.exists("migrated", true));
			
			
			// migrated pse a ose
			Bson migrated = Filters.and(Filters.exists("migration.migrated", true));
			
			
			// Filtor por fecha de emision
//			Instant instant = Instant.parse(fecha); //Pass your date.
//			Date timestamp = Date.from(instant);		
//			Bson fFECHA = Filters.gte("IssueDate", timestamp);
			
			
			
			
			// FILTROS
			
		// filter=Filters.and(Status);
		// filter=Filters.and(UBLVersionID,fType,ValidationCode,Status);
		// filter=Filters.and(SunatCode);
		// filter=Filters.and(fRuc,fType,Spdf,pdfGeneric,ValidationCode,pdfCreate,UBLVersionID);
		// filter=Filters.and(fRuc,fReferenceDate,ValidationCode);
		// filter=Filters.and(fRuc,fType,fFECHAS2,ValidationCode,Spdf);
		// filter=Filters.and(fRuc,fType,Spdf,ValidationCode);
		// filter=Filters.and(fRuc,fType,ValidationCode,fFECHAS2,UBLVersionID);
		// filter=Filters.and(fRuc,SunatCode,fFECHAS2);
		// filter=Filters.and(fRuc,fFECHA,ValidationCode);
			
//		 filter=Filters.and(fRuc);
		 
//		filter=Filters.and(fRuc,UBLVersionID);
//		filter = Filters.and(fRuc, fType, migrated, ValidationCode);
		 filter=Filters.and(fRuc,fType,ValidationCode,fFECHAS2);
		// filter=Filters.and(fRuc,fFECHA);
		// filter=Filters.and(fRuc,fFECHAS2,ValidationCode);
		// filter=Filters.and(fRuc,fFECHA,ValidationCode);
		// filter=Filters.and(fRuc,fFECHA,ValidationCode);		//
		// filter=Filters.and(fType,fFECHAS2,Spdf,ValidationCode,pdfCreate,pdfGeneric);
			
			MongoCursor<Document> result = dbO.getCollection("TRANSACTION").find(filter).iterator();


//			MongoCursor<Document> result = dbO.getCollection("TRANSACTION").find(filter).sort(new BasicDBObject("$natural", -1)).limit(10).iterator();
				
			
			boolean exist=false;			
			
			while (result.hasNext()) {				
				
				document = result.next();
				
				
			// ----------- ONLY READ DATOS URL   XML ------------------------	
//				String storageXml="";
//			if (document.toJson().contains("rackcdn.com/xml") || document.toJson().contains(".xml")) {
//				storageXml = ((Document) document.get("Storage")).getString("XML");
////				System.out.println(storageXml);
//			}
				
//			String identifiers = "";
//			try {
//
//				if (type.equals("01")) {
//					
//					 Object object = null;
//					 JAXBContext jaxbContext =
//					 JAXBContext.newInstance(InvoiceType.class);
//					 Unmarshaller unmarshaller =
//					 jaxbContext.createUnmarshaller();
//					 object = unmarshaller.unmarshal(new URL(storageXml));
//					 InvoiceType invoiceType = (InvoiceType) object;
//					
//					 String Payable_AmountXML=
//					 String.valueOf(invoiceType.getLegalMonetaryTotal().getPayableAmount().getValue());
//					 System.out.println(Payable_AmountXML);
//
//				} else if (type.equals("07")) {
//					Object object = null;
//					JAXBContext jaxbContext = JAXBContext.newInstance(CreditNoteType.class);
//					Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//					object = unmarshaller.unmarshal(new URL(storageXml));
//					CreditNoteType creditNoteType = (CreditNoteType) object;
//					// String Payable_AmountXML=
//					// String.valueOf(creditNoteType.getLegalMonetaryTotal().getPayableAmount().getValue());
//					identifiers = String.valueOf(creditNoteType.getID().getValue());
////					System.out.println("URL identifier: "+identifiers);
//
//				}
//
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
			
			// ---------------------END ONLY READ URL ----------------------------------

				
				String UUID=document.getString("UUID");			
				String identifier=document.getString("ID");				

//				Authorization.Timestamp
//				String Timestamp= ((Document) document.get("Authorization")).getString("Timestamp");
		
				
				
//				String ValidationCode2= ((Document) document.get("Validation")).getString("ValidationCode");
				
				String  rucEmisor=( (Document)((Document) ( (Document) document.get("Authorization")).get("AccountingSupplierParty")) .get("PartyIdentification") ).getString("ID");
//							
				
				
				//LLAMA FECHA EMISION
				
//				Date date=document.getDate("IssueDate");	
//				SimpleDateFormat sdfPeru = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");				
//				sdfPeru.setTimeZone(TimeZone.getTimeZone("America/Peru/Lima"));			
//				String fecha = sdfPeru.format(date);
//				
				
				// LLAMA FECHA DE ENVÍO
//				Date Timestamp= ((Document) document.get("Authorization")).getDate("Timestamp");	
//				SimpleDateFormat sdfPeru = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");					
//				sdfPeru.setTimeZone(TimeZone.getTimeZone("America/Peru/Lima"));			
//				String fecha = sdfPeru.format(Timestamp);
//				
				
				
				//rejected
				String status= ((Document) document.get("Status")).getString("Status");	
				
				
//				String SUNATProces = ((Document) document.get("Status")).getString("SUNATProcess"); 
				
				
				
				String Code="";
				String Description="";
				
				// VALIDAR

				
				
				
				//  ERRORES DE SUNAT ---------------------------
				try {
					 Code  = ((Document) ((Document) document.get("Status")).get("Response")).getString("Code"); 
//					
				} catch (Exception e) {
					// TODO: handle exception
					 Code  = "SIN COD ERROR"; 
//					
				}
				
				try {
					 Description = ((Document) ((Document) document.get("Status")).get("Response")).getString("Description"); 
					
				} catch (Exception e) {
					// TODO: handle exception
					 Description="SIN DESCRIP";
				}
				
				//Descricpcion sunat -------------
				
				
			
				
//				DocumentCode
//			
//				DocumentCode - Tipo = 01,03,07,08
				String  DocumentCode=( (Document)((Document) ( (Document) document.get("Authorization")).get("Services")) .get("SUNATClearing") ).getString("DocumentCode");
				
				
//				Validation.Description OK
//				String Description= ((Document) document.get("Validation")).getString("Description");
				
							
//				System.out.println(document.toJson().toString());
				
				
				System.out.println(result+" ==> Existe");
				
				// ----------------- datos URL XML ----------------//
				String identifiers="";
				String storageXml="";
				
				if(document.toJson().contains("xml")){
					storageXml = ((Document) document.get("Storage")).getString("XML");
					System.out.println(storageXml);				
				}
				try {
					
					if(type.equals("01")){
						Object object = null;
						JAXBContext jaxbContext = JAXBContext.newInstance(InvoiceType.class);
						Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
						object = unmarshaller.unmarshal(new URL(storageXml));
						InvoiceType invoiceType = (InvoiceType) object;
						
						String Payable_AmountXML= String.valueOf(invoiceType.getLegalMonetaryTotal().getPayableAmount().getValue());
							System.out.println(Payable_AmountXML);
						identifiers= String.valueOf(invoiceType.getID().getValue());
					}
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				// end ----------------- datos URL XML ----------------//
						
				
				
				
//				if(status.equals("ACCEPTED")||status.equals("REJECTED")){
					
//				if(status.equals("ACCEPTED")){
//				if(status.equals("REJECTED")){
				
				
//				lista.add(identifier+"\t"+ AccountingSupplierParty+"\t"+Timestamp +"\n"+status);
//				lista.add(identifier+"\t"+ AccountingSupplierParty +"\t"+status);
//				lista.add(identifier + " "+DocumentCode +" "+ fecha );
//				lista.add(rucEmisor+" "+DocumentCode+" "+fecha+" "+identifier);
//				lista.add(rucEmisor+" "+identifier+" "+DocumentCode+" "+fecha);				
				
//				lista.add(rucEmisor+" "+identifier+" "+DocumentCode+" "+fecha);
				
				lista.add(identifier);
				
//				lista.add(identifier+"\t"+DocumentCode+"\t"+status);
				
//				lista.add(identifier +"\t"+DocumentCode +"\t"+status +"\t"+Code+"\t"+Description);
//				
				
							
//				lista.add(identifier+"\t"+DocumentCode+"\t"+status);
				
//				lista.add(identifier +" "+status+" "+Code+" "+Description);
				
				
//				lista.add(identifier+ "\n"+"Codigo Error:"+Code+"\n"+Description);

				
//				}
				
				exist=true;
				
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
