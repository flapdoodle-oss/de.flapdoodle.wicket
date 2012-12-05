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
package de.flapdoodle.wicket.request.cycle.exception.listener;

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
