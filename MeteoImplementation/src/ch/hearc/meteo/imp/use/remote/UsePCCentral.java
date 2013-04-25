
package ch.hearc.meteo.imp.use.remote;

import ch.hearc.meteo.spec.afficheur.AffichageOptions;

public class UsePCCentral
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args)
		{
		main();
		}

	public static void main()
		{
		AffichageOptions affichageOptions = new AffichageOptions(10, "Serveur");
		new PCCentral(affichageOptions).run();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}
