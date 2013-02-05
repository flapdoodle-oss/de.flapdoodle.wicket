/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Jan Bernitt <unknown@email.de>
 *
 * with contributions from
 * 	nobody yet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
