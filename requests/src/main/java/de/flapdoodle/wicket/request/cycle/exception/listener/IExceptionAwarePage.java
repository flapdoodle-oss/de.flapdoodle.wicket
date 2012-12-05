package de.flapdoodle.wicket.request.cycle.exception.listener;

import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;


public interface IExceptionAwarePage {

	IRequestHandler onException(RequestCycle cycle, Exception ex);

}
