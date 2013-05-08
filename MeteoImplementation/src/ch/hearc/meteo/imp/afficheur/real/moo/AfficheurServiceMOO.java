
package ch.hearc.meteo.imp.afficheur.real.moo;

import java.util.LinkedList;
import java.util.List;

import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.MeteoServiceWrapper_I;

public class AfficheurServiceMOO
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * n = #data to print
	 */
	public AfficheurServiceMOO(String titre, int n, MeteoServiceWrapper_I meteoServiceRemote)
		{
		// Inputs
		this.n = n;
		this.titre = titre;
		this.meteoServiceRemote = meteoServiceRemote;

		//Tools
		listAltitude = new LinkedList<MeteoEvent>();
		listPression = new LinkedList<MeteoEvent>();
		listTemperature = new LinkedList<MeteoEvent>();

		isPause = false;

		// Outputs
		statAltitude = new Stat();
		statPression = new Stat();
		statTemperature = new Stat();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void printAltitude(MeteoEvent event)
		{
		if (!isPause)
			{
			manage(listAltitude, event);
			statAltitude.update(event.getValue());

			//afficherConsole(listAltitude, MeteoEventType_E.ALTITUDE.name() + ESPACE + titre);
			}
		}

	public void printPression(MeteoEvent event)
		{
		if (!isPause)
			{
			manage(listPression, event);
			statPression.update(event.getValue());

			//afficherConsole(listPression, MeteoEventType_E.PRESSION.name() + ESPACE + titre);
			}
		}

	public void printTemperature(MeteoEvent event)
		{
		if (!isPause)
			{
			manage(listTemperature, event);
			statTemperature.update(event.getValue());

			//afficherConsole(listTemperature, MeteoEventType_E.TEMPERATURE.name() + ESPACE + titre);
			}
		}

	/*------------------------------*\
	|*				Is				*|
	\*------------------------------*/

	public boolean isPause()
		{
		return isPause;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/**
	 * service affichage only
	 */
	public void setPause(boolean etat)
		{
		isPause = etat;
		}

	public void setN(int n)
		{
		this.n = n;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public String getTitre()
		{
		return this.titre;
		}

	public MeteoServiceWrapper_I getMeteoServiceRemote()
		{
		return this.meteoServiceRemote;
		}

	public List<MeteoEvent> getListAltitude()
		{
		return this.listAltitude;
		}

	public MeteoEvent getLastAltitude()
		{
		if (listAltitude.size() <= 0) { return null; }
		return this.listAltitude.get(listAltitude.size() - 1);
		}

	public List<MeteoEvent> getListPression()
		{
		return this.listPression;
		}

	public MeteoEvent getLastPression()
		{
		if (listPression.size() <= 0) { return null; }
		return this.listPression.get(listPression.size() - 1);
		}

	public List<MeteoEvent> getListTemperature()
		{
		return this.listTemperature;
		}

	public MeteoEvent getLastTemperature()
		{
		if (listTemperature.size() <= 0) { return null; }
		return this.listTemperature.get(listTemperature.size() - 1);
		}

	public Stat getStatAltitude()
		{
		return this.statAltitude;
		}

	public Stat getStatPression()
		{
		return this.statPression;
		}

	public Stat getStatTemperature()
		{
		return this.statTemperature;
		}

	public int getN()
		{
		return this.n;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void manage(List<MeteoEvent> listMeteoEvent, MeteoEvent event)
		{
		listMeteoEvent.add(event);
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static void afficherConsole(List<MeteoEvent> listMeteoEvent, String titre)
		{
		System.out.print("[AfficheurServiceMOO] : " + titre + " : ");
		for(MeteoEvent meteoEvent:listMeteoEvent)
			{
			System.out.print(meteoEvent.getValue() + " ");
			}
		System.out.println();

		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private int n;
	private String titre;
	private MeteoServiceWrapper_I meteoServiceRemote;

	// Tools
	private List<MeteoEvent> listAltitude;
	private List<MeteoEvent> listPression;
	private List<MeteoEvent> listTemperature;

	// Outputs
	private Stat statAltitude;
	private Stat statPression;
	private Stat statTemperature;

	private boolean isPause;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final String ESPACE = " ";

	}
