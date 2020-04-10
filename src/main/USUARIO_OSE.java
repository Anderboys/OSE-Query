package main;

import java.io.File;

import java.io.FileWriter;

import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;


import config.DBConfig;

public class USUARIO_OSE {
	private static final Logger LOGGER = LoggerFactory.getLogger(USUARIO_OSE.class);
	private static DBConfig dbConfig = new DBConfig();
	
	static String RUC="20551093035";	
	

//	
//	static List<String> Lista1 = new ArrayList<String>();
//	static List<String> Lista2 = new ArrayList<String>();
//	static List<String> Lista3 = new ArrayList<String>();
	
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
			
		
		// WHERE  RUC  + IDENTIFIER
		
//		Bson filter=null;
//		Bson fRUC = new Document("UserParty.PartyIdentification.ID",RUC);
		
		// QUERY COMPLETO 
//		filter=Filters.and(fRUC);


//		MongoCursor<Document> result = dbO.getCollection("USER").find(filter).iterator();	
		
		// ALL QUERY
		MongoCursor<Document> result = dbO.getCollection("USER").find().iterator();
		
//		document = result.next();		
		
//		boolean exist=false;
		
		while (result.hasNext()) {
			
			
			//EXECUTA TODO EL RESULTADO DEL MongoCursor<Document> result
			document = result.next();
			
			System.out.println(document.toJson().toString());
//			System.out.println(fRUC+" ==> Existe");
			//Status.Response.Description			
	
				

//			String status= ((Document) document.get("Status")).getString("Status");	
			
			
//			UserParty.PartyIdentification
			
			String ruc = ((Document) ((Document) document.get("UserParty")).get("PartyIdentification")).getString("ID");
			String schemeAgencyID = ((Document) ((Document) document.get("UserParty")).get("PartyIdentification")).getString("schemeAgencyID");
			
//			UserParty.PartyName.Name
			String PartyName = ((Document) ((Document) document.get("UserParty")).get("PartyName")).getString("Name");
			
			
			
//			UserParty.PostalAddress.StreetName		
			
			String StreetName = ((Document) ((Document) document.get("UserParty")).get("PostalAddress")).getString("StreetName");
		
			String CityName = ((Document) ((Document) document.get("UserParty")).get("PostalAddress")).getString("CityName");
			String CountrySubentity = ((Document) ((Document) document.get("UserParty")).get("PostalAddress")).getString("CountrySubentity");
			String District = ((Document) ((Document) document.get("UserParty")).get("PostalAddress")).getString("District");
			
//			UserParty.PostalAddress.Country.IdentificationCode
			String IdentificationCode=( (Document)((Document) ( (Document) document.get("UserParty")).get("PostalAddress")) .get("Country") ).getString("IdentificationCode");
			

//			Interfaces.SOAP.AccessKey
			
			String AccessKey="";
			String AccessKey2="";
			try {
				AccessKey = ((Document) ((Document) document.get("Interfaces")).get("SOAP")).getString("AccessKey");
					
//					Interfaces.REST.AccessKey
				AccessKey2 = ((Document) ((Document) document.get("Interfaces")).get("REST")).getString("AccessKey");
					
			} catch (Exception e) {
				// TODO: handle exception
				AccessKey="No registrado";
				AccessKey2 ="No registrado";
			}
		
			
			
			ListaReporte.add(
			"ID: "+ruc
			+"\n"+"schemeAgencyID: "+schemeAgencyID
			+"\n"+"Name: "+PartyName
			+"\n"+"StreetName: "+StreetName
			+"\n"+"CityName: "+CityName
			+"\n"+"CountrySubentity: "+CountrySubentity	
			+"\n"+"District: "+District
			+"\n"+"IdentificationCode: "+IdentificationCode
			+"\n"+"AccessKey SOAP: "+AccessKey
			+"\n"+"AccessKey REST: "+AccessKey2
			+"\n"
			);

				
		
//			exist=true;
//			break;
			count++;
			
		}

		
		
		 File archivos1 = new File("/home/acalcina/Escritorio/USUARIOS_OSE.txt");
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
