
package ch.hearc.meteo.spec.meteo.listener.event;

import java.io.Serializable;
import java.net.InetAddress;

import com.bilat.tools.reseau.rmi.RmiTools;

public class Sources implements Serializable
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Sources(String port, InetAddress adresse)
		{
		this.port = port;
		this.adresse = adresse;
		}

	public Sources(String port)
		{
		this(port, RmiTools.getLocalHost());
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override public String toString()
		{
		StringBuilder builder = new StringBuilder();
		builder.append("Source [port=");
		builder.append(this.port);
		builder.append(", adresse=");
		builder.append(this.adresse);
		builder.append("]");
		return builder.toString();
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public String getPort()
		{
		return this.port;
		}

	public InetAddress getAdresse()
		{
		return this.adresse;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs/Outputs
	private String port;
	private InetAddress adresse;
	}
