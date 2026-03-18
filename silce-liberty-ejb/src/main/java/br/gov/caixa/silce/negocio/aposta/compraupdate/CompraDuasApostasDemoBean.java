package br.gov.caixa.silce.negocio.aposta.compraupdate;

import java.util.Arrays;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.gov.caixa.silce.dominio.Subcanal;
import br.gov.caixa.silce.dominio.entidade.ApostaMegasena;
import br.gov.caixa.silce.dominio.entidade.Compra;
import br.gov.caixa.silce.dominio.entidade.SituacaoAposta;
import br.gov.caixa.silce.dominio.entidade.SituacaoCompra;
import br.gov.caixa.silce.dominio.jogos.IndicadorSurpresinha;
import br.gov.caixa.silce.dominio.jogos.TipoConcurso;
import br.gov.caixa.silce.negocio.aposta.dao.ApostaDAOLocal;
import br.gov.caixa.silce.negocio.apostador.dao.ApostadorDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.CompraDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.SituacaoApostaDAOLocal;
import br.gov.caixa.silce.negocio.aposta.dao.SituacaoCompraDAOLocal;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.DataUtil;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.util.Hora;

@Stateless
public class CompraDuasApostasDemoBean implements CompraDuasApostasDemoLocal {

    private static final String SQL_UM_APOSTADOR_ID =
        "SELECT MIN(NU_APOSTADOR) FROM LCE.LCETB002_APOSTADOR";

    @EJB
    private ApostaDAOLocal apostaDAO;

    @EJB
    private CompraDAOLocal compraDAO;

    @EJB
    private ApostadorDAOLocal apostadorDAO;

    @EJB
    private SituacaoCompraDAOLocal situacaoCompraDAO;

    @EJB
    private SituacaoApostaDAOLocal situacaoApostaDAO;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long criarCompraComDuasApostas() {
        Data agora = DataUtil.getTimestampAtual();
        Hora horaAgora = new Hora(agora);

        Compra compra = criaCompra(agora, horaAgora);
        compraDAO.insert(compra);
        compraDAO.flush();

        ApostaMegasena apostaUm = criaAposta(
            compra,
            agora,
            horaAgora,
            new Decimal("12,00"),
            10001,
            Arrays.asList(1, 2, 3, 4, 5, 6),
            Math.abs(System.nanoTime()));
        apostaDAO.insert(apostaUm);

        // ApostaMegasena apostaDois = criaAposta(
        //     compra,
        //     agora,
        //     horaAgora,
        //     new Decimal("12,00"),
        //     10002,
        //     Arrays.asList(7, 8, 9, 10, 11, 12),
        //     Math.abs(System.nanoTime()));
        // apostaDAO.insert(apostaDois);
        apostaUm.setDataInclusao(DataUtil.stringToData("22/03/1995"));
        apostaDAO.flush();

        compra.setValorTotal(apostaUm.getValor());
        compra.setValorComissao(Decimal.ZERO);
        compraDAO.flush();

        Class<?> type = null;
        entityManager.find(type, 1);

        return compra.getId();
    }

    private Compra criaCompra(Data agora, Hora horaAgora) {
        Long apostadorId = recupereUmApostadorId();
        if (apostadorId == null) {
            throw new IllegalStateException("Nenhum apostador encontrado para criar a compra de teste.");
        }

        Compra compra = new Compra();
        compra.setApostador(apostadorDAO.findReferenceById(apostadorId));
        compra.setSituacao(situacaoCompraDAO.findReferenceById(SituacaoCompra.Situacao.CARRINHO.getValue()));
        compra.setNsu(Math.abs(System.currentTimeMillis()));
        compra.setDataInicioCompra(agora);
        compra.setHoraInicioCompra(horaAgora);
        compra.setMes(Long.valueOf(agora.getMonth()));
        compra.setParticao(Math.floorMod(compra.getNsu(), 10L));
        compra.setValorTotal(Decimal.ZERO);
        compra.setValorComissao(Decimal.ZERO);
        compra.setSubcanalPagamento(Subcanal.SILCE);
        return compra;
    }

    private ApostaMegasena criaAposta(
        Compra compra,
        Data agora,
        Hora horaAgora,
        Decimal valor,
        Integer concurso,
        java.util.List<Integer> prognosticos,
        Long nsuTransacao) {

        ApostaMegasena aposta = new ApostaMegasena();
        aposta.setCompra(compra);
        aposta.setMes(compra.getMes());
        aposta.setParticao(compra.getParticao());
        aposta.setTipoConcurso(TipoConcurso.NORMAL);
        aposta.setIndicadorSurpresinha(IndicadorSurpresinha.NAO_SURPRESINHA);
        aposta.setValor(valor);
        aposta.setConcursoAlvo(concurso);
        aposta.setDataInclusao(agora);
        aposta.setSubcanalCarrinhoAposta(Subcanal.SILCE);
        aposta.setIndicadorBolao(Boolean.FALSE);
        aposta.setQuantidadeTeimosinhas(1);
        aposta.setPrognosticos(prognosticos);

        aposta.setSituacao(situacaoApostaDAO.findReferenceById(SituacaoAposta.Situacao.NAO_REGISTRADA.getValue()));
        aposta.setConcursoInicial(concurso);
        aposta.setNsuTransacao(nsuTransacao);
        aposta.setApostaTroca(Boolean.FALSE);
        aposta.setDataInicioApostaComprada(agora);
        aposta.setHoraInicioApostaComprada(horaAgora);
        aposta.setValorComissao(Decimal.ZERO);
        aposta.getApostaComprada().setSubcanalPagamento(Subcanal.SILCE);
        aposta.getApostaComprada().setIndicadorBloqueioDevolucao(Boolean.FALSE);
        return aposta;
    }

    private Long recupereUmApostadorId() {
        Number result = compraDAO.execSingleSelectByNativeQuery(SQL_UM_APOSTADOR_ID);
        if (result == null) {
            return null;
        }
        return result.longValue();
    }
}
