package br.gov.caixa.silce.negocio.integracaocompra.importers;

import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;

import br.gov.caixa.silce.dominio.entidade.Apostador;
import br.gov.caixa.silce.dominio.entidade.Compra;
import br.gov.caixa.silce.dominio.entidade.Loterica;
import br.gov.caixa.silce.dominio.entidade.SituacaoCompra;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoApostador;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoCompra;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoLoterica;
import br.gov.caixa.silce.negocio.apostador.dao.ApostadorDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.CompraDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.SituacaoCompraDAOLocal;
import br.gov.caixa.silce.negocio.apostador.dao.LotericaDAOLocal;
import br.gov.caixa.silce.negocio.integracaocompra.mappers.CompraMapper;


public class CompraSynchronizer extends AbstractIdNuvemLegadoSynchronizer<NuvemIntegracaoCompra, Compra> {

    private static final Logger LOG = LogManager.getLogger(CompraSynchronizer.class, new MessageFormatMessageFactory());

    ApostadorDAOLocal apostadorDAO;
    CompraDAOLocal compraDAO;
    SituacaoCompraDAOLocal situacaoCompraDAO;
    LotericaDAOLocal lotericaDAO;

    public CompraSynchronizer(SyncContext.EntityCache cache) throws NamingException {
        apostadorDAO = getDao(ApostadorDAOLocal.class);
        compraDAO = getDao(CompraDAOLocal.class);
        situacaoCompraDAO = getDao(SituacaoCompraDAOLocal.class);
        lotericaDAO = getDao(LotericaDAOLocal.class);
        this.dao = compraDAO;
        this.cache = cache;
        this.mapper = new CompraMapper();
    }

    @Override
    protected Compra create(NuvemIntegracaoCompra compraNuvem) {
        Compra compra = getCache().find(compraNuvem.idNuvem, Compra.class);
        compra.setCompraIntegrada(true);
        resolve(compraNuvem, compra);
        compraDAO.insert(compra);
        return compra;
    }

    @Override
    protected Compra update(NuvemIntegracaoCompra compraNuvem) {
        if (compraNuvem.getIdLegado() == null) {
            throw new IllegalStateException("id legado da aposta é null");
        }
        Long idCompra = compraNuvem.getIdNuvem();
        Compra compra = getCache().find(idCompra, Compra.class);
        resolve(compraNuvem, compra);
        Compra x = compraDAO.update(compra);
        if (compra != x) throw new IllegalStateException("entidade retornada não é a mesma do cache");
        return compra;
    }

    @Override
    protected void resolve(NuvemIntegracaoCompra compraNuvem, Compra compra) {
        Apostador apostador = recuperarApostador(compraNuvem.apostador);
        compra.setApostador(apostador);
        compra.setCompraIntegrada(true);
        compra.setSituacao(recuperarSituacaoCompra(compraNuvem));
        compra.setLoterica(recuperarLoterica(compraNuvem.loterica));
    }

    private SituacaoCompra recuperarSituacaoCompra(NuvemIntegracaoCompra compraNuvem) {
        return compraNuvem.situacaoCompra != null
            ? situacaoCompraDAO.findById(compraNuvem.situacaoCompra)
            : null;
    }

    private Apostador recuperarApostador(NuvemIntegracaoApostador apostadorNuvem) {
        return apostadorDAO.findByCPF(apostadorNuvem.getParsedCpf());
    }

    private Loterica recuperarLoterica(NuvemIntegracaoLoterica loterica) {
        if (loterica == null)
            return null;

        return lotericaDAO.findByCodigo(loterica.polo, loterica.id, loterica.dv);
    }
}
