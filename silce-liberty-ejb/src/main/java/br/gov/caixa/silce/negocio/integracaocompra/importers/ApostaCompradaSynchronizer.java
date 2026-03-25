package br.gov.caixa.silce.negocio.integracaocompra.importers;

import javax.naming.NamingException;

import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.entidade.ApostaComprada;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoApostaComprada;
import br.gov.caixa.silce.negocio.aposta.dao.ApostaCompradaDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.ApostaDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.SituacaoApostaDAOLocal;
import br.gov.caixa.silce.negocio.integracaocompra.mappers.ApostaCompradaMapper;

public class ApostaCompradaSynchronizer extends AbstractIntegracaoSynchronizer<NuvemIntegracaoApostaComprada, ApostaComprada, Long> {

    ApostaDAOLocal apostaDAO;
    ApostaCompradaDAOLocal apostaCompradaDAO;
    SituacaoApostaDAOLocal situacaoApostaDAO;

    public ApostaCompradaSynchronizer(SyncContext.EntityCache cache) throws NamingException {
        this.apostaDAO = getDao(ApostaDAOLocal.class);
        this.apostaCompradaDAO = getDao(ApostaCompradaDAOLocal.class);
        this.situacaoApostaDAO = getDao(SituacaoApostaDAOLocal.class);
        this.dao = apostaCompradaDAO;
        this.cache = cache;
        this.mapper = new ApostaCompradaMapper();
    }

    @Override
    protected void cachePreparedEntity(NuvemIntegracaoApostaComprada nuvem, ApostaComprada entity) {
        // cacheando pelo id nuvem da aposta, uma vez que não temos id's no dto nuvem da aposta comprada. Isso não gera ambiguidade porque a chave é composta.
        getCache().put(nuvem.aposta.getIdNuvem(), entity);
    }

    protected ApostaComprada find(NuvemIntegracaoApostaComprada nuvem) {
        Long id = nuvem.aposta.getIdNuvem();
        return super.find(id);
    }

    @Override
    protected ApostaComprada fetch(NuvemIntegracaoApostaComprada nuvem) {
        return apostaCompradaDAO.findNuvem(nuvem.nsu);
    }

    @Override
    protected ApostaComprada create(NuvemIntegracaoApostaComprada nuvem) {
        ApostaComprada apostaComprada = find(nuvem);
        resolve(nuvem, apostaComprada);
        apostaCompradaDAO.insert(apostaComprada);
        return apostaComprada;
    }

    @Override
    protected ApostaComprada update(NuvemIntegracaoApostaComprada nuvem) {
        ApostaComprada apostaComprada = find(nuvem);
        Long idLegadoAposta = nuvem.aposta.idLegado;
        if (idLegadoAposta == null) {
            throw new IllegalStateException("id legado da aposta é null");
        }
        resolve(nuvem, apostaComprada);
        apostaCompradaDAO.insert(apostaComprada);
        return apostaComprada;
    }

    @Override
    protected void resolve(NuvemIntegracaoApostaComprada nuvem, ApostaComprada entity) {

        if (nuvem.aposta == null) {
            throw new IllegalStateException("Não há aposta associada a essa aposta comprada");
        }

        Aposta<?> aposta = getCache().find(nuvem.aposta.idNuvem, Aposta.class);

        if (aposta == null) {
            throw new IllegalStateException("Nenhuma aposta encontrada para o id " + nuvem.aposta.idNuvem);
        }

        entity.setAposta(aposta);
        entity.setParticao(aposta.getParticao());
        entity.setMes(aposta.getMes());

        entity.setSituacao(
            situacaoApostaDAO.findById(nuvem.situacao));
    }
}
