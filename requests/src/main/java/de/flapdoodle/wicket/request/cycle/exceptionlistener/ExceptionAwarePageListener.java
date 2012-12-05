package de.flapdoodle.wicket.request.cycle.exceptionlistener;

import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.cycle.RequestCycle;

import de.flapdoodle.wicket.request.cycle.IPageExceptionListener;


public class ExceptionAwarePageListener extends AbstractPageExceptionListener {

	public ExceptionAwarePageListener() {
		super();
	}

	public ExceptionAwarePageListener(IPageExceptionListener parent) {
		super(parent);
	}

	@Override
	public IRequestHandler onException(RequestCycle cycle, Exception ex, IRequestablePage page) {
		if (page instanceof IExceptionAwarePage) {
			IRequestHandler ret=((IExceptionAwarePage) page).onException(cycle, ex);
			if (ret!=null) {
				return ret;
			}
		}
		return super.onException(cycle, ex, page);
	}
}
