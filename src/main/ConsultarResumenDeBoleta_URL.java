package main;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;


//import org.w3c.dom.Document;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;


import config.DBConfig;
import oasis.names.specification.ubl.schema.xsd.creditnote_2.CreditNoteType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import sunat.names.specification.ubl.peru.schema.xsd.retentiondocuments_1.RetentionType;
import sunat.names.specification.ubl.peru.schema.xsd.summarydocuments_1.SummaryDocumentsType;
import sunat.names.specification.ubl.peru.schema.xsd.voideddocuments_1.VoidedDocumentsType;
public class ConsultarResumenDeBoleta_URL {
//	Update_Retention_OSE
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultarResumenDeBoleta_URL.class);
	private static DBConfig dbConfig = new DBConfig();
	
	static String RUC="20100199743";

	
//	static String type="01";	
//	static String type="03";
//	static String type="07";
//	static String type="16";
//	static String type="17";
	static String type="20";
//	static String type="09";
//	static String type="08";	

	
	static List<String> error = new ArrayList<String>();
	static List<String> uuid = new ArrayList<String>();
	static List<String> ListaNOexiste = new ArrayList<String>();
	static List<String> ListaQueryOSE = new ArrayList<String>();
	
	static int count=0;
	public static void main(String[] args)throws IOException{
		
		boolean conector=true;
		 
	
		List<String> identifiers = new ArrayList<String>();
		
	
//		FileReader fileData= new FileReader("/home/acalcina/Escritorio/QueryAllODIN.txt");
//		
//		BufferedReader bf = new BufferedReader(fileData);
//		String sCadena="";
//		 while ((sCadena = bf.readLine())!=null) {
//			
//			 identifiers.add(sCadena);			 
//			 System.out.println(sCadena);
//		 }
		
	

		identifiers.add("R100-990");
		
		
//		identifiers.add("RC-20190626-2");

		
		
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
		
		
		 File archivos1 = new File("/home/acalcina/Escritorio/Consulta_URL.txt");
		 FileWriter fichero1=null;
		 PrintWriter pw1=null;		
		 try {
			if(archivos1.createNewFile()){
				LOGGER.info("txt creado");
			 }	
				fichero1=new FileWriter(archivos1);
				pw1= new PrintWriter(fichero1);
				for(int it = 0; it < ListaQueryOSE.size(); it++){
					pw1.println(ListaQueryOSE.get(it));
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
		

		
		 File archivos2 = new File("/home/acalcina/Documentos/AA_NO_REGISTRADOS/NO_REGISTRADOS.txt");
		 FileWriter fichero2=null;
		 PrintWriter pw2=null;	
		 try {
			if(archivos2.createNewFile()){
				LOGGER.info("txt creado");
			 }	
			fichero2=new FileWriter(archivos2);
			pw2= new PrintWriter(fichero2);
				for(int it = 0; it < ListaNOexiste.size(); it++){
					pw2.println(ListaNOexiste.get(it));
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
	
	
	//METODOS
	
	
	public static void work(String identifier,boolean conector,MongoDatabase dbW,MongoDatabase dbO) throws MalformedURLException {

		
		LOGGER.info("---CONEXION A ODIN EXITOSA---");
		//conexion web
//		String ticket=findOneTicket(identifier,dbW);
		
		//conexion odin
		String ticket=findOneTicket(identifier,dbO);
		
		
		if(!ticket.equals("")){
			uuid.add(ticket);

		}else{
			
			LOGGER.info("---El ticket no existe---");
			ListaNOexiste.add(identifier);
		}
	}
	
	public static String findOne (String UUID, MongoDatabase db) {
		Document document=null;
		String respuesta="";
		
		Bson filter = new Document("UUID",UUID);		
//		Bson fMigration = Filters.and(Filters.exists("migration.migrated", false));
		Bson fMigration = Filters.and(Filters.exists("migration.migrated", true));
		
		
		
		filter = Filters.and(filter, fMigration);
		MongoCursor<Document> result = db.getCollection("TRANSACTION").find(filter).iterator();

		while (result.hasNext()) {
			document = result.next();
			

			
			LOGGER.info(document.toJson().toString());
			document.remove("_id");
			document.remove("migration");
			document.remove("jwt-val");
			LOGGER.info(document.toJson().toString());
		}
		if(document!=null){
			respuesta=document.toJson().toString();
		}
		return respuesta;
	}
	
	public static  String findOneTicket(String identifier, MongoDatabase db) throws MalformedURLException {
		
		Document document=null;
		String respuesta="";
		Bson filter=null;
		
				
		Bson fRus = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);		
		Bson fType = new Document("Authorization.Services.SUNATClearing.DocumentCode",type);		
		Bson fID = new Document("ID",identifier);
		
		
		
		Bson ValidationCode = new Document("Validation.ValidationCode","0");
		
		// CON ERRORES
//		Bson fStatus = new Document("Status.Status","ERROR");


		
		// CONSULTA ...........	
//		filter=Filters.and(fRus,fType);		
//		filter = Filters.and(filter, fID);
		filter=Filters.and(fRus,fType,fID,ValidationCode);	
//		filter=Filters.and(fRus,fType,fID);
//		filter=Filters.and(fRus,fType,fID,fStatus);
		
//		filter = Filters.and(filter, fStatus);
		LOGGER.info(RUC+"-"+type+"-"+identifier);
		
		MongoCursor<Document> result = db.getCollection("TRANSACTION").find(filter).iterator();
		
		boolean exist=false;
		
		while (result.hasNext()) {
			
			document = result.next();
			System.out.println(document.toJson().toString());
			System.out.println(identifier+" ==> Existe");
				
			
			String storagePdf="";
			String storageXml="";
			String storageCdr="";		
					
			
//			if (document.toJson().contains("rackcdn.com/pdf")) {		
//			if(document.toJson().contains("rackcdn.com/pdf")||document.toJson().contains(".pdf")){
//				storagePdf = ((Document) document.get("Storage")).getString("PDF");
//				LOGGER.info(storagePdf);
//			}
			
////		if (document.toJson().contains("rackcdn.com/cdr")) {
//		if(document.toJson().contains("rackcdn.com/cdr")&& document.toJson().contains(".zip")){
//
//			storageCdr = ((Document) document.get("Storage")).getString("CDR");
//			LOGGER.info(storageCdr);
//		}

				
			// ----------- ONLY READ DATOS URL   XML ------------------------	
//			if(document.toJson().contains("rackcdn.com/xml")||document.toJson().contains(".xml")){			
			if(document.toJson().contains("xml")){
				storageXml = ((Document) document.get("Storage")).getString("XML");
				System.out.println(storageXml);				
			}

			String identifiers="";
			String docRelat ="";
			String ReferenceDate="";
			String TotalAmount="";
			
			String CustomerAssignedAccountID="";
			String AdditionalAccountID="";
			
			
			String DocumentTypeCode="";
			String serie="";
			String correlativo="";
			
			String SUNATTotalPaid="";
			
			try {
				
			if(type.equals("01")){
				Object object = null;
				JAXBContext jaxbContext = JAXBContext.newInstance(InvoiceType.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				object = unmarshaller.unmarshal(new URL(storageXml));
				InvoiceType invoiceType = (InvoiceType) object;
				
				String Payable_AmountXML= String.valueOf(invoiceType.getLegalMonetaryTotal().getPayableAmount().getValue());
					System.out.println(Payable_AmountXML);
		
			}else if(type.equals("07")){
				Object object = null;
				JAXBContext jaxbContext = JAXBContext.newInstance(CreditNoteType.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				object = unmarshaller.unmarshal(new URL(storageXml));
				CreditNoteType creditNoteType = (CreditNoteType) object;
				
//				String Payable_AmountXML= String.valueOf(creditNoteType.getLegalMonetaryTotal().getPayableAmount().getValue());
				 identifiers= String.valueOf(creditNoteType.getID().getValue());		
				
//				System.out.println(identifiers);
					
			}	
			
			
			
			
			else if(type.equals("16")){
				
				Object object = null;
				JAXBContext jaxbContext = JAXBContext.newInstance(SummaryDocumentsType.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				object = unmarshaller.unmarshal(new URL(storageXml));
				SummaryDocumentsType summaryDocumentsType = (SummaryDocumentsType) object;
				
//				String Payable_AmountXML= String.valueOf(creditNoteType.getLegalMonetaryTotal().getPayableAmount().getValue());
				
				
				identifiers= String.valueOf(summaryDocumentsType.getID().getValue());	
				docRelat = String.valueOf(summaryDocumentsType.getSummaryDocumentsLine().get(0).getId().getValue());
				ReferenceDate = String.valueOf(summaryDocumentsType.getReferenceDate().getValue());

				int cant=summaryDocumentsType.getSummaryDocumentsLine().size();			
				
				for(int i=0;i<cant;i++){				
								
					docRelat = String.valueOf(summaryDocumentsType.getSummaryDocumentsLine().get(i).getId().getValue());
					
					
					// ONLY DOCUMENT REFERENCE	
					
					CustomerAssignedAccountID = String.valueOf(summaryDocumentsType.getSummaryDocumentsLine().get(i).getAccountingCustomerParty().getCustomerAssignedAccountID().getValue().toString());
					AdditionalAccountID =String.valueOf(summaryDocumentsType.getSummaryDocumentsLine().get(i).getAccountingCustomerParty().getAdditionalAccountID().get(0).getValue().toString());
					DocumentTypeCode = summaryDocumentsType.getSummaryDocumentsLine().get(i).getDocumentTypeCode().getValue();
					
					
//					AccountingCustomerParty
					TotalAmount = summaryDocumentsType.getSummaryDocumentsLine().get(i).getTotalAmount().getValue().toString();
							
					
					
					
					// op1
//					respuesta=identifiers+"\t"+ docRelat +"\t"+ReferenceDate +"\t"+TotalAmount;
					
					
					//op2
					
					respuesta=identifiers+"\t"+DocumentTypeCode+"\t"+ docRelat +"\t"+AdditionalAccountID +"\t"+CustomerAssignedAccountID;
					
					ListaQueryOSE.add(respuesta);
					
				}		
					
			}
			
			
			
			else if(type.equals("17")){
				
				Object object = null;
				JAXBContext jaxbContext = JAXBContext.newInstance(VoidedDocumentsType.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				object = unmarshaller.unmarshal(new URL(storageXml));
				VoidedDocumentsType voidedDocumentsType = (VoidedDocumentsType) object;		
				
				
				
				identifiers= String.valueOf(voidedDocumentsType.getID().getValue());	
				
				ReferenceDate = String.valueOf(voidedDocumentsType.getReferenceDate().getValue());

				int cant=voidedDocumentsType.getVoidedDocumentsLine().size();			
				
				for(int i=0;i<cant;i++){				
					
					DocumentTypeCode = voidedDocumentsType.getVoidedDocumentsLine().get(i).getDocumentTypeCode().getValue();
					serie = String.valueOf(voidedDocumentsType.getVoidedDocumentsLine().get(i).getDocumentSerialID().getValue());
					correlativo = String.valueOf(voidedDocumentsType.getVoidedDocumentsLine().get(i).getDocumentNumberID().getValue());
								
					docRelat = serie+"-"+correlativo;
					
					respuesta=identifiers+"\t"+DocumentTypeCode+"\t"+ docRelat +"\t"+ReferenceDate +"\t"+TotalAmount;
					ListaQueryOSE.add(respuesta);
					
				}		
					
			}
			
			
			// RETENCIONES
			else if(type.equals("20")){
				
				
				
				Object object = null;				
				JAXBContext jaxbContext = JAXBContext.newInstance(RetentionType.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				object = unmarshaller.unmarshal(new URL(storageXml));
				RetentionType retentionType = (RetentionType) object;		
				
				
				
				identifiers= String.valueOf(retentionType.getID().getValue());	
				
				SUNATTotalPaid = retentionType.getSUNATTotalPaid().getValue().toString();

				System.out.println(SUNATTotalPaid);	
					
			}
			
			
//			System.out.println(docRelat);
////			respuesta=RUC+" "+identifiers+" "+ docRelat;
//			
//			if(i<= cant){						
//				respuesta=RUC+" "+identifiers+" "+ docRelat;
//				ListaQueryOSE.add(respuesta);
//				
//			}else{
//				ListaQueryOSE.add(respuesta);
//			}
			
			
			} catch (Exception e) {
				// TODO: handle exception
			}
			//---------------------END ONLY READ URL ----------------------------------
			
			
//			ID
			String id=  document.getString("ID");
			
			//UUID WEB y ODIN
			String uuid=document.getString("UUID");				
			String status= ((Document) document.get("Status")).getString("Status");				
//			String UBLVersionID=  document.getString("UBLVersionID");			
//			String SUNATProces = ((Document) document.get("Status")).getString("SUNATProcess"); //			
////			Status.Response.Code
//			String Code  = ((Document) ((Document) document.get("Status")).get("Response")).getString("Code"); 
//			
			//Status.Response.Description
//			String Description = ((Document) ((Document) document.get("Status")).get("Response")).getString("Description"); 		
			String DescriptionError= ((Document) document.get("Validation")).getString("Description");
			
//			Validation
//			String Validation= ((Document) document.get("Validation")).getString("ValidationCode");			
//			String id=document.getString("ID");	
			
			//OSE
//			Validation.Description
//			String  validacion = ((Document) document.get("Validation")).getString("Description");
			
			//FECHA de emisión OSE
//			String date = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getDate("IssueDate");			 
			
//			String date = ""+((Document) ((Document) document.get("Authorization")).get("CPE")).getDate("IssueDate");
//			 java.util.Date s=((Document) ((Document) document.get("Authorization")).get("CPE")).getString("IssueDate")
			 //Authorization.CPE.IssueDate		
			
//			Date date=document.getDate("IssueDate");	
//			SimpleDateFormat sdfPeru = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");				
//			sdfPeru.setTimeZone(TimeZone.getTimeZone("America/Peru/Lima"));			
//			String fecha = sdfPeru.format(date);
////						
			
			//** .........................................................
			
//				respuesta=RUC+" "+identifiers+" "+ docRelat;
				
//				respuesta=identifiers;
			
//				ListaQueryOSE.add(respuesta);
				
						
				
//			}
				
			}
//			else{
//				 
//				if(descrip!=null){
//					respuesta=identifier+","+uuid+","+descrip.replace("\n", " ");
//				}
				 
//			}
			
			exist=true;
//			break;
			count++;
			
//		}
			
		if(!exist){
			System.out.println(identifier+" ==> NOOOO Existe");
			uuid.add(identifier);
		}
////			if(count>0){
//				if(count==0){
//					respuesta=ticket;
//				}
//				else{
//					respuesta+="|"+ticket;
//				}
				
//			}
		return respuesta;
		}

	
		
	}

//}
