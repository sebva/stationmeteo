
package ch.hearc.meteo.imp.afficheur.simulateur.vue.atome;

import java.awt.Component;
import java.util.List;
import java.util.ListIterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;

public class BoxSerieTemporelle extends Box
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public BoxSerieTemporelle(List<MeteoEvent> listMeteoEvent)
		{
		super(BoxLayout.X_AXIS);

		this.listMeteoEvent = listMeteoEvent;

		geometry();
		control();
		apparence();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update()
		{
		vider();
		fill();

		validate();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		// rien
		}

	private void apparence()
		{
		setBorder(BorderFactory.createTitledBorder("Graphe"));
		}

	private void control()
		{
		// rien
		}

	/*------------------------------*\
	|*			  geometry			*|
	\*------------------------------*/

	private void fill()
		{
		if (listMeteoEvent.size() > 3)
			{
			ListIterator<MeteoEvent> it = listMeteoEvent.listIterator(listMeteoEvent.size() - 3);
			while(it.hasNext())
				{
				add(new JLabel(MathTools.arrondir(it.next().getValue())));
				add(Box.createHorizontalStrut(15));
				}
			}
		else
			{
			for(MeteoEvent meteoEvent:listMeteoEvent)
				{
				add(new JLabel(MathTools.arrondir(meteoEvent.getValue())));
				add(Box.createHorizontalStrut(15));
				}
			}
		}

	private void vider()
		{
		for(Component compo:this.getComponents())
			{
			this.remove(compo);
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private List<MeteoEvent> listMeteoEvent;

	}
