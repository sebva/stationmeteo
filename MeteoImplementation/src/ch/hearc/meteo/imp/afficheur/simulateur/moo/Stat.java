
package ch.hearc.meteo.imp.afficheur.simulateur.moo;

public class Stat
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Stat()
		{
		// Inputs
		this.last=0;

		// Outputs
		this.min = 0;
		this.max = 0;
		this.sum = 0;
		this.moy = 0;

		// Tools
		this.sum = 0;
		this.compteur = 0;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update(float currentValue)
		{
		compteur++;
		last = currentValue;
		sum += currentValue;


		if (compteur >= 2)
			{
			min = Math.min(min, currentValue);
			max = Math.max(max, currentValue);
			moy=sum/compteur;
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

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private float last;

	// Inputs/Outputs
	private float min;
	private float max;
	private float moy;

	// Tools
	private long compteur;
	private float sum;
	}
