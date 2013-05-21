
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import ch.hearc.meteo.imp.afficheur.real.ImageTools;

public class JFrameAfficheurService extends JFrame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JFrameAfficheurService(boolean isCentral)
		{
		jPanelStations = new LinkedList<JPanelStation>();
		this.isCentral = isCentral;

		geometry();
		control();
		apparence();
		if (isCentral)
			{
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
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public synchronized void addNewStation(JPanelStation jPanelStation)
		{
		jPanelStations.add(jPanelStation);
		tabbedPane.addTab(jPanelStation.getAfficheurServiceMOO().getTitre(), jPanelStation);
		jPanelSummary.addAfficheurServiceMOO(jPanelStation.getAfficheurServiceMOO());
		}

	public synchronized void verifyStation()
		{
		Iterator<JPanelStation> iterator = jPanelStations.iterator();
		while(iterator.hasNext())
			{
			JPanelStation jPanelStation = iterator.next();
			if (!jPanelStation.isConnected())
				{
				jPanelSummary.removeAfficheurServiceMOO(jPanelStation.getAfficheurServiceMOO());
				tabbedPane.remove(jPanelStation);
				jPanelStations.remove(jPanelStation);
				}
			}

		/*
		List<JPanelStation> panelStationsToRemove = new ArrayList<JPanelStation>();
		for(JPanelStation jPanelStation:jPanelStations)
			{
			if (!jPanelStation.isConnected())
				{
				jPanelSummary.removeAfficheurServiceMOO(jPanelStation.getAfficheurServiceMOO());
				tabbedPane.remove(jPanelStation);
				panelStationsToRemove.add(jPanelStation);
				}
			}
		for(JPanelStation jPanelStation:panelStationsToRemove)
			{
			jPanelStations.remove(jPanelStation);
			}*/
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		tabbedPane = new JTabbedPane();
		add(tabbedPane);
		jPanelSummary = new JPanelSummary();
		tabbedPane.add("Summary", jPanelSummary);
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
		setTitle("Station m�t�o");
		setIconImage(ICON);
		setSize(1100, 700);
		setMinimumSize(new Dimension(1100, 700));
		setVisible(true);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Tools
	private List<JPanelStation> jPanelStations;
	private JTabbedPane tabbedPane;
	private JPanelSummary jPanelSummary;
	private boolean isCentral;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static final Color BACKGROUND_COLOR = new Color(35, 35, 35);//41, 128, 185);
	public static final Color FOREGROUND_COLOR = new Color(241, 196, 15);
	public static final Color PLOT_BACKGROUND_COLOR = new Color(55, 55, 55);

	public static final int POOLING_DELAY = 1000;
	private static final String ICON_PATH = "res/images/app_icon.png";
	private static final Image ICON = ImageTools.load(ICON_PATH, true);
	}
