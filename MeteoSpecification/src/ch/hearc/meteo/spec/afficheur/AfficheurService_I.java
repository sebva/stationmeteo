
package ch.hearc.meteo.spec.afficheur;

import ch.hearc.meteo.spec.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;

public interface AfficheurService_I
	{

	public void printPression(MeteoEvent event);

	public void printAltitude(MeteoEvent event);

	public void printTemperature(MeteoEvent event);

	public void setMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions);
	}
