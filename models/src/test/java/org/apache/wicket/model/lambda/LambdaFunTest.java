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

import de.flapdoodle.wicket.model.AbstractReadOnlyDetachedModel;
import java.io.Serializable;
import java.util.function.Function;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;
import org.junit.Assert;
/**
 *
 * @author mosmann
 */
public class LambdaFunTest {
 
    @Test
    public void foo() {
        final Model<Integer> src = Model.of(1);
        final IModel<String> result = with(src).apply((Function<Integer,String> & Serializable) LambdaFunTest::intToString);
        Assert.assertEquals("1",result.getObject());
    }
    
    static String intToString(int value) {
        return ""+value;
    }
    
    static <T> Holder<T> with(IModel<T> model) {
        return new Holder<T>(model);
    }
    
    static class Holder<T> {
        private final IModel<T> model;

        public Holder(IModel<T> model) {
            this.model = model;
        }

        public <S extends Function<T,V> & Serializable, V> IModel<V> apply(S func) {
            return new AbstractReadOnlyDetachedModel<V>() {

                @Override
                protected V load() {
                    return func.apply(model.getObject());
                }
            };
        }
        
    }
    
    static interface SerFunction<S,D> extends Function<S,D>, Serializable {
        
    }
}
