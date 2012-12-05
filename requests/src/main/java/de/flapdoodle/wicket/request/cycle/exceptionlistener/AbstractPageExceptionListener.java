package de.flapdoodle.wicket.request.cycle.exceptionlistener;

import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.cycle.RequestCycle;

import de.flapdoodle.wicket.request.cycle.IPageExceptionListener;

public abstract class AbstractPageExceptionListener implements IPageExceptionListener {

	private final IPageExceptionListener _parent;

	public AbstractPageExceptionListener() {
		this(null);
	}
	
	public AbstractPageExceptionListener(IPageExceptionListener parent) {
		_parent = parent;
	}

	@Override
	public IRequestHandler onException(RequestCycle cycle, Exception ex, IRequestablePage page) {
		return _parent != null
				? _parent.onException(cycle, ex, page)
				: null;
	}

	@Override
	public IRequestHandler onException(RequestCycle cycle, Exception ex, Class<? extends IRequestablePage> pageClass) {
		return _parent != null
				? _parent.onException(cycle, ex, pageClass)
				: null;
	}

}
