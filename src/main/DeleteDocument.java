package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import config.DBConfig;
import pe.com.efact.db.constants.ICollections;

public class DeleteDocument {

    private static final Logger LOGGER = LoggerFactory.getLogger(update2.class);
    private static DBConfig dbConfig = new DBConfig();

    static ArrayList<String> uuid = new ArrayList<String>();
    static List<String> ListauuidNo = new ArrayList<String>();


    static int count = 0;


    //	static String tipo="XZP";
    static String status = "";


    public static void main(String[] args) throws IOException {

//		boolean conector=false; // PRUEBAS
        boolean conector = true; // PRODUCCION

        File archivos2 = new File("/tmp/uuidNo.txt");

        FileWriter fichero2 = null;
        PrintWriter pw2 = null;

        try {
            if (archivos2.createNewFile()) {
                LOGGER.info("txt creado");
            }
        } catch (IOException e4) {
            // TODO Auto-generated catch block
            e4.printStackTrace();
        }


        List<String> identifiers = new ArrayList<String>();
//		
        FileReader fileData = new FileReader("/home/acalcina/Escritorio/Query_WEB_Ticket.txt");
//		
        BufferedReader bf = new BufferedReader(fileData);
        String sCadena = "";
        while ((sCadena = bf.readLine()) != null) {

            identifiers.add(sCadena);
            System.out.println(sCadena);
        }

//		 identifiers.add("12611388-57e8-4a6f-8608-9fdf189737de");
//		 identifiers.add("620b4b28-7b70-4222-a0b3-b2de9e508b46");
//		 identifiers.add("8ab3ea7b-87c0-4e26-bf20-665ff9d559c7");
//		 identifiers.add("221e208a-ee82-4b52-b3ce-14a04af7f2ce");
//		 identifiers.add("4ee1a125-1c00-4942-b12e-19673c7c3515");
//		 identifiers.add("1f6e64ff-1a5b-4271-b9aa-dec04f0e7e11");
//		 identifiers.add("722c0634-4234-404b-837a-a844cb8319c7");
//		 identifiers.add("984f9451-8461-4a70-a0c1-3ffbeee40592");
//		 identifiers.add("219bf2d9-b023-4cc3-8a21-9c4abc211b8f");
//		 identifiers.add("12b75ff6-419c-410b-a3bc-5e4d873060b2");
//		 identifiers.add("cb31efc9-acd6-41fe-aad2-6bac602354fd");
//		 identifiers.add("c37efb93-7942-48a9-a26f-9b8ae8388afe");
//		 identifiers.add("d3b2e976-9268-4adf-87a1-3972014e75ad");
//		 identifiers.add("d5bf04a0-82f5-466c-a5a9-ead6e2bedd8f");
//		 identifiers.add("e8db4f04-8a04-43c3-a6fd-95bd79cf76c3");
//		 identifiers.add("b60fcd05-de8f-4d01-acc1-e2f59ebec134");
//		 identifiers.add("a5221a98-50b3-4b8b-8391-eb5f6486053f");
//		 identifiers.add("e5d1c8a5-c77f-4c8b-a373-4e4463a8f9eb");
//		 identifiers.add("83d03330-713f-4693-aec7-d8c5cdaf5a45");
//		 identifiers.add("85d4b79b-be4b-49cc-bcf0-b3dbb51e2141");
//		 identifiers.add("d373787f-3d86-4ca7-a424-7de09bb26f74");
//		 identifiers.add("0c41447e-4061-4ec9-b031-ca2dd5ead611");
//		 identifiers.add("fde583db-999d-475f-9e36-fe0b0d39888b");
//		 identifiers.add("e2bfdf0e-41d2-4db6-be3a-a61e8c5782ad");
//		 identifiers.add("7b8ee59c-2bf0-43e6-9a7c-f2848ed2f1ba");
//		 identifiers.add("5b0ae99e-4374-4fec-b5a4-4944b664792b");


        MongoDatabase dbW = null;
        MongoDatabase dbO = null;

        if (conector) {  //PRODUCCION

            dbW = dbConfig.connectionWeb();
            dbO = dbConfig.connectionOdin();

        } else {        //PRUEBAS

            dbW = dbConfig.connectionWebDev();
            dbO = dbConfig.connectionOseDev();
        }


        for (int i = 0; i < identifiers.size(); i++) {
            try {
                work(identifiers.get(i), conector, dbW);   // WEB
//				work(identifiers.get(i),conector,dbO);	// ODIN	cuidado xD
            } catch (Exception e) {
                // TODO Auto-generated catch block
                LOGGER.info(e.toString());
            }
        }


        System.out.println(count);

    }

    public static void work(String identifier, boolean conector, MongoDatabase dbO) {
        LOGGER.info("---CONEXION A ODIN EXITOSA---");
        String fin = findOne(identifier, dbO);
        if (!fin.equals("")) {
            uuid.add(fin);
        } else {
            LOGGER.info("---El ticket no existe---");
            ListauuidNo.add(identifier);
        }
    }

    public static String findOne(String UUID, MongoDatabase db) {
        String respuesta = "";
        String Status = "abc";
        if (Status != null && !Status.equals("")) {
            boolean resp = DeleteDocument(UUID, db);
            if (resp) {
                respuesta = UUID;
                System.out.println("Documento eliminado " + UUID);
            } else {
                System.out.println("ERROR AL ELIMINAR " + UUID);
            }
        }
        return respuesta;
    }

    public static boolean DeleteDocument(String UUID, MongoDatabase db) {
        Bson filter = new Document("UUID", UUID);
//		return delete(filter, ICollections.COLLECTION_TRANSACTION,db);
        return delete(filter, ICollections.COLLECTION_TRANSACTION, db);
    }


    public static boolean delete(Bson filter, String collection, MongoDatabase db) {
        MongoCollection<Document> c = db.getCollection(collection);
        DeleteResult result = c.deleteOne(filter);
        Boolean response = false;
        if (result.getDeletedCount() != 0) {
            response = true;
        }
        return response;
    }

}//fin


