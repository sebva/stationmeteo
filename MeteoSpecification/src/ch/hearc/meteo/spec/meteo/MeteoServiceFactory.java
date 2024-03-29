
package ch.hearc.meteo.spec.meteo;

import ch.hearc.meteo.imp.com.real.MeteoService;
import ch.hearc.meteo.imp.com.simulateur.MeteoServiceSimulateur;

public class MeteoServiceFactory
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	/**
	 * <pre>
	 * Example:
	 * 		Windows : namePort=COM1
	 * 		Linux	: ??
	 * 		Mac 	: ??
	 * </pre>
	 */
	public static MeteoService_I create(String portName)
		{
		return new MeteoService(portName);

		// Provisoire
		//return new MeteoServiceSimulateur(portName);
		}
	
	public static MeteoService_I create(String portName, boolean isReal)
		{
		if(isReal)
			return new MeteoService(portName);
		else
			return new MeteoServiceSimulateur(portName);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	}
