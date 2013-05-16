
package ch.hearc.meteo.spec.afficheur;

import ch.hearc.meteo.imp.afficheur.simulateur.AfficheurServiceSimulateur;
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
	public static AfficheurService_I create(AffichageOptions affichageOptions, MeteoServiceWrapper_I meteoServiceRemote)
		{
		//return null; // TODO

		// Provisoire
		return new AfficheurServiceSimulateur(affichageOptions, meteoServiceRemote);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}
