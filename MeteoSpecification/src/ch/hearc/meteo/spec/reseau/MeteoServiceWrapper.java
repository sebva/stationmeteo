
package ch.hearc.meteo.spec.reseau;

import java.rmi.RemoteException;

import ch.hearc.meteo.spec.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.meteo.MeteoService_I;

public class MeteoServiceWrapper implements MeteoServiceWrapper_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public MeteoServiceWrapper(MeteoService_I meteoService)
		{
		this.meteoService = meteoService;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override public void start(MeteoServiceOptions meteoServiceOptions) throws RemoteException
		{
		meteoService.start(meteoServiceOptions);
		}

	@Override public void stop() throws RemoteException
		{
		meteoService.stop();
		}

	@Override public boolean isRunning() throws RemoteException
		{
		return meteoService.isRunning();
		}

	@Override public boolean isConnect() throws RemoteException
		{
		return meteoService.isConnect();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private MeteoService_I meteoService;
	}
