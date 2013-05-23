
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.Transient;
import java.rmi.RemoteException;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import ch.hearc.meteo.imp.afficheur.real.moo.AfficheurServiceMOO;

public class JPanelDelta extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelDelta(AfficheurServiceMOO afficheurServiceMOO)
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
						try
							{
							Thread.sleep(JFrameAfficheurService.POOLING_DELAY);
							checkDelta();
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
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		setLayout(new BorderLayout());

		try
			{
			jSliderTemperatureDt = new JSlider(SwingConstants.VERTICAL, JSLIDER_MIN, JSLIDER_MAX, (int)afficheurServiceMOO.getTemperatureDT());
			}
		catch (RemoteException e)
			{
			jSliderTemperatureDt = new JSlider(SwingConstants.VERTICAL, JSLIDER_MIN, JSLIDER_MAX, 500);
			}
		try
			{
			jSliderAltitudeDt = new JSlider(SwingConstants.VERTICAL, JSLIDER_MIN, JSLIDER_MAX, (int)afficheurServiceMOO.getAltitudeDT());
			}
		catch (RemoteException e)
			{
			jSliderAltitudeDt = new JSlider(SwingConstants.VERTICAL, JSLIDER_MIN, JSLIDER_MAX, 500);
			}
		try
			{

			jSliderPressureDt = new JSlider(SwingConstants.VERTICAL, JSLIDER_MIN, JSLIDER_MAX, (int)afficheurServiceMOO.getPressureDT());
			}
		catch (RemoteException e)
			{
			jSliderPressureDt = new JSlider(SwingConstants.VERTICAL, JSLIDER_MIN, JSLIDER_MAX, 500);
			}

		jSliderTemperatureDt.setMajorTickSpacing(JSLIDER_STEP);
		jSliderAltitudeDt.setMajorTickSpacing(JSLIDER_STEP);
		jSliderPressureDt.setMajorTickSpacing(JSLIDER_STEP);

		jSliderTemperatureDt.setPaintTicks(true);
		jSliderAltitudeDt.setPaintTicks(true);
		jSliderPressureDt.setPaintTicks(true);

		jSliderTemperatureDt.setPaintLabels(true);
		jSliderAltitudeDt.setPaintLabels(true);
		jSliderPressureDt.setPaintLabels(true);

		jSliderTemperatureDt.setBackground(JFrameAfficheurService.BACKGROUND_COLOR);
		jSliderAltitudeDt.setBackground(JFrameAfficheurService.BACKGROUND_COLOR);
		jSliderPressureDt.setBackground(JFrameAfficheurService.BACKGROUND_COLOR);

		jSliderTemperatureDt.setForeground(JFrameAfficheurService.FOREGROUND_COLOR);
		jSliderAltitudeDt.setForeground(JFrameAfficheurService.FOREGROUND_COLOR);
		jSliderPressureDt.setForeground(JFrameAfficheurService.FOREGROUND_COLOR);

		jSliderTemperatureDt.addMouseListener(new MouseAdapter()
			{

				@Override
				public void mouseReleased(MouseEvent arg0)
					{
					if (jSliderTemperatureDt.getValue() == 0)
						{
						jSliderTemperatureDt.setValue(1);
						}
					afficheurServiceMOO.setTemperatureDT(jSliderTemperatureDt.getValue());
					}
			});

		jSliderAltitudeDt.addMouseListener(new MouseAdapter()
			{

				@Override
				public void mouseReleased(MouseEvent arg0)
					{
					if (jSliderAltitudeDt.getValue() == 0)
						{
						jSliderAltitudeDt.setValue(1);
						}
					afficheurServiceMOO.setAltitudeDT(jSliderAltitudeDt.getValue());
					}
			});

		jSliderPressureDt.addMouseListener(new MouseAdapter()
			{

				@Override
				public void mouseReleased(MouseEvent arg0)
					{
					if (jSliderPressureDt.getValue() == 0)
						{
						jSliderPressureDt.setValue(1);
						}
					afficheurServiceMOO.setPressureDT(jSliderPressureDt.getValue());
					}
			});

		Box boxH = Box.createHorizontalBox();

		Box boxTemperature = Box.createVerticalBox();
		JLabel jLabelTemperature = new JLabel("Température Dt");
		JPanelStation.setupJLabelStyle(jLabelTemperature, 14);
		boxTemperature.add(jLabelTemperature);
		boxTemperature.add(jSliderTemperatureDt);
		boxH.add(boxTemperature);
		boxH.add(Box.createHorizontalGlue());

		Box boxAltitude = Box.createVerticalBox();
		JLabel jLabelAltitude = new JLabel("Altitude Dt");
		JPanelStation.setupJLabelStyle(jLabelAltitude, 14);
		boxAltitude.add(jLabelAltitude);
		boxAltitude.add(jSliderAltitudeDt);
		boxH.add(boxAltitude);
		boxH.add(Box.createHorizontalGlue());

		Box boxPressure = Box.createVerticalBox();
		JLabel jLabelPressure = new JLabel("Pression Dt");
		JPanelStation.setupJLabelStyle(jLabelPressure, 14);
		boxPressure.add(jLabelPressure);
		boxPressure.add(jSliderPressureDt);
		boxH.add(boxPressure);
		boxH.add(Box.createHorizontalGlue());

		add(boxH, BorderLayout.CENTER);
		}

	private void control()
		{
		//Rien
		}

	private void apparence()
		{
		setBackground(JFrameAfficheurService.BACKGROUND_COLOR);
		}

	private void checkDelta()
		{
		try
			{
			jSliderTemperatureDt.setValue((int)afficheurServiceMOO.getTemperatureDT());
			jSliderAltitudeDt.setValue((int)afficheurServiceMOO.getAltitudeDT());
			jSliderPressureDt.setValue((int)afficheurServiceMOO.getPressureDT());
			}
		catch (RemoteException e)
			{
			}
		}

	@Override
	@Transient
	public Dimension getMaximumSize()
		{
		return new Dimension(300, 150);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Inputs/Outputs
	private AfficheurServiceMOO afficheurServiceMOO;
	private JSlider jSliderTemperatureDt;
	private JSlider jSliderAltitudeDt;
	private JSlider jSliderPressureDt;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final int JSLIDER_MIN = 500;
	private static final int JSLIDER_MAX = 3000;
	private static final int JSLIDER_STEP = 500;

	}
