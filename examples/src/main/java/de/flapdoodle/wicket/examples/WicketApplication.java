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
package de.flapdoodle.wicket.examples;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.IRequestCycleListener;

import de.flapdoodle.wicket.detach.FieldInspectingDetachListener;
import de.flapdoodle.wicket.examples.debug.DoNotSerializeMe;
import de.flapdoodle.wicket.examples.pages.StartPage;
import de.flapdoodle.wicket.request.cycle.RequestCycleExceptionListener;
import de.flapdoodle.wicket.request.cycle.exceptionlistener.ApplicationHomePageFallbackListener;
import de.flapdoodle.wicket.request.cycle.exceptionlistener.ExceptionAwarePageListener;
import de.flapdoodle.wicket.serialize.java.CheckingJavaSerializer;
import de.flapdoodle.wicket.serialize.java.ISerializableCheck;
import de.flapdoodle.wicket.serialize.java.checks.AttachedLoadableModelCheck;
import de.flapdoodle.wicket.serialize.java.checks.SerializableChecks;
import de.flapdoodle.wicket.serialize.java.checks.SerializingNotAllowedForTypesCheck;

public class WicketApplication extends WebApplication {

	@Override
	protected void init() {
		super.init();

		Class<?>[] forbiddenTypes = {DoNotSerializeMe.class};
		ISerializableCheck checks = new SerializableChecks(new AttachedLoadableModelCheck(),
				new SerializingNotAllowedForTypesCheck(forbiddenTypes));
		getFrameworkSettings().setSerializer(new CheckingJavaSerializer(getApplicationKey(), checks));
		getStoreSettings().setInmemoryCacheSize(1);

		// getFrameworkSettings().setDetachListener(new FieldInspectingDetachListener());

		getRequestCycleListeners().add(
				new RequestCycleExceptionListener(new ExceptionAwarePageListener(new ApplicationHomePageFallbackListener())));
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return StartPage.class;
	}

}
