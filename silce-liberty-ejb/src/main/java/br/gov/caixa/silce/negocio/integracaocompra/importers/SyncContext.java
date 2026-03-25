package br.gov.caixa.silce.negocio.integracaocompra.importers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.naming.NamingException;

import br.gov.caixa.dominio.AbstractEntidade;
import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.entidade.ApostaComprada;
import br.gov.caixa.silce.dominio.entidade.ComboAposta;
import br.gov.caixa.silce.dominio.entidade.Compra;
import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolao;
import br.gov.caixa.silce.dominio.entidade.ReservaCotaBolaoPK;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoAposta;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoApostaComprada;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoCombo;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoCompra;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoReserva;

/**
 * Contêiner que reúne os sincronizadores necessários a uma sincronização completa. Também guarda o cache/grafo de entidades. Os sincronizadores compartilham o
 * contexto 
 */
public class SyncContext {

    private EntityCache cache;

    private AbstractIntegracaoSynchronizer<NuvemIntegracaoCompra, Compra, Long> compraSynchronizer;
    private AbstractIntegracaoSynchronizer<NuvemIntegracaoCombo, ComboAposta, Long> comboSynchronizer;
    private AbstractIntegracaoSynchronizer<NuvemIntegracaoAposta, Aposta<?>, Long> apostaSynchronizer;
    private AbstractIntegracaoSynchronizer<NuvemIntegracaoApostaComprada, ApostaComprada, Long> apostaCompradaSynchronizer;
    private AbstractIntegracaoSynchronizer<NuvemIntegracaoReserva, ReservaCotaBolao, ReservaCotaBolaoPK> reservaCotaBolaoSynchronizer;

    public SyncContext() throws NamingException {
        this.cache = new EntityCache();
        this.compraSynchronizer = new CompraSynchronizer(this.cache);
        this.comboSynchronizer = new ComboApostaSynchronizer(this.cache);
        this.apostaSynchronizer = new ApostaSynchronizer(this.cache);
        this.apostaCompradaSynchronizer = new ApostaCompradaSynchronizer(this.cache);
        this.reservaCotaBolaoSynchronizer = new ReservaCotaBolaoSynchronizer(this.cache);
    }

    public AbstractIntegracaoSynchronizer<NuvemIntegracaoCompra, Compra, Long> getCompraSynchronizer() {
        return compraSynchronizer;
    }

    public AbstractIntegracaoSynchronizer<NuvemIntegracaoAposta, Aposta<?>, Long> getApostaSynchronizer() {
        return apostaSynchronizer;
    }

    public AbstractIntegracaoSynchronizer<NuvemIntegracaoApostaComprada, ApostaComprada, Long> getApostaCompradaSynchronizer() {
        return apostaCompradaSynchronizer;
    }

    public AbstractIntegracaoSynchronizer<NuvemIntegracaoCombo, ComboAposta, Long> getComboSynchronizer() {
        return comboSynchronizer;
    }

    public AbstractIntegracaoSynchronizer<NuvemIntegracaoReserva, ReservaCotaBolao, ReservaCotaBolaoPK> getReservaCotaBolaoSynchronizer() {
        return reservaCotaBolaoSynchronizer;
    }

    public EntityCache getCache() {
        return cache;
    }

    public Map<Key<?>, Serializable> getIdMap() {
        return cache.getIdMap();
    }

    public <E extends AbstractEntidade<I>, I extends Serializable> Map<Long, I> getIdMap(Class<E> type) {
        return cache.getIdMap(type);
    }

    public void dispose() {
        clearState();
    }

    private void clearState() {
        this.compraSynchronizer = null;
        this.comboSynchronizer = null;
        this.apostaSynchronizer = null;
        this.apostaCompradaSynchronizer = null;
        this.reservaCotaBolaoSynchronizer = null;
        this.cache = null;
    }

    public static class EntityCache {

        private volatile boolean disposed;
        private final Map<Key<?>, AbstractEntidade<?>> cache = new HashMap<>();

        public boolean isDisposed() {
            return disposed;
        }

        public void dispose() {
            disposed = true;
        }

        public <E extends AbstractEntidade<?>> E find(Key<E> key) {
            AbstractEntidade<?> entity = cache.get(key);
            Class<E> type = key.getType();
            return type.cast(entity);
        }

        public <E extends AbstractEntidade<?>> E find(Long id, Class<E> type) {
            Key<E> key = new Key<>(id, type);
            return find(key);
        }

        public void put(Key<?> key, AbstractEntidade<?> value) {
            this.cache.put(key, value);
        }

        @SuppressWarnings("rawtypes")
        public void put(Long id, AbstractEntidade<?> value) {
            @SuppressWarnings("unchecked")
            Key key = new Key(id, value.getClass());
            cache.put(key, value);
        }

        public Map<Key<?>, Serializable> getIdMap() {
            Map<Key<?>, Serializable> ids = new HashMap<Key<?>, Serializable>();
            for (Entry<Key<?>, AbstractEntidade<?>> entry : cache.entrySet()) {
                ids.put(entry.getKey(), entry.getValue().getId());
            }
            return ids;
        }

        public <E extends AbstractEntidade<I>, I extends Serializable> Map<Long, I> getIdMap(Class<E> type) {
            Map<Long, I> ids = new HashMap<Long, I>();

            for (Entry<Key<?>, AbstractEntidade<?>> entry : cache.entrySet()) {
                Key<?> key = entry.getKey();
                if (!key.matches(type)) {
                    continue;
                }

                Object idNuvem = key.getId();
                if (!(idNuvem instanceof Long)) {
                    throw new IllegalStateException("id nuvem incompatível com o mapa tipado: " + idNuvem);
                }

                @SuppressWarnings("unchecked")
                AbstractEntidade<I> entity = (AbstractEntidade<I>) entry.getValue();
                ids.put((Long) idNuvem, entity.getId());
            }

            return ids;
        }
    }

    public static class Key<T extends AbstractEntidade<?>> {

        private final Object id;
        private final Class<T> type;
        private final Class<?> identityRootType;

        public Key(Object id, Class<T> type) {
            this.id = id;
            this.type = type;
            this.identityRootType = resolveIdentityRootType(type);
        }

        public Class<T> getType() {
            return this.type;
        }

        public Object getId() {
            return this.id;
        }

        public boolean matches(Class<?> otherType) {
            return areCompatibleTypes(type, otherType);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;
            Key<?> other = (Key<?>) o;
            return Objects.equals(id, other.id) && areCompatibleTypes(type, other.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(identityRootType, id);
        }

        private static boolean areCompatibleTypes(Class<?> left, Class<?> right) {
            return left.isAssignableFrom(right) || right.isAssignableFrom(left);
        }

        private static Class<?> resolveIdentityRootType(Class<?> type) {
            Class<?> root = type;
            Class<?> parent = root.getSuperclass();
            while (parent != null && parent != Object.class) {
                root = parent;
                parent = root.getSuperclass();
            }
            return root;
        }
    }
}
