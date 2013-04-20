
package ch.hearc.meteo.imp.use.remote;

import ch.hearc.meteo.imp.reseau.AfficheurManager;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.meteo.MeteoServiceOptions;

import com.bilat.tools.reseau.rmi.RmiURL;

public class UsePCLocal
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
		String portCom = new String("COM1");
		int rand = (int)(Math.random()*100);
		MeteoServiceOptions meteoServiceOptions = new MeteoServiceOptions(100+rand,200+rand,300+rand,400+rand);
		AffichageOptions affichageOptions = new AffichageOptions(30, "test - " + String.valueOf(rand));
		new PCLocal(meteoServiceOptions, portCom, affichageOptions,new RmiURL(AfficheurManager.RMI_ID)).run();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}
