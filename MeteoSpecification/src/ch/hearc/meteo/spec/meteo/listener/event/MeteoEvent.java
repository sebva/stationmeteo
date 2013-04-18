
package ch.hearc.meteo.spec.meteo.listener.event;

import java.io.Serializable;

public class MeteoEvent implements Serializable
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public MeteoEvent(Sources source, MeteoEventType_E meteoEventType, float value, long time)
		{
		this.source = source;
		this.meteoEventType = meteoEventType;
		this.value = value;
		this.time = time;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override public String toString()
		{
		StringBuilder builder = new StringBuilder();
		builder.append("MeteoEvent [source=");
		builder.append(this.source);
		builder.append(", meteoEventType=");
		builder.append(this.meteoEventType);
		builder.append(", value=");
		builder.append(this.value);
		builder.append(", time=");
		builder.append(this.time);
		builder.append("]");
		return builder.toString();
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public Sources getSource()
		{
		return this.source;
		}

	public float getValue()
		{
		return this.value;
		}

	public long getTime()
		{
		return this.time;
		}

	public MeteoEventType_E getMeteoEventType()
		{
		return this.meteoEventType;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs/Outputs
	private Sources source;
	private MeteoEventType_E meteoEventType;

	private float value;
	private long time;
	}
