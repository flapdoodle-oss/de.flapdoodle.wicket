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
