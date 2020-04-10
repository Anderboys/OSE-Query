package config;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import pe.com.efact.db.DBFactory;
import pe.com.efact.db.config.DBConfigType;

public class DBConfig {
	private static Logger logger = Logger.getLogger(DBConfig.class);
	
	MongoClient mongoClient = null;
	private static DBConfigType dbConfigType;

//	private File file = new File("/opt/efact/ose/create_user/db-config.xml");
//	private File filewebdev = new File("/opt/efact/ose/create_user/db-config-webdev.xml");
//	private File fileosedev = new File("/opt/efact/ose/create_user/db-config-osedev.xml");
//
//	public MongoDatabase connectionOdin() {
//		mongoClient = new MongoClient(new MongoClientURI("mongodb://admin:fEmafuhanuumw6hewubruvuwubuTr@23.253.164.179:27017/admin"));
//		MongoDatabase db = mongoClient.getDatabase("ose-efact");
//		return db;
//	}
	
	//OSE
//	private File fileose =  new File ("/opt/efact/ose/migration/etc/newconfigOse.xml");
	private File fileose =  new File ("/opt/efact/ose/migration/etc/db-config-odin-prd.xml"); // new IBM cnx
		//WEB
	private File file = new File("/opt/efact/ose/create_user/db-config.xml");
	// Pruebas NEW
	private File fileosedev = new File("/opt/efact/ose/create_user/NewQA/db-config-odin-qacli.xml");
	private File filewebdev = new File("/opt/efact/ose/create_user/NewQA/db-config-web-qacli.xml");

	// Pruebas OLD
//	private File filewebdev = new File("/opt/efact/ose/create_user/db-config-webdev.xml");		
//	private File fileosedev = new File("/opt/efact/ose/create_user/db-config-osedev.xml");
	
	public MongoDatabase connectionOdin() {
		
		MongoDatabase db = null;
		try {
		dbConfigType = (DBConfigType) unmarshaller (fileose, DBConfigType.class);
		DBFactory.getInstance ().setConnection (DBFactory.TYPE_MONGODB, dbConfigType);
		db = DBFactory.getInstance (). getMongoDatabase ();
		} catch (Exception e) {
		logger.error(e);
		}
		return db;
	}
	
	
	public MongoDatabase connectionWeb() {
		MongoDatabase db = null;
		try {
			dbConfigType = (DBConfigType) unmarshaller(file, DBConfigType.class);
			DBFactory.getInstance().setConnection(DBFactory.TYPE_MONGODB, dbConfigType);
			db = DBFactory.getInstance().getMongoDatabase();
		}catch (Exception e) {
			logger.error(e);
		}
		return db;
	}
	
	public MongoDatabase connectionWebDev() {
		MongoDatabase db = null;
		try {
			dbConfigType = (DBConfigType) unmarshaller(filewebdev, DBConfigType.class);
			DBFactory.getInstance().setConnection(DBFactory.TYPE_MONGODB, dbConfigType);
			db = DBFactory.getInstance().getMongoDatabase();
		}catch (Exception e) {
			logger.error(e);
		}
		return db;
	}
	
	public MongoDatabase connectionOseDev() {
		MongoDatabase db = null;
		try {
			dbConfigType = (DBConfigType) unmarshaller(fileosedev, DBConfigType.class);
			DBFactory.getInstance().setConnection(DBFactory.TYPE_MONGODB, dbConfigType);
			db = DBFactory.getInstance().getMongoDatabase();
		}catch (Exception e) {
			logger.error(e);
		}
		return db;
	}
	
	public void closeConnOdin() {
		mongoClient.close();
	}
	
	public void closeConnWeb() {
		DBFactory.getInstance().closeConnections();
	}
	
	private static Object unmarshaller(File file, Class<?> clazz) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return jaxbUnmarshaller.unmarshal(file);
	}
}
