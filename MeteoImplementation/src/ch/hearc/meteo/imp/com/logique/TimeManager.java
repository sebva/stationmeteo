
package ch.hearc.meteo.imp.com.logique;

public class TimeManager
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public TimeManager(long dt)
		{
		// Input
		this.dt = dt;

		// Tools
		this.previousTime = currentTime();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public boolean isTimeElapse()
		{
		return currentTime() - previousTime > dt;
		}

	public void reset()
		{
		previousTime = currentTime();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private long currentTime()
		{
		return System.currentTimeMillis();
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private long dt;

	// Tools
	private long previousTime;
	}
