
package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.Stat;
import ch.hearc.meteo.imp.afficheur.simulateur.vue.atome.BoxSerieTemporelle;
import ch.hearc.meteo.imp.afficheur.simulateur.vue.atome.JPanelStat;
import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;

public class JPanelEvent extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelEvent(Stat stat, List<MeteoEvent> listMeteoEvent, String titre)
		{
		this.stat = stat;
		this.listMeteoEvent = listMeteoEvent;
		this.titre = titre;

		geometry();
		control();
		apparence();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update()
		{
		boxSerieTemnporelle.update();
		panelStat.update();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		panelStat = new JPanelStat(stat);
		boxSerieTemnporelle = new BoxSerieTemporelle(listMeteoEvent);

		panelStat.setMaximumSize(new Dimension(180, 100));
		boxSerieTemnporelle.setMaximumSize(new Dimension(250, 100));

		Box boxH = Box.createHorizontalBox();
		boxH.add(Box.createHorizontalStrut(15));
		boxH.add(panelStat);
		boxH.add(Box.createHorizontalStrut(15));
		boxH.add(boxSerieTemnporelle);
		boxH.add(Box.createHorizontalStrut(15));

		Box boxV = Box.createVerticalBox();
		boxV.add(Box.createVerticalStrut(15));
		boxV.add(boxH);
		boxV.add(Box.createVerticalStrut(15));

		setLayout(new BorderLayout());
		add(boxV, BorderLayout.CENTER);
		setBorder(BorderFactory.createTitledBorder(titre));
		}

	private void apparence()
		{
		// rien
		}

	private void control()
		{
		// rien
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private Stat stat;

	private List<MeteoEvent> listMeteoEvent;
	private String titre;

	// Tools
	private JPanelStat panelStat;
	private BoxSerieTemporelle boxSerieTemnporelle;

	}
