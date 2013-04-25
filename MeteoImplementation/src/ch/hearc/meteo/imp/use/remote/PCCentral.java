
package ch.hearc.meteo.imp.use.remote;

import java.rmi.RemoteException;

import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurFactory;
import ch.hearc.meteo.spec.reseau.AfficheurManagerFactory;
import ch.hearc.meteo.spec.reseau.AfficheurManager_I;


public class PCCentral implements PC_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public PCCentral(AffichageOptions affichageOptions)
		{
		this.affichageOptions = affichageOptions;
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
			AfficheurFactory.create(affichageOptions, null);
			}
		catch (RemoteException e)
			{
			e.printStackTrace();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private AffichageOptions affichageOptions;
	}
