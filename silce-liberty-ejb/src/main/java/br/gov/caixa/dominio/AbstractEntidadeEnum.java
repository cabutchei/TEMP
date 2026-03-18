package br.gov.caixa.dominio;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import br.gov.caixa.util.CaixaEnum;

/**
 * Entidades referentes a tabela de domínio devem sobrescrever esta classe.
 * 
 * @author c101482 - Vinicius Ferraz Campos Florentino
 * 
 * @param <T>
 *            Tipo da PK, normalmente Long
 * @param <E>
 *            Enum criado como inner class na subclasse
 */
public abstract class AbstractEntidadeEnum<T extends Serializable, E extends CaixaEnum<T>>
		extends AbstractEntidade<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private E[] valores;

	public AbstractEntidadeEnum() {
		valores = createValores();

		Set<E> ids = new HashSet<E>();

		ids.addAll(Arrays.asList(valores));

		if (ids.size() != valores.length) {
			throw new IllegalStateException(
					"Os valores passados contém valores repetidos");
		}
	}

	public boolean isMesmoEnum(E tipo) {
		return tipo.getValue().equals(getId());
	}

	public E getEnum() {
		for (E valor : valores) {
			if (valor.getValue().equals(getId())) {
				return valor;
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		return (getId() == null) ? 0 : getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!this.getClass().equals(obj.getClass())) {
			return false;
		}
		AbstractEntidadeEnum<?, ?> other = (AbstractEntidadeEnum<?, ?>) obj;
		if (getId() == null && other.getId() != null) {
			return false;
		}
		return getId().equals(other.getId());
	}

	protected abstract E[] createValores();

}
