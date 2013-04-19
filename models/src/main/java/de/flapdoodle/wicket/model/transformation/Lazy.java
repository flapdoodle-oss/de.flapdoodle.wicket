package de.flapdoodle.wicket.model.transformation;

/**
 * value access indirection
 * 
 * @author mosmann
 * 
 */
public interface Lazy<V> {
	V get();
}
