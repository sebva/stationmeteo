
package ch.hearc.meteo.imp.use.remote;

import java.rmi.RemoteException;
import java.util.LinkedList;
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

		portComs = new LinkedList<String>();
		meteoServices = new LinkedList<MeteoService_I>();
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
			//Local
			AfficheurService_I afficheurService = AfficheurFactory.create(affichageOptions, null, this);
			}
		catch (RemoteException e)
			{
			e.printStackTrace();
			}
		}

	public void addStation(String portCom)
		{
		if (!portComs.contains(portCom))
			{
			portComs.add(portCom);

			try
				{
				//SERVER
				MeteoService_I meteoService = MeteoServiceFactory.create(portCom);
				meteoServices.add(meteoService);
				MeteoServiceWrapper meteoServiceWrapper = new MeteoServiceWrapper(meteoService);
				RmiURL rmiURL = new RmiURL(IdTools.createID(PREFIX));

				RmiTools.shareObject(meteoServiceWrapper, rmiURL);

				//CLIENT

				RmiURL rmiURLafficheurServiceWrapper = afficheurManagerRemote.createRemoteAfficheurService(affichageOptions, rmiURL);
				final AfficheurServiceWrapper_I afficheurServiceWrapper = (AfficheurServiceWrapper_I)RmiTools.connectionRemoteObjectBloquant(rmiURLafficheurServiceWrapper);
				//Local
				final AfficheurService_I afficheurService = AfficheurFactory.create(affichageOptions, meteoServiceWrapper, this);

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
									afficheurServiceWrapper.printTemperature(event);
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
									afficheurServiceWrapper.printPression(event);
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
									afficheurServiceWrapper.printAltitude(event);
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
		}

	public void removePortCom(String portCom)
		{
		if (portComs.contains(portCom))
			{
			int index = portComs.indexOf(portCom);
			portComs.remove(index);
			try
				{
				meteoServices.remove(index).disconnect();
				}
			catch (MeteoServiceException e)
				{
				e.printStackTrace();
				}
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void gestionErreur()
		{
		synchronized (this)
			{
			lostConnection = true;
			System.err.println("Connexion perdue. Veuillez relancer une instance !");
			JOptionPane.showMessageDialog(null, "Connexion perdue. Vérifier l'état du serveur puis relancez le programme ! Appuyer sur OK pour quitter.", "Connexion perdue", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private MeteoServiceOptions meteoServiceOptions;
	private AffichageOptions affichageOptions;
	private RmiURL rmiURLafficheurManager;

	// Tools
	private AfficheurManager_I afficheurManagerRemote;
	private List<String> portComs;
	private List<MeteoService_I> meteoServices;

	private boolean lostConnection;
	private final static String PREFIX = "WRAPPER";
	}
