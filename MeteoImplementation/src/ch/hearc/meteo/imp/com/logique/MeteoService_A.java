
package ch.hearc.meteo.imp.com.logique;

import java.util.LinkedList;
import java.util.List;

import ch.hearc.meteo.spec.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.meteo.MeteoService_I;
import ch.hearc.meteo.spec.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.meteo.listener.MeteoListener_I;
import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.meteo.listener.event.MeteoEventType_E;
import ch.hearc.meteo.spec.meteo.listener.event.Sources;

/**
 * Couche logique
 */
public abstract class MeteoService_A implements MeteoService_I ,MeteoServiceCallback_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public MeteoService_A(String namePort)
		{
		// Inputs
		this.namePort = namePort;

		// Tools
		listMeteoListener = new LinkedList<MeteoListener_I>();

		isRunning = false;
		isConnected = false;

		// Outputs
		this.source = new Sources(namePort);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	/**
	 * assynchrone, when data "value" received , must call altitudePerformed(value)
	 */
	protected abstract void askAltitudeAsync() throws MeteoServiceException;

	/**
	 * assynchrone, when data "value" received , must call pressionPerformed(value)
	 */
	protected abstract void askPressionAsync() throws MeteoServiceException;

	/**
	 * assynchrone, when data "value" received , must call temperaturePerformed(value)
	 */
	protected abstract void askTemperatureAsync() throws MeteoServiceException;

	protected abstract void connectHardware() throws MeteoServiceException;

	protected abstract void disconnectHardware() throws MeteoServiceException;

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void altitudePerformed(float value)
		{
		avertirAltitudeListener(meteoEvent(value, MeteoEventType_E.ALTITUDE));
		}

	@Override
	public void pressionPerformed(float value)
		{
		avertirPressionListener(meteoEvent(value, MeteoEventType_E.PRESSION));
		}

	@Override
	public void temperaturePerformed(float value)
		{
		avertirTemperatureListener(meteoEvent(value, MeteoEventType_E.TEMPERATURE));
		}

	/**
	 * Call before connect
	 */
	@Override
	synchronized public void start(MeteoServiceOptions meteoServiceOptions)
		{
		if (isConnected)
			{
			if (!isRunning)
				{
				System.out.println("MeteoService_A : Start");
				isRunning = true;
				this.meteoServiceOptions = meteoServiceOptions;
				run();
				}
			else
				{
				System.out.println("MeteoService_A : Already started");
				}
			}
		else
			{
			System.out.println("MeteoService_A : Not connected : impossible to start");
			}
		}

	@Override
	public String toString()
		{
		StringBuilder builder = new StringBuilder();
		builder.append("MeteoService_A (");
		builder.append(this.namePort);
		builder.append(")");
		return builder.toString();
		}

	@Override
	synchronized public void stop()
		{
		System.out.println("MeteoService_A : Stop");
		isRunning = false;
		}

	@Override
	synchronized public void connect() throws MeteoServiceException
		{
		if (!isConnected)
			{
			connectHardware();
			System.out.println("MeteoService_A : Connect");
			isConnected = true;
			}
		else
			{
			System.out.println("MeteoService_A : Already connected");
			}
		}

	@Override
	synchronized public void disconnect() throws MeteoServiceException
		{
		if (isConnected)
			{
			stop();
			disconnectHardware();
			System.out.println("MeteoService_A : Disconnect");
			isConnected = false;
			}

		}

	@Override
	synchronized public boolean addMeteoListener(MeteoListener_I listener)
		{
		return listMeteoListener.add(listener);
		}

	@Override
	synchronized public boolean removeMeteoListener(MeteoListener_I listener)
		{
		return listMeteoListener.remove(listener);
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@Override
	public String getPort()
		{
		return namePort;
		}

	@Override
	public MeteoServiceOptions getMeteoServiceOptions()
		{
		return meteoServiceOptions;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	@Override
	public void setMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		this.meteoServiceOptions = meteoServiceOptions;
		}

	/*------------------------------*\
	|*				Is				*|
	\*------------------------------*/

	@Override
	public synchronized boolean isRunning()
		{
		return isRunning;
		}

	@Override
	public synchronized boolean isConnect()
		{
		return isConnected;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private MeteoEvent meteoEvent(float value, MeteoEventType_E meteoEventType)
		{
		long time = System.currentTimeMillis();
		return new MeteoEvent(source, meteoEventType, value, time);
		}

	private void run()
		{
		Thread thread = new Thread(new Runnable()
			{

				@Override
				public void run()
					{
					long altitudeDT = meteoServiceOptions.getAltitudeDT();
					long pressionDT = meteoServiceOptions.getPressionDT();
					long temperatureDT = meteoServiceOptions.getTemperatureDT();

					TimeManager timeManagerAltitude = new TimeManager(altitudeDT);
					TimeManager timeManagerPression = new TimeManager(pressionDT);
					TimeManager timeManagerTemperature = new TimeManager(temperatureDT);

					isRunning = true;
					long delayEffectifMS = min(meteoServiceOptions.getDelayMS(), altitudeDT, pressionDT, temperatureDT);
					while(isRunning)
						{

						try
							{
							if (timeManagerTemperature.isTimeElapse())
								{
								askTemperatureAsync();
								temperatureDT = meteoServiceOptions.getTemperatureDT();
								timeManagerTemperature.setDt(temperatureDT);
								timeManagerTemperature.reset();
								}

							if (timeManagerAltitude.isTimeElapse())
								{
								askAltitudeAsync();
								altitudeDT = meteoServiceOptions.getAltitudeDT();
								timeManagerAltitude.setDt(altitudeDT);
								timeManagerAltitude.reset();
								}

							if (timeManagerPression.isTimeElapse())
								{
								askPressionAsync();
								pressionDT = meteoServiceOptions.getPressionDT();
								timeManagerPression.setDt(pressionDT);
								timeManagerPression.reset();
								}
							}
						catch (MeteoServiceException e)
							{
							e.printStackTrace();
							}

						sleep(delayEffectifMS);
						}

					}
			});
		thread.start();
		}

	private synchronized void avertirAltitudeListener(MeteoEvent event)
		{
		for(MeteoListener_I listener:listMeteoListener)
			{
			listener.altitudePerformed(event);
			}
		}

	private synchronized void avertirPressionListener(MeteoEvent event)
		{
		for(MeteoListener_I listener:listMeteoListener)
			{
			listener.pressionPerformed(event);
			}
		}

	private synchronized void avertirTemperatureListener(MeteoEvent event)
		{
		for(MeteoListener_I listener:listMeteoListener)
			{
			listener.temperaturePerformed(event);
			}
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static void sleep(long delayMS)
		{
		try
			{
			Thread.sleep(delayMS);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		}

	private static long min(long a, long b, long c, long d)
		{
		return Math.min(Math.min(Math.min(a, b), c), d);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private String namePort;
	private MeteoServiceOptions meteoServiceOptions;

	// Tools
	private List<MeteoListener_I> listMeteoListener;
	private boolean isRunning;

	private boolean isConnected;

	// Outputs
	private Sources source;

	}
