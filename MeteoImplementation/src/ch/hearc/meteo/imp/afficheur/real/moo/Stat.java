
package ch.hearc.meteo.imp.afficheur.real.moo;

import java.util.LinkedList;
import java.util.Queue;

public class Stat
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Stat()
		{
		// Inputs
		this.last = 0;

		// Outputs
		this.min = 0;
		this.max = 0;
		this.sum = 0;
		this.moy = 0;

		// Tools
		this.sum = 0;
		this.compteur = 0;
		this.movingAverageDatas = new LinkedList<Float>();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update(float currentValue)
		{
		movingAverageDatas.add(currentValue);
		movingAverageTotal += currentValue;
		if (movingAverageDatas.size() > 50)
			{
			movingAverageTotal -= movingAverageDatas.poll();
			}

		compteur++;
		last = currentValue;
		sum += currentValue;

		if (compteur >= 2)
			{
			min = Math.min(min, currentValue);
			max = Math.max(max, currentValue);
			moy = sum / compteur;

			if (getMovingAverage() < last)
				{
				trend = Trend.up;
				}
			else
				{
				trend = Trend.down;
				}
			}
		else
			{
			min = currentValue;
			max = currentValue;
			}
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public float getMin()
		{
		return this.min;
		}

	public float getMax()
		{
		return this.max;
		}

	public float getMoy()
		{
		return moy;
		}

	public float getLast()
		{
		return last;
		}

	public Trend getTrend()
		{
		return trend;
		}

	public float getMovingAverage()
		{
		return movingAverageTotal / movingAverageDatas.size();
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private float last;

	// Inputs/Outputs
	private float min;
	private float max;
	private float moy;
	private Trend trend;

	// Tools
	private long compteur;
	private float sum;
	private Queue<Float> movingAverageDatas;
	private float movingAverageTotal;
	}
