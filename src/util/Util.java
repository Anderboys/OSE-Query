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

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constants.IPDFResourcesConstants;
//import migrate.util.JAXBContextFactory;
import oasis.names.specification.ubl.schema.xsd.creditnote_2.CreditNoteType;
import oasis.names.specification.ubl.schema.xsd.debitnote_2.DebitNoteType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import sunat.names.specification.ubl.peru.schema.xsd.perceptiondocuments_1.PerceptionType;
import sunat.names.specification.ubl.peru.schema.xsd.retentiondocuments_1.RetentionType;
import sunat.names.specification.ubl.peru.schema.xsd.summarydocuments_1.SummaryDocumentsType;
//import migrate.util.JAXBContextFactory;
import sunat.names.specification.ubl.peru.schema.xsd.voideddocuments_1.VoidedDocumentsType;

public class Util {

	private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

	private Util() {
		super();
	}
	public static Properties getFilePDFConfig() throws IOException {
		LOGGER.info("+loadFilePDFConfig()");
		Properties properties = new Properties();
		properties.load(new FileInputStream(new File(IPDFResourcesConstants.EFACT_PDF_CONFIG_FILE)));
		LOGGER.info("-loadFilePDFConfig()");
		return properties;
	}
	public static VoidedDocumentsType getVoidedFromStorage(String link) {
		VoidedDocumentsType voidedDocumentsType = null;
		try {
			JAXBContext jaxbContext = JAXBContextFactory.getInstance().getJaxBContext(VoidedDocumentsType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			voidedDocumentsType = (VoidedDocumentsType)jaxbUnmarshaller.unmarshal(new URL(link));
		} catch (Throwable e) {
			LOGGER.error(e.getMessage());
		}
		return voidedDocumentsType;
	}
		
	
	// -----------------  addd new documents
	
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
//	-----------------  addd new documents
	
	
	
	
	public static InvoiceType getInvoiceFromStorage(String link) {
		InvoiceType invoiceType = null;
		try {
			JAXBContext jaxbContext = JAXBContextFactory.getInstance().getJaxBContext(InvoiceType.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			invoiceType = (InvoiceType)jaxbUnmarshaller.unmarshal(new URL(link));
		} catch (Throwable e) {
			LOGGER.error(e.getMessage());
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
			LOGGER.error(e.getMessage());
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
			LOGGER.error(e.getMessage());
		}
		return debitNoteType;
	}
	public static void printXMLLines(Path path) throws IOException {
		List<String> allLines = Files.readAllLines(path);
		StringBuilder stringBuilder = new StringBuilder();

		for (String line : allLines) {
			stringBuilder.append(line);
			LOGGER.info(line);
		}
	}
}
