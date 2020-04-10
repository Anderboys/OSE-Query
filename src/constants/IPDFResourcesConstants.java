package constants;

public interface IPDFResourcesConstants {

	/**
	 * Resource location
	 */
	public static final String FONT_A1L = "/opt/efact/ose/pdf/v1.0.0/conf/resources/fonts/ARIAL.TTF";
	public static final String EFACT_PDF_LOGO = "/opt/efact/ose/pdf/v1.0.0/conf/resources/img/20551093035.png";
	public static final String VERI_SUNAT_LOGO = "/opt/efact/ose/pdf/v1.0.0/conf/resources/img/veri_sunat.png";
	public static final String PERU_CODE = "PE";
	public static final String CURRENCY_PERU = "PEN";
	public static final String CURRENCY_PERU_SYMBOL = "S/";

	public static final String EFACT_PDF_CONFIG_FILE = "/opt/efact/ose/pdf/v1.0.0/conf/Conf.properties";
	/**
	 * Messages
	 */
	public static final String NO_DATE_FOUND_MSG = "";
	public static final String NO_FOUND_MSG = "No especificado";
	public static final String NO_FOUND_MSG2 = "No se brind\u00f3 informaci\u00f3n";

	/**
	 * Remission guides
	 */
	public static final String SENDER_REMISSION_GUIDE_LABEL = "Gu\u00eda de remisi\u00f3n remitente";
	public static final String TRANSPORT_REMISSION_GUIDE_LABEL = "Gu\u00eda de remisi\u00f3n transportista";

	/**
	 * Additional documents
	 */
	public final static String ADD_DOC_TICKET_OUT_LABEL = "Ticket de Salida (ENAPU)";
	public final static String ADD_DOC_SCOP_LABEL = "C\u00f3digo SCOP";
	public final static String ADD_DOC_OTHER_LABEL = "Otros";
	public final static String ADD_DOC_INVOICE_LABEL = "Factura (emitida para corregir error en el RUC)";

	/**
	 * General elements
	 */
	public static final String PDF_AUTHOR = "Efact S.A.C.";
	public static final String SENDER_LABEL = "Emisor";
	public static final String RECEIVER_LABEL = "Adquiriente";
	public static final String SUPPLIER = "Proveedor";
	public static final String RUC_LABEL = "RUC";
	public static final String IVA_LABEL = "IVA";
	public static final String ID_TRIBUTARIO = "ID TRIBUTARIO";
	public static final String DNI_LABEL = "DNI";

	public static final String CONDITION_TITLE = "Condiciones de Entrega";
	public static final String VALID_UNTIL_LABEL = "V\u00e1lido hasta";
	public static final String PAYMENT_CONDITION_LABEL = "Condiciones de pago";
	public static final String PAYMENT_DELIVERY_DATE_LABEL = "Fecha de entrega";
	public static final String DELIVERY_CONDITION_LABEL = "Condiciones de entrega";
	public static final String DESTINY_ADDRESS_LABEL = "Direcci\u00f3n";
	public static final String OTHER_CONDITION_LABEL = "Condiciones adicionales";

	public static final String OBSERVATIONS_LABEL = "OBSERVACIONES";
	public static final String ADITIONAL_FIELD_LABEL = "Campo adicional";
	public static final String MORE_INFO_LABEL = "M\u00e1s Informaci\u00f3n";
	public static final String ISSUE_DATE_LABEL = "Fecha de emisi\u00f3n";
	public static final String DUE_DATE_LABEL = "Fecha de vencimiento";
	public static final String MONEY_TYPE_LABEL = "Moneda";
	public static final String IGV_LABEL = "IGV";
	public static final String ANTICIPOS_LABEL = "ANTICIPOS";
	public static final String OTROS_CARGOS_LABEL = "OTROS CARGOS";
	public static final String ISC_LABEL = "ISC";

	/**
	 * Document detail elements
	 */
	public static final String CODE_LABEL = "C\u00D3DIGO";
	public static final String QUANTITY_LABEL = "CANT.";
	public static final String UNIT_LABEL = "UNID.";
	public static final String MATERIAL_LABEL = "Material";
	public static final String DESCRIPTION_LABEL = "DESCRIPCI\u00D3N";

	public static final String LINE_DISCOUNT_LABEL = "DSCTO.";
	public static final String UNIT_VALUE_LABEL = "V. UNT.";
	public static final String UNIT_PRICE_LABEL = "P. UNT.";
	public static final String PRICE_SALE_LINE_LABEL = "P. VENTA";
	public static final String TOTAL_AMOUNT_LINE_LABEL = "TOTAL";

	/**
	 * Total amounts
	 */
	public static final String TOTAL_OPGRAVADA_LABEL = "OP. GRAVADAS";
	public static final String TOTAL_INAFECTA_LABEL = "OP. INAFECTA";
	public static final String TOTAL_EXONERADA_LABEL = "OP. EXONERADA";

	public static final String TOTAL_OPNOGRAVADA_LABEL = "Operaci\u00f3n No Gravada";
	public static final String TOTAL_DISCOUNT_LABEL = "DSCTOS TOTALES";
	public static final String TOTAL_FREE_LABEL = "TOTAL OP. GRATUITAS";
	public static final String SUBTOTAL_LABEL = "SUB TOTAL";
	public static final String TOTAL_AMOUNT_LABEL = "TOTAL";
	
	
	public static final String TOTAL_PAYED = "TOTAL PAGADO";
	public static final String TOTAL_RETENTION = "RETENCIÓN TOTAL";
	public static final String TOTAL_PERCEPTION = "PERCEPCIÓN TOTAL";

	/**
	 * Detraction
	 */
	public static final String DETRACTION_NETO_LABEL = "Neto a Pagar";
	public static final String DETRACTION_BANK_NUMBER_LABEL = "Cta. Cte. Banco de la Naci\u00f3n";
	public static final String DETRACTION_BB_SS_CODE = "C\u00f3digo de BB y SS";

	public static final String LETTER_AMOUNT_LABEL = "Importe en letras";
	public static final String ISSUED_BY_LABEL = "Emitido a trav\u00E9s de";
	public static final String OPERATOR_SERVICE_ELECTRONIC = "Operador de Servicios Electrónicos";
	public static final String OSE_RESOLUTION = "según Resolución N° 034-005-0008776";
	public static final String DIGESTVALUE_LABEL = "Resumen";
	public static final String PRINT_REPRESENTATION_LABEL = "Representaci\u00f3n impresa de la {0}, consulte en {1}";
	public static final String TITLE_RESOLUTION_CODE_LABEL = "Autorizado mediante la Resoluci\u00f3n de intendencia";

	/**
	 * International
	 */
	public static final String DIRECCION_PUNTO_PARTIDA_LABEL = "Dirección de Partida";
	public static final String DIRECCION_PUNTO_LLEGADA_LABEL = "Dirección de Llegada";
	public static final String RUC_TRANSPORTISTA = "RUC Transportista";
	public static final String RAZON_SOCIAL_TRANSPORTISTA = "Razón Social Transportista";
	public static final String MARCA_VEHICULO = "Marca del Vehículo";
	public static final String PLACA_VEHICULO = "Placa del Vehículo";
	public static final String NUMERO_LICENCIA = "N° de Licencia del Conductor";
	public static final String NOMBRES_TRANSPORTISTA = "Nombre del Conductor";
	public static final String DNI_TRANSPORTISTA = "DNI del Conductor";
	public static final String CONSTANCIA_INSCRIPCION = "Número de Constancia de Inscripción del Vehículo";
	

	/**
	 * DA elements
	 */
	public static final String DA_TITLE = "Guía de Remisión electrónica";
	public static final String DA_SUBJECT = "Guía de Remisión electrónica emitida desde Efact S.A.C.";

	/**
	 * PER elements
	 */
	public static final String PER_TITLE = "Percepción electrónica";
	public static final String PER_SUBJECT = "Comprobante de Percepción electrónica emitida desde Efact S.A.C.";

	/**
	 * RET elements
	 */
	public static final String RET_TITLE = "Retención electrónica";
	public static final String RET_SUBJECT = "Comprobante de Retención electrónica emitida desde Efact S.A.C.";

	/**
	 * Invoice elements
	 */
	public static final String INVOICE_TITLE = "Factura Electr\u00f3nica";
	public static final String INVOICE_NEGOTIABLE_TITLE = "Factura Negociable";
	public static final String INVOICE_SUBJECT = "Factura electr\u00f3nica emitida desde Efact S.A.C.";
	public static final String INVOICE_NO_OBSERVATIONS = "No se ha ingresado informaci\u00f3n para esta factura.";

	/**
	 * Boleta elements
	 */
	public static final String BOLETA_TITLE = "Boleta de Venta Electr\u00f3nica";
	public static final String BOLETA_SUBJECT = "Boleta de Venta emitida desde Efact";
	public static final String BOLETA_NO_OBSERVATIONS = "No se ha ingresado informaci\u00f3n para esta boleta de venta.";

	/**
	 * Credit Note elements
	 */
	public static final String CREDIT_NOTE_TITLE = "Nota de Cr\u00E9dito Electr\u00f3nica";
	public static final String CREDIT_NOTE_SUBJECT = "Nota de Cr\u00E9dito emitida desde Efact";
	public static final String CREDIT_NOTE_NO_OBSERVATIONS = "No se ha ingresado informaci\u00f3n para esta nota de cr\u00E9dito.";

	/**
	 * Debit Note elements
	 */
	public static final String DEBIT_NOTE_TITLE = "Nota de D\u00E9bito Electr\u00f3nica";
	public static final String DEBIT_NOTE_SUBJECT = "Nota de D\u00E9bito emitida desde efact";
	public static final String DEBIT_NOTE_NO_OBSERVATIONS = "No se ha ingresado informaci\u00f3n para esta nota de d\u00E9bito.";

	/**
	 * Order elements
	 */
	public static final String ORDER_TITLE = "Orden de compra";
	public static final String ORDER_SUBJECT = "Order de compra emitida desde Efact S.A.C.";
	public static final String ORDER_NO_MORE_INFO = "No se ha ingresado informaci\u00f3n para esta order de compra.";

	/**
	 * Quotation elements
	 */
	public static final String QUOTATION_TITLE = "Cotizaci\u00f3n";
	public static final String QUOTATION_SUBJECT = "Cotizaci\u00f3n emitida desde Efact S.A.C.";
	public static final String QUOTATION_NO_MORE_INFO = "No se ha ingresado informaci\u00f3n para esta cotizaci\u00f3n.";

	public static final String DOC_RELATION_INVOICE = "Factura";
	public static final String DOC_RELATION_BOLETA = "Boleta de Venta";
	public static final String DOC_RELATION_CN = "Nota de Cr\u00E9dito";
	public static final String DOC_RELATION_DN = "Nota de D\u00E9bito";

	/**
	 * Credit note type
	 */
	public static final String CN_OPERATION_NULLIFY_TRANSACTION = "Anulaci\u00f3n de la operaci\u00f3n";
	public static final String CN_OPERATION_ERROR_RUC = "Anulaci\u00f3n por error en el RUC";
	public static final String CN_OPERATION_ERROR_DESC = "Correcci\u00f3n por error en la descripci\u00f3n";
	public static final String CN_OPERATION_GLOBAL_DISCOUNT = "Descuento global";
	public static final String CN_OPERATION_ITEM_DISCOUNT = "Descuento por \u00edtem";
	public static final String CN_OPERATION_TOTAL_REFUND = "Devoluci\u00f3n total";
	public static final String CN_OPERATION_ITEM_REFUND = "Devoluci\u00f3n por \u00edtem";
	public static final String CN_OPERATION_BONIFICATION = "Bonificaci\u00f3n";
	public static final String CN_OPERATION_VALUE_REDUCE = "Disminuci\u00f3n en el valor";
	public static final String CN_OPERATION_OTHERS_CONCEPTS = "Otros conceptos";

	/**
	 * Debit note type
	 */
	public static final String DN_OPERATION_PENALTY_INTEREST = "Intereses por Mora";
	public static final String DN_OPERATION_INCREASE_VALUE = "Aumento en el Valor";
	public static final String DN_OPERATION_PENALTIES = "Penalidades";

	/**
	 * Attachment documents
	 */
	public static final String REMISSION_GUIDE_LABEL = "Guía(s) de Remisión";
	public static final String ADDITIONAL_DOCUMENT_LABEL = "Documentos adicionales";

	/**
	 * Document type <cbc:AdditionalAccountID>
	 */
	public static final String ID_DOC_WITHOUT_RUC_NAME = "IVA";
	public static final String ID_DOC_DNI_NAME = "DNI";
	public static final String ID_DOC_FOREIGN_CARD_NAME = "CE";
	public static final String ID_DOC_RUC_NAME = "RUC";
	public static final String ID_DOC_PASSPORT_NAME = "PASAPORTE";
	public static final String ID_DOC_DIPLOMATIC_ID_NAME = "CED. DIPLOM\u00c1TICA DE IDENTIDAD";

	public static final String CN_RELATIONAL_DOC_TIPO = "Tipo";
	public static final String CN_RELATIONAL_DOC_RUC = "Referencia";
	public static final String CN_DESCRIPTION = "Motivo";
	public static final String CN_TITLE_RELATIONAL = "DOCUMENTO QUE MODIFICA";

	/**
	 * CAVALI
	 */
	public static final String NEGOTIABLE_BILL = "FACTURA NEGOCIABLE";

} // IPDFResourcesConstants
