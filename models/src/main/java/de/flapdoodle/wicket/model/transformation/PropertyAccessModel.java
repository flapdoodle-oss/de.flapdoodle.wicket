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
package de.flapdoodle.wicket.model.transformation;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.lambda.SerializableBiConsumer;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.IMapableModel;

public class PropertyAccessModel<M, R> implements IMapableModel<R> {

	private final IModel<M> model;
	private final Function1<R, ? super M> read;
	private final SerializableBiConsumer<? super M, R> write;

	public PropertyAccessModel(IModel<M> m1, Function1<R, ? super M> read,
			SerializableBiConsumer<? super M, R> write) {
		this.model = m1;
		this.read = read;
		this.write = write;
	}

	@Override
	public R getObject() {
		return read.apply(model.getObject());
	}

	@Override
	public void setObject(R value) {
		write.accept(model.getObject(), value);
	}

	@Override
	public void detach() {
		model.detach();
	}
}
