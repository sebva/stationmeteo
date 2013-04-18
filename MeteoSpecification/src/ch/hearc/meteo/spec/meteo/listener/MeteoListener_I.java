
package ch.hearc.meteo.spec.meteo.listener;

import java.io.Serializable;
import java.rmi.Remote;

import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;

public interface MeteoListener_I extends Remote ,Serializable
	{

	public void altitudePerformed(MeteoEvent event);

	public void temperaturePerformed(MeteoEvent event);

	public void pressionPerformed(MeteoEvent event);

	}
