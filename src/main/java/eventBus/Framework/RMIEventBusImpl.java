/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package eventBus.Framework;

import java.rmi.Naming;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class RMIEventBusImpl extends UnicastRemoteObject implements RMIEventBus {
    private static final long serialVersionUID = 1L; //Default value   
    static Vector<EventQueue> eventQueueList;

	public RMIEventBusImpl() throws RemoteException {
		super();
		eventQueueList = new Vector<EventQueue>(15, 1);
	}
	public static void main(String args[]) {
		try {
			System.setProperty( "java.rmi.server.hostname", "localhost" );
			java.rmi.registry.LocateRegistry.createRegistry(1099);

			RMIEventBusImpl eventBus = new RMIEventBusImpl();
			// EventBus라는 이름으로 lookup되도록 강제 지정
			Naming.rebind("//localhost/EventBus", eventBus);
			System.out.println("Event Bus is running now...");
		} catch (Exception e) {
			System.out.println("Event bus startup error: " + e);
		}
		
	}
	synchronized public long register() throws RemoteException {
		EventQueue newEventQueue = new EventQueue();
		eventQueueList.add( newEventQueue );
		System.out.println("Component (ID:"+ newEventQueue.getId() + ") is registered...");
		return newEventQueue.getId();
	}
	synchronized public void unRegister(long id) throws RemoteException {
		EventQueue eventQueue;
		for ( int i = 0; i < eventQueueList.size(); i++ ) {
			eventQueue =  eventQueueList.get(i);			
			if (eventQueue.getId() == id) {
				eventQueue = eventQueueList.remove(i);
				System.out.println("Component (ID:"+ id + ") is unregistered...");
			}
		}
	}
	synchronized public void sendEvent(Event sentEvent) throws RemoteException {
		EventQueue eventQueue;
		for ( int i = 0; i < eventQueueList.size(); i++ ) {
			eventQueue = eventQueueList.get(i);
			eventQueue.addEvent(sentEvent);
			eventQueueList.set(i, eventQueue);
		}
		System.out.println("Event Inforamtion(ID: "+sentEvent.getEventId()+", Message: "+sentEvent.getMessage()+")");
	}
	synchronized public EventQueue getEventQueue(long id) throws RemoteException {
		EventQueue originalQueue = null; 
		EventQueue copiedQueue =  null;
		for ( int i = 0; i < eventQueueList.size(); i++ ) {
			originalQueue =  eventQueueList.get(i);
			if (originalQueue.getId() == id) {
				originalQueue = eventQueueList.get(i);
				copiedQueue = originalQueue.getCopy();
				originalQueue.clearEventQueue();
			}
		}
		return copiedQueue;
	}
}