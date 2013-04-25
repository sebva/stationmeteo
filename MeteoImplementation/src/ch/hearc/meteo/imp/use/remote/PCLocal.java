
package ch.hearc.meteo.imp.use.remote;

import java.rmi.RemoteException;

import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.meteo.MeteoServiceFactory;
import ch.hearc.meteo.spec.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.meteo.MeteoService_I;
import ch.hearc.meteo.spec.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.meteo.listener.MeteoListener_I;
import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.AfficheurManagerFactory;
import ch.hearc.meteo.spec.reseau.AfficheurManager_I;
import ch.hearc.meteo.spec.reseau.MeteoServiceWrapper;

import com.bilat.tools.reseau.rmi.IdTools;
import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

public class PCLocal implements PC_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public PCLocal(MeteoServiceOptions meteoServiceOptions, String portCom, AffichageOptions affichageOptions, RmiURL rmiURLafficheurManager)
		{
		this.meteoServiceOptions = meteoServiceOptions;
		this.portCom = portCom;
		this.affichageOptions = affichageOptions;
		this.rmiURLafficheurManager = rmiURLafficheurManager;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void run()
		{
		try
			{
			server(); // avant
			}
		catch (Exception e)
			{
			System.err.println("[PCLocal :  run : server : failed");
			e.printStackTrace();
			}

		try
			{
			client(); // aprüs
			}
		catch (RemoteException e)
			{
			System.err.println("[PCLocal :  run : client : failed");
			e.printStackTrace();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				server			*|
	\*------------------------------*/

	private void server() throws MeteoServiceException, RemoteException
		{
		try
			{
			meteoService = MeteoServiceFactory.create(portCom);
			MeteoServiceWrapper meteoServiceWrapper = new MeteoServiceWrapper(meteoService);
			RmiTools.shareObject(meteoServiceWrapper, RMI_URL);
			RmiTools.connectionRemoteObjectBloquant(rmiURLafficheurManager);
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
		}

	/*------------------------------*\
	|*				client			*|
	\*------------------------------*/

	private void client() throws RemoteException
		{
		try
			{
			//Local
			AfficheurManager_I afficheurManager = AfficheurManagerFactory.create();
			RmiURL rmiUrlAfficheurManagerLocal = afficheurManager.createRemoteAfficheurService(affichageOptions, RMI_URL);
			final AfficheurService_I afficheurService = (AfficheurService_I)RmiTools.connectionRemoteObjectBloquant(rmiUrlAfficheurManagerLocal);

			//Remote
			AfficheurManager_I afficheurManagerRemote = (AfficheurManager_I)RmiTools.connectionRemoteObjectBloquant(rmiURLafficheurManager);
			RmiURL rmiUrlAfficheurManagerRemote = afficheurManagerRemote.createRemoteAfficheurService(affichageOptions, RMI_URL);
			final AfficheurService_I afficheurServiceRemote = (AfficheurService_I)RmiTools.connectionRemoteObjectBloquant(rmiUrlAfficheurManagerRemote);

			meteoService.connect();

			meteoService.addMeteoListener(new MeteoListener_I()
				{

					@Override
					public void temperaturePerformed(MeteoEvent event)
						{
						try
							{
							afficheurService.printTemperature(event);
							afficheurServiceRemote.printTemperature(event);
							}
						catch (RemoteException e)
							{
							e.printStackTrace();
							}
						}

					@Override
					public void pressionPerformed(MeteoEvent event)
						{
						try
							{
							afficheurService.printPression(event);
							afficheurServiceRemote.printPression(event);
							}
						catch (RemoteException e)
							{
							e.printStackTrace();
							}
						}

					@Override
					public void altitudePerformed(MeteoEvent event)
						{
						try
							{
							afficheurService.printAltitude(event);
							afficheurServiceRemote.printAltitude(event);
							}
						catch (RemoteException e)
							{
							e.printStackTrace();
							}
						}
				});

			meteoService.start(meteoServiceOptions);
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private MeteoServiceOptions meteoServiceOptions;
	private String portCom;
	private AffichageOptions affichageOptions;
	private RmiURL rmiURLafficheurManager;

	// Tools
	private MeteoService_I meteoService;
	private final static String PREFIX = "WRAPPER_";
	private final static RmiURL RMI_URL = new RmiURL(IdTools.createID(PREFIX));
	}
