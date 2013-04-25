
package ch.hearc.meteo.imp.afficheur.simulateur.moo;

import java.util.LinkedList;
import java.util.List;

import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.meteo.listener.event.MeteoEventType_E;
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

		isPause = (meteoServiceRemote==null);

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

			afficherConsole(listAltitude, MeteoEventType_E.ALTITUDE.name() + ESPACE + titre);
			}
		}

	public void printPression(MeteoEvent event)
		{
		if (!isPause)
			{
			manage(listPression, event);
			statPression.update(event.getValue());

			afficherConsole(listPression, MeteoEventType_E.PRESSION.name() + ESPACE + titre);
			}
		}

	public void printTemperature(MeteoEvent event)
		{
		if (!isPause)
			{
			manage(listTemperature, event);
			statTemperature.update(event.getValue());

			afficherConsole(listTemperature, MeteoEventType_E.TEMPERATURE.name() + ESPACE + titre);
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

	public List<MeteoEvent> getListPression()
		{
		return this.listPression;
		}

	public List<MeteoEvent> getListTemperature()
		{
		return this.listTemperature;
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

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void manage(List<MeteoEvent> listMeteoEvent, MeteoEvent event)
		{
		if (listMeteoEvent.size() == n)
			{
			listMeteoEvent.remove(0);
			}

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
