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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TestSampler {

	@Test
	public void booleanHasTwo() {
		Iterator<Boolean> iterator = new BoolVariation().iterator();
		Set<Boolean> values = new HashSet<Boolean>();

		assertThat(iterator.hasNext()).isTrue();
		values.add(iterator.next());
		assertThat(iterator.hasNext()).isTrue();
		values.add(iterator.next());
		assertThat(iterator.hasNext()).isFalse();

		assertThat(values.remove(Boolean.TRUE)).isTrue();
		assertThat(values.remove(Boolean.FALSE)).isTrue();
		assertThat(values.size()).isEqualTo((Integer) 0);
	}

	@Test
	public void booleanWithNullableHasMore() {
		Iterator<Boolean> iterator = new NullableVariation<Boolean>(new BoolVariation()).iterator();
		Set<Boolean> values = new HashSet<Boolean>();

		assertThat(iterator.hasNext()).isTrue();
		values.add(iterator.next());
		assertThat(iterator.hasNext()).isTrue();
		values.add(iterator.next());
		assertThat(iterator.hasNext()).isTrue();
		values.add(iterator.next());
		assertThat(iterator.hasNext()).isFalse();

		assertThat(values.remove(Boolean.TRUE)).isTrue();
		assertThat(values.remove(Boolean.FALSE)).isTrue();
		assertThat(values.remove(null)).isTrue();
		assertThat(values.size()).isEqualTo((Integer) 0);
	}

	@Test
	public void fixedValues() {
		Iterator<Integer> iterator = new FixedValuesVariation<Integer>(1, 2, 3).iterator();
		assertThat(iterator.hasNext()).isTrue();
		assertThat(iterator.next()).isEqualTo(Integer.valueOf(1));
		assertThat(iterator.hasNext()).isTrue();
		assertThat(iterator.next()).isEqualTo(Integer.valueOf(2));
		assertThat(iterator.hasNext()).isTrue();
		assertThat(iterator.next()).isEqualTo(Integer.valueOf(3));
	}
}
