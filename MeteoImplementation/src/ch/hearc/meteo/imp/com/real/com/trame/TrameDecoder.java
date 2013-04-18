
package ch.hearc.meteo.imp.com.real.com.trame;

import ch.hearc.meteo.spec.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.meteo.listener.event.MeteoEventType_E;

public class TrameDecoder
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	/**
	 * <pre>
	 *
	 * Inputs : Une trame du type : 00 0X YY ZZ TT UU VV 00 0D 0A
	 *
	 * Outputs : valeur du float coder dans les octest YY ZZ UU VV
	 *
	 * Note :
	 *
	 * 		(N1) Le reader a ete instantie de la maniere suivante :
	 *  					bufferedReader = new BufferedReader(new InputStreamReader(port.getInputStream()));
	 *
	 * 		(N2) La lecture du flux doit s'employer de la maniere suivante :
	 * 				String trame=bufferedReader.readLine();
	 * 				float valeurReponse = TrameDecoder.valeur(trame);
	 *
	 * </pre>
	 */
	public static float valeur(String trame) throws MeteoServiceException
		{
		try
			{
			byte[] tabByte = trame.getBytes();
			int valueInt = ((0xff & tabByte[6]) | ((0xff & tabByte[5]) << 8) | ((0xff & tabByte[3]) << 16) | ((0xff & tabByte[2]) << 24));

			float valeur = Float.intBitsToFloat(valueInt);

			return valeur;
			}
		catch (Exception e)
			{
			throw new MeteoServiceException("Trame invalide : value format : " + trame);
			}
		}

	/**
	 * <pre>
	 * see TrameDecoder.valeur()
	 * </pre>
	 */
	public static MeteoEventType_E dataType(String trame) throws MeteoServiceException
		{
		byte[] tabByte = trame.getBytes();
		int bitDataType = (0xff & tabByte[1]);

		if (bitDataType == 00)
			{
			return MeteoEventType_E.PRESSION;
			}
		else if (bitDataType == 01)
			{
			return MeteoEventType_E.TEMPERATURE;
			}
		else if (bitDataType == 02)
			{
			return MeteoEventType_E.ALTITUDE;
			}
		else
			{
			throw new MeteoServiceException("Trame invalide : DataType unknown : " + bitDataType);
			}
		}
	}
