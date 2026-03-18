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
import br.gov.caixa.silce.negocio.apostador.dao.ApostadorDAO;
import br.gov.caixa.silce.negocio.aposta.dao.CompraDAO;
import br.gov.caixa.silce.negocio.aposta.dao.SituacaoCompraDAO;
import br.gov.caixa.silce.negocio.apostador.dao.LotericaDAO;


public class CompraSynchronizer
        extends IntegracaoSynchronizer<NuvemIntegracaoCompra, Compra, Long> {

    private static final Logger LOG =
        LogManager.getLogger(CompraSynchronizer.class, new MessageFormatMessageFactory());

    ApostadorDAO apostadorDAO;
    CompraDAO compraDAO;
    SituacaoCompraDAO situacaoCompraDAO;
    LotericaDAO lotericaDAO;

    public CompraSynchronizer(SyncContext context) {
        super(context);
        try {
            apostadorDAO = getDao(ApostadorDAO.class);
            compraDAO = getDao(CompraDAO.class);
            situacaoCompraDAO = getDao(SituacaoCompraDAO.class);
            lotericaDAO = getDao(LotericaDAO.class);
        } catch (NamingException e) {
            logInitError(e);
        }
    }

    @Override
    protected Compra prepareUpdate(NuvemIntegracaoCompra compraNuvem) {
        Compra entity = dao.findById(compraNuvem.getIdLegado());
        mapper.map(compraNuvem, entity);
        return entity;
    }

    @Override
    protected Compra create(NuvemIntegracaoCompra compraNuvem) {

        Compra compra =
            this.context.getCache().find(compraNuvem.idNuvem, Compra.class);

        compra.setCompraIntegrada(true);

        resolveRelations(compraNuvem, compra);

        compraDAO.insert(compra);

        this.context.getCache().put(compraNuvem.idNuvem, compra);

        return compra;
    }

    @Override
    protected Compra update(NuvemIntegracaoCompra compraNuvem) {
        Long idLegadoCompra = compraNuvem.getIdLegado();

        if (compraNuvem.getIdLegado() == null) {
            throw new IllegalStateException("id legado da aposta é null");
        }
        Long idCompra = compraNuvem.getIdNuvem();
        // Compra compra = compraDAO.findById(compra.idLegado);
        Compra compra = this.context.getCache().find(idCompra, Compra.class);
        resolveRelations(compraNuvem, compra);
        Compra x = compraDAO.update(compra);
        if (compra != x) throw new IllegalStateException("entidade retornada não é a mesma do cache");
        return compra;
    }

    @Override
    protected void resolveRelations(NuvemIntegracaoCompra compraNuvem, Compra compra) {
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
