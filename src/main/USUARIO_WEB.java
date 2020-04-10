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

public class USUARIO_WEB {
	private static final Logger LOGGER = LoggerFactory.getLogger(USUARIO_WEB.class);
	private static DBConfig dbConfig = new DBConfig();
	
	static String RUC="20535898554";	
	
	
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
		
		
		
		
		// WHERE  RUC  + IDENTIFIER
		

		Bson fRUC = new Document("UserParty.PartyIdentification.ID",RUC);	


		
		// QUERY COMPLETO 
		filter=Filters.and(fRUC);


		MongoCursor<Document> result = dbW.getCollection("USER").find(filter).iterator();	
		
		
//		document = result.next();		
		
//		boolean exist=false;
		
		while (result.hasNext()) {
			
			
			//EXECUTA TODO EL RESULTADO DEL MongoCursor<Document> result
			document = result.next();
			
			System.out.println(document.toJson().toString());
			System.out.println(fRUC+" ==> Existe");
			//Status.Response.Description			
	
				

//			String status= ((Document) document.get("Status")).getString("Status");	
			
			
//			UserParty.PartyIdentification
			
			String Description = ((Document) ((Document) document.get("UserParty")).get("PartyIdentification")).getString("ID");
			String schemeAgencyID = ((Document) ((Document) document.get("UserParty")).get("PartyIdentification")).getString("schemeAgencyID");
			
//			UserParty.PartyName.Name
			String PartyName = ((Document) ((Document) document.get("UserParty")).get("PartyName")).getString("Name");
			

			
//			UserParty.PartyRegistration.Date
			
			String PartyRegistration = ((Document) ((Document) document.get("UserParty")).get("PartyRegistration")).getString("Date");
//			Date PartyRegistration = ((Document) ((Document) document.get("UserParty")).get("PartyRegistration")).getDate("Date");
			
//			UserParty.PartyCurrentPlan.Name
			String PartyCurrentPlan = ((Document) ((Document) document.get("UserParty")).get("PartyCurrentPlan")).getString("Name");
			
//			UserParty.PartyCurrentPlan.Status
			int PlanStatus = ((Document) ((Document) document.get("UserParty")).get("PartyCurrentPlan")).getInteger("Status");
			
//			UserParty.PartyCurrentPlan.FromDate
			String FromDate = ((Document) ((Document) document.get("UserParty")).get("PartyCurrentPlan")).getString("FromDate");

			
//			UserParty.Contact.ElectronicMail
			String ElectronicMail = ((Document) ((Document) document.get("UserParty")).get("Contact")).getString("ElectronicMail");
//			
			
//			Boolean Generic=( (Document)((Document) ( (Document) document.get("Authorization")).get("Services")) .get("PDF") ).getBoolean("Generic");
			
//			UserParty.User.ElectronicMail
//			String ElectronicMail2 = ((Document) ((Document) document.get("UserParty")).get("User")).getString("ElectronicMail");
			
			
			
			ListaReporte.add(Description
					
			+"\n"+schemeAgencyID
			+"\n"+"Name: "+PartyName
			+"\n"+"PartyRegistration: "+PartyRegistration			
			+"\n"
			+"\n"+"PartyCurrentPlan"
			+"\n"+"Name: "+PartyCurrentPlan
			+"\n"+"Status: "+PlanStatus	
			+"\n"+"FromDate: "+FromDate
			+"\n"+"Mail: "+ElectronicMail
			
			);

				
		
//			exist=true;
//			break;
			count++;
			
		}

		
		
		 File archivos1 = new File("/home/acalcina/Escritorio/USUARIO_WEB.txt");
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
