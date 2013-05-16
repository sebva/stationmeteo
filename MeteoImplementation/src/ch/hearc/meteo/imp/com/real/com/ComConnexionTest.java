
package ch.hearc.meteo.imp.com.real.com;

import ch.hearc.meteo.imp.com.logique.MeteoServiceCallback_I;

public class ComConnexionTest
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args)
		{
		try
			{
			main();
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
		}

	public static void main() throws Exception
		{
		ComConnexion com = new ComConnexion(new MeteoServiceCallback_I()
			{

				@Override
				public void temperaturePerformed(float value)
					{
					System.out.println("Température : " + value);
					}

				@Override
				public void pressionPerformed(float value)
					{
					System.out.println("Pression : " + value);
					}

				@Override
				public void altitudePerformed(float value)
					{
					System.out.println("Altitude : " + value);
					}
			}, "COM4", new ComOption());

		com.connect();
		com.start();
		com.askAltitudeAsync();
		com.askPressionAsync();
		com.askTemperatureAsync();
		Thread.sleep(700);

		com.stop();
		com.disconnect();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}
