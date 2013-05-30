
package ch.hearc.meteo.imp.use.remote;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurFactory;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.meteo.MeteoServiceFactory;
import ch.hearc.meteo.spec.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.meteo.MeteoService_I;
import ch.hearc.meteo.spec.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.meteo.listener.MeteoListener_I;
import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.AfficheurManager_I;
import ch.hearc.meteo.spec.reseau.AfficheurServiceWrapper_I;
import ch.hearc.meteo.spec.reseau.MeteoServiceWrapper;
import ch.hearc.meteo.spec.reseau.MeteoServiceWrapper_I;

import com.bilat.tools.reseau.rmi.IdTools;
import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

public class PCLocal implements PC_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public PCLocal(MeteoServiceOptions meteoServiceOptions, AffichageOptions affichageOptions, RmiURL rmiURLafficheurManager)
		{
		this.meteoServiceOptions = meteoServiceOptions;
		this.affichageOptions = affichageOptions;
		this.rmiURLafficheurManager = rmiURLafficheurManager;
		this.lostConnection = false;

		meteoServiceWrappers = new ArrayList<MeteoServiceWrapper_I>();
		afficheurServiceWrappers = new ArrayList<AfficheurServiceWrapper_I>();
		meteoServices = new ArrayList<MeteoService_I>();
		rmiURLs = new ArrayList<RmiURL>();
		portComs = new ArrayList<>();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void run()
		{
		try
			{
			//Remote
			afficheurManagerRemote = (AfficheurManager_I)RmiTools.connectionRemoteObjectBloquant(rmiURLafficheurManager);
			afficheurService = AfficheurFactory.create(affichageOptions, null, this);

			}
		catch (RemoteException e)
			{
			e.printStackTrace();
			}
		}

	public void addStation(String portCom)
		{
		if (!portComs.contains(portCom)) { return; }
		portComs.add(portCom);
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
			client(); // après
			}
		catch (RemoteException e)
			{
			System.err.println("[PCLocal :  run : client : failed");
			e.printStackTrace();
			}
		}

	public void removePortCom(String portCom)
		{
		try
			{
			int index = portComs.indexOf(portCom);
			meteoServices.get(index).disconnect();
			}
		catch (MeteoServiceException e)
			{
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
			MeteoService_I meteoService = MeteoServiceFactory.create(portComs.get(portComs.size() - 1));
			MeteoServiceWrapper meteoServiceWrapper = new MeteoServiceWrapper(meteoService);
			RmiURL rmiURL = new RmiURL(IdTools.createID(PREFIX));

			meteoServices.add(meteoService);
			meteoServiceWrappers.add(meteoServiceWrapper);
			rmiURLs.add(rmiURL);

			RmiTools.shareObject(meteoServiceWrapper, rmiURL);
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
			afficheurService = AfficheurFactory.create(affichageOptions, meteoServiceWrappers.get(meteoServiceWrappers.size() - 1), this);

			RmiURL rmiURLafficheurServiceWrapper = afficheurManagerRemote.createRemoteAfficheurService(affichageOptions, rmiURLs.get(rmiURLs.size() - 1));
			AfficheurServiceWrapper_I afficheurServiceWrapper = (AfficheurServiceWrapper_I)RmiTools.connectionRemoteObjectBloquant(rmiURLafficheurServiceWrapper);
			afficheurServiceWrappers.add(afficheurServiceWrapper);

			MeteoService_I meteoService = meteoServices.get(meteoServices.size() - 1);
			meteoService.addMeteoListener(new MeteoListener_I()
				{

					@Override
					public void temperaturePerformed(MeteoEvent event)
						{
						try
							{
							if (!lostConnection)
								{
								afficheurService.printTemperature(event);
								for(AfficheurServiceWrapper_I afficheurServiceWrapper:afficheurServiceWrappers)
									{
									afficheurServiceWrapper.printTemperature(event);
									}
								}
							}
						catch (RemoteException e)
							{
							gestionErreur();
							}
						}

					@Override
					public void pressionPerformed(MeteoEvent event)
						{
						try
							{
							if (!lostConnection)
								{
								afficheurService.printPression(event);
								for(AfficheurServiceWrapper_I afficheurServiceWrapper:afficheurServiceWrappers)
									{
									afficheurServiceWrapper.printPression(event);
									}
								}
							}
						catch (RemoteException e)
							{
							gestionErreur();
							}
						}

					@Override
					public void altitudePerformed(MeteoEvent event)
						{
						try
							{
							if (!lostConnection)
								{
								afficheurService.printAltitude(event);
								for(AfficheurServiceWrapper_I afficheurServiceWrapper:afficheurServiceWrappers)
									{
									afficheurServiceWrapper.printAltitude(event);
									}
								}
							}
						catch (RemoteException e)
							{
							gestionErreur();
							}
						}
				});

			meteoService.connect();
			meteoService.start(meteoServiceOptions);
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
		}

	private void gestionErreur()
		{
		lostConnection = true;
		System.err.println("Connexion perdue. Veuillez relancer une instance !");
		JOptionPane.showMessageDialog(null, "Connexion perdue. Vérifier l'état du serveur puis relancez le programme ! Appuyer sur OK pour quitter.", "Connexion perdue", JOptionPane.ERROR_MESSAGE);
		System.exit(-1);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private MeteoServiceOptions meteoServiceOptions;
	private AffichageOptions affichageOptions;
	private RmiURL rmiURLafficheurManager;

	// Tools
	private List<MeteoServiceWrapper_I> meteoServiceWrappers;
	private List<String> portComs;
	private List<RmiURL> rmiURLs;
	private List<AfficheurServiceWrapper_I> afficheurServiceWrappers;
	private List<MeteoService_I> meteoServices;
	private AfficheurManager_I afficheurManagerRemote;

	private AfficheurService_I afficheurService;
	private boolean lostConnection;
	private final static String PREFIX = "WRAPPER";
	}
