
package ch.hearc.meteo.spec.afficheur;

import java.io.Serializable;

public class AffichageOptions implements Serializable
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public AffichageOptions(int n, String titre)
		{
		this.n = n;
		this.titre = titre;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override public String toString()
		{
		StringBuilder builder = new StringBuilder();
		builder.append("AffichageOptions [n=");
		builder.append(this.n);
		builder.append(", titre=");
		builder.append(this.titre);
		builder.append("]");
		return builder.toString();
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public int getN()
		{
		return this.n;
		}

	public String getTitre()
		{
		return this.titre;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private int n; // nb data to print

	private String titre;
	}
