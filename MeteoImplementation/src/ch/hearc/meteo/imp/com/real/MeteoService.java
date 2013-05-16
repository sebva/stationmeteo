
package ch.hearc.meteo.imp.com.real;

import ch.hearc.meteo.imp.com.logique.MeteoService_A;
import ch.hearc.meteo.imp.com.real.com.ComConnexion;
import ch.hearc.meteo.imp.com.real.com.ComConnexions_I;
import ch.hearc.meteo.imp.com.real.com.ComOption;
import ch.hearc.meteo.spec.meteo.exception.MeteoServiceException;

/**
 * <pre>
 * Repousse les requêtes sur comConnexion et transforme les Exceptions en MeteoServiceException
 * Aucune trace de javacomm ici, toute cette problematique est encapsuler dans l'objet implementant ComConnexions_I (separation des couches)
 * </pre>
 */
public class MeteoService extends MeteoService_A
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public MeteoService(String portName)
		{
		super(portName);

		this.comConnexion = new ComConnexion(this, portName, new ComOption());
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * assynchrone, when data "value" received , must call altitudePerformed(value) of MeteoServiceCallback_I
	 */
	@Override protected void askAltitudeAsync() throws MeteoServiceException
		{
		try
			{
			comConnexion.askAltitudeAsync();
			}
		catch (Exception e)
			{
			throw new MeteoServiceException("[MeteoService] askAltitudeAsync failure", e);
			}
		}

	/**
	 * assynchrone, when data "value" received , must call pressionPerformed(value) of MeteoServiceCallback_I
	 */
	@Override protected void askPressionAsync() throws MeteoServiceException
		{
		try
			{
			comConnexion.askPressionAsync();
			}
		catch (Exception e)
			{
			throw new MeteoServiceException("[MeteoService] : askPressionAsync failure", e);
			}
		}

	/**
	 * assynchrone, when data "value" received , must call temperaturePerformed(value) of MeteoServiceCallback_I
	 */
	@Override protected void askTemperatureAsync() throws MeteoServiceException
		{
		try
			{
			comConnexion.askTemperatureAsync();
			}
		catch (Exception e)
			{
			throw new MeteoServiceException("[MeteoService] : askTemperatureAsync failure", e);
			}
		}

	@Override protected void connectHardware() throws MeteoServiceException
		{
		try
			{
			comConnexion.connect();
			}
		catch (Exception e)
			{
			throw new MeteoServiceException("[MeteoService] : connect failure", e);
			}

		}

	@Override protected void disconnectHardware() throws MeteoServiceException
		{
		try
			{
			comConnexion.disconnect();
			}
		catch (Exception e)
			{
			throw new MeteoServiceException("[MeteoService] : disconnect failure", e);
			}

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private ComConnexions_I comConnexion;

	}
