
package ch.hearc.meteo.spec.afficheur;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hearc.meteo.spec.meteo.listener.event.MeteoEvent;

public interface AfficheurService_I extends Remote
	{

	public void printPression(MeteoEvent event) throws RemoteException;

	public void printAltitude(MeteoEvent event) throws RemoteException;

	public void printTemperature(MeteoEvent event) throws RemoteException;

	}
