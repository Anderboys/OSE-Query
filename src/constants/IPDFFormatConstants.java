package constants;

/**
 * Constants for the format of the PDF.
 * 
 * @author Jose Manuel Lucas Barrera (jlucas@efact.pe)
 */
public interface IPDFFormatConstants {

	/**
	 * Locale and Zone management
	 */
	public static final String LOCALE_EN_US = "en_US";
	public static final String LOCALE_ES = "es";
	public static final String LOCALE_PE = "PE";
	public static final String LANG_PATTERN = "llll";

	/**
	 * Regex and format patterns
	 */
	public static final String PATTERN_FLOAT_DEC = "###,###.00";
	public static final String PATTERN_DATE = "dd-MMM-yyyy";
	public static final String SUFFIX_TEXT_LENGTH = "...";
	public static final String INPUT_DATE_PATTERN = "yyyy-MM-dd";
	public static final String EMPTY_TEXT = "";
	public static final String ADDRESS_FORMAT = "{0} - {1}";
	public static final String ADDRESS_FORMAT_NAT = "{0} - {1} - {2}";
	public static final String RESOLUTION_CODE_FORMAT = "{0} N° {1}";
	public static final String NUM_REMISSION_GUIDE = "N° {0} - {1}";
	public static final String PERCENT_DETRACTION = "Detracci\u00f3n ({0}%)";
	public static final String PERCENT_PERCEPTION = "Percepci\u00f3n ({0}%)";
	public static final String ADDITIONAL_DOCUMENT_FORMAT = "N° {0} - {1}";

	public static final String ADDITIONAL_INFO_PREFIX = "WX";

} // IPDFFormatConstants
