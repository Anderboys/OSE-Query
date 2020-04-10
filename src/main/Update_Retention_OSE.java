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

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;

import config.DBConfig;
import oasis.names.specification.ubl.schema.xsd.creditnote_2.CreditNoteType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import pe.com.efact.db.constants.ICollections;
import sunat.names.specification.ubl.peru.schema.xsd.retentiondocuments_1.RetentionType;
import sunat.names.specification.ubl.peru.schema.xsd.summarydocuments_1.SummaryDocumentsType;
import sunat.names.specification.ubl.peru.schema.xsd.voideddocuments_1.VoidedDocumentsType;


import com.mongodb.client.MongoDatabase;


public class Update_Retention_OSE {
	
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
			
			
			//---------------------END ONLY READ URL ----------------------------------
			
			
//			ID
			String id=  document.getString("ID");
			
			//UUID  ODIN
			String uuid=document.getString("UUID");	
			
			String status= ((Document) document.get("Status")).getString("Status");				

			
				
			
			try {
				
				
				// RETENCIONES
				 if(type.equals("20")){
					
					
					
					Object object = null;				
					JAXBContext jaxbContext = JAXBContext.newInstance(RetentionType.class);
					Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
					object = unmarshaller.unmarshal(new URL(storageXml));
					RetentionType retentionType = (RetentionType) object;		
								
					
					identifiers= String.valueOf(retentionType.getID().getValue());	
					
					SUNATTotalPaid = retentionType.getSUNATTotalPaid().getValue().toString();

					System.out.println(uuid);	
					System.out.println(SUNATTotalPaid);
					
					
					Boolean resp = updateStatusPDF(uuid,SUNATTotalPaid,db);
					
						if(resp){
							respuesta=uuid;
							System.out.println("Actualizado "+uuid +" --> " + SUNATTotalPaid);
						}	else{
							System.out.println("error al actualizar");
						}		
						
				}
				
						
				
				} catch (Exception e) {
					// TODO: handle exception
				}
			
			
			
			
			
			
			
			exist=true;

			count++;
			

	

		
		}
		return respuesta;

	}
	

	public static boolean updateStatusPDF(String UUID, String SUNATTotalPaid,MongoDatabase db) {
		
		Bson filter = new Document("UUID", UUID);		
		Bson newValue = new Document("PayableAmount", SUNATTotalPaid);
		Bson updateOp = new Document("$set", newValue);		
		return update(filter, updateOp, ICollections.COLLECTION_TRANSACTION,db);
			
	}	

	
	public static boolean update(Bson filter, Bson update, String collection,MongoDatabase db) {
		MongoCollection<Document> c = db.getCollection(collection);
		UpdateResult result = c.updateOne(filter, update);
		Boolean response = false;
		
		if (result.getModifiedCount() != 0) {
			response = true;
		}
		
		return response;
	}

}
		

