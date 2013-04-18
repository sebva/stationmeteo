
package ch.hearc.meteo.imp.use.local;

import ch.hearc.meteo.spec.meteo.MeteoServiceFactory;
import ch.hearc.meteo.spec.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.meteo.MeteoService_I;
import ch.hearc.meteo.spec.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.meteo.listener.MeteoListener_I;
import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;

public class UseMeteoServiceAffichageConsole
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
		catch (MeteoServiceException e)
			{
			e.printStackTrace();
			}
		}

	public static void main() throws MeteoServiceException
		{
		MeteoService_I meteoService = MeteoServiceFactory.create("COM1");

		meteoService.connect();

		meteoService.addMeteoListener(new MeteoListener_I()
			{

				@Override public void temperaturePerformed(MeteoEvent event)
					{
					System.out.println(event);
					}

				@Override public void pressionPerformed(MeteoEvent event)
					{
					System.out.println(event);
					}

				@Override public void altitudePerformed(MeteoEvent event)
					{
					System.out.println(event);
					}
			});

		scenario(meteoService);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/**
	 * <pre>
	 * Exemple pour mettre à l'épreuve la logique de fonctionnement
	 * Checker dans la console que la logique verbeuse aficher est correcte
	 * </pre>
	 */
	private static void scenario(MeteoService_I meteoService) throws MeteoServiceException
		{
		MeteoServiceOptions meteoServiceOptions1 = new MeteoServiceOptions(800, 1000, 1200);
		MeteoServiceOptions meteoServiceOptions2 = new MeteoServiceOptions(100, 100, 100);

		for(int i = 1; i <= 2; i++)
			{
			meteoService.start(meteoServiceOptions1);
			meteoService.start(meteoServiceOptions2);
			sleep(3000);
			meteoService.stop();
			sleep(3000);
			}

		meteoService.disconnect();
		sleep(100);
		meteoService.start(meteoServiceOptions2);
		}

	private static void sleep(long delayMS)
		{
		System.out.println("sleep main: " + delayMS);
		try
			{
			Thread.sleep(delayMS);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		}

	}
