
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.real.moo.AfficheurServiceMOO;
import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;

public class JPanelTemperature extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelTemperature(AfficheurServiceMOO afficheurServiceMOO)
		{
		this.afficheurServiceMOO = afficheurServiceMOO;

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
		MeteoEvent meteoEvent = afficheurServiceMOO.getLastTemperature();
		jLabelTemperature.setText(String.format("%.2f", meteoEvent.getValue()) + "°C");
		}

	public void updateGUI()
		{

		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

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

		Box boxH = Box.createHorizontalBox();

		jLabelTemperature = new JLabel();
		Font font = new Font(jLabelTemperature.getFont().getName(), Font.PLAIN, 24);
		jLabelTemperature.setFont(font);
		jLabelTemperature.setForeground(JFrameAfficheurService.FOREGROUND_COLOR);
		jPanelGraphTemperature = new JPanelMeteoEventGraph(TITLE, X_LABEL, Y_LABEL, 30, JFrameAfficheurService.FOREGROUND_COLOR, JFrameAfficheurService.BACKGROUND_COLOR, false, afficheurServiceMOO);

		add(boxH, BorderLayout.CENTER);

		boxH.add(jLabelTemperature);
		boxH.add(jPanelGraphTemperature);
		}

	private void apparence()
		{
		setBackground(JFrameAfficheurService.BACKGROUND_COLOR);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Inputs
	private JLabel jLabelTemperature;
	private JPanelMeteoEventGraph jPanelGraphTemperature;
	private AfficheurServiceMOO afficheurServiceMOO;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final String TITLE = "Température";
	private static final String X_LABEL = "Heure";
	private static final String Y_LABEL = "°C";

	}
