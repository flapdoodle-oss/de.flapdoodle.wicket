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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.wicket.model.IModel;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.AbstractReadOnlyDetachedModel;
import de.flapdoodle.wicket.model.IReadOnlyMapModel;

public class MapModel<K,V> extends AbstractReadOnlyDetachedModel<Map<K,V>> implements IReadOnlyMapModel<K, V> {

	private final IModel<? extends Iterable<? extends V>> source;
	private final Function1<K, ? super V> keyTransformation;

	public MapModel(IModel<? extends Iterable<? extends V>> source, Function1<K, ? super V> keyTransformation) {
		this.source = source;
		this.keyTransformation = keyTransformation;
	}
	
	@Override
	protected Map<K, V> load() {
		return asMap(source.getObject(), keyTransformation);
	}

	protected static <K,V> Map<K, V> asMap(Iterable<? extends V> src, Function1<K, ? super V> transformation) {
		Map<K,V> result=new LinkedHashMap<>();
		for (V value : src) {
			K key = transformation.apply(value);
			V old = result.put(key, value);
			if (old!=null) {
				throw new IllegalArgumentException("multiple values got the same key: "+value+","+old+" -> "+key);
			}
		}
		return Collections.unmodifiableMap(result);
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		source.detach();
	}
}
