/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package eventBus.Components.ClientOutput;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import eventBus.Framework.Event;
import eventBus.Framework.EventId;
import eventBus.Framework.EventQueue;
import eventBus.Framework.RMIEventBus;

public class ClientOutputMain {
	public static void main(String[] args) throws RemoteException, IOException, NotBoundException {
		RMIEventBus eventBusInterface = (RMIEventBus) Naming.lookup("EventBus");
		long componentId = eventBusInterface.register();
		System.out.println("** ClientOutputMain (ID:" + componentId + ") is successfully registered...");
		
		Event event = null;
		boolean done = false;
		while (!done) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			EventQueue eventQueue = eventBusInterface.getEventQueue(componentId);
			for(int i = 0; i < eventQueue.getSize(); i++)  {
				event = eventQueue.getEvent();
				if (event.getEventId() == EventId.ClientOutput) {
					printOutput(event);
				} else if (event.getEventId() == EventId.QuitTheSystem) {
					//printLogReceive(event);
					eventBusInterface.unRegister(componentId);
					done = true;
				}
			}
		}
	}
	private static void printOutput(Event event) {
		System.out.println(event.getMessage());
	}
}
