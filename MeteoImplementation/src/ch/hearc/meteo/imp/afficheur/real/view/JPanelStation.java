
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
		Date lastUpdateDate = new Date(afficheurServiceMOO.getLastTemperature().getTime());
		jLabelLastUpdate.setText("Dernière mise à jour : " + DATE_FORMAT.format(lastUpdateDate));
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(afficheurServiceMOO.getTitre());
		if (afficheurServiceMOO.getLastAltitude() != null)
			{
			stringBuilder.append(" situé à ");
			stringBuilder.append(afficheurServiceMOO.getLastAltitude().getValue());
			stringBuilder.append("m d'altitude");
			}
		jLabelName.setText(stringBuilder.toString());
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static void setJLabelStyle(JLabel jLabel, int fontSize)
		{
		Font font = new Font(jLabel.getFont().getName(), Font.PLAIN, fontSize);
		jLabel.setFont(font);
		jLabel.setForeground(JFrameAfficheurService.FOREGROUND_COLOR);
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
		Box boxV = Box.createVerticalBox();

		add(boxV, BorderLayout.CENTER);

		jPanelTemperature = new JPanelTemperature(afficheurServiceMOO);
		jPanelPressure = new JPanelPressure(afficheurServiceMOO);
		jLabelLastUpdate = new JLabel();
		jLabelName = new JLabel();

		add(boxV, BorderLayout.CENTER);

		boxV.add(jLabelName);
		boxV.add(jLabelLastUpdate);
		boxV.add(jPanelTemperature);
		boxV.add(jPanelPressure);
		}

	private void control()
		{
		//Rien
		}

	private void apparence()
		{
		setJLabelStyle(jLabelLastUpdate, 16);
		setJLabelStyle(jLabelName, 20);
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
	private JPanelTemperature jPanelTemperature;
	private JPanelPressure jPanelPressure;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	//Tools
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

	}
