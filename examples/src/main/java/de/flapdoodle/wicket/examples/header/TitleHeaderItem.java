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
package de.flapdoodle.wicket.examples.header;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Response;

import java.util.Collections;

public class TitleHeaderItem extends HeaderItem {

	private final IModel<String> header;

	public TitleHeaderItem(IModel<String> header) {
		this.header = header;
	}

	@Override
	public Iterable<?> getRenderTokens() {
		return Collections.singleton(renderTitle());
	}

	@Override
	public void render(Response response) {
		response.write(renderTitle());
	}

	private String renderTitle() {
		return "<title>"+header.getObject()+"</title>";
	}
}
