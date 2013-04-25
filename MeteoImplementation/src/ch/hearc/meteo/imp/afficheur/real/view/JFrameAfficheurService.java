
package ch.hearc.meteo.imp.afficheur.real.view;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import ch.hearc.meteo.imp.afficheur.simulateur.vue.JPanelRoot;

public class JFrameAfficheurService extends JFrame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JFrameAfficheurService()
		{
		jPanelStations = new LinkedList<JPanelStation>();

		geometry();
		control();
		apparence();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void addNewStation(JPanelStation jPanelStation)
		{
		jPanelStations.add(jPanelStation);

		tabbedPane.addTab(jPanelStation.getAfficheurServiceMOO().getTitre(), new JPanelRoot(jPanelStation.getAfficheurServiceMOO()));
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		tabbedPane = new JTabbedPane();
		add(tabbedPane);
		}

	private void control()
		{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

	private void apparence()
		{
		setTitle("Station météo");
		setSize(500, 550);
		setResizable(false);
		setVisible(true);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Tools
	private List<JPanelStation> jPanelStations;
	private JTabbedPane tabbedPane;

	}
