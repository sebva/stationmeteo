
package ch.hearc.meteo.imp.afficheur.real;

import java.rmi.RemoteException;

import ch.hearc.meteo.imp.afficheur.real.moo.AfficheurServiceMOO;
import ch.hearc.meteo.imp.afficheur.real.view.JFrameAfficheurService;
import ch.hearc.meteo.imp.afficheur.real.view.JPanelStation;
import ch.hearc.meteo.imp.use.remote.PCLocal;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.MeteoServiceWrapper_I;

public class AfficheurService implements AfficheurService_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * n = #data to print
	 */
	public AfficheurService(String titre, int n, MeteoServiceWrapper_I meteoServiceRemote)
		{

		jFrameAfficheurService = JFrameAfficheurService.getInstance(n == -1);

		if (meteoServiceRemote != null)
			{
			System.out.println(meteoServiceRemote.hashCode());
			afficheurServiceMOO = new AfficheurServiceMOO(titre, n, meteoServiceRemote);
			jPanelStation = new JPanelStation(afficheurServiceMOO);
			jFrameAfficheurService.addNewStation(jPanelStation);
			}
		}

	public AfficheurService(AffichageOptions affichageOptions, MeteoServiceWrapper_I meteoService, PCLocal pc)
		{
		this(affichageOptions.getTitre(), affichageOptions.getN(), meteoService);
		jFrameAfficheurService.setPCLocal(pc);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void printAltitude(MeteoEvent event)
		{
		afficheurServiceMOO.printAltitude(event);
		jPanelStation.refresh();
		}

	@Override
	public void printTemperature(MeteoEvent event)
		{
		afficheurServiceMOO.printTemperature(event);
		jPanelStation.refresh();
		}

	@Override
	public void printPression(MeteoEvent event)
		{
		afficheurServiceMOO.printPression(event);
		jPanelStation.refresh();
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

	//Tools
	private AfficheurServiceMOO afficheurServiceMOO;
	private JPanelStation jPanelStation;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	//Tools
	private static JFrameAfficheurService jFrameAfficheurService;
	}
