
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
		connectedListModel = new DefaultListModel<>();
		connectedList = new JList<>(connectedListModel);

		refreshBanListButton = new JButton("Rafraîchir la liste des ports");
		detectButton = new JButton("Lancer la détection");

		lblDetectedPorts = new JLabel("Ports détectés");
		lblPortsToBan = new JLabel("Ports à bannir de la détection");
		lblConnectedPorts = new JLabel("Stations connectées");

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
		box.add(lblConnectedPorts);
		JPanel jPanelConnectedPorts = new JPanel(new BorderLayout());
		jPanelConnectedPorts.add(new JScrollPane(connectedList), BorderLayout.CENTER);
		box.add(jPanelConnectedPorts);

		add(box);
		}

	private void control()
		{
		refreshBanListButton.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
					{
					banListModel.clear();
					populateBanList();
					}
			});

		detectButton.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent arg0)
					{
					detectMeteoStations();
					}
			});

		detectedList.addMouseListener(new MouseAdapter()
			{

				@Override
				public void mouseClicked(MouseEvent evt)
					{
					@SuppressWarnings("unchecked")
					JList<Entry<String, Boolean>> list = (JList<Entry<String, Boolean>>)evt.getSource();
					if (evt.getClickCount() == 2)
						{
						int index = list.locationToIndex(evt.getPoint());
						addStation(detectedListModel.get(index));
						}
					}
			});

		connectedList.addMouseListener(new MouseAdapter()
			{

				@Override
				public void mouseClicked(MouseEvent evt)
					{
					@SuppressWarnings("unchecked")
					JList<Entry<String, Boolean>> list = (JList<Entry<String, Boolean>>)evt.getSource();
					if (evt.getClickCount() == 2)
						{
						int index = list.locationToIndex(evt.getPoint());
						removeStation(connectedListModel.get(index));
						}
					}
			});
		}

	private void apparence()
		{
		setBackground(JFrameAfficheurService.BACKGROUND_COLOR);
		JPanelStation.setupJLabelStyle(lblDetectedPorts, 18);
		JPanelStation.setupJLabelStyle(lblPortsToBan, 18);
		JPanelStation.setupJLabelStyle(lblConnectedPorts, 18);

		lblDetectedPorts.setAlignmentX(CENTER_ALIGNMENT);
		lblPortsToBan.setAlignmentX(CENTER_ALIGNMENT);
		lblConnectedPorts.setAlignmentX(CENTER_ALIGNMENT);
		lblDetectedPorts.setHorizontalAlignment(SwingConstants.CENTER);
		lblPortsToBan.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnectedPorts.setHorizontalAlignment(SwingConstants.CENTER);

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
					final Entry<String, Boolean> realValue = (Entry<String, Boolean>)value;
					super.getListCellRendererComponent(list, realValue.getKey(), index, isSelected, cellHasFocus);

					String path = realValue.getValue() ? "images/meteo.png" : "images/no-meteo.png";
					ImageIcon icon = ImageTools.loadIconJar(path, true);

					setIcon(icon);

					return this;
					}
			});

		connectedList.setCellRenderer(new DefaultListCellRenderer()
			{

				@Override
				public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
					{
					@SuppressWarnings("unchecked")
					final Entry<String, Boolean> realValue = (Entry<String, Boolean>)value;
					super.getListCellRendererComponent(list, realValue.getKey(), index, isSelected, cellHasFocus);

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
						if (!bannedPorts.contains(port))
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

	private void addStation(Entry<String, Boolean> port)
		{
		detectedListModel.removeElement(port);
		connectedListModel.addElement(port);

		PCLocal pc = JFrameAfficheurService.getInstance(false).getPCLocal();
		pc.addStation(port.getKey());
		}

	private void removeStation(final Entry<String, Boolean> port)
		{
		connectedListModel.removeElement(port);
		detectedListModel.addElement(port);

		PCLocal pc = JFrameAfficheurService.getInstance(false).getPCLocal();
		pc.removePortCom(port.getKey());
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private DefaultListModel<Entry<String, Boolean>> detectedListModel;
	private JList<Entry<String, Boolean>> detectedList;
	private DefaultListModel<String> banListModel;
	private JList<String> banList;
	private DefaultListModel<Entry<String, Boolean>> connectedListModel;
	private JList<Entry<String, Boolean>> connectedList;
	private JButton refreshBanListButton;
	private JButton detectButton;
	private JProgressBar detectionProgress;
	private MeteoPortDetectionService_I detectionService;
	private JLabel lblDetectedPorts;
	private JLabel lblPortsToBan;
	private JLabel lblConnectedPorts;
	}
