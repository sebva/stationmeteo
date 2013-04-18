
package ch.hearc.meteo.imp.afficheur.simulateur.vue.atome;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.Stat;

public class JPanelStat extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelStat(Stat stat)
		{
		this.stat = stat;

		geometry();
		control();
		apparence();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update()
		{
		labelCurrent.setText("" + MathTools.arrondir(stat.getLast()));
		labelMin.setText("min " + MathTools.arrondir(stat.getMin()));
		labelMax.setText("max " + MathTools.arrondir(stat.getMax()));
		labelMoy.setText("moy " + MathTools.arrondir(stat.getMoy()));
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		labelCurrent = new JLabel("-----");

		boxV();

		GridLayout layout = new GridLayout(1, 1);
		layout.setHgap(15);
		setLayout(layout);
		add(labelCurrent);
		add(boxV);
		}

	private void boxV()
		{
		labelMin = new JLabel("min ------");
		labelMax = new JLabel("max ------");
		labelMoy = new JLabel("moy ------");

		boxV = Box.createVerticalBox();

		boxV.add(labelMin);
		boxV.add(labelMax);
		boxV.add(Box.createVerticalGlue());
		boxV.add(labelMoy);
		}

	private void apparence()
		{
		labelCurrent.setFont(new Font("courier", Font.BOLD, 25));
		labelCurrent.setForeground(Color.RED);

		setBorder(BorderFactory.createTitledBorder("Statistique"));
		}

	private void control()
		{
		// rien
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private Stat stat;

	// Tools
	private JLabel labelCurrent;
	private JLabel labelMin;
	private JLabel labelMax;
	private JLabel labelMoy;

	private Box boxV;

	}
