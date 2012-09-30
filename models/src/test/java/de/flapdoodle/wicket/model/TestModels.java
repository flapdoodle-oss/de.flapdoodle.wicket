package de.flapdoodle.wicket.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Assert;
import org.junit.Test;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.functions.Function2;
import de.flapdoodle.functions.Function3;


public class TestModels {

	@Test
	public void testWithOneSourceModelFunctionLastStyle() {
		Model<Integer> source = Model.of(1);
		
		IModel<String> model = Models.on(source).apply(new Function1<String, Integer>() {
			@Override
			public String apply(Integer value) {
				return ""+value;
			}
		});
		
		checkModelModifications(source, model);

		checkExceptionOnSetObject(model,"Some String");
	}
	
	@Test
	public void testWithOneSourceModelFunctionFirstStyle() {
		Model<Integer> source = Model.of(1);
		
		IModel<String> model = Models.apply(new Function1<String, Integer>() {
			@Override
			public String apply(Integer value) {
				return ""+value;
			}
		}).to(source);
		
		checkModelModifications(source, model);

		checkExceptionOnSetObject(model,"Some String");
	}

	private void checkModelModifications(Model<Integer> source, IModel<String> model) {
		Assert.assertEquals("1", model.getObject());
		source.setObject(2);
		Assert.assertEquals("1", model.getObject());
		model.detach();
		Assert.assertEquals("2", model.getObject());
	}

	@Test
	public void testWithTwoSourceModels() {
		Model<Integer> a = Model.of(1);
		Model<Integer> b = Model.of(2);
		
		IModel<Integer> model = Models.on(a,b).apply(new Function2<Integer, Integer, Integer>() {
			@Override
			public Integer apply(Integer value, Integer value2) {
				return value+value2;
			}
		});
		
		Assert.assertEquals(Integer.valueOf(3), model.getObject());
		a.setObject(2);
		Assert.assertEquals(Integer.valueOf(3), model.getObject());
		model.detach();
		Assert.assertEquals(Integer.valueOf(4), model.getObject());
		b.setObject(3);
		Assert.assertEquals(Integer.valueOf(4), model.getObject());
		model.detach();
		Assert.assertEquals(Integer.valueOf(5), model.getObject());

		checkExceptionOnSetObject(model,100);
	}
	
	@Test
	public void testWith3SourceModels() {
		Model<Integer> a = Model.of(1);
		Model<Integer> b = Model.of(2);
		Model<String> postfix = Model.of("Kinder");
		
		IModel<String> model = Models.on(a,b,postfix).apply(new Function3<String, Integer, Integer,String>() {
			@Override
			public String apply(Integer value, Integer value2,String value3) {
				return value+value2+" "+value3;
			}
		});
		
		Assert.assertEquals("3 Kinder", model.getObject());
		a.setObject(2);
		Assert.assertEquals("3 Kinder", model.getObject());
		model.detach();
		Assert.assertEquals("4 Kinder", model.getObject());
		b.setObject(3);
		Assert.assertEquals("4 Kinder", model.getObject());
		model.detach();
		Assert.assertEquals("5 Kinder", model.getObject());

		checkExceptionOnSetObject(model,"nix");
	}
	
	private <T> void checkExceptionOnSetObject(IModel<T> model,T value) {
		Exception e=null;
		try {
			model.setObject(value);
		} catch (Exception ex) {
			e=ex;
		}
		Assert.assertNotNull("Exception on setObject",e);
	}
}
