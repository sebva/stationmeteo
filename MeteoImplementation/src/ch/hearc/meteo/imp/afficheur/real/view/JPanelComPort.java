
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import ch.hearc.meteo.imp.afficheur.real.ImageTools;
import ch.hearc.meteo.imp.use.remote.PCLocal;
import ch.hearc.meteo.spec.meteo.MeteoPortDetectionServiceFactory;
import ch.hearc.meteo.spec.meteo.MeteoPortDetectionService_I;

public class JPanelComPort extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * Appeler ce constructeur, puis setVisible(true), et enfin getSelectedPorts()
	 * pour obtenir la liste des ports choisis.
	 */
	public JPanelComPort()
		{
		this.detectionService = MeteoPortDetectionServiceFactory.create();

		geometry();
		control();
		apparence();

		populateBanList();
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

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		setLayout(new FlowLayout(FlowLayout.CENTER));

		banListModel = new DefaultListModel<>();
		banList = new JList<>(banListModel);
		detectedListModel = new DefaultListModel<>();
		detectedList = new JList<>(detectedListModel);

		refreshBanListButton = new JButton("Rafraîchir la liste des ports");
		detectButton = new JButton("Lancer la détection");
		validateButton = new JButton("Valider");

		lblDetectedPorts = new JLabel("Ports détectés");
		lblPortsToBan = new JLabel("Ports à bannir de la détection");

		detectionProgress = new JProgressBar(0, 100);

		Box box = Box.createVerticalBox();
		box.add(lblPortsToBan);
		JPanel jPanelBan = new JPanel(new BorderLayout());
		jPanelBan.add(new JScrollPane(banList), BorderLayout.CENTER);
		jPanelBan.add(refreshBanListButton, BorderLayout.SOUTH);
		box.add(jPanelBan);
		box.add(Box.createVerticalStrut(15));
		box.add(lblDetectedPorts);
		box.add(detectionProgress);
		JPanel jPanelDetection = new JPanel(new BorderLayout());
		jPanelDetection.add(new JScrollPane(detectedList), BorderLayout.CENTER);
		jPanelDetection.add(detectButton, BorderLayout.SOUTH);
		box.add(jPanelDetection);
		box.add(Box.createVerticalStrut(20));
		JPanel jPanelValidation = new JPanel(new BorderLayout());
		jPanelValidation.add(validateButton, BorderLayout.CENTER);
		box.add(jPanelValidation);

		add(box);
		}

	private void control()
		{
		detectButton.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent arg0)
					{
					detectMeteoStations();
					}
			});

		validateButton.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
					{
					applyChanges();
					}
			});
		}

	private void apparence()
		{
		setBackground(JFrameAfficheurService.BACKGROUND_COLOR);
		JPanelStation.setupJLabelStyle(lblDetectedPorts, 18);
		JPanelStation.setupJLabelStyle(lblPortsToBan, 18);

		lblDetectedPorts.setAlignmentX(CENTER_ALIGNMENT);
		lblPortsToBan.setAlignmentX(CENTER_ALIGNMENT);
		lblDetectedPorts.setHorizontalAlignment(SwingConstants.CENTER);
		lblPortsToBan.setHorizontalAlignment(SwingConstants.CENTER);

		detectButton.setAlignmentX(CENTER_ALIGNMENT);

		banList.setCellRenderer(new DefaultListCellRenderer()
			{

				@Override
				public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
					{
					super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

					String path = isSelected ? "images/no-meteo.png" : "images/ok-meteo.png";
					ImageIcon icon = ImageTools.loadIconJar(path, true);
					setIcon(icon);
					return this;
					}
			});

		detectedList.setCellRenderer(new DefaultListCellRenderer()
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

	private void populateBanList()
		{
		List<String> ports = detectionService.findPortSerie();
		for(String port:ports)
			{
			banListModel.addElement(port);
			}
		}

	private void detectMeteoStations()
		{
		detectionProgress.setValue(0);
		detectedListModel.clear();
		detectButton.setEnabled(false);

		SwingWorker<Map<String, Boolean>, Integer> worker = new SwingWorker<Map<String, Boolean>, Integer>()
					{

				@Override
				protected Map<String, Boolean> doInBackground() throws Exception
					{
					String[] allPorts = new String[banListModel.size()];
					banListModel.copyInto(allPorts);
					List<String> bannedPorts = banList.getSelectedValuesList();

					int nbPorts = allPorts.length - bannedPorts.size();
					int current = 0;

					Map<String, Boolean> map = new TreeMap<>();
					for(String port:allPorts)
						{
						if(!bannedPorts.contains(port))
							{
							map.put(port, detectionService.isStationMeteoAvailable(port));
							setProgress((100 * ++current) / nbPorts);
							}
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
							detectedListModel.addElement(port);
							}
						}
					catch (Exception e)
						{
						e.printStackTrace();
						}

					detectButton.setEnabled(true);
					}
			};

		worker.addPropertyChangeListener(new PropertyChangeListener()
			{

				@Override
				public void propertyChange(PropertyChangeEvent evt)
					{
					if ("progress".equals(evt.getPropertyName()))
						{
						detectionProgress.setValue((Integer)evt.getNewValue());
						}
					}
			});

		worker.execute();
		}

	private void applyChanges()
		{
		List<String> selectedPorts = new ArrayList<>(detectedListModel.size());

		for(Entry<String, Boolean> port:detectedList.getSelectedValuesList())
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
		PCLocal pc = JFrameAfficheurService.getInstance(false).getPCLocal();
		for(String port:selectedPorts)
			{
			pc.addStation(port);
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private DefaultListModel<Entry<String, Boolean>> detectedListModel;
	private JList<Entry<String, Boolean>> detectedList;
	private DefaultListModel<String> banListModel;
	private JList<String> banList;
	private JButton refreshBanListButton;
	private JButton detectButton;
	private JButton validateButton;
	private JProgressBar detectionProgress;
	private MeteoPortDetectionService_I detectionService;
	private JLabel lblDetectedPorts;
	private JLabel lblPortsToBan;
	}
