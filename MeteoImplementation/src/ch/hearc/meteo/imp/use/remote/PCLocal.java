
package ch.hearc.meteo.imp.use.remote;

import java.rmi.RemoteException;

import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.meteo.exception.MeteoServiceException;

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

	@Override public void run()
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
		// TODO Auto-generated method stub

		}

	/*------------------------------*\
	|*				client			*|
	\*------------------------------*/

	private void client() throws RemoteException
		{
		// TODO Auto-generated method stub

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
	}
