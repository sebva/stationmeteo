
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.text.DecimalFormat;

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

import ch.hearc.meteo.imp.afficheur.real.moo.Pressure;

public class JPanelDialPressure extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelDialPressure(Pressure pressure)
		{
		this.pressure = pressure;
		createDataset();

		geometry();
		control();
		apparence();
		}

	private void createDataset()
		{
		currentSeaLevelPressure = new DefaultValueDataset(0.0);
		currentPressure = new DefaultValueDataset(0.0);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void refresh()
		{
		currentSeaLevelPressure.setValue(pressure.getSeaLevelPressure());
		currentPressure.setValue(pressure.getPressure());
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/
	@Override
	public Dimension getMaximumSize()
		{
		return new Dimension(300, 300);
		}

	private void control()
		{
		//Rien
		}

	private void geometry()
		{
		setLayout(new BorderLayout());
		add(createDial(), BorderLayout.CENTER);
		}

	private void apparence()
		{
		setBackground(JFrameAfficheurService.BACKGROUND_COLOR);
		}

	private ChartPanel createDial()
		{
		DialPlot dialplot = new DialPlot();
		dialplot.setDataset(0, currentSeaLevelPressure);
		dialplot.setDataset(1, currentPressure);

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

		StandardDialScale standarddialscale = new StandardDialScale(MIN_PRESSURE_SEA, MAX_PRESSURE_SEA, -120.0, -300.0, MAJOR_TICK, MINOR_TICK);
		standarddialscale.setTickRadius(0.9);
		standarddialscale.setTickLabelOffset(0.18);
		standarddialscale.setMajorTickPaint(JFrameAfficheurService.FOREGROUND_COLOR);
		standarddialscale.setMinorTickPaint(JFrameAfficheurService.FOREGROUND_COLOR);
		standarddialscale.setTickLabelFont(new Font("Dialog", 0, 14));
		standarddialscale.setTickLabelFormatter(new DecimalFormat("#"));
		standarddialscale.setTickLabelPaint(JFrameAfficheurService.FOREGROUND_COLOR);
		dialplot.addScale(0, standarddialscale);

		StandardDialScale standarddialscale1 = new StandardDialScale(MIN_PRESSURE, MAX_PRESSURE, -120.0, -300.0, MAJOR_TICK, MINOR_TICK);
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

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Inputs
	private DefaultValueDataset currentSeaLevelPressure;
	private DefaultValueDataset currentPressure;
	private Pressure pressure;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static final String TITLE = "Pression atmosphérique";
	public static final String UNITY = "hPa";
	private static final Double MIN_PRESSURE_SEA = 970.0;
	private static final Double MAX_PRESSURE_SEA = 1060.0;
	private static final Double MIN_PRESSURE = 910.0;
	private static final Double MAX_PRESSURE = 1000.0;
	private static final Double MAJOR_TICK = 10.0;
	private static final Integer MINOR_TICK = 9;

	}
