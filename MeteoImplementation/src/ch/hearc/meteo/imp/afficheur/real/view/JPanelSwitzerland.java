
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.beans.Transient;

import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.real.ImageTools;
import ch.hearc.meteo.imp.afficheur.real.moo.AfficheurServiceMOO;

public class JPanelSwitzerland extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelSwitzerland(AfficheurServiceMOO afficheurServiceMOO)
		{
		this.afficheurServiceMOO = afficheurServiceMOO;
		currentImageDimension = new Dimension();

		geometry();
		control();
		apparence();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	protected void paintComponent(Graphics g)
		{
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D)g;
		AffineTransform transformOld = g2d.getTransform();
		draw(g2d);
		g2d.setTransform(transformOld);
		}

	@Override
	@Transient
	public Dimension getMinimumSize()
		{
		return new Dimension(300, 300);
		}

	@Override
	@Transient
	public Dimension getMaximumSize()
		{

		return new Dimension(SWITZERLAND.getWidth(this), SWITZERLAND.getHeight(this));
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
		//Rien
		}

	private void apparence()
		{
		setBackground(JFrameAfficheurService.BACKGROUND_COLOR);
		}

	private void draw(Graphics2D g2d)
		{
		if (!currentImageDimension.equals(getSize()))
			{
			double widthFactor = (double)getWidth() / SWITZERLAND.getWidth(this);
			double heightFactor = (double)getHeight() / SWITZERLAND.getHeight(this);
			double minFactor = (widthFactor < heightFactor) ? widthFactor : heightFactor;

			scaledSwitzerland = SWITZERLAND.getScaledInstance((int)(minFactor * SWITZERLAND.getWidth(this)), (int)(minFactor * SWITZERLAND.getHeight(this)), Image.SCALE_FAST);
			currentImageDimension = new Dimension(getSize());
			}

		if (scaledSwitzerland != null && scaledSwitzerland.getHeight(this) > 0 && scaledSwitzerland.getHeight(this) > 0)
			{
			g2d.drawImage(scaledSwitzerland, 0, 0, this);
			}
		g2d.setColor(Color.red);
		Point point = positionOnScreenForCoordinate(afficheurServiceMOO.getLongitude(), afficheurServiceMOO.getLatitude());
		g2d.fillOval(point.x, point.y, 5, 5);
		}

	private Point positionOnScreenForCoordinate(final double LONGITUDE, final double LATITUDE)
		{
		double longitudePositionInPercent = 1 - (MAX_LONGITUDE - LONGITUDE) / (MAX_LONGITUDE - MIN_LONGITUDE);
		double latitudePositionInPercent = (MAX_LATITUDE - LATITUDE) / (MAX_LATITUDE - MIN_LATITUDE);

		int x = (int)(scaledSwitzerland.getWidth(this) * longitudePositionInPercent);
		int y = (int)(scaledSwitzerland.getHeight(this) * latitudePositionInPercent);

		return new Point(x, y);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Inputs
	private AfficheurServiceMOO afficheurServiceMOO;
	private Dimension currentImageDimension;
	private Image scaledSwitzerland;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final double MIN_LONGITUDE = 5.95459;
	private static final double MAX_LONGITUDE = 10.992201;
	private static final double MIN_LATITUDE = 45.817913;
	private static final double MAX_LATITUDE = 47.798485;
	private static final String SWITZERLAND_PATH = "images/switzerland.png";
	private static final Image SWITZERLAND = ImageTools.loadJar(SWITZERLAND_PATH, true);
	}
