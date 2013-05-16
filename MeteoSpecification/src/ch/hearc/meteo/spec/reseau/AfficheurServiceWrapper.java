
package ch.hearc.meteo.spec.reseau;

import java.rmi.RemoteException;

import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;

public class AfficheurServiceWrapper implements AfficheurServiceWrapper_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public AfficheurServiceWrapper(AfficheurService_I afficheurService)
		{
		this.afficheurService = afficheurService;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override public void printPression(MeteoEvent event) throws RemoteException
		{
		afficheurService.printPression(event);
		}

	@Override public void printAltitude(MeteoEvent event) throws RemoteException
		{
		afficheurService.printAltitude(event);
		}

	@Override public void printTemperature(MeteoEvent event) throws RemoteException
		{
		afficheurService.printTemperature(event);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private AfficheurService_I afficheurService;

	@Override
	public void setMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		afficheurService.setMeteoServiceOptions(meteoServiceOptions);
		}
	}
