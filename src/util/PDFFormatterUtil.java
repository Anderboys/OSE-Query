package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constants.IPDFCurrencyConstants;
import constants.IPDFFormatConstants;

/**
 * This class formats and fix text for the PDF.
 * 
 * @author Jose Manuel Lucas Barrera (jlucas@efact.pe)
 */
public class PDFFormatterUtil {
	private static final Logger logger = LoggerFactory.getLogger(PDFFormatterUtil.class);

	public static java.util.Map<String, Object> currencyCodeValue;
	public static java.util.Map<String, Object> countryCodeValue;

	/**
	 * Sole private constructor to avoid instance
	 */
	private PDFFormatterUtil() {
	}

	/**
	 * This method initializes the values for currency codes and country codes.
	 */
	public static void initConfiguration() {
		if (logger.isDebugEnabled()) {
			logger.debug("+initConfiguration()");
		}
		fillCurrencyCodeValueMap();
		fillCountryCodeValueMap();
		if (logger.isDebugEnabled()) {
			logger.debug("-initConfiguration()");
		}
	} // initConfiguration

	/**
	 * This method initializes the values for currency codes.
	 */
	private static void fillCurrencyCodeValueMap() {
		currencyCodeValue = new java.util.LinkedHashMap<String, Object>();

		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.PE_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.PE_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.EU_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.EU_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.US_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.US_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.EMPTY_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.EMPTY_CURRENCY_CODE);

		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.AFN_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.AFN_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.DZD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.DZD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.ARS_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.ARS_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.AMD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.AMD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.AWG_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.AWG_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.AUD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.AUD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.AZM_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.AZM_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.BSD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.BSD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.BHD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.BHD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.THB_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.THB_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.PAB_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.PAB_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.BBD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.BBD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.BYR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.BYR_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.BZD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.BZD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.VEF_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.VEF_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.BOB_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.BOB_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.BRL_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.BRL_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.BND_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.BND_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.BGN_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.BGN_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.BIF_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.BIF_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.CAD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.CAD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.CVE_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.CVE_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.KYD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.KYD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.GHS_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.GHS_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.XOF_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.XOF_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.XAF_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.XAF_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.XPF_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.XPF_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.CLP_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.CLP_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.COP_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.COP_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.KMF_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.KMF_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.CDF_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.CDF_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.BAM_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.BAM_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.NIO_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.NIO_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.CRC_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.CRC_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.HRK_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.HRK_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.CUP_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.CUP_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.CZK_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.CZK_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.GMD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.GMD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.DKK_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.DKK_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.MKD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.MKD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.DJF_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.DJF_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.STD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.STD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.DOP_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.DOP_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.VND_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.VND_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.XCD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.XCD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.EGP_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.EGP_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.SVC_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.SVC_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.ETB_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.ETB_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.EU_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.EU_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.FKP_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.FKP_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.FJD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.FJD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.HUF_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.HUF_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.GIP_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.GIP_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.XAU_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.XAU_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.HTG_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.HTG_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.PYG_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.PYG_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.GNF_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.GNF_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.GYD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.GYD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.HKD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.HKD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.UAH_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.UAH_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.ISK_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.ISK_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.INR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.INR_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.IRR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.IRR_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.IQD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.IQD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.JMD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.JMD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.JOD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.JOD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.KES_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.KES_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.PGK_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.PGK_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.LAK_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.LAK_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.EEK_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.EEK_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.KWD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.KWD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.MWK_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.MWK_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.AOA_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.AOA_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.MMK_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.MMK_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.GEL_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.GEL_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.LVL_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.LVL_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.LBP_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.LBP_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.ALL_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.ALL_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.HNL_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.HNL_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.SLL_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.SLL_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.LRD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.LRD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.LYD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.LYD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.SZL_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.SZL_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.LTL_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.LTL_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.LSL_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.LSL_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.MGA_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.MGA_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.MYR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.MYR_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.TMM_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.TMM_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.MUR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.MUR_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.MZN_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.MZN_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.MXN_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.MXN_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.MDL_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.MDL_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.MAD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.MAD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.BOV_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.BOV_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.NGN_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.NGN_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.ERN_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.ERN_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.NAD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.NAD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.NPR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.NPR_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.ILS_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.ILS_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.RON_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.RON_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.TWD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.TWD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.NZD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.NZD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.BTN_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.BTN_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.KPW_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.KPW_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.NOK_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.NOK_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.PE_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.PE_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.MRO_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.MRO_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.TOP_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.TOP_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.PKR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.PKR_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.XPD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.XPD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.MOP_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.MOP_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.UYU_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.UYU_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.PHP_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.PHP_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.GBP_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.GBP_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.BWP_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.BWP_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.QAR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.QAR_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.GTQ_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.GTQ_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.ZAR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.ZAR_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.OMR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.OMR_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.KHR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.KHR_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.MVR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.MVR_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.IDR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.IDR_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.RUB_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.RUB_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.RWF_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.RWF_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.SHP_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.SHP_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.SAR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.SAR_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.XDR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.XDR_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.RSD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.RSD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.SCR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.SCR_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.XAG_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.XAG_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.SGD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.SGD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.SKK_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.SKK_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.KGS_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.KGS_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.SOS_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.SOS_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.TJS_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.TJS_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.LKR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.LKR_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.SDG_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.SDG_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.SRD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.SRD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.SEK_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.SEK_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.CHF_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.CHF_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.SYP_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.SYP_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.BDT_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.BDT_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.WST_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.WST_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.TZS_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.TZS_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.KZT_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.KZT_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.MNT_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.MNT_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.TND_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.TND_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.TRY_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.TRY_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.AED_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.AED_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.UGX_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.UGX_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.XFU_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.XFU_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.US_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.US_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.USN_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.USN_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.USS_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.USS_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.UZS_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.UZS_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.VUV_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.VUV_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.CHE_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.CHE_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.CHW_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.CHW_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.KRW_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.KRW_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.YER_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.YER_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.JPY_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.JPY_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.CNY_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.CNY_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.ZMK_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.ZMK_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.ZWR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.ZWR_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.ZWR_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.ZWR_CURRENCY_CODE_ALT);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.PLN_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.PLN_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.VEB_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.VEB_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.ANG_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.ANG_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.BMD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.BMD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.CYP_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.CYP_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.GHC_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.GHC_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.MGF_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.MGF_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.MTL_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.MTL_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.MZM_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.MZM_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.ROL_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.ROL_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.SBD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.SBD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.SDD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.SDD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.SIT_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.SIT_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.SRG_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.SRG_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.TRL_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.TRL_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.TTD_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.TTD_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.XPT_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.XPT_CURRENCY_CODE);
		currencyCodeValue.put(fixAllowTextLength(IPDFCurrencyConstants.YUM_CURRENCY_NAME, 30),
				IPDFCurrencyConstants.YUM_CURRENCY_CODE);
	} // fillCurrencyCodeValueMap

	/**
	 * This method initializes the values for country codes.
	 */
	private static void fillCountryCodeValueMap() {
		countryCodeValue = new java.util.LinkedHashMap<String, Object>();

		/* Setting values. */
		countryCodeValue.put(fixAllowTextLength("Afghanistan", 30), "AF");
		countryCodeValue.put(fixAllowTextLength("Albania", 30), "AL");
		countryCodeValue.put(fixAllowTextLength("Alemania", 30), "DE");
		countryCodeValue.put(fixAllowTextLength("Algeria", 30), "DZ");
		countryCodeValue.put(fixAllowTextLength("American Samoa", 30), "AS");
		countryCodeValue.put(fixAllowTextLength("Andorra", 30), "AD");
		countryCodeValue.put(fixAllowTextLength("Angola", 30), "AO");
		countryCodeValue.put(fixAllowTextLength("Anguilla", 30), "AI");
		countryCodeValue.put(fixAllowTextLength("Antarctica", 30), "AQ");
		countryCodeValue.put(fixAllowTextLength("Antigua And Barbuda", 30), "AG");
		countryCodeValue.put(fixAllowTextLength("Argentina", 30), "AR");
		countryCodeValue.put(fixAllowTextLength("Armenia", 30), "AM");
		countryCodeValue.put(fixAllowTextLength("Aruba", 30), "AW");
		countryCodeValue.put(fixAllowTextLength("Australia", 30), "AU");
		countryCodeValue.put(fixAllowTextLength("Austria", 30), "AT");
		countryCodeValue.put(fixAllowTextLength("Azerbaijan", 30), "AZ");
		countryCodeValue.put(fixAllowTextLength("Bahamas", 30), "BS");
		countryCodeValue.put(fixAllowTextLength("Bahrain", 30), "BH");
		countryCodeValue.put(fixAllowTextLength("Bangladesh", 30), "BD");
		countryCodeValue.put(fixAllowTextLength("Barbados", 30), "BB");
		countryCodeValue.put(fixAllowTextLength("Belarus", 30), "BY");
		countryCodeValue.put(fixAllowTextLength("Belgica", 30), "BE");
		countryCodeValue.put(fixAllowTextLength("Belize", 30), "BZ");
		countryCodeValue.put(fixAllowTextLength("Benin", 30), "BJ");
		countryCodeValue.put(fixAllowTextLength("Bermuda", 30), "BM");
		countryCodeValue.put(fixAllowTextLength("Butan", 30), "BT");
		countryCodeValue.put(fixAllowTextLength("Bolivia", 30), "BO");
		countryCodeValue.put(fixAllowTextLength("Bosnia y Herzegovina", 30), "BA");
		countryCodeValue.put(fixAllowTextLength("Botswana", 30), "BW");
		countryCodeValue.put(fixAllowTextLength("Brasil", 30), "BR");
		countryCodeValue.put(fixAllowTextLength("Territorio Britanico del oceano", 30), "IO");
		countryCodeValue.put(fixAllowTextLength("Brunei Darussalam", 30), "BN");
		countryCodeValue.put(fixAllowTextLength("Bulgaria", 30), "BG");
		countryCodeValue.put(fixAllowTextLength("Burkina Faso", 30), "BF");
		countryCodeValue.put(fixAllowTextLength("Burundi", 30), "BI");
		countryCodeValue.put(fixAllowTextLength("Cambodia", 30), "KH");
		countryCodeValue.put(fixAllowTextLength("Cameroon", 30), "CM");
		countryCodeValue.put(fixAllowTextLength("Canada", 30), "CA");
		countryCodeValue.put(fixAllowTextLength("Cape Verde", 30), "CV");
		countryCodeValue.put(fixAllowTextLength("Cayman Islands", 30), "KY");
		countryCodeValue.put(fixAllowTextLength("Republica centro africana", 30), "CF");
		countryCodeValue.put(fixAllowTextLength("Chad", 30), "TD");
		countryCodeValue.put(fixAllowTextLength("Chile", 30), "CL");
		countryCodeValue.put(fixAllowTextLength("China", 30), "CN");
		countryCodeValue.put(fixAllowTextLength("Christmas Island", 30), "CX");
		countryCodeValue.put(fixAllowTextLength("Islas Cocos (Keeling)", 30), "CC");
		countryCodeValue.put(fixAllowTextLength("Colombia", 30), "CO");
		countryCodeValue.put(fixAllowTextLength("Comoros", 30), "KM");
		countryCodeValue.put(fixAllowTextLength("Congo", 30), "CG");
		countryCodeValue.put(fixAllowTextLength("Congo, Republica democratica", 30), "CD");
		countryCodeValue.put(fixAllowTextLength("Costa Rica", 30), "CR");
		countryCodeValue.put(fixAllowTextLength("Cote D'ivoire", 30), "CI");
		countryCodeValue.put(fixAllowTextLength("Croacia", 30), "HR");
		countryCodeValue.put(fixAllowTextLength("Cuba", 30), "CU");
		countryCodeValue.put(fixAllowTextLength("Cyprus", 30), "CY");
		countryCodeValue.put(fixAllowTextLength("Dinamarca", 30), "DK");
		countryCodeValue.put(fixAllowTextLength("Djibouti", 30), "DJ");
		countryCodeValue.put(fixAllowTextLength("Dominica", 30), "DM");
		countryCodeValue.put(fixAllowTextLength("Ecuador", 30), "EC");
		countryCodeValue.put(fixAllowTextLength("Egipto", 30), "EG");
		countryCodeValue.put(fixAllowTextLength("El Salvador", 30), "SV");
		countryCodeValue.put(fixAllowTextLength("Equatorial Guinea", 30), "GQ");
		countryCodeValue.put(fixAllowTextLength("Emirato Arabes Unidos", 30), "AE");
		countryCodeValue.put(fixAllowTextLength("Eritrea", 30), "ER");
		countryCodeValue.put(fixAllowTextLength("Espana", 30), "ES");
		countryCodeValue.put(fixAllowTextLength("Estonia", 30), "EE");
		countryCodeValue.put(fixAllowTextLength("Estados Unidos", 30), "US");
		countryCodeValue.put(fixAllowTextLength("Estados Unidos, Islas Menores", 30), "UM");
		countryCodeValue.put(fixAllowTextLength("Ethiopia", 30), "ET");
		countryCodeValue.put(fixAllowTextLength("Filipinas", 30), "PH");
		countryCodeValue.put(fixAllowTextLength("Fiji", 30), "FJ");
		countryCodeValue.put(fixAllowTextLength("Finlandia", 30), "FI");
		countryCodeValue.put(fixAllowTextLength("Francia", 30), "FR");
		countryCodeValue.put(fixAllowTextLength("Guyana Francesa", 30), "GF");
		countryCodeValue.put(fixAllowTextLength("Gabon", 30), "GA");
		countryCodeValue.put(fixAllowTextLength("Gambia", 30), "GM");
		countryCodeValue.put(fixAllowTextLength("Georgia", 30), "GE");
		countryCodeValue.put(fixAllowTextLength("Ghana", 30), "GH");
		countryCodeValue.put(fixAllowTextLength("Gibraltar", 30), "GI");
		countryCodeValue.put(fixAllowTextLength("Grecia", 30), "GR");
		countryCodeValue.put(fixAllowTextLength("Greenland", 30), "GL");
		countryCodeValue.put(fixAllowTextLength("Grenada", 30), "GD");
		countryCodeValue.put(fixAllowTextLength("Guadeloupe", 30), "GP");
		countryCodeValue.put(fixAllowTextLength("Guam", 30), "GU");
		countryCodeValue.put(fixAllowTextLength("Guatemala", 30), "GT");
		countryCodeValue.put(fixAllowTextLength("Guernsey", 30), "GG");
		countryCodeValue.put(fixAllowTextLength("Guinea", 30), "GN");
		countryCodeValue.put(fixAllowTextLength("Guinea-Bissau", 30), "GW");
		countryCodeValue.put(fixAllowTextLength("Guyana", 30), "GY");
		countryCodeValue.put(fixAllowTextLength("Haiti", 30), "HT");
		countryCodeValue.put(fixAllowTextLength("Heard Island And Mcdonald Islands", 30), "HM");
		countryCodeValue.put(fixAllowTextLength("Holy See (Vatican City State)", 30), "VA");
		countryCodeValue.put(fixAllowTextLength("Honduras", 30), "HN");
		countryCodeValue.put(fixAllowTextLength("Hong Kong", 30), "HK");
		countryCodeValue.put(fixAllowTextLength("Hungary", 30), "HU");
		countryCodeValue.put(fixAllowTextLength("Iceland", 30), "IS");
		countryCodeValue.put(fixAllowTextLength("India", 30), "IN");
		countryCodeValue.put(fixAllowTextLength("Indonesia", 30), "ID");
		countryCodeValue.put(fixAllowTextLength("Iran, Islamic Republic Of", 30), "IR");
		countryCodeValue.put(fixAllowTextLength("Iraq", 30), "IQ");
		countryCodeValue.put(fixAllowTextLength("Ireland", 30), "IE");
		countryCodeValue.put(fixAllowTextLength("Islas Bouvet", 30), "BV");
		countryCodeValue.put(fixAllowTextLength("Islas Cook", 30), "CK");
		countryCodeValue.put(fixAllowTextLength("Islas Faroe", 30), "FO");
		countryCodeValue.put(fixAllowTextLength("Islas Malvinas", 30), "FK");
		countryCodeValue.put(fixAllowTextLength("Islas Norfolk", 30), "NF");
		countryCodeValue.put(fixAllowTextLength("Islas Northern Mariana", 30), "MP");
		countryCodeValue.put(fixAllowTextLength("Isle Of Man", 30), "IM");
		countryCodeValue.put(fixAllowTextLength("Israel", 30), "IL");
		countryCodeValue.put(fixAllowTextLength("Italia", 30), "IT");
		countryCodeValue.put(fixAllowTextLength("Jamaica", 30), "JM");
		countryCodeValue.put(fixAllowTextLength("Japon", 30), "JP");
		countryCodeValue.put(fixAllowTextLength("Jersey", 30), "JE");
		countryCodeValue.put(fixAllowTextLength("Jordan", 30), "JO");
		countryCodeValue.put(fixAllowTextLength("Kazakhstan", 30), "KZ");
		countryCodeValue.put(fixAllowTextLength("Kenya", 30), "KE");
		countryCodeValue.put(fixAllowTextLength("Kiribati", 30), "KI");
		countryCodeValue.put(fixAllowTextLength("Korea, Republica popular democratica de", 30), "KP");
		countryCodeValue.put(fixAllowTextLength("Korea, Republica de", 30), "KR");
		countryCodeValue.put(fixAllowTextLength("Kuwait", 30), "KW");
		countryCodeValue.put(fixAllowTextLength("Kyrgyzstan", 30), "KG");
		countryCodeValue.put(fixAllowTextLength("Lao People's Democratic Republic", 30), "LA");
		countryCodeValue.put(fixAllowTextLength("Latvia", 30), "LV");
		countryCodeValue.put(fixAllowTextLength("Lebanon", 30), "LB");
		countryCodeValue.put(fixAllowTextLength("Lesotho", 30), "LS");
		countryCodeValue.put(fixAllowTextLength("Liberia", 30), "LR");
		countryCodeValue.put(fixAllowTextLength("Libyan Arab Jamahiriya", 30), "LY");
		countryCodeValue.put(fixAllowTextLength("Liechtenstein", 30), "LI");
		countryCodeValue.put(fixAllowTextLength("Lithuania", 30), "LT");
		countryCodeValue.put(fixAllowTextLength("Luxembourg", 30), "LU");
		countryCodeValue.put(fixAllowTextLength("Macao", 30), "MO");
		countryCodeValue.put(fixAllowTextLength("Macedonia, The Former Yugoslav Republic Of", 30), "MK");
		countryCodeValue.put(fixAllowTextLength("Madagascar", 30), "MG");
		countryCodeValue.put(fixAllowTextLength("Malawi", 30), "MW");
		countryCodeValue.put(fixAllowTextLength("Malaysia", 30), "MY");
		countryCodeValue.put(fixAllowTextLength("Maldives", 30), "MV");
		countryCodeValue.put(fixAllowTextLength("Mali", 30), "ML");
		countryCodeValue.put(fixAllowTextLength("Malta", 30), "MT");
		countryCodeValue.put(fixAllowTextLength("Marshall Islands", 30), "MH");
		countryCodeValue.put(fixAllowTextLength("Martinique", 30), "MQ");
		countryCodeValue.put(fixAllowTextLength("Mauritania", 30), "MR");
		countryCodeValue.put(fixAllowTextLength("Mauritius", 30), "MU");
		countryCodeValue.put(fixAllowTextLength("Mayotte", 30), "YT");
		countryCodeValue.put(fixAllowTextLength("Mexico", 30), "MX");
		countryCodeValue.put(fixAllowTextLength("Micronesia, Federated States Of", 30), "FM");
		countryCodeValue.put(fixAllowTextLength("Moldova", 30), "MD");
		countryCodeValue.put(fixAllowTextLength("Monaco", 30), "MC");
		countryCodeValue.put(fixAllowTextLength("Mongolia", 30), "MN");
		countryCodeValue.put(fixAllowTextLength("Montenegro", 30), "ME");
		countryCodeValue.put(fixAllowTextLength("Montserrat", 30), "MS");
		countryCodeValue.put(fixAllowTextLength("Morocco", 30), "MA");
		countryCodeValue.put(fixAllowTextLength("Mozambique", 30), "MZ");
		countryCodeValue.put(fixAllowTextLength("Myanmar", 30), "MM");
		countryCodeValue.put(fixAllowTextLength("Namibia", 30), "NA");
		countryCodeValue.put(fixAllowTextLength("Nauru", 30), "NR");
		countryCodeValue.put(fixAllowTextLength("Nepal", 30), "NP");
		countryCodeValue.put(fixAllowTextLength("Netherlands", 30), "NL");
		countryCodeValue.put(fixAllowTextLength("Netherlands Antilles", 30), "AN");
		countryCodeValue.put(fixAllowTextLength("Nueva Caledonia", 30), "NC");
		countryCodeValue.put(fixAllowTextLength("Nueva Zealand", 30), "NZ");
		countryCodeValue.put(fixAllowTextLength("Nicaragua", 30), "NI");
		countryCodeValue.put(fixAllowTextLength("Niger", 30), "NE");
		countryCodeValue.put(fixAllowTextLength("Nigeria", 30), "NG");
		countryCodeValue.put(fixAllowTextLength("Niue", 30), "NU");
		countryCodeValue.put(fixAllowTextLength("Noruega", 30), "NO");
		countryCodeValue.put(fixAllowTextLength("Oman", 30), "OM");
		countryCodeValue.put(fixAllowTextLength("Pakistan", 30), "PK");
		countryCodeValue.put(fixAllowTextLength("Palau", 30), "PW");
		countryCodeValue.put(fixAllowTextLength("Palestinian", 30), "PS");
		countryCodeValue.put(fixAllowTextLength("Panama", 30), "PA");
		countryCodeValue.put(fixAllowTextLength("Papua Nueva Guinea", 30), "PG");
		countryCodeValue.put(fixAllowTextLength("Paraguay", 30), "PY");
		countryCodeValue.put(fixAllowTextLength("Pitcairn", 30), "PN");
		countryCodeValue.put(fixAllowTextLength("Poland", 30), "PL");
		countryCodeValue.put(fixAllowTextLength("Polinesia Francesa", 30), "PF");
		countryCodeValue.put(fixAllowTextLength("Portugal", 30), "PT");
		countryCodeValue.put(fixAllowTextLength("Puerto Rico", 30), "PR");
		countryCodeValue.put(fixAllowTextLength("Qatar", 30), "QA");
		countryCodeValue.put(fixAllowTextLength("Reino Unido", 30), "GB");
		countryCodeValue.put(fixAllowTextLength("Republica Arabe Siria", 30), "SY");
		countryCodeValue.put(fixAllowTextLength("Republica Checa", 30), "CZ");
		countryCodeValue.put(fixAllowTextLength("Republica Dominicana", 30), "DO");
		countryCodeValue.put(fixAllowTextLength("Russian Federation", 30), "RU");
		countryCodeValue.put(fixAllowTextLength("Rumania", 30), "RO");
		countryCodeValue.put(fixAllowTextLength("Rwanda", 30), "RW");
		countryCodeValue.put(fixAllowTextLength("Santa Barthelemy", 30), "BL");
		countryCodeValue.put(fixAllowTextLength("Santa Helena", 30), "SH");
		countryCodeValue.put(fixAllowTextLength("Santa Kitts And Nevis", 30), "KN");
		countryCodeValue.put(fixAllowTextLength("Santa Lucia", 30), "LC");
		countryCodeValue.put(fixAllowTextLength("Santa Martin", 30), "MF");
		countryCodeValue.put(fixAllowTextLength("Santa Pierre And Miquelon", 30), "PM");
		countryCodeValue.put(fixAllowTextLength("Santa Vincent And The Grenadines", 30), "VC");
		countryCodeValue.put(fixAllowTextLength("Samoa", 30), "WS");
		countryCodeValue.put(fixAllowTextLength("San Marino", 30), "SM");
		countryCodeValue.put(fixAllowTextLength("Sao Tome And Principe", 30), "ST");
		countryCodeValue.put(fixAllowTextLength("Saudi Arabia", 30), "SA");
		countryCodeValue.put(fixAllowTextLength("Senegal", 30), "SN");
		countryCodeValue.put(fixAllowTextLength("Serbia", 30), "RS");
		countryCodeValue.put(fixAllowTextLength("Seychelles", 30), "SC");
		countryCodeValue.put(fixAllowTextLength("Sierra Leone", 30), "SL");
		countryCodeValue.put(fixAllowTextLength("Singapore", 30), "SG");
		countryCodeValue.put(fixAllowTextLength("Slovakia", 30), "SK");
		countryCodeValue.put(fixAllowTextLength("Slovenia", 30), "SI");
		countryCodeValue.put(fixAllowTextLength("Solomon Islands", 30), "SB");
		countryCodeValue.put(fixAllowTextLength("Somalia", 30), "SO");
		countryCodeValue.put(fixAllowTextLength("Sur Africa", 30), "ZA");
		countryCodeValue.put(fixAllowTextLength("Sur Georgia And The South Sandwich Islands", 30), "GS");
		countryCodeValue.put(fixAllowTextLength("Sri Lanka", 30), "LK");
		countryCodeValue.put(fixAllowTextLength("Sudan", 30), "SD");
		countryCodeValue.put(fixAllowTextLength("Suriname", 30), "SR");
		countryCodeValue.put(fixAllowTextLength("Svalbard And Jan Mayen", 30), "SJ");
		countryCodeValue.put(fixAllowTextLength("Swaziland", 30), "SZ");
		countryCodeValue.put(fixAllowTextLength("Suecia", 30), "SE");
		countryCodeValue.put(fixAllowTextLength("Suiza", 30), "CH");
		countryCodeValue.put(fixAllowTextLength("Taiwan, Provincia de China", 30), "TW");
		countryCodeValue.put(fixAllowTextLength("Tajikistan", 30), "TJ");
		countryCodeValue.put(fixAllowTextLength("Tanzania, United Republic Of", 30), "TZ");
		countryCodeValue.put(fixAllowTextLength("Tailandia", 30), "TH");
		countryCodeValue.put(fixAllowTextLength("Territorio Sur Frances", 30), "TF");
		countryCodeValue.put(fixAllowTextLength("Timor-Leste", 30), "TL");
		countryCodeValue.put(fixAllowTextLength("Togo", 30), "TG");
		countryCodeValue.put(fixAllowTextLength("Tokelau", 30), "TK");
		countryCodeValue.put(fixAllowTextLength("Tonga", 30), "TO");
		countryCodeValue.put(fixAllowTextLength("Trinidad y Tobago", 30), "TT");
		countryCodeValue.put(fixAllowTextLength("Tunisia", 30), "TN");
		countryCodeValue.put(fixAllowTextLength("Turkey", 30), "TR");
		countryCodeValue.put(fixAllowTextLength("Turkmenistan", 30), "TM");
		countryCodeValue.put(fixAllowTextLength("Turks And Caicos Islands", 30), "TC");
		countryCodeValue.put(fixAllowTextLength("Tuvalu", 30), "TV");
		countryCodeValue.put(fixAllowTextLength("Uganda", 30), "UG");
		countryCodeValue.put(fixAllowTextLength("Ukraine", 30), "UA");
		countryCodeValue.put(fixAllowTextLength("Uruguay", 30), "UY");
		countryCodeValue.put(fixAllowTextLength("Uzbekistan", 30), "UZ");
		countryCodeValue.put(fixAllowTextLength("Vanuatu", 30), "VU");
		countryCodeValue.put(fixAllowTextLength("Venezuela", 30), "VE");
		countryCodeValue.put(fixAllowTextLength("VietNam", 30), "VN");
		countryCodeValue.put(fixAllowTextLength("Virgin Islands, British", 30), "VG");
		countryCodeValue.put(fixAllowTextLength("Virgin Islands, U.S.", 30), "VI");
		countryCodeValue.put(fixAllowTextLength("Wallis And Futuna", 30), "WF");
		countryCodeValue.put(fixAllowTextLength("Western Sahara", 30), "EH");
		countryCodeValue.put(fixAllowTextLength("Yemen", 30), "YE");
		countryCodeValue.put(fixAllowTextLength("Zambia", 30), "ZM");
		countryCodeValue.put(fixAllowTextLength("Zimbabwe", 30), "ZW");
	} // fillCountryCodeValueMap

	/**
	 * This method formats the text length.
	 * 
	 * @param text
	 *            The input test.
	 * @param maxLength
	 * @return
	 */
	private static String fixAllowTextLength(String text, int maxLength) {
		if (text.length() > maxLength) {
			text = text.substring(0, maxLength - 2) + IPDFFormatConstants.SUFFIX_TEXT_LENGTH;
		}
		return text;
	} // fixAllowTextLength

	/**
	 * 
	 * @param type
	 * @return
	 */
	public static String getDocumentIdentifierName(String type) {
		String result;
		switch (type) {
		case "0":
			result = "VAT";
			break;
		case "1":
			result = "DNI";
			break;
		case "4":
			result = "C. DE EXTRANJERIA";
			break;
		case "7":
			result = "PASAPORTE";
			break;
		case "8":
			result = "DIPLOMATICA DE I.";
			break;
		case "6":
			result = "RUC";
			break;
		case "A":
			result = "DIPLOMATICA DE I.";
			break;
		default:
			result = "DOCUMENTO";
			break;
		}
		return result;
	}

	public static int countCaracteres(String text, int max) {
		int result = (int) Math.round((text.length() / max) + 0.5);
		result = result + text.split("%5D").length - 1;
		return result;
	}

} // PDFFormatUtil
