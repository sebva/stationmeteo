
package ch.hearc.meteo.imp.afficheur.real.view;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class JFrameAfficheurService extends JFrame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JFrameAfficheurService()
		{
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

		tabbedPane.addTab(jPanelStation.getAfficheurServiceMOO().getTitre(), jPanelStation);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		tabbedPane = new JTabbedPane();
		}

	private void control()
		{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

	private void apparence()
		{
		setTitle("Station m�t�o");
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