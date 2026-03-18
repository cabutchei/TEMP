package br.gov.caixa.silce.negocio.integracaocompra.importers;

import java.util.Map;
import java.util.Objects;

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

public class SyncContext {

    private EntityCache cache;

    private AbstractIntegracaoSynchronizer<NuvemIntegracaoCompra, Compra, Long> compraSynchronizer;
    private AbstractIntegracaoSynchronizer<NuvemIntegracaoCombo, ComboAposta, Long> comboSynchronizer;
    private AbstractIntegracaoSynchronizer<NuvemIntegracaoAposta, Aposta<?>, Long> apostaSynchronizer;
    private AbstractIntegracaoSynchronizer<NuvemIntegracaoApostaComprada, ApostaComprada, Long> apostaCompradaSynchronizer;
    private AbstractIntegracaoSynchronizer<NuvemIntegracaoReserva, ReservaCotaBolao, ReservaCotaBolaoPK> reservaCotaBolaoSynchronizer;

    public SyncContext() {
        this.compraSynchronizer = new CompraSynchronizer(this);
        this.cache = new EntityCache();
        this.comboSynchronizer = new ComboApostaSynchronizer(this);
        this.apostaSynchronizer = new ApostaSynchronizer(this);
        this.apostaCompradaSynchronizer = new ApostaCompradaSynchronizer(this);
        this.reservaCotaBolaoSynchronizer = new ReservaCotaBolaoSynchronizer(this);
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
        private Map<Key<?>, AbstractEntidade<?>> cache;

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
    }

    public static class Key<T extends AbstractEntidade<?>> {

        private final Object id;
        private final Class<T> type;

        public Key(Object id, Class<T> type) {
            this.id = id;
            this.type = type;
        }

        public Class<T> getType() {
            return this.type;
        }

        public Object getId() {
            return this.id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;
            Key<?> other = (Key<?>) o;
            return type.equals(other.type) && id.equals(other.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, id);
        }
    }
}
