/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package eventBus.Framework;

import java.io.Serializable;
import eventBus.Framework.EventId;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L; //Default serializable value  
    private String message;
	private EventId eventId;

	public Event(EventId id, String text ) {
		this.message = text;
		this.eventId = id;
	}
	public Event(EventId id ) {
		this.message = null;
		this.eventId = id;
	}
	public EventId getEventId() {
		return eventId;
	}
	public String getMessage() {
		return message;
	}
}