
package ch.hearc.meteo.spec.meteo;

import java.util.List;

public interface MeteoPortDetectionService_I
	{
	
	/**
	 * see public List<String> findPortNameMeteo(List<String> listPortExcluded);
	 */
	public List<String> findPortNameMeteo();
	
	/**
	 * Return la liste des ports surlesquels sont branchés une station météo, except listPortExcluded
	 * Contraintes :
	 * 		(C1) Doit refermer les ports!
	 * 		(C2) Doit être safe (javacomm->enumartion port serie -> list<String> listPortCandidat -> listPortCanidat.removeAll(listPortExcluded) -> tester sur cette liste si station meteo avec isStationMeteo
	 *
	 */
	public List<String> findPortNameMeteo(List<String> listPortExcluded);
	
	/**
	 * Retourne la liste de tous les ports séries
	 */
	public List<String> findPortSerie();
	
	/**
	 * Return true si station meteo est connecté au portName
	 * Return false si pas sation meteo connecté au PortName, ou si StationMeteo connecte mais deja en utilisation
	 */
	public boolean isStationMeteoAvailable(String portName);
	}
