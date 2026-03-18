package br.gov.caixa.dominio;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação utilizada para anotar quais métodos dos EJBs serão cacheados LOCALMENTE.
 * 
 * @author c101482
 * @author c127237
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LocalCacheable {

	// ver possiveis valores no ehcache.xsd
	boolean eternal() default false;

	int maxEntriesLocalHeap() default 100;

	int timeToIdleSeconds() default 60;

	int timeToLiveSeconds() default 60;

	boolean overflowToDisk() default false;
	
	boolean cacheNullValues() default true;

}
