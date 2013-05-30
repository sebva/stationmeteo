
package ch.hearc.meteo.spec.afficheur;

import ch.hearc.meteo.imp.afficheur.real.AfficheurService;
import ch.hearc.meteo.imp.use.remote.PCLocal;
import ch.hearc.meteo.spec.reseau.MeteoServiceWrapper_I;

public class AfficheurFactory
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	/**
	 * n : #data to print/memorize
	 */
	public static AfficheurService_I create(AffichageOptions affichageOptions, MeteoServiceWrapper_I meteoServiceRemote, PCLocal pc)
		{
		return new AfficheurService(affichageOptions, meteoServiceRemote, pc);

		// Provisoire
		//return new AfficheurServiceSimulateur(affichageOptions, meteoServiceRemote);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}
