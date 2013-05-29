
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.real.moo.AfficheurServiceMOO;

public class JPanelTemperature extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelTemperature(AfficheurServiceMOO afficheurServiceMOO)
		{
		geometry();
		control();
		apparence();

		jPanelGraphTemperature.addDatas(afficheurServiceMOO.getListTemperature(), afficheurServiceMOO.getTitre());
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void refresh()
		{
		jPanelGraphTemperature.refresh();
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@Override
	public Dimension getMaximumSize()
		{
		return new Dimension(1000, 300);
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

		jPanelGraphTemperature = new JPanelMeteoEventGraph(TITLE, X_LABEL, Y_LABEL, JFrameAfficheurService.FOREGROUND_COLOR, JFrameAfficheurService.BACKGROUND_COLOR, JFrameAfficheurService.PLOT_BACKGROUND_COLOR, false);

		add(jPanelGraphTemperature, BorderLayout.CENTER);
		}

	private void apparence()
		{
		setBackground(JFrameAfficheurService.BACKGROUND_COLOR);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Inputs
	private JPanelMeteoEventGraph jPanelGraphTemperature;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final String TITLE = "Température";
	private static final String X_LABEL = "Heure";
	private static final String Y_LABEL = "°C";

	}
