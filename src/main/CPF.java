package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
public class CPF {
	private static final Logger LOGGER = LoggerFactory.getLogger(CPE_SUMMARY.class);
	private static DBConfig dbConfig = new DBConfig();
	
	
	static String RUC="20414407677";
	static String type="03";
	static String Series="0001";
	
	
	
	static List<String> ListaReporte = new ArrayList<String>();
	static List<String> ListPadronesNO = new ArrayList<String>();
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
				
				
		
		Document document=null;
		String respuesta="";
		Bson filter=null;
			
		

		Bson fRuc = new Document("PartyIdentification",RUC);
		Bson fType = new Document("DocumentType",type);		
		Bson fSERIES = new Document("Series",Series);

		
	
		// QUERY COMPLETO 		
//		filter=Filters.and(fRuc);		

		filter=Filters.and(fRuc,fType,fSERIES);	
		
		MongoCursor<Document> result = dbO.getCollection("AUTORIZACION_CPF").find(filter).iterator();	
		
		// ejecuta todo el resultado
//		document = result.next();		
		
//		boolean exist=false;
		
		while (result.hasNext()) {
			
			document = result.next();
			
			System.out.println(document.toJson().toString());
			System.out.println(fRuc+" ==> Existe");
			//Status.Response.Description			
			
			//WEB
				
		
			
			String PartyIdentification=document.getString("PartyIdentification");
			String DocumentType=document.getString("DocumentType");
			String Series=document.getString("Series");
			
			String NumberStart=document.getString("NumberStart");
			String NumberEnd=document.getString("NumberEnd");
		
			
//			Authorization.Timestamp
//			String Timestamp= ((Document) document.get("Authorization")).getString("Timestamp");
					
			
				ListaReporte.add(						
				"PartyIdentification: "+PartyIdentification+"\n"+
				"DocumentType: "+DocumentType+"\n"+		
				"Series: "+Series+"\n"+
				"NumberStart: "+NumberStart+"\n"+
				"NumberEnd: "+NumberEnd
				+"\n"
				);

		}

		
		
		 File archivos1 = new File("/home/acalcina/Escritorio/REPORTE_CPF.txt");
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
		
	}
	
	

	
	
	
	}

//}
