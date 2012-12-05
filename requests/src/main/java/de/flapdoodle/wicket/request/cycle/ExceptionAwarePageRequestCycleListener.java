package de.flapdoodle.wicket.request.cycle;

import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.cycle.PageRequestHandlerTracker;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.IPageRequestHandler;


public class ExceptionAwarePageRequestCycleListener extends PageRequestHandlerTracker {

	@Override
	public IRequestHandler onException(RequestCycle cycle, Exception ex) {
		IPageRequestHandler latestPageRequestHandler = getLastHandler(cycle);
		if (latestPageRequestHandler!=null) {
			if (latestPageRequestHandler.isPageInstanceCreated()) {
				IRequestablePage page = latestPageRequestHandler.getPage();
				if (page instanceof IExceptionAwarePage) {
					return ((IExceptionAwarePage) page).onException(cycle, ex);
				}
			}
		}
		
		return super.onException(cycle, ex);
	}
}
