
package ch.hearc.meteo.imp.use.remote;

import javax.swing.UIManager;

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

		try
			{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}

		String titre = "Serveur";
		int n = 10;
		System.setProperty("sun.rmi.activation.execTimeout", "1000");
		AffichageOptions affichageOptions = new AffichageOptions(n, titre);

		new PCCentral(affichageOptions).run();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}
