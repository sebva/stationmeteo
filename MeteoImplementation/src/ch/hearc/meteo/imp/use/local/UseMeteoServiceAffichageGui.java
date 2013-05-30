
package ch.hearc.meteo.imp.use.local;

import java.rmi.RemoteException;

import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurFactory;
import ch.hearc.meteo.spec.meteo.MeteoServiceFactory;
import ch.hearc.meteo.spec.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.meteo.MeteoService_I;
import ch.hearc.meteo.spec.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.meteo.listener.MeteoAdapter;
import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.AfficheurServiceWrapper_I;
import ch.hearc.meteo.spec.reseau.MeteoServiceWrapper;
import ch.hearc.meteo.spec.reseau.MeteoServiceWrapper_I;

import com.bilat.tools.reseau.rmi.RmiTools;

public class UseMeteoServiceAffichageGui
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args) throws MeteoServiceException
		{
		//main();
		}

	public static void main() throws MeteoServiceException
		{
		String portName = "COM4";
		String titre = RmiTools.getLocalHost() + " " + portName;

		MeteoService_I meteoService = MeteoServiceFactory.create(portName);

		MeteoServiceWrapper_I meteoServiceWrapperForRemoteAccess = new MeteoServiceWrapper(meteoService);

		AffichageOptions affichageOptions = new AffichageOptions(3, titre);
		final AfficheurServiceWrapper_I afficheurService = (AfficheurServiceWrapper_I)AfficheurFactory.create(affichageOptions, meteoServiceWrapperForRemoteAccess, null);

		meteoService.connect();

		// Liason entre les deux services d'affichage : MeteoService_I et AfficheurService_I
		meteoService.addMeteoListener(new MeteoAdapter()
			{

				@Override public void temperaturePerformed(MeteoEvent event)
					{
					try
						{
						afficheurService.printTemperature(event);
						afficheurService.printAltitude(event);
						afficheurService.printPression(event);
						}
					catch (RemoteException e)
						{
						e.printStackTrace();
						}
					}

			});

		MeteoServiceOptions meteoServiceOptions = new MeteoServiceOptions(800, 1000, 1200);
		meteoService.start(meteoServiceOptions);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}
