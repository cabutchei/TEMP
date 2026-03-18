package br.gov.caixa.silce.negocio.integracaocompra.importers;

import javax.naming.NamingException;

import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.entidade.ApostaComprada;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoApostaComprada;
import br.gov.caixa.silce.negocio.aposta.dao.ApostaCompradaDAO;
import br.gov.caixa.silce.negocio.aposta.dao.ApostaDAO;
import br.gov.caixa.silce.negocio.aposta.dao.SituacaoApostaDAO;

public class ApostaCompradaSynchronizer extends AbstractIntegracaoSynchronizer<NuvemIntegracaoApostaComprada, ApostaComprada, Long> {

    ApostaDAO apostaDAO;
    ApostaCompradaDAO apostaCompradaDAO;
    SituacaoApostaDAO situacaoApostaDAO;

    public ApostaCompradaSynchronizer(SyncContext context) {
        super(context);
        try {
            this.apostaDAO = getDao(ApostaDAO.class);
            this.apostaCompradaDAO = getDao(ApostaCompradaDAO.class);
            this.situacaoApostaDAO = getDao(SituacaoApostaDAO.class);
        } catch (NamingException e) {
            logInitError(e);
        }
    }

    @Override
    protected void cache(NuvemIntegracaoApostaComprada nuvem, ApostaComprada entity) {
        this.context.getCache().put(nuvem.aposta.getIdNuvem(), entity);
    }

    protected ApostaComprada find(NuvemIntegracaoApostaComprada nuvem) {
        Long id = nuvem.aposta.getIdNuvem();
        return super.find(id);
    }

    @Override
    protected ApostaComprada prepareUpdate(NuvemIntegracaoApostaComprada nuvem) {
        ApostaComprada entity = apostaCompradaDAO.findNuvem(nuvem.nsu);
        mapper.map(nuvem, entity);
        return entity;
    }

    @Override
    protected ApostaComprada create(NuvemIntegracaoApostaComprada nuvem) {
        ApostaComprada apostaComprada = find(nuvem);
        resolveRelations(nuvem, apostaComprada);
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

        resolveRelations(nuvem, apostaComprada);

        apostaCompradaDAO.insert(apostaComprada);

        return apostaComprada;
    }

    @Override
    protected void resolveRelations(NuvemIntegracaoApostaComprada nuvem, ApostaComprada entity) {

        if (nuvem.aposta == null) {
            throw new IllegalStateException("Não há aposta associada a essa aposta comprada");
        }

        Long idAposta = nuvem.aposta.idLegado;

        if (idAposta == null) {
            throw new IllegalStateException("Nenhum id legado associado à compra");
        }

        Aposta<?> aposta = apostaDAO.findById(idAposta);

        if (aposta == null) {
            throw new IllegalStateException("Nenhuma aposta encontrada para o id " + idAposta);
        }

        entity.setAposta(aposta);
        entity.setParticao(aposta.getParticao());
        entity.setMes(aposta.getMes());

        entity.setSituacao(
            situacaoApostaDAO.findById(nuvem.situacao));
    }
}
