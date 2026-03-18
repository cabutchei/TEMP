package br.gov.caixa.dominio;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação utilizada para anotar quais métodos dos EJBs serão cacheados pelo cliente REMOTO.
 * Toda a implementação do cache de fato é feita apenas no CLIENTE REMOTO. 
 * Essa anotação apenas MARCA as configurações que o cliente remoto deverá utilizar.
 * 
 * Chamadas de EJBs locais não devem usar essa anotação para definir seus caches.
 * Para isso existe a anotação {@link LocalCacheable}.
 * 
 * @author c101482
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cacheable {

	// ver possiveis valores no ehcache.xsd
	boolean eternal() default false;

	int maxEntriesLocalHeap() default 100;

	int timeToIdleSeconds() default 60;

	int timeToLiveSeconds() default 60;

	boolean overflowToDisk() default false;
	
	boolean cacheNullValues() default true;

}
