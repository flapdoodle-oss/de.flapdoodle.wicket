package de.flapdoodle.tests.sampler;

public class EnumVariation<T extends Enum<T>> extends FixedValuesVariation<T> {

	public EnumVariation(Class<T> enumType) {
		super(enumType.getEnumConstants());
	}
}
