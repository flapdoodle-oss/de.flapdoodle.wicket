package de.flapdoodle.wicket.request.cycle.exceptionlistener;

import org.apache.wicket.Application;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.handler.RedirectRequestHandler;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ApplicationHomePageFallbackListener extends AbstractPageExceptionListener {

	@Override
	public IRequestHandler onException(RequestCycle cycle, Exception ex, Class<? extends IRequestablePage> pageClass) {
		return redirectToHomePage(cycle);
	}

	@Override
	public IRequestHandler onException(RequestCycle cycle, Exception ex, IRequestablePage page) {
		return redirectToHomePage(cycle);
	}

	private RedirectRequestHandler redirectToHomePage(RequestCycle cycle) {
		return new RedirectRequestHandler(cycle.urlFor(Application.get().getHomePage(), new PageParameters().add("cause","badThings")).toString());
	}

}
