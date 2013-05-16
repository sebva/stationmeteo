
package ch.hearc.meteo.imp.com.real;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ch.hearc.meteo.imp.com.real.com.ComOption;
import ch.hearc.meteo.imp.com.real.com.ComPortDetection;
import ch.hearc.meteo.spec.meteo.MeteoPortDetectionService_I;



public class MeteoPortDetectionService implements MeteoPortDetectionService_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public MeteoPortDetectionService()
		{
		this.comPortDetection = new ComPortDetection(new ComOption());
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public List<String> findPortNameMeteo()
		{
		return findPortNameMeteo(new ArrayList<String>(0));
		}

	@Override
	public List<String> findPortNameMeteo(List<String> listPortExcluded)
		{
		List<String> allPorts = findPortSerie();
		List<String> meteoPortList = new LinkedList<>();

		for(String portName : allPorts)
			{
			if(listPortExcluded.contains(portName))
				{
				continue;
				}

			if(isStationMeteoAvailable(portName))
				{
				meteoPortList.add(portName);
				}
			}

		return meteoPortList;
		}

	@Override
	public List<String> findPortSerie()
		{
		return comPortDetection.listePortSerie();
		}

	@Override
	public boolean isStationMeteoAvailable(String portName)
		{
		return comPortDetection.testPort(portName);
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private ComPortDetection comPortDetection;
	}

