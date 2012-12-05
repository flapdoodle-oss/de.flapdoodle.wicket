/**
 * Copyright (C) 2011
 * Michael Mosmann <michael@mosmann.de>
 * Jan Bernitt <unknown@email.de>
 * 
 * with contributions from
 * nobody yet
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.wicket.examples.requests;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.handler.RedirectRequestHandler;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.flapdoodle.wicket.request.cycle.IExceptionAwarePage;

public class UseExceptionAwarePage extends WebPage implements IExceptionAwarePage {

	public UseExceptionAwarePage() {
		add(new BadPanel("bad"));
		
		throw new BadThingHappenException("constructor");
	}

	@Override
	public IRequestHandler onException(RequestCycle cycle, Exception ex) {
		return new RedirectRequestHandler(urlFor(Application.get().getHomePage(),new PageParameters().add("cause",ex.getMessage())).toString());
	}
}
