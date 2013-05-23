
package ch.hearc.meteo.imp.use.remote;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;

import javax.swing.UIManager;

import ch.hearc.meteo.imp.reseau.AfficheurManager;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.meteo.MeteoServiceOptions;

import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

public class UsePCLocal
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args)
		{
		main();
		}

	public static void main()
		{
		try
			{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			FileInputStream fis = new FileInputStream(FILE_PROPERTIES);
			BufferedInputStream bis = new BufferedInputStream(fis);
			Properties propertie = new Properties();
			propertie.load(bis);

			String stringIP = propertie.getProperty(ADRESSE_IP);
			InetAddress ip = InetAddress.getByName(stringIP);
			int rmi_port = Integer.valueOf(propertie.getProperty(RMI_PORT));

			String name = propertie.getProperty(LOCATION_NAME);
			String latitude = propertie.getProperty(LATITUDE);
			String longitude = propertie.getProperty(LONGITUDE);

			bis.close();
			fis.close();

			RmiURL rmiUrl = new RmiURL(AfficheurManager.RMI_ID, ip, rmi_port);


			int rand = (int)(Math.random() * 100);
			int n = 10;
			MeteoServiceOptions meteoServiceOptions = new MeteoServiceOptions(n + rand, 2*n + rand, 3*n + rand, 4*n + rand);
			AffichageOptions affichageOptions = new AffichageOptions(n, name + ";" + latitude + ";" + longitude);

			new PCLocal(meteoServiceOptions, affichageOptions, rmiUrl).run();
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static void saveProperties() throws IOException
		{
		FileOutputStream fos = new FileOutputStream(FILE_PROPERTIES);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		Properties propertie = new Properties();

		propertie.setProperty(ADRESSE_IP, "127.0.0.1");
		propertie.setProperty(RMI_PORT, "" + RmiTools.PORT_RMI_DEFAUT);
		propertie.setProperty(LOCATION_NAME, "Neuchâtel");
		propertie.setProperty(LATITUDE, "6.45");
		propertie.setProperty(LONGITUDE, "45.666");
		propertie.store(bos, STORE_NAME);

		bos.close();
		fos.close();
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final String STORE_NAME = "Station Météo - Config";
	private static final String ADRESSE_IP = "ADRESSE_IP";
	private static final String RMI_PORT = "RMI_PORT";
	private static final String LOCATION_NAME = "LOCATION_NAME";
	private static final String LATITUDE = "LATITUDE";
	private static final String LONGITUDE = "LONGITUDE";
	private static final String FILE_PROPERTIES = "settings.ini";

	}
