
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
		String titre = "Serveur";
		int n = 10;

		AffichageOptions affichageOptions = new AffichageOptions(n, titre);
		new PCCentral(affichageOptions).run();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}
