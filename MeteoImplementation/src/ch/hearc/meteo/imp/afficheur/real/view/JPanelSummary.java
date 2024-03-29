
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.real.ImageTools;
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

		jPanelMeteoEventGraphTemperature = new JPanelMeteoEventGraph(TITLE_TEMPERATURE, THERMOMETER, X_LABEL, Y_LABEL_TEMPERATURE, JFrameAfficheurService.FOREGROUND_COLOR, JFrameAfficheurService.BACKGROUND_COLOR, JFrameAfficheurService.PLOT_BACKGROUND_COLOR, true);
		jPanelMeteoEventGraphAltitude = new JPanelMeteoEventGraph(TITLE_ALTITUDE, ALTITUDE, X_LABEL, Y_LABEL_ALTITUDE, JFrameAfficheurService.FOREGROUND_COLOR, JFrameAfficheurService.BACKGROUND_COLOR, JFrameAfficheurService.PLOT_BACKGROUND_COLOR, true);
		jPanelMeteoEventGraphPression = new JPanelMeteoEventGraph(TITLE_PRESSION, PRESSURE, X_LABEL, Y_LABEL_PRESSION, JFrameAfficheurService.FOREGROUND_COLOR, JFrameAfficheurService.BACKGROUND_COLOR, JFrameAfficheurService.PLOT_BACKGROUND_COLOR, true);

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

	private static final String TITLE_TEMPERATURE = "Temp�rature";
	private static final String TITLE_ALTITUDE = "Altitude";
	private static final String TITLE_PRESSION = "Pression";
	private static final String X_LABEL = "Heure";
	private static final String Y_LABEL_TEMPERATURE = "�C";
	private static final String Y_LABEL_ALTITUDE = "m";
	private static final String Y_LABEL_PRESSION = "hPa";

	private static final String THERMOMETER_PATH = "images/thermometer.png";
	private static final String PRESSURE_PATH = "images/pressure.png";
	private static final String ALTITUDE_PATH = "images/altitude.png";

	public static final Icon THERMOMETER = ImageTools.loadIconJar(THERMOMETER_PATH, true);
	private static final Icon PRESSURE = ImageTools.loadIconJar(PRESSURE_PATH, true);
	private static final Icon ALTITUDE = ImageTools.loadIconJar(ALTITUDE_PATH, true);
	}
