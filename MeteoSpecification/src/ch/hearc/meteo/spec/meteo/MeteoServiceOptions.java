
package ch.hearc.meteo.spec.meteo;

import java.io.Serializable;

import junit.framework.Assert;

public class MeteoServiceOptions implements Serializable
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public MeteoServiceOptions(long altitudeDT, long pressionDT, long temperatureDT, long delayMS)
		{
		setAltitudeDT(altitudeDT);
		setPressionDT(pressionDT);
		setTemperatureDT(temperatureDT);
		setDelayMS(delayMS);
		}

	public MeteoServiceOptions(long altitudeDT, long pressionDT, long temperatureDT)
		{
		this(altitudeDT, pressionDT, temperatureDT, DELAY_MS);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/


	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/**
	 * hyp : altitudeDT>0
	 */
	public void setAltitudeDT(long altitudeDT)
		{
		Assert.assertTrue(altitudeDT > 0);
		this.altitudeDT = Math.max(EPSILON_MS, altitudeDT);
		}

	/**
	 * hyp : pressionDT>0
	 */
	public void setPressionDT(long pressionDT)
		{
		Assert.assertTrue(pressionDT > 0);
		this.pressionDT = Math.max(EPSILON_MS, pressionDT);
		}

	/**
	 * hyp : temperatureDT>0
	 */
	public void setTemperatureDT(long temperatureDT)
		{
		Assert.assertTrue(temperatureDT > 0);
		this.temperatureDT = Math.max(EPSILON_MS, temperatureDT);
		}

	/**
	 * hyp : delayMS>0
	 */
	public void setDelayMS(long delayMS)
		{
		Assert.assertTrue(delayMS > 0);
		this.delayMS = Math.abs(delayMS);
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public long getAltitudeDT()
		{
		return this.altitudeDT;
		}

	public long getPressionDT()
		{
		return this.pressionDT;
		}

	public long getTemperatureDT()
		{
		return this.temperatureDT;
		}

	public static long getDelayMs()
		{
		return DELAY_MS;
		}

	public long getDelayMS()
		{
		return this.delayMS;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/





	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs/Outputs
	private long altitudeDT;
	private long pressionDT;
	private long temperatureDT;

	private long delayMS;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final long DELAY_MS = 1000;
	private static final long EPSILON_MS = 500;

	}
