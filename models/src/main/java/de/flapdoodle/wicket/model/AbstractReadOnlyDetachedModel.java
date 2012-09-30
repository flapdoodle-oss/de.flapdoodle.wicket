package de.flapdoodle.wicket.model;

import org.apache.wicket.model.LoadableDetachableModel;

/**
 * AbstractReadOnlyDetachedModel is an adapter base class for implementing models which have detach logic
 * and are read-only. AbstractReadOnlyDetachedModel is a combination of AbstractReadOnlyModel and LoadableDetachableModel. 
 * 
 * AbstractReadOnlyDetachedModel holds a temporary, transient model object, 
 * that is set when {@link #getObject()} is called by calling abstract method 'load', 
 * and that will be reset/ set to null on {@link #detach()}.

 * A usage example:
 * 
 * <pre>
 * AbstractReadOnlyDetachedModel<Some> venueListModel = new AbstractReadOnlyDetachedModel<Some>()
 * {
 * 	protected Some load()
 * 	{
 * 		return getSomeThing();
 * 	}
 * };
 * </pre>
 * 
 * @param <T>
 *            The model object
 */
public abstract class AbstractReadOnlyDetachedModel<T> extends LoadableDetachableModel<T> {

	/**
	 * This default implementation of setObject unconditionally throws an
	 * UnsupportedOperationException. Since the method is final, any subclass is effectively a
	 * read-only model.
	 * 
	 * @param object
	 *            The object to set into the model
	 * @throws UnsupportedOperationException
	 */
	@Override
	public final void setObject(final T object)
	{
		throw new UnsupportedOperationException("Model " + getClass() +
			" does not support setObject(Object)");
	}
	
}
