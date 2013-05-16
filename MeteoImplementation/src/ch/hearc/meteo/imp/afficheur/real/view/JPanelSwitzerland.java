
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.real.moo.AfficheurServiceMOO;

public class JPanelSwitzerland extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelSwitzerland()
		{
		this.afficheurServiceMOOs = new ArrayList<AfficheurServiceMOO>();

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
							Thread.sleep(JFrameAfficheurService.POOLING_DELAY);
							refresh();
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

	public void refresh()
		{
		repaint();
		}

	public void addAfficheurServiceMOO(AfficheurServiceMOO afficheurServiceMOO)
		{
		afficheurServiceMOOs.add(afficheurServiceMOO);
		}

	public void removeAfficheurServiceMOO(AfficheurServiceMOO afficheurServiceMOO)
		{
		afficheurServiceMOOs.remove(afficheurServiceMOO);
		}

	@Override
	protected void paintComponent(Graphics g)
		{
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D)g;
		AffineTransform transformOld = g2d.getTransform();
		draw(g2d);
		g2d.setTransform(transformOld);
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

		Box boxV = Box.createVerticalBox();

		add(boxV, BorderLayout.CENTER);
		}

	private void apparence()
		{
		setBackground(JFrameAfficheurService.BACKGROUND_COLOR);
		}

	private void draw(Graphics2D g2d)
		{
		g2d.setColor(Color.red);
		for(AfficheurServiceMOO afficheurServiceMOO:afficheurServiceMOOs)
			{
			Point point = positionOnScreenForCoordinate(afficheurServiceMOO.getLongitude(), afficheurServiceMOO.getLatitude());
			System.out.println(point);
			g2d.fillOval(point.x, point.y, 10, 10);
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private Point positionOnScreenForCoordinate(final double LONGITUDE, final double LATITUDE)
		{
		double longitudePositionInPercent = 1 - (MAX_LONGITUDE - LONGITUDE) / (MAX_LONGITUDE - MIN_LONGITUDE);
		double latitudePositionInPercent = 1 - (MAX_LATITUDE - LATITUDE) / (MAX_LATITUDE - MIN_LATITUDE);

		System.out.println("LONGITUDE : " + LONGITUDE);
		System.out.println("LATITUDE : " + LATITUDE);
		System.out.println("longitudePositionInPercent : " + longitudePositionInPercent);
		System.out.println("latitudePositionInPercent : " + latitudePositionInPercent);

		int x = (int)(this.getWidth() * longitudePositionInPercent);
		int y = (int)(this.getHeight() * latitudePositionInPercent);

		return new Point(x, y);
		}

	//Inputs
	private List<AfficheurServiceMOO> afficheurServiceMOOs;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final double MIN_LONGITUDE = 5.933904;
	private static final double MAX_LONGITUDE = 10.505933;
	private static final double MIN_LATITUDE = 45.801967;
	private static final double MAX_LATITUDE = 47.835252;

	}
