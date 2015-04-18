package com.citsamex.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * true if there are more than one record in PowerSuite mapping to this object.
 * 
 * @author david.qian
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OneMany {
	boolean value();
}
