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
package org.apache.wicket.model.lambda;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 *
 */
public class Lambdas
{
	/**
	 * Creates a chained model that uses a model and a getter. This model is null safe, such that
	 * when the base model returns {@code null} the resulting expression is {@code null}. Example
	 * usage:
	 * 
	 * <pre>
	 * IModel<Person> personModel = ...;
	 * Lambdas.of(personModel, Person::getName);
	 * </pre>
	 * 
	 * @param base
	 *            the model that is used as a base to evaluate the getter against
	 * @param getter
	 *            a function that gets a {@code <T>}
	 * @return a model that takes the base model and applies the getter to get the model object
	 */
	public static <T, X, F extends SerializableFunction<X, T>> IModel<T> of(IModel<X> base,
		F getter)
	{
		return new IDefaultModel<T>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public T getObject()
			{
				X object = base.getObject();
				if (object != null)
				{
					return getter.apply(object);
				}
				return null;
			}

			@Override
			public void detach()
			{
				base.detach();
			}
		};
	}

	@SuppressWarnings("javadoc")
	public static <T, X, Y, XY extends SerializableFunction<X, Y>, YT extends SerializableFunction<Y, T>> IModel<T> of(
		IModel<X> base, XY xToY, YT yToT)
	{
		return new IDefaultModel<T>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public T getObject()
			{
				X object = base.getObject();
				if (object != null)
				{
					Y y = xToY.apply(object);
					if (y != null)
					{
						return yToT.apply(y);
					}
				}
				return null;
			}

			@Override
			public void detach()
			{
				base.detach();
			}
		};
	}

	/**
	 * Creates a model that uses the getter and setter. Calls to the getter (and setter) are not
	 * cached but directly executed on each {@code getObject()} and {@code setObject()}. Example
	 * usage:
	 * 
	 * <pre>
	 * Lambdas.of(person::getName, person::setName);
	 * </pre>
	 * 
	 * @param getter
	 *            a supplier that gets a {@code <T>}
	 * @param setter
	 *            a consumer that sets a {@code <T>}
	 * @return a model that uses the consumer and supplier to get and set the model object
	 */
	public static <T, G extends SerializableSupplier<T>, S extends SerializableConsumer<T>> IModel<T> of(
		G getter, S setter)
	{
		return new LambdaModel<>(getter, setter);
	}

	/**
	 * A read-only model that caches the call to the {@code getter()} until the model is detached.
	 * 
	 * @param getter
	 *            a supplier that gets a {@code <T>}
	 * @return the read-only model that gets a {@code <T>} and caches the result until the model is
	 *         detached.
	 */
	public static <T, S extends SerializableSupplier<T>> IModel<T> cachedReadOnly(S getter)
	{
		return new LoadableDetachableModel<T>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected T load()
			{
				return getter.get();
			}
		};
	}

	/**
	 * Creates a read-only model using a getter. Example usage:
	 * 
	 * <pre>
	 * Lambdas.readOnly(person::getName);
	 * </pre>
	 * 
	 * @param getter
	 *            the getter function (also known as supplier)
	 * @return a read-only model
	 */
	public static <T, S extends SerializableSupplier<T>> IModel<T> readOnly(S getter)
	{
		return new ReadOnlyModel<>(getter);
	}

	/**
	 * Creates a write-only model using a getter. Example usage:
	 * 
	 * <pre>
	 * Lambdas.writeOnly(person::setName);
	 * </pre>
	 * 
	 * @param setter
	 *            the setter function (also known as consumer)
	 * @return a write-only model
	 * @throws UnsupportedOperationException
	 *             when {@link IModel#getObject()} is called
	 */
	public static <T, S extends Serializable & Consumer<T>> IModel<T> writeOnly(S setter)
	{
		return new WriteOnlyModel<>(setter);
	}


	interface IDefaultModel<T> extends IModel<T>
	{
		@Override
		default T getObject()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		default void setObject(T object)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		default void detach()
		{
		}
	}

	static class ReadOnlyModel<T, G extends SerializableSupplier<T>> implements IDefaultModel<T>
	{
		private static final long serialVersionUID = 1L;

		private G getter;

		public ReadOnlyModel(G getter)
		{
			this.getter = getter;
		}

		@Override
		public T getObject()
		{
			return getter.get();
		}

		@Override
		public int hashCode()
		{
			return org.apache.wicket.util.lang.Objects.hashCode(getter);
		}

		@Override
		public boolean equals(Object obj)
		{
			if (obj == null)
			{
				return false;
			}
			if (getClass() != obj.getClass())
			{
				return false;
			}
			final ReadOnlyModel<?, ?> other = (ReadOnlyModel<?, ?>)obj;
			if (!Objects.equals(this.getter, other.getter))
			{
				return false;
			}
			return true;
		}
	}

	static class WriteOnlyModel<T, S extends Serializable & Consumer<T>> implements IDefaultModel<T>
	{
		private static final long serialVersionUID = 1L;

		private S setter;

		public WriteOnlyModel(S consumer)
		{
			this.setter = consumer;
		}

		@Override
		public void setObject(T object)
		{
			setter.accept(object);
		}

		@Override
		public int hashCode()
		{
			return org.apache.wicket.util.lang.Objects.hashCode(setter);
		}

		@Override
		public boolean equals(Object obj)
		{
			if (obj == null)
			{
				return false;
			}
			if (getClass() != obj.getClass())
			{
				return false;
			}
			final WriteOnlyModel<?, ?> other = (WriteOnlyModel<?, ?>)obj;
			if (!Objects.equals(this.setter, other.setter))
			{
				return false;
			}
			return true;
		}
	}

	static class LambdaModel<T, S extends Serializable & Supplier<T>, C extends Serializable & Consumer<T>>
		implements
			IModel<T>
	{
		private static final long serialVersionUID = 1L;

		private S supplier;

		private C consumer;

		public LambdaModel(S supplier, C consumer)
		{
			this.supplier = supplier;
			this.consumer = consumer;
		}

		@Override
		public void detach()
		{
		}

		@Override
		public T getObject()
		{
			return supplier.get();
		}

		@Override
		public void setObject(T object)
		{
			consumer.accept(object);
		}

		@Override
		public int hashCode()
		{
			return org.apache.wicket.util.lang.Objects.hashCode(supplier, consumer);
		}

		@Override
		public boolean equals(Object obj)
		{
			if (obj == null)
			{
				return false;
			}
			if (getClass() != obj.getClass())
			{
				return false;
			}
			final LambdaModel<?, ?, ?> other = (LambdaModel<?, ?, ?>)obj;
			if (!Objects.equals(this.supplier, other.supplier))
			{
				return false;
			}
			if (!Objects.equals(this.consumer, other.consumer))
			{
				return false;
			}
			return true;
		}
	}
}
