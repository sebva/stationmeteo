
package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.spec.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.reseau.MeteoServiceWrapper_I;

public class JPanelControl extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelControl(AfficheurServiceMOO afficheurServiceMOO)
		{
		this.afficheurServiceMOO = afficheurServiceMOO;
		this.meteoServiceRemote = afficheurServiceMOO.getMeteoServiceRemote();

		geometry();
		control();
		apparence();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		boutonStart = new JButton("Start");
		boutonStop = new JButton("Stop");
		boutonPause = new JToggleButton("Pause");

		boutonPause.setToolTipText("Affichage only");

		Box boxH = Box.createHorizontalBox();
		boxH.add(Box.createHorizontalGlue());
		boxH.add(boutonStart);
		boxH.add(Box.createHorizontalStrut(15));
		boxH.add(boutonStop);
		boxH.add(Box.createHorizontalGlue());
		boxH.add(boutonPause);
		boxH.add(Box.createHorizontalGlue());

		setLayout(new BorderLayout());
		add(boxH, BorderLayout.CENTER);
		}

	private void apparence()
		{
		// setBackground(Color.YELLOW);
		}

	private void control()
		{
		boutonStart.addActionListener(new ActionListener()
			{

				@Override public void actionPerformed(ActionEvent e)
					{
					try
						{
						long altitudeDT = 2000;
						long pressionDT = 3000;
						long temperatureDT = 1000;
						MeteoServiceOptions meteoServiceOptions = new MeteoServiceOptions(altitudeDT, pressionDT, temperatureDT);

						meteoServiceRemote.start(meteoServiceOptions);

						enableStop();
						}
					catch (RemoteException e1)
						{
						e1.printStackTrace();
						}

					}
			});

		boutonStop.addActionListener(new ActionListener()
			{

				@Override public void actionPerformed(ActionEvent e)
					{
					try
						{
						meteoServiceRemote.stop();

						enableStart();
						}
					catch (RemoteException e1)
						{
						e1.printStackTrace();
						}

					}
			});

		boutonPause.addActionListener(new ActionListener()
			{

				@Override public void actionPerformed(ActionEvent e)
					{
					afficheurServiceMOO.setPause(!afficheurServiceMOO.isPause());
					}
			});

		threadEtatBouton = new Thread(new Runnable()
			{

				@Override public void run()
					{
					while(true)
						{
						try
							{
							sleep(POOLING_ETAT_BOUTON_DT);
							updateEtatBouton();
							}
						catch (RemoteException e)
							{
							e.printStackTrace();
							}
						}
					}
			});
		threadEtatBouton.start();
		}

	private void updateEtatBouton() throws RemoteException
		{
		if (meteoServiceRemote.isRunning())
			{
			enableStop();
			}
		else
			{
			enableStart();
			}
		}

	private void enableStart()
		{
		boutonStop.setEnabled(false);
		boutonStart.setEnabled(true);
		}

	private void enableStop()
		{
		boutonStart.setEnabled(false);
		boutonStop.setEnabled(true);
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static void sleep(long delayMS)
		{
		//System.out.println("sleep main: "+delayMS);
		try
			{
			Thread.sleep(delayMS);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Input
	private MeteoServiceWrapper_I meteoServiceRemote;
	private AfficheurServiceMOO afficheurServiceMOO;

	// Tools
	private JButton boutonStart;
	private JButton boutonStop;
	private JToggleButton boutonPause;

	private Thread threadEtatBouton;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final long POOLING_ETAT_BOUTON_DT = 2000;

	}
