
package ch.hearc.meteo.spec.meteo;

import ch.hearc.meteo.imp.com.real.MeteoPortDetectionService;


public class MeteoPortDetectionServiceFactory
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

	public static MeteoPortDetectionService_I create()
		{
		return new MeteoPortDetectionService();

		// Provisoire
		//return new MeteoPortDetectionServiceSimulateur();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	}
