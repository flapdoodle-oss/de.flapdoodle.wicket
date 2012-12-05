package de.flapdoodle.wicket.request.cycle;

import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.cycle.RequestCycle;


public interface IPageExceptionListener {

	IRequestHandler onException(RequestCycle cycle, Exception ex, IRequestablePage page);
	
	IRequestHandler onException(RequestCycle cycle, Exception ex, Class<? extends IRequestablePage> pageClass);

}
