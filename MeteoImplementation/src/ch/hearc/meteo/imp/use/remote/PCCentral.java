
package ch.hearc.meteo.imp.use.remote;

import java.rmi.RemoteException;

import ch.hearc.meteo.spec.reseau.AfficheurManagerFactory;
import ch.hearc.meteo.spec.reseau.AfficheurManager_I;


public class PCCentral implements PC_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public PCCentral()
		{
		// rien
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override public void run()
		{
		server();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void server()
		{
		try
			{
			AfficheurManager_I afficheurManager = AfficheurManagerFactory.create();
			}
		catch (RemoteException e)
			{
			e.printStackTrace();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	}
