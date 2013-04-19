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
package de.flapdoodle.tests.sampler;


public class Variation {

	private Variation() {
		// no instance
	}
	
	public static IVariation<Boolean> bool() {
		return new BoolVariation();
	}
	
	public static <T extends Enum<T>> IVariation<T> of(Class<T> enumType) {
		return new EnumVariation<T>(enumType);
	}
	
	public static <T> IVariation<T> withNull(IVariation<T> source) {
		return new NullableVariation<T>(source);
	}
	
	public static <T> IVariation<T> of(T...values) {
		return new FixedValuesVariation<T>(values);
	}
}
