
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

import ch.hearc.meteo.imp.afficheur.real.moo.AfficheurServiceMOO;
import ch.hearc.meteo.imp.afficheur.real.moo.Trend;

public class JPanelPression extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelPression(AfficheurServiceMOO afficheurServiceMOO)
		{
		this.afficheurServiceMOO = afficheurServiceMOO;

		createDataset();

		geometry();
		control();
		apparence();
		}

	private void createDataset()
		{
		currentPression = new DefaultValueDataset(0.0);
		meanPression = new DefaultValueDataset(0.0);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void refresh()
		{
		float currentPressionValue = (afficheurServiceMOO.getLastPression() == null) ? 0 : afficheurServiceMOO.getLastPression().getValue();
		float currentTemperatureValue = (afficheurServiceMOO.getLastTemperature() == null) ? 0 : afficheurServiceMOO.getLastTemperature().getValue();
		float currentAltitudeValue = (afficheurServiceMOO.getLastAltitude() == null) ? 0 : afficheurServiceMOO.getLastAltitude().getValue();

		currentPression.setValue(currentPressionValue);
		meanPression.setValue(afficheurServiceMOO.getStatPression().getMoy());
		jLabelPression.setText(String.format("Pression actuel : %.2f", currentPressionValue) + UNITY);
		jLabelSeeLevelPression.setText(String.format("Pression niveau de la mère : %.2f", reducePressionToSeeLevel(currentPressionValue, currentAltitudeValue, currentTemperatureValue)) + UNITY);
		jLabelMeanPression.setText(String.format("Ø : %.2f", afficheurServiceMOO.getStatPression().getMoy()) + UNITY);

		StringBuilder stringBuilder = new StringBuilder();

		if (currentPressionValue < 1000)
			{
			stringBuilder.append("Pluie");
			}
		else if (currentPressionValue > 1030)
			{
			stringBuilder.append("Beau");
			}
		else
			{
			stringBuilder.append("Variable");
			}

		if (afficheurServiceMOO.getStatPression().getTrend() == Trend.up)
			{
			stringBuilder.append(" \u25B2");
			}
		else if (afficheurServiceMOO.getStatPression().getTrend() == Trend.down)
			{
			stringBuilder.append(" \u25BC");
			}

		jLabelTrend.setText(stringBuilder.toString());
		}

	public void updateGUI()
		{

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/
	/**
	 * http://rosset.org/linux/pressure/howto6.html
	 */
	private float reducePressionToSeeLevel(final float pression, final float altitude, final float temperature)
		{
		final float ACCELERATION_CONSTANT = 9.80665f;
		final float AIR_CONSTANT = 287.04f;
		float temperatureKelvin = temperature + 273.15f;

		return (float)(pression * Math.pow(Math.E, (ACCELERATION_CONSTANT * altitude) / (AIR_CONSTANT * temperatureKelvin)));
		}

	private void control()
		{
		//Rien
		}

	private void geometry()
		{
		setLayout(new BorderLayout());

		Box boxH = Box.createHorizontalBox();

		jLabelPression = new JLabel();
		jLabelSeeLevelPression = new JLabel();
		jLabelMeanPression = new JLabel();
		jLabelTrend = new JLabel();
		JPanelStation.setJLabelStyle(jLabelPression, 24);
		JPanelStation.setJLabelStyle(jLabelSeeLevelPression, 24);
		JPanelStation.setJLabelStyle(jLabelMeanPression, 24);
		JPanelStation.setJLabelStyle(jLabelTrend, 24);
		Box boxV = Box.createVerticalBox();

		boxV.add(jLabelPression);
		boxV.add(jLabelSeeLevelPression);
		boxV.add(jLabelMeanPression);
		boxV.add(jLabelTrend);

		SquarePanel squarePanel = new SquarePanel();
		squarePanel.setLayout(new BorderLayout());
		squarePanel.add(createDial());

		boxH.add(boxV);
		boxH.add(Box.createHorizontalGlue());
		boxH.add(squarePanel);
		boxH.add(Box.createHorizontalGlue());

		add(boxH, BorderLayout.CENTER);
		}

	private ChartPanel createDial()
		{
		DialPlot dialplot = new DialPlot();
		dialplot.setDataset(0, currentPression);
		dialplot.setDataset(1, meanPression);

		StandardDialFrame standarddialframe = new StandardDialFrame();
		standarddialframe.setBackgroundPaint(Color.WHITE);
		standarddialframe.setForegroundPaint(Color.DARK_GRAY);
		dialplot.setDialFrame(standarddialframe);

		GradientPaint gradientpaint = new GradientPaint(new Point(), JFrameAfficheurService.BACKGROUND_COLOR, new Point(), new Color(52, 152, 219));
		DialBackground dialbackground = new DialBackground(gradientpaint);
		dialbackground.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
		dialplot.setBackground(dialbackground);

		DialTextAnnotation dialtextannotation = new DialTextAnnotation(UNITY);
		dialtextannotation.setFont(new Font("Dialog", 1, 14));
		dialtextannotation.setRadius(0.6);
		dialplot.addLayer(dialtextannotation);

		StandardDialScale standarddialscale = new StandardDialScale(MIN_PRESSION, MAX_PRESSION, -120.0, -300.0, MAJOR_TICK, MINOR_TICK);
		standarddialscale.setTickRadius(0.9);
		standarddialscale.setTickLabelOffset(0.18);
		standarddialscale.setMajorTickPaint(JFrameAfficheurService.FOREGROUND_COLOR);
		standarddialscale.setMinorTickPaint(JFrameAfficheurService.FOREGROUND_COLOR);
		standarddialscale.setTickLabelFont(new Font("Dialog", 0, 14));
		standarddialscale.setTickLabelFormatter(new DecimalFormat("#"));
		standarddialscale.setTickLabelPaint(JFrameAfficheurService.FOREGROUND_COLOR);
		dialplot.addScale(0, standarddialscale);

		StandardDialScale standarddialscale1 = new StandardDialScale(MIN_PRESSION, MAX_PRESSION, -120.0, -300.0, MAJOR_TICK, MINOR_TICK);
		standarddialscale1.setTickRadius(0.5);
		standarddialscale1.setTickLabelOffset(0.15);
		standarddialscale1.setTickLabelFont(new Font("Dialog", 0, 10));
		standarddialscale1.setTickLabelFormatter(new DecimalFormat("#"));
		standarddialscale1.setMajorTickPaint(Color.RED);
		standarddialscale1.setMinorTickPaint(Color.RED);
		standarddialscale1.setTickLabelPaint(JFrameAfficheurService.FOREGROUND_COLOR);
		dialplot.addScale(1, standarddialscale1);

		dialplot.mapDatasetToScale(1, 1);

		//Petite aiguille
		org.jfree.chart.plot.dial.DialPointer.Pin pin = new org.jfree.chart.plot.dial.DialPointer.Pin(1); // Index du dataset (mean)
		pin.setRadius(0.5);//Taille
		dialplot.addPointer(pin);

		//Grande aiguille
		org.jfree.chart.plot.dial.DialPointer.Pointer pointer = new org.jfree.chart.plot.dial.DialPointer.Pointer(0);
		dialplot.addPointer(pointer);

		// Centre
		DialCap dialcap = new DialCap();
		dialcap.setFillPaint(gradientpaint);
		dialplot.setCap(dialcap);

		JFreeChart jfreechart = new JFreeChart(dialplot);
		jfreechart.setTitle(TITLE);
		jfreechart.setBackgroundPaint(JFrameAfficheurService.BACKGROUND_COLOR);

		ChartPanel chartpanel = new ChartPanel(jfreechart, false);
		return chartpanel;
		}

	private void apparence()
		{
		setBackground(JFrameAfficheurService.BACKGROUND_COLOR);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Inputs
	private JLabel jLabelMeanPression;
	private JLabel jLabelPression;
	private JLabel jLabelSeeLevelPression;
	private JLabel jLabelTrend;
	private AfficheurServiceMOO afficheurServiceMOO;
	private DefaultValueDataset currentPression;
	private DefaultValueDataset meanPression;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final String TITLE = "Pression atmosphérique";
	private static final String UNITY = "hPa";
	private static final Double MIN_PRESSION = 970.0;
	private static final Double MAX_PRESSION = 1060.0;
	private static final Double MAJOR_TICK = 10.0;
	private static final Integer MINOR_TICK = 9;

	/**
	 * http://stackoverflow.com/questions/16075022/making-a-jpanel-square
	 */
	class SquarePanel extends JPanel
		{

		@Override
		public Dimension getMinimumSize()
			{
			return new Dimension(10, 10);
			}

		@Override
		public Dimension getMaximumSize()
			{
			return new Dimension(500, 500);
			}

		@Override
		public Dimension getPreferredSize()
			{
			Dimension d = super.getPreferredSize();
			java.awt.Container c = getParent();
			if (c != null)
				{
				d = c.getSize();
				}
			else
				{
				return new Dimension(10, 10);
				}
			int w = (int)d.getWidth();
			int h = (int)d.getHeight();
			int s = (w < h ? w : h);
			return new Dimension(s, s);
			}
		}
	}
