
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.Color;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.real.moo.AfficheurServiceMOO;
import ch.hearc.meteo.imp.afficheur.real.moo.Pressure;
import ch.hearc.meteo.imp.afficheur.real.moo.Trend;

public class JPanelPressure extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelPressure(AfficheurServiceMOO afficheurServiceMOO)
		{
		this.afficheurServiceMOO = afficheurServiceMOO;
		pressure = new Pressure(afficheurServiceMOO);

		geometry();
		control();
		apparence();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void refresh()
		{
		jPanelDialPressure.refresh();

		jLabelPressure.setText(String.format("Pression actuelle : %.2f", pressure.getPressure()) + JPanelDialPressure.UNITY);
		jLabelSeeLevelPressure.setText(String.format("Pression niveau de la mer : %.2f", pressure.getSeaLevelPressure()) + JPanelDialPressure.UNITY);
		jLabelMeanPressure.setText(String.format("Pression moyenne : %.2f", afficheurServiceMOO.getStatPression().getMovingAverage()) + JPanelDialPressure.UNITY);

		StringBuilder stringBuilder = new StringBuilder();

		if (pressure.getSeaLevelPressure() < 1000)
			{
			stringBuilder.append("Pluie");
			}
		else if (pressure.getSeaLevelPressure() > 1030)
			{
			stringBuilder.append("Beau");
			}
		else
			{
			stringBuilder.append("Variable");
			}

		if (afficheurServiceMOO.getStatPression().getTrend() == Trend.up)
			{
			stringBuilder.append(" /\\");
			jLabelTrend.setForeground(TREND_UP_COLOR);
			}
		else if (afficheurServiceMOO.getStatPression().getTrend() == Trend.down)
			{
			stringBuilder.append(" \\/");
			jLabelTrend.setForeground(TREND_DOWN_COLOR);
			}

		jLabelTrend.setText(stringBuilder.toString());
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
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		Box boxText = Box.createVerticalBox();

		jLabelPressure = new JLabel();
		jLabelSeeLevelPressure = new JLabel();
		jLabelMeanPressure = new JLabel();
		jLabelTrend = new JLabel();
		boxText.add(jLabelPressure);
		boxText.add(jLabelSeeLevelPressure);
		boxText.add(jLabelMeanPressure);
		boxText.add(jLabelTrend);

		jPanelDialPressure = new JPanelDialPressure(pressure);

		JPanelStation.setupJLabelStyle(jLabelPressure, 18);
		JPanelStation.setupJLabelStyle(jLabelSeeLevelPressure, 18);
		JPanelStation.setupJLabelStyle(jLabelMeanPressure, 18);
		JPanelStation.setupJLabelStyle(jLabelTrend, 18);

		add(boxText);
		add(jPanelDialPressure);
		}

	private void apparence()
		{
		setBackground(JFrameAfficheurService.BACKGROUND_COLOR);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Inputs
	private JLabel jLabelMeanPressure;
	private JLabel jLabelPressure;
	private JLabel jLabelSeeLevelPressure;
	private JLabel jLabelTrend;
	private AfficheurServiceMOO afficheurServiceMOO;

	//Outputs
	private JPanelDialPressure jPanelDialPressure;

	//Tools
	private Pressure pressure;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final Color TREND_UP_COLOR = new Color(0, 255, 0);
	private static final Color TREND_DOWN_COLOR = new Color(255, 0, 0);

	}
