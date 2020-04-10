package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

import config.DBConfig;
import pe.com.efact.db.constants.ICollections;
public class update2 {
	private static final Logger LOGGER = LoggerFactory.getLogger(update2.class);
	private static DBConfig dbConfig = new DBConfig();
	static List<String> uuid = new ArrayList<String>();
	static List<String> uuidNo = new ArrayList<String>();
	static List<String> uuidA = new ArrayList<String>();
	
	
	static int count=0;
	
	
//	static String tipo="XZP";
	static String tipo="P";
	
	public static void main(String[] args){
		boolean conector=true;
		 File archivos2 = new File("/tmp/log-query-stogareNo.txt");
		
		 FileWriter fichero2=null;
		 PrintWriter pw2=null;
		
		 try {
			if(archivos2.createNewFile()){
				LOGGER.info("txt creado");
			 }
		} catch (IOException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		
		List<String> identifiers = new ArrayList<String>();
	
		identifiers.add("9b1ea4aa-d8e6-429c-a192-ae5ae0e6f97f");
//		identifiers.add("cbab7550-8c78-449f-8437-82b19ac3326b");
//		identifiers.add("ac5c5d11-9643-466b-b016-e0273d54d596");
//		identifiers.add("a8ecd364-268e-49d0-971b-4d435d5c3af4");

		MongoDatabase dbO=null;
		
		if(conector){
			dbO = dbConfig.connectionOdin();
		}else{
			dbO = dbConfig.connectionOseDev();
		}
		
		
		for (int i = 0; i < identifiers.size(); i++) {
			try {
				work(identifiers.get(i),conector,dbO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				 LOGGER.info(e.toString());
			}
		}
		
		try{
//			
			fichero2=new FileWriter(archivos2);
			pw2= new PrintWriter(fichero2);
			for(int it = 0; it < uuidNo.size(); it++){
				pw2.println(uuidNo.get(it));
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
	
	public static void work(String identifier,boolean conector,MongoDatabase dbO) {
		
		LOGGER.info("---CONEXION A ODIN EXITOSA---");
		String fin=findOne(identifier,dbO);
		
		if(!fin.equals("")){
			uuid.add(fin);
		}else{
			LOGGER.info("---El ticket no existe---");
		}
	}
	
	public static String findOne (String UUID, MongoDatabase db) {
		
		String respuesta="";
		
//		https://d8d8c0e9bb3c2ab739c5-18f8633f069e36508964cc734a222969.ssl.cf1.rackcdn.com/pdf/8de7c946-0a75-47fa-855d-4514fc543525
		
		// BAJAS
//		
		String linkP="https://d8d8c0e9bb3c2ab739c5-18f8633f069e36508964cc734a222969.ssl.cf1.rackcdn.com/pdf/"+UUID+"";
		String linkX="http://e8f70adf5bc761aa7675-c9fd2f3f5e5f00b258eee015b0906ac6.r86.cf1.rackcdn.com/xml/"+UUID+"";
		String linkZ="http://e8f70adf5bc761aa7675-c9fd2f3f5e5f00b258eee015b0906ac6.r86.cf1.rackcdn.com/cdr/"+UUID+"";

		
		
		
		//RETENCIONES
//		String linkP="https://6b2f2a096128eebc0b6e-4c00e0cdce6c1882ca38b58b3050617b.ssl.cf1.rackcdn.com/pdf/"+UUID+"";
//		String linkX="https://6b2f2a096128eebc0b6e-4c00e0cdce6c1882ca38b58b3050617b.ssl.cf1.rackcdn.com/xml/"+UUID+"";
//		String linkZ="https://6b2f2a096128eebc0b6e-4c00e0cdce6c1882ca38b58b3050617b.ssl.cf1.rackcdn.com/cdr/"+UUID+"";

			
		//NC
		
//		String linkP="https://cd24a403f7a995f15cf0-b3784495ab31676beb8af064eaf1d8af.ssl.cf1.rackcdn.com/pdf/"+UUID+"";
//		String linkX="https://cd24a403f7a995f15cf0-b3784495ab31676beb8af064eaf1d8af.ssl.cf1.rackcdn.com/xml/"+UUID+"";
//		String linkZ="https://cd24a403f7a995f15cf0-b3784495ab31676beb8af064eaf1d8af.ssl.cf1.rackcdn.com/cdr/"+UUID+"";

		
		
		//factura
//		String linkP="http://cbaf7a412878f2502db9-664e81554d16856ded8e9ebd885ea538.r52.cf1.rackcdn.com/pdf/"+UUID+"";
//		String linkX="http://cbaf7a412878f2502db9-664e81554d16856ded8e9ebd885ea538.r52.cf1.rackcdn.com/xml/"+UUID+"";
//		String linkZ="http://cbaf7a412878f2502db9-664e81554d16856ded8e9ebd885ea538.r52.cf1.rackcdn.com/cdr/"+UUID+"";

		
		
		
		//factura
//		String linkP="https://7c49413b1c22c814e97e-3ea8c335857d761126421d6b6cb03647.ssl.cf1.rackcdn.com/pdf/"+UUID+"";
//		String linkX="https://7c49413b1c22c814e97e-3ea8c335857d761126421d6b6cb03647.ssl.cf1.rackcdn.com/xml/"+UUID+"";
//		String linkZ="https://7c49413b1c22c814e97e-3ea8c335857d761126421d6b6cb03647.ssl.cf1.rackcdn.com/cdr/"+UUID+"";

		//factura
		
//		String linkP="https://e8b9f60964cba393c404-664e81554d16856ded8e9ebd885ea538.ssl.cf1.rackcdn.com/pdf/"+UUID+"";
//		String linkX="https://e8b9f60964cba393c404-664e81554d16856ded8e9ebd885ea538.ssl.cf1.rackcdn.com/xml/"+UUID+"";
//		String linkZ="https://e8b9f60964cba393c404-664e81554d16856ded8e9ebd885ea538.ssl.cf1.rackcdn.com/cdr/"+UUID+"";

	//GUÍA DE REMISIÓN
		
//		String linkP="https://924778971936242b3913-7976bbd679d5a084f4530f75103e81e7.ssl.cf1.rackcdn.com/pdf/"+UUID+"";
//		String linkX="https://924778971936242b3913-7976bbd679d5a084f4530f75103e81e7.ssl.cf1.rackcdn.com/xml/"+UUID+"";
//		String linkZ="https://924778971936242b3913-7976bbd679d5a084f4530f75103e81e7.ssl.cf1.rackcdn.com/cdr/"+UUID+"";

		
//		String linkP="https://ed429dd8cdaf3556627f-b9b98161792fd22cffcef346ea5c966a.ssl.cf1.rackcdn.com/pdf/"+UUID+"";
//		String linkX="https://ed429dd8cdaf3556627f-b9b98161792fd22cffcef346ea5c966a.ssl.cf1.rackcdn.com/xml/"+UUID+"";
//		String linkZ="https://ed429dd8cdaf3556627f-b9b98161792fd22cffcef346ea5c966a.ssl.cf1.rackcdn.com/cdr/"+UUID+"";

//		String linkP="https://21f35c5c4efde7dbf139-834215dc91c380a8c1e9e28eb9ef57e3.ssl.cf1.rackcdn.com/pdf/"+UUID+"";
//		String linkX="https://21f35c5c4efde7dbf139-834215dc91c380a8c1e9e28eb9ef57e3.ssl.cf1.rackcdn.com/xml/"+UUID+"";
//		String linkZ="https://21f35c5c4efde7dbf139-834215dc91c380a8c1e9e28eb9ef57e3.ssl.cf1.rackcdn.com/cdr/"+UUID+"";

		
		// all
//		String linkP="https://e8b9f60964cba393c404-664e81554d16856ded8e9ebd885ea538.ssl.cf1.rackcdn.com/pdf/490cfe71-6ddf-4dc6-9e94-5e03935a5463";
//		String linkX="https://e8b9f60964cba393c404-664e81554d16856ded8e9ebd885ea538.ssl.cf1.rackcdn.com/xml/d627fefa-1d92-44a5-ab2f-24d3713561c0";
//		String linkZ="https://e8b9f60964cba393c404-664e81554d16856ded8e9ebd885ea538.ssl.cf1.rackcdn.com/cdr/d627fefa-1d92-44a5-ab2f-24d3713561c0";

		
		
		
		if(tipo.equals("X")){
			if(linkX!=null&&!linkX.equals("")){
						boolean resp=updateStatusXML(UUID,linkX,db);
						if(resp){
							respuesta=UUID;
							System.out.println("Link XML Actualizado "+UUID);
						}else{
							System.out.println("ERROR XML"+UUID);
						}
					}
		}
		if(tipo.equals("P")){
			if(linkP!=null&&!linkP.equals("")){
				boolean resp=updateStatusPDF(UUID,linkP,db);
						if(resp){
							respuesta=UUID;
							System.out.println("Link PDF Actualizado "+UUID);
						}else{
							System.out.println("ERROR PDF"+UUID);
							
						}
					}
		}
		if(tipo.equals("Z")){
			if(linkZ!=null&&!linkZ.equals("")){
						boolean resp=updateStatusCDR(UUID,linkZ,db);
						if(resp){
							respuesta=UUID;
							System.out.println("Link CDR Actualizado "+UUID);
						}else{
							System.out.println("ERROR CDR"+UUID);
						}
					}
		}
		
		
	
		return respuesta;
	}
	public static boolean update(Bson filter, Bson update, String collection,MongoDatabase db) {
		
		MongoCollection<Document> c = db.getCollection(collection);
		
		UpdateResult result = c.updateOne(filter, update);		
//		UpdateResult result = c.deleteOne(filter, update);  // for delete 
		
		Boolean response = false;
		
		if (result.getModifiedCount() != 0) {
			response = true;
		}
		
		return response;
	}
	public static boolean updateStatusPDF(String UUID, String link,MongoDatabase db) {
		Bson filter = new Document("UUID", UUID);
		
		Bson newValue = new Document("Storage.PDF", link);
		Bson updateOp = new Document("$set", newValue);
		return update(filter, updateOp, ICollections.COLLECTION_TRANSACTION,db);
	}
	public static boolean updateStatusXML(String UUID, String link,MongoDatabase db) {
		
		Bson filter = new Document("UUID", UUID);		
		Bson newValue = new Document("Storage.XML", link);
		Bson updateOp = new Document("$set", newValue);
		return update(filter, updateOp, ICollections.COLLECTION_TRANSACTION,db);
	}
	public static boolean updateStatusCDR(String UUID, String link,MongoDatabase db) {
		Bson filter = new Document("UUID", UUID);
		
		Bson newValue = new Document("Storage.CDR", link);
		Bson updateOp = new Document("$set", newValue);
		return update(filter, updateOp, ICollections.COLLECTION_TRANSACTION,db);
	}
	}

//}
