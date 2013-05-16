
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class JFrameAfficheurService extends JFrame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * Si meteoServiceRemote est null, alors on est le serveur et on affiche des tabs
	 */
	public JFrameAfficheurService()
		{
		jPanelStations = new LinkedList<JPanelStation>();

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
							verifyStation();
							Thread.sleep(POOLING_DELAY);
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

	public synchronized void addNewStation(JPanelStation jPanelStation)
		{
		jPanelStations.add(jPanelStation);
		tabbedPane.addTab(jPanelStation.getAfficheurServiceMOO().getTitre(), jPanelStation);
		jPanelSummary.addAfficheurServiceMOO(jPanelStation.getAfficheurServiceMOO());
		jPanelSwitzerland.addAfficheurServiceMOO(jPanelStation.getAfficheurServiceMOO());
		}

	public synchronized void verifyStation()
		{
		List<JPanelStation> panelStationsToRemove = new ArrayList<JPanelStation>();
		for(JPanelStation jPanelStation:jPanelStations)
			{
			if (!jPanelStation.isConnected())
				{
				jPanelSummary.removeAfficheurServiceMOO(jPanelStation.getAfficheurServiceMOO());
				jPanelSwitzerland.removeAfficheurServiceMOO(jPanelStation.getAfficheurServiceMOO());
				tabbedPane.remove(jPanelStation);
				panelStationsToRemove.add(jPanelStation);
				}
			}
		for(JPanelStation jPanelStation:panelStationsToRemove)
			{
			jPanelStations.remove(jPanelStation);
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		tabbedPane = new JTabbedPane();
		add(tabbedPane);
		jPanelSummary = new JPanelSummary();
		jPanelSwitzerland = new JPanelSwitzerland();
		tabbedPane.add("Summary", jPanelSummary);
		tabbedPane.add("Maps", jPanelSwitzerland);
		}

	private void control()
		{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

	private void apparence()
		{
		setBackground(BACKGROUND_COLOR);
		getContentPane().setBackground(BACKGROUND_COLOR);
		setBackground(BACKGROUND_COLOR);
		setTitle("Station météo");
		setSize(750, 850);
		setMinimumSize(new Dimension(750, 850));
		setVisible(true);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Tools
	private List<JPanelStation> jPanelStations;
	private JTabbedPane tabbedPane;
	private JPanelSummary jPanelSummary;
	private JPanelSwitzerland jPanelSwitzerland;
	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static final Color BACKGROUND_COLOR = new Color(41, 128, 185);
	public static final Color FOREGROUND_COLOR = new Color(241, 196, 15);
	public static final int POOLING_DELAY = 1000;
	}
