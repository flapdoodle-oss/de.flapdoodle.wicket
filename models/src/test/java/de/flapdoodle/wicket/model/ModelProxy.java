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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.wicket.model.IModel;

public class ModelProxy<T> extends DelegatingModel<T> {

	AtomicInteger detachCalled=new AtomicInteger(0);
	AtomicInteger setObjectCalled=new AtomicInteger(0);
	AtomicInteger getObjectCalled=new AtomicInteger(0);
	AtomicReference<T> setObjectValue=new AtomicReference<>();
	
	public ModelProxy(IModel<T> delegate) {
		super(delegate);
	}
	
	@Override
	public void detach() {
		detachCalled.incrementAndGet();
		super.detach();
	}
	
	@Override
	public void setObject(T object) {
		setObjectCalled.incrementAndGet();
		setObjectValue.set(object);
		super.setObject(object);
	}
	
	@Override
	public T getObject() {
		getObjectCalled.incrementAndGet();
		return super.getObject();
	}

	public int getObjectCalled() {
		return getObjectCalled.get();
	}
	
	public int setObjectCalled() {
		return setObjectCalled.get();
	}
	
	public int detachCalled() {
		return detachCalled.get();
	}
	
	public T lastSetObjectValue() {
		return setObjectValue.get();
	}
}
