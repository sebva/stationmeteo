
package ch.hearc.meteo.imp.com.real.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import ch.hearc.meteo.imp.com.logique.MeteoServiceCallback_I;
import ch.hearc.meteo.imp.com.real.com.trame.TrameDecoder;
import ch.hearc.meteo.imp.com.real.com.trame.TrameEncoder;
import ch.hearc.meteo.spec.meteo.exception.MeteoServiceException;

public class ComConnexion implements ComConnexions_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public ComConnexion(MeteoServiceCallback_I meteoServiceCallback, String portName, ComOption comOption)
		{
		this.comOption = comOption;
		if (portName == null)
			{
			autodetectPort();
			}
		else
			{
			this.portName = portName;
			}
		this.meteoServiceCallback = meteoServiceCallback;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void start() throws Exception
		{

		}

	@Override
	public void stop() throws Exception
		{

		}

	@Override
	public void connect() throws Exception
		{
		serialPort = (SerialPort)CommPortIdentifier.getPortIdentifier(portName).open(getClass().getSimpleName(), 1000);
		serialPort.setSerialPortParams(comOption.getSpeed(), comOption.getDataBit(), comOption.getStopBit(), comOption.getParity());

		reader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
		outputStream = serialPort.getOutputStream();

		serialPort.notifyOnDataAvailable(true);
		serialPort.addEventListener(new SerialPortEventListener()
			{

				@Override
				public void serialEvent(final SerialPortEvent event)
					{

					if (event.getEventType() != SerialPortEvent.DATA_AVAILABLE || meteoServiceCallback == null) { return; }

					try
						{
						String trame = reader.readLine();
						float valeur = TrameDecoder.valeur(trame);

						switch(TrameDecoder.dataType(trame))
							{
							case ALTITUDE:
								meteoServiceCallback.altitudePerformed(valeur);
								break;
							case PRESSION:
								meteoServiceCallback.pressionPerformed(valeur);
								break;
							case TEMPERATURE:
								meteoServiceCallback.temperaturePerformed(valeur);
								break;
							}
						}
					catch (IOException e1)
						{
						// TODO Auto-generated catch block
						e1.printStackTrace();
						}
					catch (MeteoServiceException e)
						{
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
					}

			});
		}

	@Override
	public void disconnect() throws Exception
		{
		serialPort.removeEventListener();
		serialPort.close();
		}

	@Override
	public void askAltitudeAsync() throws Exception
		{
		String trame = "010200";
		outputStream.write(TrameEncoder.coder(trame));
		}

	@Override
	public void askPressionAsync() throws Exception
		{
		String trame = "010000";
		outputStream.write(TrameEncoder.coder(trame));
		}

	@Override
	public void askTemperatureAsync() throws Exception
		{
		String trame = "010100";
		outputStream.write(TrameEncoder.coder(trame));
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@Override
	public String getNamePort()
		{
		return portName;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setCallback(MeteoServiceCallback_I callback)
		{
		this.meteoServiceCallback = callback;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void autodetectPort()
		{
		Enumeration portIdentifiers = CommPortIdentifier.getPortIdentifiers();
		while(portIdentifiers.hasMoreElements())
			{
			CommPortIdentifier portToTest = (CommPortIdentifier)portIdentifiers.nextElement();
			testPort(portToTest);
			if (portName != null) { return; }
			}
		}

	private void testPort(final CommPortIdentifier portIdentifier)
		{
		try
			{
			SerialPort port = (SerialPort)portIdentifier.open(getClass().getSimpleName(), 1000);
			port.setSerialPortParams(comOption.getSpeed(), comOption.getDataBit(), comOption.getStopBit(), comOption.getParity());

			final BufferedReader reader = new BufferedReader(new InputStreamReader(port.getInputStream()));
			OutputStream outputStream = port.getOutputStream();

			final CyclicBarrier barrier = new CyclicBarrier(2);

			port.notifyOnDataAvailable(true);
			port.addEventListener(new SerialPortEventListener()
				{

					@Override
					public void serialEvent(final SerialPortEvent event)
						{
						try
							{
							if (event.getEventType() != SerialPortEvent.DATA_AVAILABLE) { return; }

							String trame = reader.readLine();

							TrameDecoder.valeur(trame);
							ComConnexion.this.portName = portIdentifier.getName();
							}
						catch (Exception e)
							{

							}

						try
							{
							barrier.await();
							}
						catch (InterruptedException | BrokenBarrierException e)
							{
							e.printStackTrace();
							}
						}
				});

			String trame = "010200";
			outputStream.write(TrameEncoder.coder(trame));

			barrier.await(2, TimeUnit.SECONDS);
			port.close();
			}
		catch (Exception e)
			{

			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Input
	private ComOption comOption;
	private String portName;
	private MeteoServiceCallback_I meteoServiceCallback = null;

	// Tools
	private SerialPort serialPort;
	private OutputStream outputStream;
	private BufferedReader reader;

	}
