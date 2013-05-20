
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.real.moo.AfficheurServiceMOO;

public class JPanelSummary extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelSummary()
		{
		this.afficheurServiceMOOs = new ArrayList<AfficheurServiceMOO>();

		geometry();
		control();
		apparence();

		Thread thread = new Thread(new Runnable()
			{

				@Override
				public void run()
					{
					while(true)
						{
						try
							{
							Thread.sleep(JFrameAfficheurService.POOLING_DELAY);
							refresh();
							}
						catch (Exception e)
							{
							e.printStackTrace();
							}
						}
					}
			});

		thread.start();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void refresh()
		{
		jPanelMeteoEventGraphTemperature.refresh();
		jPanelMeteoEventGraphAltitude.refresh();
		jPanelMeteoEventGraphPression.refresh();
		}

	public void updateGUI()
		{

		}

	public void addAfficheurServiceMOO(AfficheurServiceMOO afficheurServiceMOO)
		{
		jPanelMeteoEventGraphTemperature.addDatas(afficheurServiceMOO.getListTemperature(), afficheurServiceMOO.getTitre());
		jPanelMeteoEventGraphAltitude.addDatas(afficheurServiceMOO.getListAltitude(), afficheurServiceMOO.getTitre());
		jPanelMeteoEventGraphPression.addDatas(afficheurServiceMOO.getListPression(), afficheurServiceMOO.getTitre());
		afficheurServiceMOOs.add(afficheurServiceMOO);
		}

	public void removeAfficheurServiceMOO(AfficheurServiceMOO afficheurServiceMOO)
		{
		jPanelMeteoEventGraphTemperature.removeDatas(afficheurServiceMOO.getListTemperature());
		jPanelMeteoEventGraphAltitude.removeDatas(afficheurServiceMOO.getListAltitude());
		jPanelMeteoEventGraphPression.removeDatas(afficheurServiceMOO.getListPression());
		afficheurServiceMOOs.remove(afficheurServiceMOO);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void control()
		{
		//Rien
		}

	private void geometry()
		{
		setLayout(new BorderLayout());

		Box boxV = Box.createVerticalBox();

		jPanelMeteoEventGraphTemperature = new JPanelMeteoEventGraph(TITLE_TEMPERATURE, X_LABEL, Y_LABEL_TEMPERATURE, 30, JFrameAfficheurService.FOREGROUND_COLOR, JFrameAfficheurService.BACKGROUND_COLOR, JFrameAfficheurService.PLOT_BACKGROUND_COLOR, true, null);
		jPanelMeteoEventGraphAltitude = new JPanelMeteoEventGraph(TITLE_ALTITUDE, X_LABEL, Y_LABEL_ALTITUDE, 30, JFrameAfficheurService.FOREGROUND_COLOR, JFrameAfficheurService.BACKGROUND_COLOR, JFrameAfficheurService.PLOT_BACKGROUND_COLOR, true, null);
		jPanelMeteoEventGraphPression = new JPanelMeteoEventGraph(TITLE_PRESSION, X_LABEL, Y_LABEL_PRESSION, 30, JFrameAfficheurService.FOREGROUND_COLOR, JFrameAfficheurService.BACKGROUND_COLOR, JFrameAfficheurService.PLOT_BACKGROUND_COLOR, true, null);

		add(boxV, BorderLayout.CENTER);

		boxV.add(jPanelMeteoEventGraphTemperature);
		boxV.add(jPanelMeteoEventGraphPression);
		boxV.add(jPanelMeteoEventGraphAltitude);
		}

	private void apparence()
		{
		setBackground(JFrameAfficheurService.BACKGROUND_COLOR);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Outputs
	private JPanelMeteoEventGraph jPanelMeteoEventGraphTemperature;
	private JPanelMeteoEventGraph jPanelMeteoEventGraphAltitude;
	private JPanelMeteoEventGraph jPanelMeteoEventGraphPression;

	//Inputs
	private List<AfficheurServiceMOO> afficheurServiceMOOs;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final String TITLE_TEMPERATURE = "Température";
	private static final String TITLE_ALTITUDE = "Altitude";
	private static final String TITLE_PRESSION = "Pression";
	private static final String X_LABEL = "Heure";
	private static final String Y_LABEL_TEMPERATURE = "°C";
	private static final String Y_LABEL_ALTITUDE = "m";
	private static final String Y_LABEL_PRESSION = "hPa";
	}
