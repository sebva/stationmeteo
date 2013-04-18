
package ch.hearc.meteo.imp.com.simulateur;

import ch.hearc.meteo.imp.com.logique.MeteoService_A;
import ch.hearc.meteo.imp.com.simulateur.fonction.atome.FonctionAltitude;
import ch.hearc.meteo.imp.com.simulateur.fonction.atome.FonctionPression;
import ch.hearc.meteo.imp.com.simulateur.fonction.atome.FonctionTemperature;
import ch.hearc.meteo.spec.meteo.exception.MeteoServiceException;

public class MeteoServiceSimulateur extends MeteoService_A
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public MeteoServiceSimulateur(String namePort)
		{
		super(namePort);

		fonctionAltitude = new FonctionAltitude();
		fonctionPression = new FonctionPression();
		fonctionTemperature = new FonctionTemperature();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override protected void askAltitudeAsync()
		{
		altitudePerformed(fonctionAltitude.value());
		}

	@Override protected void askPressionAsync()
		{
		pressionPerformed(fonctionPression.value());
		}

	@Override protected void askTemperatureAsync()
		{
		temperaturePerformed(fonctionTemperature.value());
		}

	@Override protected void connectHardware() throws MeteoServiceException
		{
		// rien
		}

	@Override protected void disconnectHardware() throws MeteoServiceException
		{
		// rien
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private FonctionAltitude fonctionAltitude;
	private FonctionPression fonctionPression;
	private FonctionTemperature fonctionTemperature;

	}
