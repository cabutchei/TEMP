package br.caixa.loterias.bootstrap;

import br.caixa.loterias.model.aposta.ApostaIndividual;
import br.caixa.loterias.model.apostador.Apostador;
import br.caixa.loterias.model.bolao.Bolao;
import br.caixa.loterias.model.combo.Combo;
import br.caixa.loterias.model.compra.Compra;
import br.caixa.loterias.model.compra.enums.SituacaoCompraEnum;
import br.caixa.loterias.model.enums.situacaoapostaindividual.SituacaoApostaIndividualEnum;
import br.caixa.loterias.model.enums.tipocomboenum.TipoComboEnum;
import br.caixa.loterias.model.fechamento.Fechamento;
import br.caixa.loterias.model.itemcompravel.ItemCompravel;
import br.caixa.loterias.model.itemcompravel.TipoItemCompravelEnum;
import br.caixa.loterias.model.matrizprognostico.MatrizPrognostico;
import br.caixa.loterias.model.matrizprognostico.TipoMatrizPrognosticoEnum;
import br.caixa.loterias.model.pagamento.entities.Pagamento;
import br.caixa.loterias.model.registroenviointegracao.RegistroEnvioIntegracao;
import br.caixa.loterias.model.reservacotabolao.ReservaCotaBolao;
import br.caixa.loterias.model.reservacotabolao.enums.SituacaoReservaCotaBolaoEnum;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class H2SampleDataLoader {

    private final EntityManager entityManager;

    public H2SampleDataLoader(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    void onStart(@Observes StartupEvent event) {
        Long totalCompras = entityManager.createQuery(
                "select count(c) from Compra c",
                Long.class
        ).getSingleResult();

        if (totalCompras > 0) {
            return;
        }

        LocalDateTime baseTime = LocalDateTime.of(2026, 3, 27, 10, 0);

        Apostador apostador1 = new Apostador("11111111111");
        apostador1.setIndicadorMigracao(Boolean.FALSE);
        entityManager.persist(apostador1);

        Apostador apostador2 = new Apostador("22222222222");
        apostador2.setIndicadorMigracao(Boolean.TRUE);
        entityManager.persist(apostador2);

        Pagamento pagamento1 = new Pagamento();
        entityManager.persist(pagamento1);

        Pagamento pagamento2 = new Pagamento();
        entityManager.persist(pagamento2);

        Combo combo = new Combo();
        combo.setTipoCombo(TipoComboEnum.SORTE_FACIL);
        combo.setMes(3L);
        combo.setDataInclusao(baseTime.minusDays(2));
        entityManager.persist(combo);

        Bolao bolao = new Bolao();
        bolao.setId(1001L);
        bolao.setQtdCotas(20);
        bolao.setTerminalLoterico(1234);
        bolao.setTimestampRegistro(baseTime.minusDays(3));
        bolao.setLotericaId(456L);
        bolao.setTimestampAtualizacao(baseTime.minusDays(1));
        bolao.setUfLotericaId(35L);
        bolao.setMunicipioLotericaId(3550308L);
        bolao.setIdBolaoMarketplace("Ym9sYW8tMTAwMQ==");
        entityManager.persist(bolao);

        Compra compra1 = new Compra();
        compra1.setApostador(apostador1);
        compra1.setPagamento(pagamento1);
        compra1.setSituacao(SituacaoCompraEnum.FINALIZADA_TODAS_APOSTAS_EFETIVADAS);
        compra1.setTimestampInicio(baseTime.minusHours(4));
        compra1.setTimestampAlteracaoSituacao(baseTime.minusHours(2));
        compra1.setTimestampFinalizacao(baseTime.minusHours(1));
        compra1.setSubcanal(1);
        compra1.setIdCompraLegado(9001L);
        compra1.setNsuCompra(800001L);
        compra1.setIdExterno("PAY-EXT-9001");
        entityManager.persist(compra1);

        ApostaIndividual aposta1 = ApostaIndividual.builder()
                .idLegado(7001L)
                .indicadorCombo(Boolean.TRUE)
                .teimosinhaQtd(2)
                .teimosinhaConcursoInicial(3210)
                .teimosinhaBilheteTroca(Boolean.FALSE)
                .espelho(Boolean.FALSE)
                .geraEspelho(Boolean.FALSE)
                .valor(new BigDecimal("12.50"))
                .surpresinha(false)
                .concursoAlvo(3210)
                .situacao(SituacaoApostaIndividualEnum.EM_PROCESSAMENTO)
                .tipoConcurso(1L)
                .modalidade(8L)
                .serieBilhete("LF-001")
                .nsuAposta(555001L)
                .timestampInclusao(baseTime.minusHours(4))
                .timestampEnvioSispl(baseTime.minusHours(3))
                .timestampEfetivacaoSispl(baseTime.minusHours(2))
                .timestampFinalizacaoProcessamento(baseTime.minusHours(1))
                .timestampAlteracaoSituacao(baseTime.minusHours(1))
                .combo(combo)
                .build();

        MatrizPrognostico matriz1 = createMatriz(
                "01,02,03,04,05,06,07,08,09,10,11,12,13,14,15",
                TipoMatrizPrognosticoEnum.LOTOFACIL_MATRIZ_UNICA,
                15L,
                aposta1
        );
        aposta1.setMatrizesPrognostico(List.of(matriz1));

        ItemCompravel itemCompra1 = new ItemCompravel();
        itemCompra1.setCompra(compra1);
        itemCompra1.setTipo(TipoItemCompravelEnum.APOSTA);
        itemCompra1.setAposta(aposta1);
        itemCompra1.setApostaLegado(7001L);
        entityManager.persist(itemCompra1);

        ReservaCotaBolao reserva1 = new ReservaCotaBolao();
        reserva1.setSituacao(SituacaoReservaCotaBolaoEnum.RESERVADA);
        reserva1.setValorCota(new BigDecimal("20.00"));
        reserva1.setValorTarifaServico(new BigDecimal("5.00"));
        reserva1.setValorTotalCota(new BigDecimal("25.00"));
        reserva1.setTimestampAlteracaoSituacao(baseTime.minusHours(2));
        reserva1.setTimestampExpiracaoReserva(baseTime.plusHours(6));
        reserva1.setTimestampReserva(baseTime.minusHours(3));
        reserva1.setBolao(bolao);
        reserva1.setIdReservaCarrinho(9901L);
        reserva1.setNsuReserva(777001L);
        reserva1.setNsbCota("NSB-COTA-001");
        reserva1.setNuCotaInterno(7L);
        reserva1.setTimestampFinalizacaoProcessamento(baseTime.minusHours(1));
        reserva1.setIdCotaMkp("Nzpib2xhby0xMDAx");
        reserva1.setTimestampCriacao(baseTime.minusHours(3));
        reserva1.setTimestampModificacao(baseTime.minusHours(2));
        reserva1.setValorTarifaCusteio(new BigDecimal("1.50"));
        reserva1.setValorCotaCusteio(new BigDecimal("18.50"));

        ItemCompravel itemCompra2 = new ItemCompravel();
        itemCompra2.setCompra(compra1);
        itemCompra2.setTipo(TipoItemCompravelEnum.RESERVA_COTA);
        itemCompra2.setReserva(reserva1);
        entityManager.persist(itemCompra2);

        compra1.setItensCompraveis(new ArrayList<>(List.of(itemCompra1, itemCompra2)));

        Compra compra2 = new Compra();
        compra2.setApostador(apostador2);
        compra2.setPagamento(pagamento2);
        compra2.setSituacao(SituacaoCompraEnum.EM_PROCESSAMENTO);
        compra2.setTimestampInicio(baseTime.minusDays(1).minusHours(2));
        compra2.setTimestampAlteracaoSituacao(baseTime.minusDays(1).minusHours(1));
        compra2.setTimestampFinalizacao(null);
        compra2.setSubcanal(3);
        compra2.setIdCompraLegado(9002L);
        compra2.setNsuCompra(800002L);
        compra2.setIdExterno("PAY-EXT-9002");
        entityManager.persist(compra2);

        ApostaIndividual aposta2 = ApostaIndividual.builder()
                .idLegado(7002L)
                .indicadorCombo(Boolean.FALSE)
                .teimosinhaQtd(1)
                .teimosinhaConcursoInicial(6543)
                .teimosinhaBilheteTroca(Boolean.FALSE)
                .espelho(Boolean.FALSE)
                .geraEspelho(Boolean.FALSE)
                .valor(new BigDecimal("5.00"))
                .surpresinha(true)
                .concursoAlvo(6543)
                .situacao(SituacaoApostaIndividualEnum.PAGAMENTO_EM_PROCESSAMENTO)
                .tipoConcurso(1L)
                .modalidade(2L)
                .serieBilhete("MS-001")
                .nsuAposta(555002L)
                .timestampInclusao(baseTime.minusDays(1).minusHours(2))
                .timestampEnvioSispl(baseTime.minusDays(1).minusHours(1))
                .timestampEfetivacaoSispl(null)
                .timestampFinalizacaoProcessamento(null)
                .timestampAlteracaoSituacao(baseTime.minusDays(1).minusMinutes(30))
                .build();

        MatrizPrognostico matriz2 = createMatriz(
                "03,07,11,18,25,42",
                TipoMatrizPrognosticoEnum.MEGASENA_MATRIZ_UNICA,
                6L,
                aposta2
        );
        aposta2.setMatrizesPrognostico(List.of(matriz2));

        ItemCompravel itemCompra3 = new ItemCompravel();
        itemCompra3.setCompra(compra2);
        itemCompra3.setTipo(TipoItemCompravelEnum.APOSTA);
        itemCompra3.setAposta(aposta2);
        itemCompra3.setApostaLegado(7002L);
        entityManager.persist(itemCompra3);

        compra2.setItensCompraveis(new ArrayList<>(List.of(itemCompra3)));

        Fechamento fechamento = new Fechamento();
        fechamento.setIdConcurso(3210L);
        fechamento.setIdModalidade(8L);
        entityManager.persist(fechamento);

        RegistroEnvioIntegracao registroEnvioIntegracao = new RegistroEnvioIntegracao();
        entityManager.persist(registroEnvioIntegracao);

        entityManager.flush();
    }

    private MatrizPrognostico createMatriz(
            String valor,
            TipoMatrizPrognosticoEnum tipo,
            Long quantidadeMarcacoes,
            ApostaIndividual aposta
    ) {
        MatrizPrognostico matriz = new MatrizPrognostico();
        matriz.setValor(valor);
        matriz.setTipoMatriz(tipo);
        matriz.setQuantidadeMarcacoes(quantidadeMarcacoes);
        matriz.setApostaAssociada(aposta);
        return matriz;
    }
}
