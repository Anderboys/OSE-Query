package constants;

public interface IPDFConstants {

	public static final String PDF_CLIENT_XML_CONTEXT = "efact.alfa1lab.cuzco.pdf_adapter.xml.config";
	public static final String CERO_TEXT = "0.00";
	public static final String NULL_TEXT = "- - -";
	public static final String BLANK_TEXT = "";

	/**
	 * Identity Document Types.
	 */
	public static final String ID_DOC_WITHOUT_RUC = "0";
	public static final String ID_DOC_DNI = "1";
	public static final String ID_DOC_FOREIGN_CARD = "4";
	public static final String ID_DOC_RUC = "6";
	public static final String ID_DOC_PASSPORT = "7";
	public static final String ID_DOC_DIPLOMATIC_ID = "8";

	/**
	 * Data config
	 */
	public static final String PROPERTY_KEY_PATH_CONFIG = "ose.pdf.xml-sunat";
	public static final String REPOSITORY_PDF_FILES = "ose.pdf.pdf-file";
	public static final String RESOLUTION = "0340050004177/SUNAT";//"ose.pdf.resolution";
	public static final String EFACT_EMAIL = "www.efact.pe";//"ose.pdf.efactemail";
	public static final String PATH_BASE_CLIENT = "/var/efact/pdf/clients";//"efact.pdf.customized.path_base";
	public static final String PATH_BASE_SENDER_LOGO = "/opt/efact/ose/pdf/v1.0.0/conf/resources/img";

	public static final String FORMAT_IMAGE_PNG = ".png";
	/**
	 * Date
	 */
	public static final String ISSUE_DATE = "issueDate";
	public static final String DUE_DATE = "dueDate";

	/**
	 * TaxTotal type values.
	 */
	public static final String TAXTOTAL_IGVVALUE = "igv_value";
	public static final String TAXTOTAL_IGVPERCENT = "igv_percent";
	public static final String TAXTOTAL_ISCVALUE = "isc_value";
	public static final String TAXTOTALLINE_IGVPERCENT = "igvline_percent";
	public static final String TAXTOTAL_IGV_VALLUE = "discount_igv_value";
	public static final String LINE_EXTENSION_AMOUNT = "line_extension_amount";
	public static final String ALLOWANCE_TOTAL_AMOUNT = "Allowance_Total_Amount";
	public static final String GRAVADO = "gravado";
	public static final String INAFECTO = "INA";
	public static final String EXONERADO = "EXO";
	
	/**
	 * Tax Codes.
	 */
	public final static String TAX_CODE_IGV = "1000";
	public final static String TAX_CODE_ISC = "2000";
	public final static String TAX_CODE_OTH = "9999";

	/**
	 * Pricing reference.
	 */
	public final static String PRICE_TYPECODE_UNITPRICE = "01";
	public final static String PRICE_TYPECODE_VALUEREFERENCE = "02";

	/**
	 * Type SENDER and RECIPIENT
	 */
	public static final int SENDER_USER = 0;
	public static final int RECIPIENT_USER = 1;

	/**
	 * Remission guides
	 */
	public static final String SENDER_REMISSION_GUIDE = "09";
	public static final String TRANSPORT_REMISSION_GUIDE = "31";

	/**
	 * Documents
	 */
	public static final String DOC_INVOICE = "01";
	public static final String DOC_BOLETA = "03";
	public static final String DOC_CREDIT_NOTE = "07";
	public static final String DOC_DEBIT_NOTE = "08";
	public static final String DOC_RETENTION = "20";
	public static final String DOC_PERCEPTION = "40";
	public static final String DOC_DESPATCH_ADVICE = "09";
	public static final String DOC_VOIDED = "17";

	/**
	 * Additional document
	 */
	public final static String ADD_DOC_TICKET_OUT_CODE = "04";
	public final static String ADD_DOC_SCOP_CODE = "05";
	public final static String ADD_DOC_OTHER_CODE = "99";
	public final static String ADD_DOC_INVOICE_CODE = "01";

	/**
	 * Hidden values
	 */
	public final static String HIDDEN_UPRICE = "hidden_uprice";
	public final static String HIDDEN_PRICE = "hidden_xprice";
	public final static String HIDDEN_LINEDISCOUNT = "discount_desc_value";

	/**
	 * UBL constants
	 */
	public static final String UBL_ID = "ID";
	public static final String UBL_PayableAmount = "PayableAmount";
	public static final String UBL_TotalAmount = "TotalAmount";
	public static final String UBL_Percent = "Percent";
	public static final String UBL_Value = "Value";
	public static final String UBL_AdditionalMonetaryTotal = "AdditionalMonetaryTotal";
	public static final String UBL_AdditionalProperty = "AdditionalProperty";
	public static final String UBL_SUNATEMBEDEDDESPATCHADVICE = "SUNATEmbededDespatchAdvice";
	public static final String UBL_SIGNED_INFO = "SignedInfo";
	public static final String UBL_REFERENCE = "Reference";
	public static final String UBL_DIGEST_VALUE = "DigestValue";
	
	/**
	 * ExtensionContent total amount code.
	 */
	public static final String UBL_CODE_1000 = "1000";
	public static final String UBL_CODE_1001 = "1001";
	public static final String UBL_CODE_1002 = "1002";
	public static final String UBL_CODE_1003 = "1003";
	public static final String UBL_CODE_1004 = "1004";
	public static final String UBL_CODE_1005 = "1005";
	public static final String UBL_CODE_2000 = "2000";
	public static final String UBL_CODE_2001 = "2001";
	public static final String UBL_CODE_2002 = "2002";
	public static final String UBL_CODE_2003 = "2003";
	public static final String UBL_CODE_2004 = "2004";
	public static final String UBL_CODE_2005 = "2005";
	public static final String UBL_CODE_3000 = "3000";
	public static final String UBL_CODE_3001 = "3001";
	public static final String UBL_CODE_3002 = "3002";
	public static final String UBL_CODE_3003 = "3003";
	public static final String UBL_CODE_3004 = "3004";
	public static final String UBL_CODE_3005 = "3005";
	public static final String UBL_CODE_3006 = "3006";
	public static final String UBL_CODE_3007 = "3007";
	public static final String UBL_CODE_3008 = "3008";
	public static final String UBL_CODE_3009 = "3009";
	public static final String UBL_CODE_3010 = "3010";
	public static final String UBL_CODE_4000 = "4000";
	public static final String UBL_CODE_4001 = "4001";
	public static final String UBL_CODE_4002 = "4002";
	public static final String UBL_CODE_4003 = "4003";
	public static final String UBL_CODE_4004 = "4004";
	public static final String UBL_CODE_4005 = "4005";
	public static final String UBL_CODE_4006 = "4006";
	public static final String UBL_CODE_4007 = "4007";
	public static final String UBL_CODE_4008 = "4008";
	public static final String UBL_CODE_4009 = "4009";

	/**
	 * IGV data
	 */
	public static final String IGV_PERCENT = "18.00";

	/**
	 * Padding type
	 */
	public static final int PADDING_RIGHT = 0;
	public static final int PADDING_LEFT = 1;
	public static final int PADDING_BOTTOM = 2;
	public static final int PADDING_TOP = 3;
	public static final int PADDING_ALL = 4;

	/**
	 * Height type
	 */
	public static final int HEIGHT_FIXED = 0;
	public static final int HEIGHT_MINIMUN = 1;

	/**
	 * X : position images
	 */
	public static final int WITH_IMAGE = 136;
	public static final int WITHOUT_IMAGE = 48;

	/**
	 * Related document constants
	 */
	public static final int RELATED_DOC_IDENTIFIER = 1;
	public static final int RELATED_DOC_DOCUMENT_TYPE = 2;
	public static final int RELATED_DOC_OPERATION_TYPE = 3;
	public static final int RELATED_DOC_DESCRIPTION = 4;

	public static final String EE_XML = ".xml";
	public static final String EE_JAR = ".jar";
	public static final String EE_PDF = ".pdf";

	public static final String BREAK_LINE = "%5D";
	public static final String BREAK_LINE_VALUE = "\n";

	public static final String HIDDEN_TOTAL = "hidden_total";
	public static final String TOPITOP_VALUE_00 = "tit00";

	public static final String PAYMENT_CONDITION = "CP";
	public static final String CLIENT_CODE = "CC";
	public static final String VENDOR_CODE = "CV";


	public static final String FILE = "/home/dortiz/";

} // IPDFConstants
