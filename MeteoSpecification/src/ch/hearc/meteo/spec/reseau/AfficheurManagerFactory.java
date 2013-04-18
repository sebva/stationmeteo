
package ch.hearc.meteo.spec.reseau;

import java.rmi.RemoteException;

import ch.hearc.meteo.imp.reseau.AfficheurManager;

public class AfficheurManagerFactory
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static AfficheurManager_I create() throws RemoteException
		{
		return  AfficheurManager.getInstance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/




	}
