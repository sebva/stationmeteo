
package ch.hearc.meteo.spec.meteo;

import ch.hearc.meteo.spec.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.meteo.listener.MeteoListener_I;

public interface MeteoService_I
	{

	public void connect() throws MeteoServiceException;

	public void disconnect() throws MeteoServiceException;

	/**
	 * DT = Delta Time in ms
	 */
	public void start(MeteoServiceOptions meteoServiceOptions);

	public void stop();

	public boolean isRunning();

	public boolean isConnect();

	public String getPort();

	public boolean addMeteoListener(MeteoListener_I listener);

	public boolean removeMeteoListener(MeteoListener_I listener);
	}
