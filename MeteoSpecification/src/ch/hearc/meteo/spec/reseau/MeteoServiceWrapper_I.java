
package ch.hearc.meteo.spec.reseau;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hearc.meteo.spec.meteo.MeteoServiceOptions;

/**
 * <pre>
 * L'objectif est de ne pas poluer l'implementation du serviceMeteo avec la moindre trace de RMI.
 * En effet, le serviceMeteo n'est pas forcement utilisable en reseau, et son implementation doit etre independante de cette problematique.
 *
 * Service restreint utilisable à distance sur le serveur d'affichage.
 * </pre>
 */
public interface MeteoServiceWrapper_I extends Remote
	{

	public void start(MeteoServiceOptions meteoServiceOptions) throws RemoteException;

	public void stop() throws RemoteException;

	public boolean isRunning() throws RemoteException;

	public boolean isConnect() throws RemoteException;


	}
