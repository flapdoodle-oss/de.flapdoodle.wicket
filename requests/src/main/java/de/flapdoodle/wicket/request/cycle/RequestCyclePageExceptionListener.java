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
package de.flapdoodle.wicket.request.cycle;

import org.apache.wicket.core.request.handler.IPageRequestHandler;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.PageRequestHandlerTracker;
import org.apache.wicket.request.cycle.RequestCycle;

public class RequestCyclePageExceptionListener extends PageRequestHandlerTracker {

	private final IPageExceptionListener _pageExceptionListener;

	public RequestCyclePageExceptionListener(IPageExceptionListener pageExceptionListener) {
		_pageExceptionListener = pageExceptionListener;
	}

	@Override
	public IRequestHandler onException(RequestCycle cycle, Exception ex) {
		IPageRequestHandler latestPageRequestHandler = getLastHandler(cycle);
		if (latestPageRequestHandler != null) {
			if (latestPageRequestHandler.isPageInstanceCreated()) {
				return _pageExceptionListener.onException(cycle, ex, latestPageRequestHandler.getPage());
			} else {
				return _pageExceptionListener.onException(cycle, ex, latestPageRequestHandler.getPageClass());
			}
		}

		return super.onException(cycle, ex);
	}
}
