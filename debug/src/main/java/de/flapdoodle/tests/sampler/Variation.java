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
