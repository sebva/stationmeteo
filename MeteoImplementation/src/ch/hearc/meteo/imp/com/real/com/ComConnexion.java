
package ch.hearc.meteo.imp.com.real.com;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import gnu.io.SerialPort;

import ch.hearc.meteo.imp.com.logique.MeteoServiceCallback_I;

public class ComConnexion implements ComConnexions_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public ComConnexion(MeteoServiceCallback_I meteoServiceCallback, String portName, ComOption comOption)
		{
		this.comOption = comOption;
		this.portName = portName;
		this.meteoServiceCallback = meteoServiceCallback;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override public void start() throws Exception
		{
		// TODO Auto-generated method stub

		}

	@Override public void stop() throws Exception
		{
		// TODO Auto-generated method stub

		}

	@Override public void connect() throws Exception
		{
		// TODO Auto-generated method stub

		}

	@Override public void disconnect() throws Exception
		{
		// TODO Auto-generated method stub

		}

	@Override public void askAltitudeAsync() throws Exception
		{
		// TODO Auto-generated method stub

		}

	@Override public void askPressionAsync() throws Exception
		{
		// TODO Auto-generated method stub

		}

	@Override public void askTemperatureAsync() throws Exception
		{
		// TODO Auto-generated method stub

		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@Override public String getNamePort()
		{
		return portName;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Input
	private ComOption comOption;
	private String portName;
	private MeteoServiceCallback_I meteoServiceCallback;

	// Tools
	private SerialPort serialPort;
	private BufferedWriter writer;
	private BufferedReader reader;

	}
