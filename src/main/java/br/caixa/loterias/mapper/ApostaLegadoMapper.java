package br.caixa.loterias.mapper;

import br.caixa.loterias.model.combo.Combo;
import br.caixa.loterias.model.compra.Compra;
import br.caixa.loterias.model.itemcompravel.ItemCompravel;
import br.caixa.loterias.model.legado.ApostaCompradaLegado;
import br.caixa.loterias.model.legado.ApostaLegado;
import br.caixa.loterias.model.legado.ApostaLotomaniaLegado;
import br.caixa.loterias.model.legado.CamposEspecificosModalidade;
import br.caixa.loterias.model.legado.ComboApostaLegado;
import br.caixa.loterias.model.legado.IndicadorSurpresinhaLegado;
import br.caixa.loterias.model.legado.LotericaLegado;
import br.caixa.loterias.model.legado.ModalidadeLegado;
import br.caixa.loterias.model.legado.ReservaCotaBolaoLegado;
import br.caixa.loterias.model.legado.SubCanalLegado;
import br.caixa.loterias.model.legado.TipoComboLegado;
import br.caixa.loterias.model.legado.TipoConcursoLegado;
import br.caixa.loterias.model.matrizprognostico.MatrizPrognostico;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ApostaLegadoMapper {

    private final CompraMapper compraMapper;

    public ApostaLegadoMapper(CompraMapper compraMapper) {
        this.compraMapper = compraMapper;
    }

    public ApostaLegado toApostaLegado(ItemCompravel item) {
        return switch (item.getTipo()) {
            case APOSTA -> toApostaLegado(new ItemApostaIndividual(item));
            case RESERVA_COTA -> toApostaLegado(new ItemReservaCotaBolao(item));
            default -> throw new UnsupportedOperationException();
        };
    }

    public ApostaLegado toApostaLegado(ItemApostaIndividual itemApostaIndividual) {
        if (itemApostaIndividual == null) return null;

        ApostaLegado apostaLegado = new ApostaLegado();
        apostaLegado.setIdLegado(itemApostaIndividual.getIdLegado());
        apostaLegado.setIdNuvem(itemApostaIndividual.getId());
        apostaLegado.setCompra(compraMapper.toLegado(itemApostaIndividual.getCompra()));
        apostaLegado.setModalidade(toModalidade(itemApostaIndividual.getModalidade()));
        apostaLegado.setTipoConcurso(toTipoConcurso(itemApostaIndividual.getTipoConcurso()));
        apostaLegado.setIndicadorSurpresinha(toIndicadorSurpresinha(itemApostaIndividual.isSurpresinha()));
        apostaLegado.setValor(itemApostaIndividual.getValor());
        apostaLegado.setComprada(toApostaCompradaLegado(itemApostaIndividual));
        apostaLegado.setConcursoAlvo(itemApostaIndividual.getConcursoAlvo());
        apostaLegado.setDataInclusao(toDate(itemApostaIndividual.getTimestampInclusao()));
        apostaLegado.setSubcanal(toSubcanal(itemApostaIndividual.getCompra()));
        apostaLegado.setApostaOriginalEspelho(toApostaOriginalEspelho(itemApostaIndividual));
        apostaLegado.setComboAposta(toComboApostaLegado(itemApostaIndividual.getCombo()));
        apostaLegado.setNuCombo(itemApostaIndividual.getCombo() != null ? itemApostaIndividual.getCombo().getId() : null);
        apostaLegado.setQtdTeimosinhas(itemApostaIndividual.getTeimosinhaQtd());

        List<String> matrizes = itemApostaIndividual.getMatrizesPrognostico()
                .stream()
                .map(MatrizPrognostico::getValor)
                .collect(Collectors.toList());
        ajustaPrognosticoLegado(matrizes, apostaLegado);

        if (apostaLegado.getComprada() != null) {
            apostaLegado.getComprada().setAposta(apostaLegado);
        }

        return apostaLegado;
    }

    public ApostaLegado toApostaLegado(ItemReservaCotaBolao itemReservaCotaBolao) {
        if (itemReservaCotaBolao == null) {
            return null;
        }

        ApostaLegado apostaLegado = new ApostaLegado();
        apostaLegado.setIdNuvem(itemReservaCotaBolao.getId());
        apostaLegado.setCompra(compraMapper.toLegado(itemReservaCotaBolao.getCompra()));
        apostaLegado.setValor(itemReservaCotaBolao.getValorTotalCota());
        apostaLegado.setSubcanal(toSubcanal(itemReservaCotaBolao.getCompra()));
        apostaLegado.setIndicadorBolao(Boolean.TRUE);
        apostaLegado.setReservaCotaBolao(toReservaCotaBolaoLegado(itemReservaCotaBolao));

        if (apostaLegado.getReservaCotaBolao() != null) {
            apostaLegado.getReservaCotaBolao().setAposta(apostaLegado);
        }

        return apostaLegado;
    }

    private ModalidadeLegado toModalidade(Long modalidade) {
        return modalidade != null ? ModalidadeLegado.getByCodigo(modalidade.intValue()) : null;
    }

    private TipoConcursoLegado toTipoConcurso(Long tipoConcurso) {
        if (tipoConcurso == null) {
            return null;
        }

        String valor = String.valueOf(tipoConcurso);
        return TipoConcursoLegado.getByCodigo(valor.charAt(0));
    }

    private IndicadorSurpresinhaLegado toIndicadorSurpresinha(boolean surpresinha) {
        return surpresinha
                ? IndicadorSurpresinhaLegado.SURPRESINHA
                : IndicadorSurpresinhaLegado.NAO_SURPRESINHA;
    }

    private SubCanalLegado toSubcanal(Compra compra) {
        if (compra == null || compra.getSubcanal() == null) {
            return null;
        }

        return SubCanalLegado.getByCodigo(compra.getSubcanal());
    }

    private Date toDate(LocalDateTime timestamp) {
        if (timestamp == null) {
            return null;
        }

        return Date.from(timestamp.atZone(ZoneId.systemDefault()).toInstant());
    }

    private ApostaCompradaLegado toApostaCompradaLegado(ItemApostaIndividual itemApostaIndividual) {
        ApostaCompradaLegado comprada = new ApostaCompradaLegado();
        comprada.setNsu(itemApostaIndividual.getNsuAposta());
        comprada.setConcursoInicial(
                itemApostaIndividual.getTeimosinhaConcursoInicial() != null
                        ? itemApostaIndividual.getTeimosinhaConcursoInicial()
                        : itemApostaIndividual.getConcursoAlvo()
        );
        comprada.setBilheteTroca(itemApostaIndividual.getTeimosinhaBilheteTroca());
        comprada.setTsInicioApostaComprada(toDate(itemApostaIndividual.getTimestampInclusao()));
        comprada.setTsEnvioSispl(toDate(itemApostaIndividual.getTimestampEnvioSispl()));
        comprada.setTsEfetivacaoSispl(toDate(itemApostaIndividual.getTimestampEfetivacaoSispl()));
        comprada.setTsFinalizacaoProcessamento(toDate(itemApostaIndividual.getTimestampFinalizacaoProcessamento()));
        comprada.setTsUltimaSituacao(toDate(itemApostaIndividual.getTimestampAlteracaoSituacao()));
        comprada.setSubcanal(toSubcanal(itemApostaIndividual.getCompra()));

        if (itemApostaIndividual.getSituacao() != null) {
            comprada.setSituacao(switch (itemApostaIndividual.getSituacao()) {
                case NAO_REGISTRADA -> br.caixa.loterias.model.legado.SituacaoApostaLegado.SituacaoLegado.PENDENTE;
                case EM_PROCESSAMENTO, EM_PROCESSAMENTO_ENVIADA_SISPL, PAGAMENTO_EM_PROCESSAMENTO ->
                        br.caixa.loterias.model.legado.SituacaoApostaLegado.SituacaoLegado.PROCESSANDO;
                case NAO_EFETIVADA_DINHEIRO_EM_DEVOLUCAO ->
                        br.caixa.loterias.model.legado.SituacaoApostaLegado.SituacaoLegado.CANCELADA;
            });
        }

        return comprada;
    }

    private ApostaLotomaniaLegado toApostaOriginalEspelho(ItemApostaIndividual itemApostaIndividual) {
        if (!Boolean.TRUE.equals(itemApostaIndividual.getEspelho())) {
            return null;
        }

        ApostaLotomaniaLegado espelho = new ApostaLotomaniaLegado();
        espelho.setEspelho(Boolean.TRUE);
        return espelho;
    }

    private ComboApostaLegado toComboApostaLegado(Combo combo) {
        if (combo == null) {
            return null;
        }

        TipoComboLegado tipoComboLegado = new TipoComboLegado();
        if (combo.getTipoCombo() != null) {
            tipoComboLegado.setId(combo.getTipoCombo().getCodigo());
            tipoComboLegado.setNome(combo.getTipoCombo().getNome());
            tipoComboLegado.setDescricao(combo.getTipoCombo().getDescricao());
        }

        ComboApostaLegado comboLegado = new ComboApostaLegado(tipoComboLegado);
        comboLegado.setId(combo.getId());
        comboLegado.setMes(combo.getMes());
        comboLegado.setDataInclusao(combo.getDataInclusao() != null ? combo.getDataInclusao().toString() : null);
        comboLegado.setTipoCombo(tipoComboLegado);
        return comboLegado;
    }

    private ReservaCotaBolaoLegado toReservaCotaBolaoLegado(ItemReservaCotaBolao itemReservaCotaBolao) {
        ReservaCotaBolaoLegado reservaLegado = new ReservaCotaBolaoLegado();
        reservaLegado.setCodBolao(itemReservaCotaBolao.getIdBolaoMkp());
        reservaLegado.setCodCota(itemReservaCotaBolao.getIdCotaMkp());
        reservaLegado.setLoterica(toLotericaLegado(itemReservaCotaBolao));
        reservaLegado.setDataHoraReserva(toDate(itemReservaCotaBolao.getTimestampReserva()));
        reservaLegado.setDataExpiracaoReservaCotaBolao(toDate(itemReservaCotaBolao.getTimestampExpiracaoReserva()));
        reservaLegado.setNumeroCotaReservada(toInteger(itemReservaCotaBolao.getNuCotaInterno()));
        reservaLegado.setQtdCotaTotal(itemReservaCotaBolao.getBolao() != null ? itemReservaCotaBolao.getBolao().getQtdCotas() : null);
        reservaLegado.setValorCotaReservada(itemReservaCotaBolao.getValorCota());
        reservaLegado.setValorTarifaServico(itemReservaCotaBolao.getValorTarifaServico());
        reservaLegado.setDataRegistroBolao(
                itemReservaCotaBolao.getBolao() != null
                        ? toDate(itemReservaCotaBolao.getBolao().getTimestampRegistro())
                        : null
        );
        reservaLegado.setNumeroTerminalLoterico(
                itemReservaCotaBolao.getBolao() != null ? itemReservaCotaBolao.getBolao().getTerminalLoterico() : null
        );
        reservaLegado.setDadosCotaBolao(itemReservaCotaBolao.getIdCotaMkp());
        reservaLegado.setSituacao(itemReservaCotaBolao.getSituacao() != null ? itemReservaCotaBolao.getSituacao().getValue() : null);
        reservaLegado.setDataUltimaSituacao(toDate(itemReservaCotaBolao.getTimestampAlteracaoSituacao()));
        reservaLegado.setValorCotaCusteio(itemReservaCotaBolao.getValorCotaCusteio());
        reservaLegado.setValorTarifaCusteio(itemReservaCotaBolao.getValorTarifaCusteio());
        reservaLegado.setNsu(itemReservaCotaBolao.getNsuReserva());
        return reservaLegado;
    }

    private LotericaLegado toLotericaLegado(ItemReservaCotaBolao itemReservaCotaBolao) {
        if (itemReservaCotaBolao.getBolao() == null) {
            return null;
        }

        LotericaLegado loterica = new LotericaLegado();
        loterica.setId(itemReservaCotaBolao.getBolao().getLotericaId());
        return loterica;
    }

    private Integer toInteger(Long value) {
        return value != null ? value.intValue() : null;
    }

    private ApostaLegado ajustaPrognosticoLegado(List<String> matrizes, ApostaLegado apostaLegado) {
        CamposEspecificosModalidade camposEspecificosModalidade = new CamposEspecificosModalidade();

        if (matrizes == null || matrizes.isEmpty() || apostaLegado.getModalidade() == null) {
            return apostaLegado;
        }

        switch (apostaLegado.getModalidade()) {
            case TIMEMANIA:
                apostaLegado.setPrognostico(ajustaNumerosPrognosticosLegado(matrizes.get(0)));
                if (matrizes.size() > 1) {
                    camposEspecificosModalidade.setTimeManiaNumeroEquipeEsportiva(
                            removeCaracteresEspeciais(matrizes.get(1))
                    );
                    apostaLegado.setCamposEspecificosModalidade(camposEspecificosModalidade);
                }
                return apostaLegado;
            case DIA_DE_SORTE:
                apostaLegado.setPrognostico(ajustaNumerosPrognosticosLegado(matrizes.get(0)));
                if (matrizes.size() > 1) {
                    camposEspecificosModalidade.setDiaDeSorteNumeroMesSorte(
                            ajustaNumerosPrognosticosLegado(matrizes.get(1))
                    );
                    apostaLegado.setCamposEspecificosModalidade(camposEspecificosModalidade);
                }
                return apostaLegado;
            case SUPER_7:
                apostaLegado.setPrognostico(String.join("", ajustaNumeroSuperSete(matrizes.get(0))));
                return apostaLegado;
            case LOTECA:
                List<String> numerosLoteca = new ArrayList<>();
                for (String matriz : matrizes) {
                    numerosLoteca.add(ajustaNumerosPrognosticosLegado(matriz));
                }
                apostaLegado.setPrognostico(String.join("", numerosLoteca));
                return apostaLegado;
            case MAIS_MILIONARIA:
                apostaLegado.setPrognostico(ajustaNumerosPrognosticosLegado(matrizes.get(0)));
                if (matrizes.size() > 1) {
                    camposEspecificosModalidade.setMaisMilionariaNumeroTrevo(
                            ajustaNumerosPrognosticosLegado(matrizes.get(1))
                    );
                    apostaLegado.setCamposEspecificosModalidade(camposEspecificosModalidade);
                }
                return apostaLegado;
            default:
                apostaLegado.setPrognostico(ajustaNumerosPrognosticosLegado(matrizes.get(0)));
                return apostaLegado;
        }
    }

    private String removeCaracteresEspeciais(String input) {
        return input == null ? "" : input.replaceAll("[\\W]|\\s", "");
    }

    private String ajustaNumerosPrognosticosLegado(String prognosticos) {
        return Arrays.stream(removeCaracteresEspeciais(prognosticos).split(";"))
                .filter(dezena -> !dezena.isEmpty())
                .map(dezena -> String.format("%03d", Integer.parseInt(dezena)))
                .collect(Collectors.joining(""));
    }

    private List<String> ajustaNumeroSuperSete(String prognosticos) {
        String[] dezenas = prognosticos.split(";");
        List<String> retorno = new ArrayList<>();

        for (String dezena : dezenas) {
            String numero = removeCaracteresEspeciais(dezena.trim());
            if (numero.length() < 2) {
                continue;
            }
            int primeiroDigito = Character.getNumericValue(numero.charAt(0));
            int segundoDigito = Character.getNumericValue(numero.charAt(1));
            int resultado = segundoDigito * 7 + primeiroDigito;
            retorno.add(ajustaNumerosPrognosticosLegado(Integer.toString(resultado)));
        }

        return retorno;
    }
}
