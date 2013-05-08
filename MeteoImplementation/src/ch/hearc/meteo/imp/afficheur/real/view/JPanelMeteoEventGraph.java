
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import ch.hearc.meteo.imp.afficheur.real.moo.AfficheurServiceMOO;
import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;

public class JPanelMeteoEventGraph extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelMeteoEventGraph(String title, String xLabel, String yLabel, int n, Color plotColor, Color backgroundColor, boolean showLegend, AfficheurServiceMOO afficheurServiceMOO)
		{
		this.title = title;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.n = n;
		this.plotColor = plotColor;
		this.backgroundColor = backgroundColor;
		this.showLegend = showLegend;
		this.afficheurServiceMOO = afficheurServiceMOO;

		this.datas = new ArrayList<TimeSeries>();
		this.meteoEvents = new ArrayList<List<MeteoEvent>>();
		this.dataSet = new TimeSeriesCollection();

		geometry();
		control();
		apparence();
		if (afficheurServiceMOO != null)
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
								Thread.sleep(1000);
								checkN();
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

	public synchronized void addDatas(List<MeteoEvent> list, String title)
		{
		meteoEvents.add(list);

		TimeSeries serie = new TimeSeries(title);
		datas.add(serie);
		dataSet.addSeries(serie);
		}

	public synchronized void removeDatas(List<MeteoEvent> list)
		{
		int index = meteoEvents.indexOf(list);
		dataSet.removeSeries(index);
		meteoEvents.remove(index);
		}

	public synchronized void refresh()
		{
		ListIterator<List<MeteoEvent>> meteoEventsListIterator = meteoEvents.listIterator();
		int i = 0;
		while(meteoEventsListIterator.hasNext())
			{
			List<MeteoEvent> meteoEventList = meteoEventsListIterator.next();
			synchronized (meteoEventList)
				{
				TimeSeries timeSeries = datas.get(i);
				if (meteoEventList.size() > 0)
					{
					MeteoEvent lastMeteoEvent = meteoEventList.get(meteoEventList.size() - 1);
					timeSeries.addOrUpdate(new Millisecond(new Date(lastMeteoEvent.getTime())), lastMeteoEvent.getValue());
					}

				ListIterator<MeteoEvent> iterator = meteoEventList.listIterator(meteoEventList.size());
				while(iterator.hasPrevious() && timeSeries.getItemCount() < n)
					{
					MeteoEvent meteoEvent = iterator.previous();
					timeSeries.addOrUpdate(new Millisecond(new Date(meteoEvent.getTime())), meteoEvent.getValue());
					}
				}
			i++;
			}
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
		if (afficheurServiceMOO != null)
			{
			afficheurServiceMOO.setN(n);
			}
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		setLayout(new BorderLayout());
		JFreeChart chart = createChart(dataSet);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setDomainZoomable(false);
		chartPanel.setRangeZoomable(false);
		jSliderN = new JSlider(5, 500, n);
		jSliderN.setPaintTicks(true);
		jSliderN.setPaintLabels(true);
		jSliderN.setMajorTickSpacing(20);
		jSliderN.addChangeListener(new ChangeListener()
			{

				@Override
				public void stateChanged(ChangeEvent arg0)
					{
					setN(jSliderN.getValue());
					}
			});
		jSliderN.setBackground(JFrameAfficheurService.BACKGROUND_COLOR);

		Box boxV = Box.createVerticalBox();
		boxV.add(chartPanel);
		boxV.add(jSliderN);

		add(boxV, BorderLayout.CENTER);
		}

	private void control()
		{
		//Rien
		}

	private void apparence()
		{
		//Rien
		}

	private JFreeChart createChart(TimeSeriesCollection dataset)
		{
		JFreeChart chart = org.jfree.chart.ChartFactory.createTimeSeriesChart(title, xLabel, yLabel, dataset, showLegend, false, false);

		XYPlot plot = (XYPlot)chart.getPlot();

		chart.setBackgroundPaint(backgroundColor);
		plot.setBackgroundPaint(backgroundColor);
		plot.setDomainGridlinePaint(Color.BLACK);
		plot.setRangeGridlinePaint(Color.BLACK);

		DateAxis axis = (DateAxis)plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("HH:mm:ss"));
		plot.getRenderer().setSeriesPaint(0, plotColor);

		return chart;
		}

	private void checkN()
		{
		if (afficheurServiceMOO.getN() != n)
			{
			System.out.println("n changed");
			jSliderN.setValue(n);
			setN(n);
			}
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
	private List<List<MeteoEvent>> meteoEvents;
	private boolean showLegend;
	private AfficheurServiceMOO afficheurServiceMOO;

	//Outputs

	//Tools
	private JSlider jSliderN;
	private List<TimeSeries> datas;
	private TimeSeriesCollection dataSet;

	}
