
package ch.hearc.meteo.imp.afficheur.simulateur;

import java.rmi.RemoteException;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.imp.afficheur.simulateur.vue.JFrameAfficheurService;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.MeteoServiceWrapper_I;

public class AfficheurServiceSimulateur implements AfficheurService_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * n = #data to print
	 */
	public AfficheurServiceSimulateur(String titre, int n, MeteoServiceWrapper_I meteoServiceRemote)
		{
		afficheurServiceMOO = new AfficheurServiceMOO(titre, n, meteoServiceRemote);
		jFrameAfficheurService = new JFrameAfficheurService(afficheurServiceMOO);
		}

	public AfficheurServiceSimulateur(AffichageOptions affichageOptions, MeteoServiceWrapper_I meteoService)
		{
		this(affichageOptions.getTitre(), affichageOptions.getN(), meteoService);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override public void printAltitude(MeteoEvent event)
		{
		afficheurServiceMOO.printAltitude(event);
		jFrameAfficheurService.refresh();
		}

	@Override public void printTemperature(MeteoEvent event)
		{
		afficheurServiceMOO.printTemperature(event);
		jFrameAfficheurService.refresh();
		}

	@Override public void printPression(MeteoEvent event)
		{
		afficheurServiceMOO.printPression(event);
		jFrameAfficheurService.refresh();
		}

	@Override
	public MeteoServiceOptions getMeteoServiceOptions() throws RemoteException
		{
		return afficheurServiceMOO.getMeteoServiceRemote().getMeteoServiceOptions();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private AfficheurServiceMOO afficheurServiceMOO;
	private JFrameAfficheurService jFrameAfficheurService;

	}
