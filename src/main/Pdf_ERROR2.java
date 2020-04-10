package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

//import config.RabbitMQConfig;
//import pe.com.efact.broker.config.BrokerConfigType;
//import util.UtilRabbit;
import config.DBConfig;
import constants.IPDFConstants;
public class Pdf_ERROR2 {
	private static final Logger LOGGER = LoggerFactory.getLogger(Pdf_ERROR2.class);
	private static DBConfig dbConfig = new DBConfig();
	static String RUC="20507847626";
	static String type="03";
	static List<String> lista = new ArrayList<String>();
	static List<String> lista2 = new ArrayList<String>();
	static List<String> uuid = new ArrayList<String>();
	static List<String> doc = new ArrayList<String>();
	static List<String> doc2 = new ArrayList<String>();
	static List<String> doc3 = new ArrayList<String>();
	public static void main(String[] args){
		boolean conector=true;
//		java.util.Date fecha = new Date();
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		System.out.println("Fecha: "+dateFormat.format(date));
		 String fecha=dateFormat.format(date)+"";
		File archivos = new File("/home/dortiz/Documentos/consulta-sunat/PDF-ERROR2/"+fecha+"-MS.txt");
		File archivos2 = new File("/home/dortiz/Documentos/consulta-sunat/PDF-ERROR2/"+fecha+".txt");
		File archivos3 = new File("/home/dortiz/Documentos/consulta-sunat/PDF-ERROR2/"+fecha+"-MS-UUID.txt");
		File archivos4 = new File("/home/dortiz/Documentos/consulta-sunat/PDF-ERROR2/"+fecha+"-UUID.txt");
		 FileWriter fichero=null;
		 PrintWriter pw=null;
		 FileWriter fichero2=null;
		 PrintWriter pw2=null;
		 FileWriter fichero3=null;
		 PrintWriter pw3=null;
		 FileWriter fichero4=null;
		 PrintWriter pw4=null;
		 try {
			if(archivos.createNewFile()){
				LOGGER.info("txt creado");
			 }
			if(archivos2.createNewFile()){
				LOGGER.info("txt creado");
			 }
			if(archivos3.createNewFile()){
				LOGGER.info("txt creado");
			 }
			if(archivos4.createNewFile()){
				LOGGER.info("txt creado");
			 }
		} catch (IOException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		
		List<String> identifiers = new ArrayList<String>();
		identifiers.add("B009-00005992");
//		identifiers.add("F465-0000074");




//		identifiers.add("F001-00000881");
		MongoDatabase dbW=null;
		MongoDatabase dbO=null;
		if(conector){
			dbW = dbConfig.connectionWeb();
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
		
//		for(int it = 0; it < doc.size(); it++){
//			String[] lis=doc.get(it).split("\t");
//			String UUID=lis[0];
//			String ID=lis[1];
//			String ext=lis[2];
//			String DocumentType=lis[3];
//			String PartyIdentification=lis[4];
//			boolean respue=false;
//			respue=saveDocument( UUID, ID, ext, DocumentType, PartyIdentification);
////			lista.add(PartyIdentification+","+DocumentType+","+ID+","+UUID);
//			if(respue){
//				
//				doc2.add(UUID);
//			}else{
//				doc3.add(UUID);
//			}
//		}
		try{
//			
			fichero=new FileWriter(archivos);
			pw= new PrintWriter(fichero);
			for(int it = 0; it < lista.size(); it++){
				pw.println(lista.get(it));
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
		try{
//			
			fichero2=new FileWriter(archivos2);
			pw2= new PrintWriter(fichero2);
			for(int it = 0; it < lista2.size(); it++){
				pw2.println(lista2.get(it));
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
		
		try{
//			
			fichero3=new FileWriter(archivos3);
			pw3= new PrintWriter(fichero3);
			for(int it = 0; it < doc2.size(); it++){
				pw3.println(doc2.get(it));
			}
			
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				try{
					if(null != fichero3)
						fichero3.close();
				}catch (Exception e2) {
					e2.printStackTrace();
				}
		}
		try{
//			
			fichero4=new FileWriter(archivos4);
			pw4= new PrintWriter(fichero2);
			for(int it = 0; it < doc3.size(); it++){
				pw4.println(doc3.get(it));
			}
			
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				try{
					if(null != fichero4)
						fichero4.close();
				}catch (Exception e2) {
					e2.printStackTrace();
				}
		}
		
		
	}
	
	public static void work(String identifier,boolean conector,MongoDatabase dbW,MongoDatabase dbO) {

		
		LOGGER.info("---CONEXION A ODIN EXITOSA---");
		String ticket=findOneTicket(identifier,dbO);
		if(!ticket.equals("")){
			uuid.add(ticket);

		}else{
			LOGGER.info("---El ticket no existe---");
//			error.add(identifier);
		}
	}
	
	public static String findOne (String UUID, MongoDatabase db) {
		Document document=null;
		String respuesta="";
		Bson filter = new Document("UUID",UUID);
		Bson fMigration = Filters.and(Filters.exists("migration.migrated", false));
		filter = Filters.and(filter, fMigration);
		MongoCursor<Document> result = db.getCollection("TRANSACTION").find(filter).iterator();

		while (result.hasNext()) {
			document = result.next();
//			boolean v=((Document) ((Document)((Document) document.get("Authorization")).get("Services")).get("PDF")).getBoolean("Generic");
//		if(Generic){
//			((Document) ((Document)((Document) document.get("Authorization")).get("Services")).get("PDF")).replace("Generic", false,true);
//		}else{
//			((Document) ((Document)((Document) document.get("Authorization")).get("Services")).get("PDF")).replace("Generic", true,false);
//		}
			

//			((Document)  document.get("Status")).replace("Status", "VOIDED", "ACCEPTED");
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
	
	public static String findOneTicket(String identifier, MongoDatabase db) {
		Document document=null;
		String respuesta="";
		Bson filter=null;
		Bson fRus = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);
		Bson fType = new Document("Authorization.CPE.DocumentType",type);
		Bson fID = new Document("ID",identifier);
		Bson fStatus = new Document("Status.Status","ERROR");
		filter=Filters.and(fRus,fType);
		filter = Filters.and(filter, fID);
		filter = Filters.and(filter, fStatus);
		LOGGER.info(RUC+"-"+type+"-"+identifier);
		MongoCursor<Document> result = db.getCollection("PDF_ERROR").find().iterator();
		int count=0;
		while (result.hasNext()) {
			document = result.next();
			//Authorization.Services.PDF.Generic
			//			((Document)  document.get("Status")).replace("Status", "VOIDED", "ACCEPTED");
//			LOGGER.info(""+document.get("Status"));
			LOGGER.info(document.toJson().toString());
			String PartyIdentification = document.getString("PartyIdentification");
			String DocumentType = document.getString("DocumentType");
			String ID = document.getString("ID");
			String UUID = document.getString("UUID");
            String DescriptionError = document.getString("DescriptionError");
//            String ticket = ((Document)  document.get("Status")).getString("Ticket");
//                  String accessKey = ((Document) ((Document) document.get("Interfaces")).get("REST")).getString("AccessKey");
			LOGGER.info(PartyIdentification+","+DocumentType+","+ID+","+UUID+","+DescriptionError);
			doc.add(UUID+"\t"+ ID+"\t"+ "pdf"+"\t"+ DocumentType+"\t"+ PartyIdentification);
			if(DescriptionError.contains("MS-PDF")){
					
				lista.add(PartyIdentification+","+DocumentType+","+ID+","+UUID);
					
				
			}else{
				if(PartyIdentification.equals("20507661441")){
					lista2.add(PartyIdentification+","+DocumentType+","+ID+","+UUID);
				}
				
			}
//			if(count>0){
//				if(count==0){
//					respuesta=ticket;
//				}
//				else{
//					respuesta+="|"+ticket;
//				}
				
//			}
			count++;
		}
		System.out.println(""+count);
		return respuesta;
	}
	public static boolean saveDocument(String Uuid,String identifier,String tipoD,String tipo,String ruc) {
    	boolean respuesta=false;
		try {
		
				String script = "";
				String tipoFile="";
				boolean valCDR=false;
				if(tipoD.equals("zip")){
					tipoFile="CDR/";
					valCDR=true;
				}else if(tipoD.equals("pdf")){
					tipoFile="PDF/";
				}
				if(!valCDR&&tipo.equals("01")){
					tipoFile+="INVOICE/";
				}else if(!valCDR&&tipo.equals("03")){
					tipoFile+="BOLETA/";
				}else if(!valCDR&&tipo.equals("07")){
					tipoFile+="CREDIT_NOTE/";
				}else if(!valCDR&&tipo.equals("07")){
					tipoFile+="DEBIT_NOTE/";
				}else if(!valCDR&&tipo.equals("20")){
					tipoFile+="RETENTION/";
				}else if(!valCDR&&tipo.equals("40")){
					tipoFile+="PERCEPTION/";
				}
//				String fileRuc=IPDFConstants.FILE+"ose/sftp/"+ ruc;
//				File fileR=new File(fileRuc);
//				if(!fileR.exists()){
//					fileR.mkdirs();
//				}
				if(!tipoD.equals("zip")){
					String rutaInicial="/home/dortiz/Documentos/consulta-sunat/PDF-ERROR2/PDF-VALIDO/";
			
					LOGGER.info("saveDocument+ >>>");
					script = "scp -i /home/dortiz/llave_dortiz dortiz@23.253.207.57:/opt/efact/ose/storage_smb/"+tipoFile+Uuid+" "+rutaInicial;
					LOGGER.info(script);
//					ejecutarCMD(script);
//					Thread.sleep(2000);
					respuesta=ejecutarCMD(script);
//						Process server1Jar = Runtime.getRuntime().exec(script);
//						int exitValJarS1 = server1Jar.waitFor();
//						if (exitValJarS1 != 0) {
//							LOGGER.info("not save- >>> "+ ruc +"-"+tipo+"-"+identifier+"."+tipoD);
//						} else {
//							LOGGER.info("save in Out- >>> " +tipoFile+identifier+"."+tipoD);
//							respuesta=true;
//						}

						LOGGER.info("saveDocument-");
				}else{
					String rutaInicial= ruc +"-"+tipo+"-"+identifier+".xml";
					LOGGER.info("saveDocument+ >>>");
					script = "scp -i llave_dortiz dortiz@23.253.207.57:/opt/efact/ose/storage_smb/"+tipoFile+Uuid+" "+rutaInicial;
					LOGGER.info(script);
//					ejecutarCMD(script);
//					Thread.sleep(2000);
					respuesta=ejecutarCMD(script);
//						Process server1Jar = Runtime.getRuntime().exec(script);
//						int exitValJarS1 = server1Jar.waitFor();
//						if (exitValJarS1 != 0) {
//							LOGGER.info("not save- >>> "+ ruc +"-"+tipo+"-"+identifier+"."+tipoD);
//						} else {
//							LOGGER.info("save in Out- >>> " +tipoFile+identifier+"."+tipoD);
////							comprimir(rutaInicial);
//							respuesta=true;
//						}

						LOGGER.info("saveDocument-");
				}
				
			
		} catch (Exception e) {
			LOGGER.info(e.toString());
		}
		return respuesta;
	}
	public static boolean  ejecutarCMD(String comando) throws IOException{
		 String s = null;
		 boolean resp=false;
			try{
//				ejecutarCMD();
				String cmd = comando; //Comando de apagado en linux
				Runtime.getRuntime().exec(cmd);
				 Process p = Runtime.getRuntime().exec(cmd);
				 LOGGER.info(cmd);
	           BufferedReader stdInput = new BufferedReader(new InputStreamReader(
	                           p.getInputStream()));

	           BufferedReader stdError = new BufferedReader(new InputStreamReader(
	                           p.getErrorStream()));

	           // Leemos la salida del comando
	           LOGGER.info("\n");
	           while ((s = stdInput.readLine()) != null) {
	        	   resp=true;
	        	   LOGGER.info(s);
	           }

	           
	           while ((s = stdError.readLine()) != null) {
	        	   LOGGER.info(s);
	           }
				
			}catch (Exception e) {
				LOGGER.info(e.toString());
				
			}
			return resp;
		}
}
