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
package de.flapdoodle.wicket.detach;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.IDetachListener;
import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * some trouble with listview
 * @author mosmann
 *
 */
public class FieldInspectingDetachListener implements IDetachListener {

	private static final Logger log = LoggerFactory.getLogger(FieldInspectingDetachListener.class);

	@Override
	public void onDetach(Component component) {
		Class<? extends Component> clazz = component.getClass();
		if (false) log.debug("detaching {} ", typeName(clazz));
		
		List<Field> fields = getAllFields(clazz);
		
		Map<Object,Boolean> hitMap=new IdentityHashMap<Object, Boolean>();
		checkFields(typeName(clazz)+"[path="+component.getPath()+"]", hitMap, component, fields);
	}

	private String typeName(Class<? extends Component> clazz) {
		return "" + clazz.getName() + (clazz.isAnonymousClass() ? "("+clazz.getSuperclass().getName()+")" : "");
	}

	private void checkFields(String id, Map<Object,Boolean> hitMap, Object component, List<Field> fields) {
		for (Field f : fields) {
			if (isWorthALook(f)) {
				try {
					if (false) log.debug("inspect {}",f);
					
					f.setAccessible(true);
					Object value = f.get(component);
					if (!hitMap.containsKey(value)) {
						hitMap.put(value, true);
						if (value!=null) {
							if (value instanceof LoadableDetachableModel) {
								LoadableDetachableModel ldm=(LoadableDetachableModel) value;
								if (ldm.isAttached()) {
									log.error(id+": loadable model "+ldm+" in "+f+" is NOT detached ",new RuntimeException());
								}
							}
							
							checkFields(id,hitMap, value, getAllFields(value.getClass()));
						}
					}
				} catch (IllegalArgumentException e) {
					log.error("get value",e);
				} catch (IllegalAccessException e) {
					log.error("get value",e);
				}
			}
		}
		
		if (component instanceof Component) {
			Component c=(Component) component;
			IModel<?> model = c.getDefaultModel();
			if (model!=null) {
				checkFields(id,hitMap,model,getAllFields(model.getClass()));
			}
		}
	}

	private boolean isWorthALook(Field f) {
		Class<?> fieldType = f.getType();
		if (fieldType.isPrimitive()) return false;
		Package fieldPackage = fieldType.getPackage();
		if (fieldPackage!=null) {
			if (fieldPackage.getName().startsWith("java.lang.")) return false;
		}
		if (Page.class.isAssignableFrom(fieldType)) return false;
		if (f.getDeclaringClass()==Component.class) {
			if (f.getName().equals("parent")) {
				return false;
			}
			if (f.getName().equals("data")) {
				return false;
			}
		}
		if (Modifier.isStatic(f.getModifiers())) return false;
		return true;
	}

	private List<Field> getAllFields(Class<?> clazz) {
		List<Field> fields=new ArrayList<Field>();
		fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		Class<?> superclass = clazz.getSuperclass();
		if (superclass!=null) {
			fields.addAll(getAllFields(superclass));
		}
		return fields;
	}

	@Override
	public void onDestroyListener() {
		
	}

}
