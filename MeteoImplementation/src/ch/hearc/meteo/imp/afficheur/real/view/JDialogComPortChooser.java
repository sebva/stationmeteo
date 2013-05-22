
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import ch.hearc.meteo.imp.afficheur.real.ImageTools;
import ch.hearc.meteo.spec.meteo.MeteoPortDetectionServiceFactory;
import ch.hearc.meteo.spec.meteo.MeteoPortDetectionService_I;

public class JDialogComPortChooser extends JDialog
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * Appeler ce constructeur, puis setVisible(true), et enfin getSelectedPorts()
	 * pour obtenir la liste des ports choisis.
	 */
	public JDialogComPortChooser()
		{
		geometry();
		control();
		apparence();

		discoverPorts();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public List<String> getSelectedPorts()
		{
		return selectedPorts;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		setLayout(new BorderLayout());

		listModel = new DefaultListModel<>();
		list = new JList<>(listModel);
		add(list, BorderLayout.CENTER);
		selectButton = new JButton("Valider");
		manualButton = new JButton("Saisie manuelle");

		Box boxButtons = Box.createHorizontalBox();
		boxButtons.add(selectButton);
		boxButtons.add(Box.createHorizontalGlue());
		boxButtons.add(manualButton);
		add(boxButtons, BorderLayout.SOUTH);
		}

	private void control()
		{
		setModal(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		selectButton.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
					{
					getPortsAndDispose();
					}
			});

		manualButton.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
					{
					String port = JOptionPane.showInputDialog(JDialogComPortChooser.this, "Entrez le nom du port série", "Saisie port série", JOptionPane.PLAIN_MESSAGE);
					if(port != null && !"".equals(port))
						{
						selectedPorts = new ArrayList<>(1);
						selectedPorts.add(port);

						dispose();
						}
					}
			});

		addWindowListener(new WindowAdapter()
			{

				@Override
				public void windowClosing(WindowEvent e)
					{
					System.exit(0);
					}
			});
		}

	private void apparence()
		{
		setSize(400, 400);
		setLocationRelativeTo(null);
		setTitle("Stations météo détectées");

		list.setCellRenderer(new DefaultListCellRenderer()
			{

				@Override
				public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
					{
					@SuppressWarnings("unchecked")
					Entry<String, Boolean> realValue = (Entry<String, Boolean>)value;

					super.getListCellRendererComponent(list, realValue.getKey(), index, isSelected, cellHasFocus);

					String path = realValue.getValue() ? "images/meteo.png" : "images/no-meteo.png";
					ImageIcon icon = ImageTools.loadIconJar(path, true);
					setIcon(icon);
					return this;
					}
			});
		}

	private void discoverPorts()
		{
		SwingWorker<Map<String, Boolean>, Integer> worker = new SwingWorker<Map<String, Boolean>, Integer>()
			{

				@Override
				protected Map<String, Boolean> doInBackground() throws Exception
					{
					MeteoPortDetectionService_I detectionService = MeteoPortDetectionServiceFactory.create();

					List<String> ports = detectionService.findPortSerie();
					int nbPorts = ports.size();
					int current = 0;

					Map<String, Boolean> map = new TreeMap<>();
					for(String port:ports)
						{
						map.put(port, detectionService.isStationMeteoAvailable(port));
						setProgress((100 * ++current) / nbPorts);
						}

					return map;
					}

				@Override
				protected void done()
					{
					try
						{
						Map<String, Boolean> ports = get();
						for(Entry<String, Boolean> port:ports.entrySet())
							{
							listModel.addElement(port);
							}
						}
					catch (Exception e)
						{
						e.printStackTrace();
						}
					waitDialog.dispose();
					}
			};

		waitDialog = new JDialog(this, true);
		waitDialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		final JProgressBar prgBar = new JProgressBar(0, 100);
		worker.addPropertyChangeListener(new PropertyChangeListener()
			{

				@Override
				public void propertyChange(PropertyChangeEvent evt)
					{
					if ("progress".equals(evt.getPropertyName()))
						{
						prgBar.setValue((Integer)evt.getNewValue());
						}
					}
			});

		waitDialog.setLayout(new FlowLayout(FlowLayout.CENTER));
		waitDialog.add(new JLabel("Veuillez patienter, détection des stations météo..."));
		waitDialog.add(prgBar);
		waitDialog.setSize(400, 100);
		waitDialog.setTitle("Détection en cours...");

		worker.execute();
		waitDialog.setVisible(true);
		}

	private void getPortsAndDispose()
		{
		selectedPorts = new ArrayList<String>(listModel.size());

		for(Entry<String, Boolean> port:list.getSelectedValuesList())
			{
			// Pas une station météo
			if (!port.getValue())
				{
				int result = JOptionPane.showConfirmDialog(this, port.getKey() + " n'a pas été détecté comme étant une station météo,  voulez-vous vraiment l'utiliser ?", "Pas une station météo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				if (result != JOptionPane.YES_OPTION)
					{
					continue;
					}
				}

			selectedPorts.add(port.getKey());
			}

		dispose();
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private DefaultListModel<Entry<String, Boolean>> listModel;
	private JDialog waitDialog;
	private JList<Entry<String, Boolean>> list;
	private JButton selectButton;
	private JButton manualButton;

	// Output
	private List<String> selectedPorts;
	}
