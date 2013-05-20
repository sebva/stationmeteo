
package ch.hearc.meteo.imp.afficheur.real.moo;

public class Pressure
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Pressure(AfficheurServiceMOO afficheurServiceMOO)
		{
		this.afficheurServiceMOO = afficheurServiceMOO;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public float getPressure()
		{
		float currentPressureValue = (afficheurServiceMOO.getLastPression() == null) ? 0 : afficheurServiceMOO.getLastPression().getValue();
		return currentPressureValue;
		}

	public float getSeaLevelPressure()
		{
		float currentPressureValue = (afficheurServiceMOO.getLastPression() == null) ? 0 : afficheurServiceMOO.getLastPression().getValue();
		float currentTemperatureValue = (afficheurServiceMOO.getLastTemperature() == null) ? 0 : afficheurServiceMOO.getLastTemperature().getValue();
		float currentAltitudeValue = (afficheurServiceMOO.getLastAltitude() == null) ? 0 : afficheurServiceMOO.getLastAltitude().getValue();

		return reducePressionToSeaLevel(currentPressureValue, currentAltitudeValue, currentTemperatureValue);
		}

	public float getMeanPressure()
		{
		return afficheurServiceMOO.getStatPression().getMoy();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/
	/**
	 * http://rosset.org/linux/pressure/howto6.html
	 */
	private float reducePressionToSeaLevel(final float pressure, final float altitude, final float temperature)
		{
		final float ACCELERATION_CONSTANT = 9.80665f;
		final float AIR_CONSTANT = 287.04f;
		float temperatureKelvin = temperature + 273.15f;

		return (float)(pressure * Math.pow(Math.E, (ACCELERATION_CONSTANT * altitude) / (AIR_CONSTANT * temperatureKelvin)));
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Tools
	private AfficheurServiceMOO afficheurServiceMOO;

	}
