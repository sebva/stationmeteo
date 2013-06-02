
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;

public class JPanelMeteoEventGraph extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelMeteoEventGraph(String title, Icon icon, String xLabel, String yLabel, Color plotColor, Color backgroundColor, Color plotBackground, boolean showLegend)
		{
		this.title = title;
		this.icon = icon;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.n = JSLIDER_VALUE_MIN;
		this.plotColor = plotColor;
		this.backgroundColor = backgroundColor;
		this.plotBackground = plotBackground;
		this.showLegend = showLegend;
		lowerRange = null;
		upperRange = null;
		lowerDomain = null;
		upperDomain = null;

		this.datas = new ArrayList<TimeSeries>();
		this.meteoEvents = new ArrayList<List<MeteoEvent>>();
		this.dataSet = new TimeSeriesCollection();

		geometry();
		control();
		apparence();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public synchronized void addDatas(List<MeteoEvent> list, String title)
		{
		TimeSeries serie = new TimeSeries(title);
		serie.setMaximumItemCount(n);

		meteoEvents.add(list);
		datas.add(serie);
		dataSet.addSeries(serie);
		}

	public synchronized void removeDatas(List<MeteoEvent> list)
		{
		int index = meteoEvents.indexOf(list);
		dataSet.removeSeries(index);
		meteoEvents.remove(index);
		datas.remove(index);
		}

	public synchronized void refresh()
		{
		ListIterator<List<MeteoEvent>> meteoEventsListIterator = meteoEvents.listIterator();
		int i = 0;
		upperRange = lowerRange = null;
		upperDomain = lowerDomain = null;
		while(meteoEventsListIterator.hasNext())
			{
			List<MeteoEvent> meteoEventList = meteoEventsListIterator.next();

			synchronized (meteoEventList)
				{
				TimeSeries timeSeries = dataSet.getSeries(i);

				if (meteoEventList.size() > 0)
					{
					MeteoEvent lastMeteoEvent = meteoEventList.get(meteoEventList.size() - 1);
					timeSeries.addOrUpdate(new Millisecond(new Date(lastMeteoEvent.getTime())), lastMeteoEvent.getValue());
					computeRangeByNewValue(lastMeteoEvent.getValue());
					computeDomainByNewValue(lastMeteoEvent.getTime());
					}

				ListIterator<MeteoEvent> iterator = meteoEventList.listIterator(meteoEventList.size());
				int j = 0;
				while(iterator.hasPrevious() && j < n)
					{
					try
						{
						MeteoEvent meteoEvent = iterator.previous();
						timeSeries.addOrUpdate(new Millisecond(new Date(meteoEvent.getTime())), meteoEvent.getValue());
						computeRangeByNewValue(meteoEvent.getValue());
						computeDomainByNewValue(meteoEvent.getTime());
						}
					catch (Exception e)
						{
						// Rien
						}
					j++;
					}
				}
			i++;
			}
		checkN();
		setRange();
		setDomain();
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setN(int n)
		{
		int oldN = this.n;
		this.n = n;
		for(TimeSeries timeSeries:datas)
			{
			timeSeries.setMaximumItemCount(n);
			}
		if (oldN < n)
			{
			refresh();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		setLayout(new BorderLayout());
		final JFreeChart chart = createChart(dataSet);
		ChartPanel chartPanel = new ChartPanel(chart)
			{

				@Override
				public void paintComponent(Graphics g)
					{
					super.paintComponent(g);
					Dimension size = this.getSize();
					int w = (int)Math.rint(size.width);
					int h = (int)Math.rint(size.height);
					chart.draw((Graphics2D)g, new Rectangle2D.Double(0, 0, w, h));
					}
			};

		chartPanel.setDomainZoomable(false);
		chartPanel.setRangeZoomable(false);

		jSliderN = new JSlider(JSLIDER_VALUE_MIN, JSLIDER_VALUE_MIN, JSLIDER_VALUE_MIN);
		jSliderN.setPaintTicks(true);
		jSliderN.setPaintLabels(true);
		jSliderN.addChangeListener(new ChangeListener()
			{

				@Override
				public void stateChanged(ChangeEvent arg0)
					{
					setN(jSliderN.getValue());
					}
			});

		jSliderN.setBackground(JFrameAfficheurService.BACKGROUND_COLOR);
		jSliderN.setForeground(JFrameAfficheurService.FOREGROUND_COLOR);

		Box boxV = Box.createVerticalBox();
		boxV.add(chartPanel);
		boxV.add(jSliderN);

		add(boxV, BorderLayout.CENTER);

		add(new JLabel(icon), BorderLayout.WEST);
		}

	private void control()
		{
		//Rien
		}

	private void apparence()
		{
		setBackground(JFrameAfficheurService.BACKGROUND_COLOR);
		}

	private JFreeChart createChart(TimeSeriesCollection dataset)
		{
		JFreeChart chart = org.jfree.chart.ChartFactory.createTimeSeriesChart(title, xLabel, yLabel, dataset, showLegend, false, false);

		plot = (XYPlot)chart.getPlot();

		chart.setBackgroundPaint(backgroundColor);
		chart.getTitle().setPaint(plotColor);
		plot.setBackgroundPaint(plotBackground);
		plot.setDomainGridlinePaint(Color.BLACK);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainCrosshairVisible(true);

		domainAxis = (DateAxis)plot.getDomainAxis();
		domainAxis.setTickLabelPaint(JFrameAfficheurService.FOREGROUND_COLOR);
		domainAxis.setLabelPaint(JFrameAfficheurService.FOREGROUND_COLOR);
		domainAxis.setDateFormatOverride(new SimpleDateFormat("HH:mm:ss"));
		plot.getRenderer().setSeriesPaint(0, plotColor);
		plot.getRenderer().setBaseStroke(new BasicStroke(2));
		((AbstractRenderer)plot.getRenderer()).setAutoPopulateSeriesStroke(false);
		rangeAxis = plot.getRangeAxis();
		rangeAxis.setTickLabelPaint(JFrameAfficheurService.FOREGROUND_COLOR);
		rangeAxis.setLabelPaint(JFrameAfficheurService.FOREGROUND_COLOR);
		setRange();
		setDomain();

		return chart;
		}

	private void checkN()
		{
		ListIterator<List<MeteoEvent>> meteoEventsListIterator = meteoEvents.listIterator();
		int max = 0;
		while(meteoEventsListIterator.hasNext())
			{
			List<MeteoEvent> meteoEventList = meteoEventsListIterator.next();
			int n = meteoEventList.size();
			if (max < n)
				{
				max = n;
				}
			}
		if (max < JSLIDER_VALUE_MIN)
			{
			max = JSLIDER_VALUE_MIN;
			}
		jSliderN.setMaximum(max);
		setMajorTickSpacing(jSliderN, max);
		/*int spacing = (((max - JSLIDER_VALUE_MIN) / 10) < 10) ? 10 : ((max - JSLIDER_VALUE_MIN) / 10);
		jSliderN.setMajorTickSpacing(spacing);
		jSliderN.createStandardLabels(spacing);*/
		}

	/**
	 * https://forums.oracle.com/forums/thread.jspa?threadID=1353301
	 */
	private void setMajorTickSpacing(JSlider slider, int maxValue)
		{
		Graphics graphics = slider.getGraphics();
		FontMetrics fontMetrics = graphics.getFontMetrics();
		int width = slider.getWidth();
		// try with the following values:
		// 1,2,5,10,20,50,100,200,500,...
		int tickSpacing = 1;
		for(int i = 0, tmpWidthSum = width + 1; tmpWidthSum > (width / 2); i++)
			{
			tickSpacing = (int)Math.pow(10, (i / 3));
			switch(i % 3)
				{
				case 1:
					tickSpacing *= 2;
					break;
				case 2:
					tickSpacing *= 5;
				}
			tmpWidthSum = 0;
			for(int j = 0; j < maxValue; j += tickSpacing)
				{
				Rectangle2D stringBounds = fontMetrics.getStringBounds(String.valueOf(j), graphics);
				tmpWidthSum += (int)stringBounds.getWidth();
				if (tmpWidthSum > (width / 2))
					{
					// the labels are longer than the slider
					break;
					}
				}
			}
		slider.setMajorTickSpacing(tickSpacing);
		slider.setLabelTable(slider.createStandardLabels(tickSpacing));
		}

	private void computeDomainByNewValue(long value)
		{
		if (upperDomain == null || lowerDomain == null)
			{
			upperDomain = lowerDomain = value;
			}
		if (upperDomain < value)
			{
			upperDomain = value;
			}
		else if (lowerDomain > value)
			{
			lowerDomain = value;
			}
		}

	private void setDomain()
		{
		if (upperDomain == null || lowerDomain == null) { return; }
		domainAxis.setMinimumDate(new Date(lowerDomain));
		domainAxis.setMaximumDate(new Date(upperDomain));
		}

	private void computeRangeByNewValue(float value)
		{
		if (upperRange == null || lowerRange == null)
			{
			upperRange = lowerRange = value;
			}
		if (upperRange < value)
			{
			upperRange = value;
			}
		else if (lowerRange > value)
			{
			lowerRange = value;
			}
		}

	private void setRange()
		{
		if (upperRange == null || lowerRange == null) { return; }

		double max = (Math.abs(lowerRange) > Math.abs(upperRange)) ? Math.abs(lowerRange) : Math.abs(upperRange);
		double offset = max * (OFFSET_RANGE_PERCENT / 100);
		if (offset < OFFSET_RANGE_MIN)
			{
			offset = OFFSET_RANGE_MIN;
			}
		rangeAxis.setRange(lowerRange - offset, upperRange + offset);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Inputs/Outputs
	private String title;
	private String xLabel;
	private String yLabel;
	private int n;
	private Color plotColor;
	private Color backgroundColor;
	private Color plotBackground;
	private List<List<MeteoEvent>> meteoEvents;
	private boolean showLegend;
	private Icon icon;

	//Outputs

	//Tools
	private JSlider jSliderN;
	private List<TimeSeries> datas;
	private TimeSeriesCollection dataSet;
	private Float lowerRange;
	private Float upperRange;
	private Long lowerDomain;
	private Long upperDomain;
	private ValueAxis rangeAxis;
	private DateAxis domainAxis;
	private XYPlot plot;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final int JSLIDER_VALUE_MIN = 10;
	private static final double OFFSET_RANGE_MIN = 1.0;
	private static final double OFFSET_RANGE_PERCENT = 5.0;

	}
