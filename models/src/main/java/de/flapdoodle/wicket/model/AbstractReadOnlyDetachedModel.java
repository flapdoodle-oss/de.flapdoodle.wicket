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
public abstract class AbstractReadOnlyDetachedModel<T> extends LoadableDetachableModel<T> implements IReadOnlyModel<T> {

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
