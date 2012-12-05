package de.flapdoodle.wicket.request.cycle;

import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.cycle.PageRequestHandlerTracker;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.IPageRequestHandler;

public class RequestCycleExceptionListener extends PageRequestHandlerTracker {

	private final IPageExceptionListener _pageExceptionListener;

	public RequestCycleExceptionListener(IPageExceptionListener pageExceptionListener) {
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
