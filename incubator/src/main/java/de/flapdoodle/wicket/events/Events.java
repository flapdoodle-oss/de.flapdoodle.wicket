package de.flapdoodle.wicket.events;

import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.event.IEventSink;
import org.apache.wicket.event.IEventSource;


public class Events {

	private Events() {
		// no instance
	}
	
	public static <T extends IEventSource & IEventSink> Source<T> from(T source) {
		return new Source<T>(source);
	}
	
	public static class Source<T extends IEventSource & IEventSink> {

		private final T _source;

		public Source(T source) {
			_source = source;
		}
		
		public SourceAndBroadcast<T> broadcast(Broadcast broadcast) {
			return new SourceAndBroadcast<T>(_source,broadcast);
		}
		
	}
	
	public static class SourceAndBroadcast<T extends IEventSource & IEventSink> {

		private final T _source;
		private final Broadcast _broadcast;

		public SourceAndBroadcast(T source, Broadcast broadcast) {
			_source = source;
			_broadcast = broadcast;
		}
	
		public <T> void send(T payload) {
			_source.send(_source, _broadcast, payload);
		}
		
		public <T,R> R sendWithReply(T payload,Class<? extends R> replyType) {
			EventSinkWrapper<R> sinkWrapper = new EventSinkWrapper<R>(_source,replyType);
			_source.send(sinkWrapper, _broadcast, payload);
			return sinkWrapper.getReply();
		}
	}
	
	static class EventSinkWrapper<R> implements IEventSink {

		private final Class<? extends R> _replyType;
		private final IEventSink _parent;
		R _reply;

		public EventSinkWrapper(IEventSink parent, Class<? extends R> replyType) {
			_parent = parent;
			_replyType = replyType;
		}
		
		public R getReply() {
			return _reply;
		}

		@Override
		public void onEvent(IEvent<?> event) {
			Object payload = event.getPayload();
			if (_replyType.isInstance(payload)) {
				_reply=(R) payload;
			}
			
			_parent.onEvent(event);
		}
	}
}
