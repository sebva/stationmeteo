
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.Color;

import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.real.moo.AfficheurServiceMOO;

public class JPanelStation extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelStation(AfficheurServiceMOO afficheurServiceMOO)
		{
		this.afficheurServiceMOO = afficheurServiceMOO;

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
						updateGUI();
						try
							{
							Thread.sleep(1000);
							}
						catch (InterruptedException e)
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

	public void refresh()
		{
		//TODO: UPDATE DATA
		jPanelGraph.addData(afficheurServiceMOO.getListTemperature());
		}

	public void updateGUI()
		{

		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public AfficheurServiceMOO getAfficheurServiceMOO()
		{
		return this.afficheurServiceMOO;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		jPanelGraph = new JPanelGraph("Température", "Heure", "°C", 30, Color.blue, Color.GRAY);
		add(jPanelGraph);
		//add(new JPanelRoot(afficheurServiceMOO));
		}

	private void control()
		{
		//Rien
		}

	private void apparence()
		{
		//Rien
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Tools
	private JPanelGraph jPanelGraph;

	// Inputs
	private AfficheurServiceMOO afficheurServiceMOO;

	}
