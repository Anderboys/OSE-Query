package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import sunat.names.specification.ubl.peru.schema.xsd.sunataggregatecomponents_1.VoidedDocumentsLineType;
import sunat.names.specification.ubl.peru.schema.xsd.voideddocuments_1.VoidedDocumentsType;
import util.Util;
import config.DBConfig;
import oasis.names.specification.ubl.schema.xsd.creditnote_2.CreditNoteType;
import oasis.names.specification.ubl.schema.xsd.debitnote_2.DebitNoteType;
//import migrate.util.Util;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;

public class valCBError {
	private static final Logger LOGGER = LoggerFactory.getLogger(valCBError.class);
	private static DBConfig dbConfig = new DBConfig();
	static List<String> lista = new ArrayList<String>();
	public static void main(String[] args){
		
		MongoDatabase dbO=null;
		dbO = dbConfig.connectionOdin();
		File archivos = new File("/home/acalcina/Escritorio/valCBError.txt");//lista
		FileWriter fichero=null;
		 PrintWriter pw=null;
		 try {
				if(archivos.createNewFile()){
					LOGGER.info("txt creado");
				 }
		 } catch (IOException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
		 
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",new Locale("en","PE"));

	  // 2346 error resumen de boletas no coincide emitir por el portal
//		2375 =Fecha de emisión de la boleta no coincide con la fecha de emisión consignada en la comunicación.
//		BasicDBObject query = new BasicDBObject("Validation.ValidationCode", "2375")//B:2346//F:2375 
		BasicDBObject query = new BasicDBObject("Validation.ValidationCode", "2346")//B:2346//F:2375		
				.append("Authorization.Services.SUNATClearing.DocumentCode", "17")
//				.append("Authorization.AccountingSupplierParty.PartyIdentification.ID", "20100377358")
				;
		try {
			//-5 horas de diferencia para buscar
			query.put("Authorization.Timestamp", BasicDBObjectBuilder.start("$gte", format.parse("2019-01-20T00:00:00Z")).add("$lte", format.parse("2019-01-23T15:07:00Z")).get());
//			query.put("Authorization.Timestamp", BasicDBObjectBuilder.start("$gte", format.parse("2019-01-08T12:00:00Z")).add("$lte", format.parse("2019-01-09T00:00:00Z")).get());     	

		}catch (Exception e) {}
		MongoCursor<Document> result = dbO.getCollection("TRANSACTION").find(query).iterator();
		Document document=null;
		while (result.hasNext()) {
			document = result.next();
//			String reference =document.getString("ReferenceDate");
			String UUIDB=document.getString("UUID");
			try {
				String link=((Document) document.get("Storage")).getString("XML");//Storage.XML
				VoidedDocumentsType voidedDocumentsType = Util.getVoidedFromStorage(link);
				String issueDate=voidedDocumentsType.getIssueDate().getValue().toString();
				String reference=voidedDocumentsType.getReferenceDate().getValue().toString();
				String date=format.format(((Document) document.get("Authorization")).getDate("Timestamp"));
				//Authorization.AccountingSupplierParty.PartyIdentification.ID
				String RUC=((Document)((Document) ((Document) document.get("Authorization")).get("AccountingSupplierParty")).get("PartyIdentification")).getString("ID");
				List<VoidedDocumentsLineType> listVoidedDocumentsLineType = voidedDocumentsType.getVoidedDocumentsLine();
				String[] idCB=voidedDocumentsType.getID().getValue().split("-");
				String[] fechaVAl=issueDate.split("-");
				String valFecha=fechaVAl[0]+fechaVAl[1]+fechaVAl[2];
				if(idCB[1].equals(valFecha))
					System.out.println(">>>>>>>Fecha CB correcta".toUpperCase());
				else
					System.out.println("Fecha CB Incorrecta");
				System.out.println(">>>>>>> "+UUIDB+" | "+ date +"<<<<<<<<"+document.getString("Interface"));
				for (VoidedDocumentsLineType voidedDocumentsLineType : listVoidedDocumentsLineType) {
//					String[] line = {
//										voidedDocumentsLineType.getLineID().getValue(),
							String identifier=voidedDocumentsLineType.getDocumentSerialID().getValue()+"-"+Integer.parseInt(voidedDocumentsLineType.getDocumentNumberID().getValue());
							String tipo=voidedDocumentsLineType.getDocumentTypeCode().getValue();
							Bson filter2 = new Document("Validation.ValidationCode","0");
							Bson filter3 = new Document("ID",identifier);
							Bson filter4 = new Document("Authorization.Services.SUNATClearing.DocumentCode",tipo);
							Bson filter5 = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);
							filter2 = Filters.and(filter2, filter3,filter4,filter5);
							MongoCursor<Document> result2 = dbO.getCollection("TRANSACTION").find(filter2).iterator();
							Document document2=null;
							while (result2.hasNext()) {
								document2 = result2.next();
								String uuid=document2.getString("UUID");
								String fecha="";
								try {
//									Date rDate = document2.getDate("IssueDate");
//									SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",new Locale("en","PE"));
//									fecha = df.format(rDate);
									String url=((Document) document2.get("Storage")).getString("XML");
									if(tipo.equals("01")) {
										InvoiceType invoiceType = Util.getInvoiceFromStorage(url);
										fecha=invoiceType.getIssueDate().getValue().toString();
									}else if(tipo.equals("07")) {
										CreditNoteType creditNoteType = Util.getCreditNoteFromStorage(url);
										fecha=creditNoteType.getIssueDate().getValue().toString();
									}else if(tipo.equals("08")) {
										DebitNoteType debitNoteType = Util.getDebitNoteTypeFromStorage(url);
										fecha=debitNoteType.getIssueDate().getValue().toString();
									}
									
									System.out.println(reference +"||"+ fecha);
									if(reference.equalsIgnoreCase(fecha)) {
										System.out.println(uuid+" Fecha correcta");
										lista.add(uuid+","+fecha);
									}else {
										System.out.println(uuid+" Fecha Incorrecta");
									}
								}catch (Exception e) {
									// TODO: handle exception
								} 
							}
//										voidedDocumentsLineType.getVoidReasonDescription().getValue()
//									};
//					listVoidedDocumentsLine.add(line);
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			
			
			
		}
		
		try{
//			
			fichero=new FileWriter(archivos);
			pw= new PrintWriter(fichero);
			for(int it = 0; it < lista.size(); it++){
				
				String[] li=lista.get(it).split(",");
				//db.TRANSACTION.update({"UUID":""},{ $set: {"IssueDate":new ISODate("")}})
				pw.println("db.TRANSACTION.update({\"UUID\":\""+li[0]+"\"},{ $set: {\"IssueDate\":new ISODate(\""+li[1]+" 05:00:00.000Z\")}});");//2018-12-29 00:00:00.000Z
			}
			
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				try{
					if(null != fichero)
						fichero.close();
				}catch (Exception e2) {
					e2.printStackTrace();
				}
		}
	}
}
