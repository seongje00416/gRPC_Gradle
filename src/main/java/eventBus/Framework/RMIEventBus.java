/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package eventBus.Framework;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIEventBus extends Remote {
	public long register() throws RemoteException;
	public void unRegister(long SenderID) throws RemoteException;
	public void sendEvent(Event m ) throws RemoteException;
	public EventQueue getEventQueue(long SenderID) throws RemoteException;
}
