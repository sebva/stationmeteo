
package ch.hearc.meteo.imp.com.simulateur;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ch.hearc.meteo.spec.meteo.MeteoPortDetectionService_I;



public class MeteoPortDetectionServiceSimulateur implements MeteoPortDetectionService_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public MeteoPortDetectionServiceSimulateur()
		{

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
		List<String> allPorts = new LinkedList<>();
		for(int i = 1; i <= Math.random() * 5 + 1; i++)
			{
			allPorts.add("COM" + i);
			}

		return allPorts;
		}

	@Override
	public boolean isStationMeteoAvailable(String portName)
		{
		try
			{
			Thread.sleep((long)(Math.random() * 1000.0));
			}
		catch (InterruptedException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return Math.random() > 0.6;
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
	}

