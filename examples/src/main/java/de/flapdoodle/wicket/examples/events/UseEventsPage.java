package de.flapdoodle.wicket.examples.events;

import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebPage;

import de.flapdoodle.wicket.events.Events;


public class UseEventsPage extends WebPage {

	
	public UseEventsPage() {
		String reply=Events.from(this).broadcast(Broadcast.BUBBLE).sendWithReply("Hallo",String.class);
	}
	
	@Override
	public void onEvent(IEvent<?> event) {
	}
}
