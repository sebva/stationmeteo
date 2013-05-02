
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;

public class JPanelGraph extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void addData(List<MeteoEvent> list)
		{
		while(datas.getItemCount() >= n)
			{
			datas.delete(datas.getDataItem(0).getPeriod());
			}

		ListIterator<MeteoEvent> iterator = list.listIterator(list.size());
		while(datas.getItemCount() < n && iterator.hasPrevious())
			{
			MeteoEvent meteoEvent = iterator.previous();
			datas.addOrUpdate(new Millisecond(new Date(meteoEvent.getTime())), meteoEvent.getValue());
			}
		}

	public JPanelGraph(String title, String xLabel, String yLabel, int n, Color plotColor, Color backgroundColor)
		{
		this.title = title;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.n = n;
		this.plotColor = plotColor;
		this.backgroundColor = backgroundColor;

		geometry();
		control();
		apparence();
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		JFreeChart chart = createChart(createDataset());
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setDomainZoomable(false);
		chartPanel.setRangeZoomable(false);
		//chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		add(chartPanel);
		}

	private void control()
		{
		//Rien
		}

	private void apparence()
		{
		//Rien
		}

	private TimeSeriesCollection createDataset()
		{
		datas = new TimeSeries(title);
		TimeSeriesCollection dataSet = new TimeSeriesCollection();

		dataSet.addSeries(datas);

		return dataSet;
		}

	private JFreeChart createChart(TimeSeriesCollection dataset)
		{
		JFreeChart chart = org.jfree.chart.ChartFactory.createTimeSeriesChart(title, xLabel, yLabel, dataset, false, false, false);

		XYPlot plot = (XYPlot)chart.getPlot();

		//chart.setBackgroundPaint(backgroundColor);
		plot.setBackgroundPaint(backgroundColor);
		plot.setDomainGridlinePaint(Color.BLACK);
		plot.setRangeGridlinePaint(Color.BLACK);

		DateAxis axis = (DateAxis)plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("HH:mm:ss"));
		plot.getRenderer().setSeriesPaint(0, plotColor);

		return chart;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Inputs
	private String title;
	private String xLabel;
	private String yLabel;
	private int n;
	private Color plotColor;
	private Color backgroundColor;
	private TimeSeries datas;

	}
