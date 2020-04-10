package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//import migrate.util.JAXBContextFactory;
import oasis.names.specification.ubl.schema.xsd.creditnote_2.CreditNoteType;
import oasis.names.specification.ubl.schema.xsd.debitnote_2.DebitNoteType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import sunat.names.specification.ubl.peru.schema.xsd.perceptiondocuments_1.PerceptionType;
import sunat.names.specification.ubl.peru.schema.xsd.retentiondocuments_1.RetentionType;
import sunat.names.specification.ubl.peru.schema.xsd.summarydocuments_1.SummaryDocumentsType;
//import migrate.util.JAXBContextFactory;
import sunat.names.specification.ubl.peru.schema.xsd.voideddocuments_1.VoidedDocumentsType;

public class Util2 {

//	private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

	private Util2() {
		super();
	}
	public static Properties getFilePDFConfig() throws IOException {
		System.out.println("+loadFilePDFConfig()");
		Properties properties = new Properties();
		properties.load(new FileInputStream(new File("/opt/efact/ose/pdf/v1.0.0/conf/Conf.properties")));
		System.out.println("-loadFilePDFConfig()");
		return properties;
	}
	public static VoidedDocumentsType getVoidedFromStorage(String link) {
		VoidedDocumentsType voidedDocumentsType = null;
		try {
			JAXBContext jaxbContext = JAXBContextFactory.getInstance().getJaxBContext(VoidedDocumentsType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			voidedDocumentsType = (VoidedDocumentsType)jaxbUnmarshaller.unmarshal(new URL(link));
		} catch (Throwable e) {
			System.err.println(e.getMessage());
		}
		return voidedDocumentsType;
	}
	public static SummaryDocumentsType getSummaryDocumentsType(String link) {
		SummaryDocumentsType voidedDocumentsType = null;
		try {
			JAXBContext jaxbContext = JAXBContextFactory.getInstance().getJaxBContext(SummaryDocumentsType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			voidedDocumentsType = (SummaryDocumentsType)jaxbUnmarshaller.unmarshal(new URL(link));
		} catch (Throwable e) {
			System.err.println(e.getMessage());
		}
		return voidedDocumentsType;
	}
	public static InvoiceType getInvoiceFromStorage(String link) {
		InvoiceType invoiceType = null;
		try {
			JAXBContext jaxbContext = JAXBContextFactory.getInstance().getJaxBContext(InvoiceType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			invoiceType = (InvoiceType)jaxbUnmarshaller.unmarshal(new URL(link));
		} catch (Throwable e) {
			System.err.println(e.getMessage());
		}
		return invoiceType;
	}
	public static CreditNoteType getCreditNoteFromStorage(String link) {
		CreditNoteType creditNoteType = null;
		try {
			JAXBContext jaxbContext = JAXBContextFactory.getInstance().getJaxBContext(CreditNoteType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			creditNoteType = (CreditNoteType)jaxbUnmarshaller.unmarshal(new URL(link));
		} catch (Throwable e) {
			System.err.println(e.getMessage());
		}
		return creditNoteType;
	}
	public static DebitNoteType getDebitNoteTypeFromStorage(String link) {
		DebitNoteType debitNoteType = null;
		try {
			JAXBContext jaxbContext = JAXBContextFactory.getInstance().getJaxBContext(DebitNoteType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			debitNoteType = (DebitNoteType)jaxbUnmarshaller.unmarshal(new URL(link));
		} catch (Throwable e) {
			System.err.println(e.getMessage());
		}
		return debitNoteType;
	}
	public static RetentionType getRetentionTypeFromStorage(String link) {
		RetentionType retentionType = null;
		try {
			JAXBContext jaxbContext = JAXBContextFactory.getInstance().getJaxBContext(RetentionType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			retentionType = (RetentionType)jaxbUnmarshaller.unmarshal(new URL(link));
		} catch (Throwable e) {
			System.err.println(e.getMessage());
		}
		return retentionType;
	}
	public static PerceptionType getPerceptionTypeFromStorage(String link) {
		PerceptionType perceptionType = null;
		try {
			JAXBContext jaxbContext = JAXBContextFactory.getInstance().getJaxBContext(PerceptionType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			perceptionType = (PerceptionType)jaxbUnmarshaller.unmarshal(new URL(link));
		} catch (Throwable e) {
			System.err.println(e.getMessage());
		}
		return perceptionType;
	}
	public static void printXMLLines(Path path) throws IOException {
		List<String> allLines = Files.readAllLines(path);
		StringBuilder stringBuilder = new StringBuilder();

		for (String line : allLines) {
			stringBuilder.append(line);
			System.out.println(line);
		}
	}
	public static boolean saveDocument(String Uuid,String tipoD,String tipo) {
    	boolean respuesta=false;
		try {
				String tipoFile="";
				boolean valCDR=false;
				if(tipoD.equals("cdr")){
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
				}else if(!valCDR&&tipo.equals("08")){
					tipoFile+="DEBIT_NOTE/";
				}else if(!valCDR&&tipo.equals("20")){
					tipoFile+="RETENTION/";
				}else if(!valCDR&&tipo.equals("40")){
					tipoFile+="PERCEPTION/";
				}else if(!valCDR&&tipo.equals("16")){
					tipoFile+="SUMMARY/";
				}else if(!valCDR&&tipo.equals("18")){
					tipoFile+="REVERSED/";
				}else if(!valCDR&&tipo.equals("17")){
					tipoFile+="VOIDED/";
				}
//				Thread.sleep(500);
					String rutaInicial="/opt/efact/ose/storage_smb/"+tipoFile+Uuid;
//					script = "ls -l "+rutaInicial ;
					
					File file =new File(rutaInicial);
					System.out.println(rutaInicial);
					if(!file.exists()){

//						if (exitValJarS1 != 0) {
//							System.out.println("2=>"+script);
//							Process server1Jar2 = Runtime.getRuntime().exec(script);
//							int exitValJarS2 = server1Jar2.waitFor();
//							if (exitValJarS2 != 0) {
//								System.out.println("3=>"+script);
//								Process server1Jar3 = Runtime.getRuntime().exec(script);
//								int exitValJarS3 = server1Jar3.waitFor();
//								if (exitValJarS3 != 0) {
//								}else{
//									respuesta=true;
//								}
//							}else{
//								respuesta=true;
//							}
//						} else {
//							respuesta=true;
//						}

					}else{
						respuesta=true;
					}

		} catch (Exception e) {
		}
		return respuesta;
	}
}
