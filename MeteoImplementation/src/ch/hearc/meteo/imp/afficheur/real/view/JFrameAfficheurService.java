
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
import ch.hearc.meteo.imp.use.remote.PCLocal;

public class JFrameAfficheurService extends JFrame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	private JFrameAfficheurService(boolean isCentral)
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

	public synchronized static JFrameAfficheurService getInstance(boolean isCentral)
		{
		if (instance == null)
			{
			instance = new JFrameAfficheurService(isCentral);
			}
		return instance;
		}

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
				iterator.remove();
				}
			}
		}

	public void setPCLocal(PCLocal pc)
		{
		this.pc = pc;
		}

	public PCLocal getPCLocal()
		{
		return pc;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		tabbedPane = new JTabbedPane();
		tabbedPane.setUI(new MeteoTabbedPaneUI());
		add(tabbedPane);
		jPanelSummary = new JPanelSummary();
		tabbedPane.add("Summary", jPanelSummary);
		if (!isCentral)
			{
			jPanelComPort = new JPanelComPort();
			tabbedPane.add("Serial ports", jPanelComPort);
			}
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
		setTitle("Station météo" + ((isCentral) ? " - Central" : ""));
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
	private JPanelComPort jPanelComPort;
	private boolean isCentral;
	private PCLocal pc;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static JFrameAfficheurService instance;

	public static final Color BACKGROUND_COLOR = new Color(35, 35, 35);//41, 128, 185);
	public static final Color FOREGROUND_COLOR = new Color(241, 196, 15);
	public static final Color PLOT_BACKGROUND_COLOR = new Color(75, 75, 75);

	public static final int POOLING_DELAY = 1000;
	private static final String ICON_PATH = "images/app_icon.png";
	private static final Image ICON = ImageTools.loadJar(ICON_PATH, true);
	}
