
package ch.hearc.meteo.imp.com.real.com;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import ch.hearc.meteo.imp.com.real.com.trame.TrameDecoder;
import ch.hearc.meteo.imp.com.real.com.trame.TrameEncoder;




public class ComPortDetection
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public ComPortDetection(ComOption comOption)
		{
		assert(comOption != null);
		this.comOption = comOption;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public List<String> listePortSerie()
		{
		@SuppressWarnings("rawtypes")
		Enumeration portIdentifiers = CommPortIdentifier.getPortIdentifiers();
		List<String> list = new LinkedList<>();
		while(portIdentifiers.hasMoreElements())
			{
			CommPortIdentifier port = (CommPortIdentifier)portIdentifiers.nextElement();
			list.add(port.getName());
			}
		return list;
		}

	public boolean testPort(final String portName)
		{
		try
			{
			SerialPort port = (SerialPort)CommPortIdentifier.getPortIdentifier(portName).open(getClass().getSimpleName(), 1000);
			port.setSerialPortParams(comOption.getSpeed(), comOption.getDataBit(), comOption.getStopBit(), comOption.getParity());

			final BufferedReader reader = new BufferedReader(new InputStreamReader(port.getInputStream()));
			OutputStream outputStream = port.getOutputStream();

			final CyclicBarrier barrier = new CyclicBarrier(2);

			port.notifyOnDataAvailable(true);
			SerialPortEventListener serialPortEventListener = new SerialPortEventListener()
				{

					@Override
					public void serialEvent(final SerialPortEvent event)
						{
						try
							{
							if (event.getEventType() != SerialPortEvent.DATA_AVAILABLE) { return; }

							String trame = reader.readLine();

							TrameDecoder.valeur(trame);
							testPortResult = true;
							}
						catch (Exception e)
							{
							testPortResult = false;
							}

						try
							{
							barrier.await();
							}
						catch (InterruptedException | BrokenBarrierException e)
							{
							// Rien
							}
						}
				};
			port.addEventListener(serialPortEventListener);

			String trame = "010200";
			outputStream.write(TrameEncoder.coder(trame));

			barrier.await(2, TimeUnit.SECONDS);
			port.close();
			return testPortResult;
			}
		catch (Exception e)
			{
			return false;
			}
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

	// Input
	private ComOption comOption;
	// Tools
	// Résultat du test du port
	private boolean testPortResult;
	}

