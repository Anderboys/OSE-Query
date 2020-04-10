package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static DBConfig dbConfig = new DBConfig();

    static String RUC = "20132627216";
    static String type = "01";

    static String eE = "ACCEPTED";

    static List<String> uuid = new ArrayList<String>();

    public static void main(String[] args) {
        boolean conector = true;
        File archivos = new File("/tmp/log-query-ose.txt");
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            if (archivos.createNewFile()) {
                LOGGER.info("txt creado");
            }
        } catch (IOException e4) {
            // TODO Auto-generated catch block
            e4.printStackTrace();
        }
        List<String> identifiers = new ArrayList<String>();

        identifiers.add("F005-0007931");
//		identifiers.add("F005-0007932");
//		identifiers.add("F005-0007933");
//		identifiers.add("F005-0007934");

//		identifiers.add("F001-00000881");
        MongoDatabase dbW = null;
        MongoDatabase dbO = null;
        if (conector) {
            dbW = dbConfig.connectionWeb();
            dbO = dbConfig.connectionOdin();
        } else {
            dbW = dbConfig.connectionWebDev();
            dbO = dbConfig.connectionOseDev();
        }


        for (int i = 0; i < identifiers.size(); i++) {
            try {
                work(identifiers.get(i), conector, dbW, dbO);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                LOGGER.info(e.toString());
            }
        }

        try {
//			
            fichero = new FileWriter(archivos);
            pw = new PrintWriter(fichero);
            for (int it = 0; it < uuid.size(); it++) {
                pw.println(uuid.get(it));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    public static void work(String identifier, boolean conector, MongoDatabase dbW, MongoDatabase dbO) {


        LOGGER.info("---CONEXION A ODIN EXITOSA---");
        String ticket = findOneTicket(identifier, dbW);
        if (!ticket.equals("")) {
            uuid.add(ticket);
            LOGGER.info(ticket);

        } else {
            LOGGER.info("---El ticket no existe---");
//			error.add(identifier);
        }
    }


    public static String findOneTicket(String identifier, MongoDatabase db) {
        Document document = null;
        String respuesta = "";
        Bson filter = null;
        Bson fRus = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID", RUC);
        Bson fType = new Document("Authorization.CPE.DocumentType", type);
        Bson fID = new Document("ID", identifier);
        Bson fStatus = new Document("Status.Status", "ERROR");
        filter = Filters.and(fRus, fType);
        filter = Filters.and(filter, fID);
        filter = Filters.and(filter, fStatus);
        LOGGER.info(RUC + "-" + type + "-" + identifier);
        MongoCursor<Document> result = db.getCollection("TRANSACTION").find(filter).iterator();
        int count = 0;
        List<String> doc = new ArrayList<String>();
        while (result.hasNext()) {
            document = result.next();
            //Authorization.Services.PDF.Generic
            //			((Document)  document.get("Status")).replace("Status", "VOIDED", "ACCEPTED");
////			LOGGER.info(""+document.get("Status"));
//			LOGGER.info(document.toJson().toString());
            String ticket = document.getString("UUID");
            String status = ((Document) document.get("Status")).getString("Status");
            doc.add(ticket + "," + status);
////            String ticket = ((Document)  document.get("Status")).getString("Ticket");
////                  String accessKey = ((Document) ((Document) document.get("Interfaces")).get("REST")).getString("AccessKey");
//			LOGGER.info(ticket);
//			
////			if(count>0){
//				if(count==0){
//					respuesta=ticket;
//				}
//				else{
//					respuesta+="|"+ticket;
//				}
//				
////			}
            count++;
        }
        if (eE.equals("error")) {
            int r = 0;
            int coun = 0;
            for (int i = 0; i < doc.size(); i++) {

                String[] lis = doc.get(i).split(",");

                if (r == 0) {
                    respuesta = lis[0];
                    r = 1;
                } else {
                    respuesta += "|" + lis[0];
                }


            }
        } else {
            int r = 0;
            int coun = 0;
            for (int i = 0; i < doc.size(); i++) {
                if (count > 1) {
                    String[] lis = doc.get(i).split(",");
                    if (coun == 0 & (lis[1].equals("ACCEPTED") || lis[1].equals("VOIDED"))) {
                        coun++;
                    } else {
                        if (r == 0) {
                            respuesta = lis[0];
                            r = 1;
                        } else {
                            respuesta += "|" + lis[0];
                        }

                    }
                }
            }
            int r2 = 0;
            if (coun == 0) {
                for (int i = 0; i < doc.size(); i++) {
                    if (count > 1) {
                        String[] lis = doc.get(i).split(",");
                        if (coun == 0 & (lis[1].equals("ERROR"))) {
                            coun++;
                        } else {
                            if (r2 == 0) {
                                respuesta = lis[0];
                                r2 = 1;
                            } else {
                                respuesta += "|" + lis[0];
                            }
                        }
                    }
                }
            }
        }

        return respuesta;
    }

}
