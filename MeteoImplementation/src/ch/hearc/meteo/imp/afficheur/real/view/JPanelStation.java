
package ch.hearc.meteo.imp.afficheur.real.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.real.moo.AfficheurServiceMOO;

public class JPanelStation extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelStation(AfficheurServiceMOO afficheurServiceMOO)
		{
		this.afficheurServiceMOO = afficheurServiceMOO;

		geometry();
		control();
		apparence();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void refresh()
		{
		jPanelTemperature.refresh();
		jPanelPressure.refresh();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(afficheurServiceMOO.getTitre());
		if (afficheurServiceMOO.getLastAltitude() != null)
			{
			stringBuilder.append(" situé à ");
			stringBuilder.append((int)afficheurServiceMOO.getLastAltitude().getValue());
			stringBuilder.append("m d'altitude");
			}
		jLabelName.setText(stringBuilder.toString());

		stringBuilder = new StringBuilder();
		if (afficheurServiceMOO.getLastTemperature() != null)
			{
			stringBuilder.append("Température actuelle : ");
			stringBuilder.append(String.format("%.2f", afficheurServiceMOO.getLastTemperature().getValue()));
			stringBuilder.append("°C");
			}
		jLabelTemperature.setText(stringBuilder.toString());

		stringBuilder = new StringBuilder();
		if (afficheurServiceMOO.getLastPression() != null)
			{
			stringBuilder.append("Pression actuelle : ");
			stringBuilder.append(String.format("%.2f", afficheurServiceMOO.getLastPression().getValue()));
			stringBuilder.append("hPa");
			}
		jLabelPressure.setText(stringBuilder.toString());

		Date lastUpdateDate = new Date(afficheurServiceMOO.getLastTemperature().getTime());
		jLabelLastUpdate.setText("Dernière mise à jour : " + DATE_FORMAT.format(lastUpdateDate));
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static void setupJLabelStyle(JLabel jLabel, int fontSize)
		{
		Font font = new Font(jLabel.getFont().getName(), Font.PLAIN, fontSize);
		jLabel.setFont(font);
		jLabel.setForeground(JFrameAfficheurService.FOREGROUND_COLOR);
		jLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public AfficheurServiceMOO getAfficheurServiceMOO()
		{
		return this.afficheurServiceMOO;
		}

	public boolean isConnected()
		{
		try
			{
			return afficheurServiceMOO.getMeteoServiceRemote().isConnect();
			}
		catch (RemoteException e)
			{
			return false;
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		setLayout(new BorderLayout());
		Box mainBox = Box.createHorizontalBox();
		Box infosBox = Box.createVerticalBox();
		Box datasBox = Box.createVerticalBox();

		jPanelTemperature = new JPanelTemperature(afficheurServiceMOO);
		jPanelPressure = new JPanelPressure(afficheurServiceMOO);
		jPanelSwitzerland = new JPanelSwitzerland(afficheurServiceMOO);
		jPanelDelta = new JPanelDelta(afficheurServiceMOO);
		jLabelLastUpdate = new JLabel();
		jLabelName = new JLabel();
		jLabelTemperature = new JLabel();
		jLabelPressure = new JLabel();

		add(mainBox, BorderLayout.CENTER);
		mainBox.add(infosBox);
		mainBox.add(Box.createHorizontalGlue());
		mainBox.add(datasBox);

		Box infosTextParent = Box.createHorizontalBox();
		Box infosText = Box.createVerticalBox();
		infosText.add(jLabelName);
		infosText.add(jLabelTemperature);
		infosText.add(jLabelPressure);
		infosText.add(jLabelLastUpdate);
		infosTextParent.add(Box.createHorizontalGlue());
		infosTextParent.add(infosText);
		infosTextParent.add(Box.createHorizontalGlue());
		infosBox.add(infosTextParent);

		Box deltaParent = Box.createHorizontalBox();
		deltaParent.add(Box.createHorizontalGlue());
		deltaParent.add(jPanelDelta);
		deltaParent.add(Box.createHorizontalGlue());
		infosBox.add(Box.createVerticalStrut(50));
		infosBox.add(deltaParent);
		infosBox.add(Box.createVerticalStrut(50));
		infosBox.add(jPanelSwitzerland);

		datasBox.add(jPanelTemperature);
		datasBox.add(jPanelPressure);

		}

	private void control()
		{
		//Rien
		}

	private void apparence()
		{
		setupJLabelStyle(jLabelLastUpdate, 16);
		setupJLabelStyle(jLabelName, 20);
		setupJLabelStyle(jLabelTemperature, 20);
		setupJLabelStyle(jLabelPressure, 20);
		setBackground(JFrameAfficheurService.BACKGROUND_COLOR);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Tools

	// Inputs
	private AfficheurServiceMOO afficheurServiceMOO;

	// Outputs
	private JLabel jLabelLastUpdate;
	private JLabel jLabelName;
	private JLabel jLabelTemperature;
	private JLabel jLabelPressure;
	private JPanelTemperature jPanelTemperature;
	private JPanelPressure jPanelPressure;
	private JPanelSwitzerland jPanelSwitzerland;
	private JPanelDelta jPanelDelta;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	//Tools
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

	}
