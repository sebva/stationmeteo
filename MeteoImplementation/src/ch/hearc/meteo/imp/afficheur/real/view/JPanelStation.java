
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

		Thread thread = new Thread(new Runnable()
			{

				@Override
				public void run()
					{
					while(true)
						{
						updateGUI();
						try
							{
							Thread.sleep(1000);
							}
						catch (InterruptedException e)
							{
							e.printStackTrace();
							}
						}
					}
			});

		thread.start();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void refresh()
		{
		//TODO: UPDATE DATA
		jPanelTemperature.refresh();
		jPanelPression.refresh();
		Date lastUpdateDate = new Date(afficheurServiceMOO.getLastTemperature().getTime());
		jLabelLastUpdate.setText("Dernière mise à jour : " + DATE_FORMAT.format(lastUpdateDate));
		}

	public void updateGUI()
		{
		jPanelTemperature.updateGUI();
		jPanelPression.updateGUI();
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
		jPanelPression = new JPanelPression(afficheurServiceMOO);
		jLabelLastUpdate = new JLabel();
		jLabelName = new JLabel();
		jLabelName.setText(afficheurServiceMOO.getTitre());

		add(boxV, BorderLayout.CENTER);

		boxV.add(jLabelName);
		boxV.add(jLabelLastUpdate);
		boxV.add(jPanelTemperature);
		boxV.add(jPanelPression);
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
	private JPanelPression jPanelPression;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	//Tools
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

	}
