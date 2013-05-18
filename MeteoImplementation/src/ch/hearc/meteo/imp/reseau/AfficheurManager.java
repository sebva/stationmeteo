
package ch.hearc.meteo.imp.reseau;

import java.rmi.RemoteException;

import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurFactory;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.reseau.AfficheurManager_I;
import ch.hearc.meteo.spec.reseau.AfficheurServiceWrapper;
import ch.hearc.meteo.spec.reseau.MeteoServiceWrapper_I;

import com.bilat.tools.reseau.rmi.IdTools;
import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

/**
 * <pre>
 * One instance only (Singleton) on PC-Central
 * RMI-Shared
 * </pre>
 */
public class AfficheurManager implements AfficheurManager_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	private AfficheurManager() throws RemoteException
		{
		server();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * Remote use
	 */
	@Override
	public RmiURL createRemoteAfficheurService(AffichageOptions affichageOptions, RmiURL meteoServiceRmiURL) throws RemoteException
		{
		try
			{
			// client
			MeteoServiceWrapper_I meteoServiceWrapperRemote = null;
			meteoServiceWrapperRemote = (MeteoServiceWrapper_I)RmiTools.connectionRemoteObjectBloquant(meteoServiceRmiURL);

			// server
			AfficheurService_I afficheurService = createAfficheurService(affichageOptions, meteoServiceWrapperRemote);
			AfficheurServiceWrapper afficheurServiceWrapper = new AfficheurServiceWrapper(afficheurService);
			RmiURL afficheurServicermiURL = rmiUrl();
			RmiTools.shareObject(afficheurServiceWrapper, afficheurServicermiURL);

			return afficheurServicermiURL; // Retourner le RMI-ID pour une connection distante sur le serveur d'affichage
			}
		catch (Exception e)
			{
			e.printStackTrace();
			return null;
			}
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static synchronized AfficheurManager_I getInstance() throws RemoteException
		{
		if (INSTANCE == null)
			{
			INSTANCE = new AfficheurManager();
			}

		return INSTANCE;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private AfficheurService_I createAfficheurService(AffichageOptions affichageOptions, MeteoServiceWrapper_I meteoServiceRemote)
		{
		return AfficheurFactory.create(affichageOptions, meteoServiceRemote);
		}

	private void server() throws RemoteException
		{
		RmiTools.shareObject(this, new RmiURL(PREFIXE));
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	/**
	 * Thread Safe
	 */
	private static RmiURL rmiUrl()
		{
		String id = IdTools.createID(PREFIXE);

		return new RmiURL(id);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	// Tools

	private static AfficheurManager_I INSTANCE = null;

	// Tools final
	private static final String PREFIXE = "AFFICHEUR_SERVICE";

	public static final String RMI_ID = PREFIXE;
	}
