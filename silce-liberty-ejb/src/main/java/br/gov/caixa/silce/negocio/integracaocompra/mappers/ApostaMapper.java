package br.gov.caixa.silce.negocio.integracaocompra.mappers;

import br.gov.caixa.silce.dominio.entidade.AbstractApostaNumerica;
import br.gov.caixa.silce.dominio.entidade.Aposta;
import br.gov.caixa.silce.dominio.entidade.ApostaMegasena;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoAposta;
import br.gov.caixa.silce.dominio.util.ApostaUtil;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Decimal;
import br.gov.caixa.silce.dominio.jogos.IndicadorSurpresinha;
import br.gov.caixa.silce.dominio.jogos.Modalidade;
import br.gov.caixa.silce.dominio.jogos.PalpitesSequenciais;

import java.util.List;

import br.gov.caixa.silce.dominio.Subcanal;
import br.gov.caixa.silce.dominio.jogos.TipoConcurso;

public class ApostaMapper extends Mapper<NuvemIntegracaoAposta, Aposta<?>> {

    @Override
    public Aposta<?> map(NuvemIntegracaoAposta apostaNuvem) {
        Modalidade modalidade = Modalidade.getByCodigo(apostaNuvem.modalidade);
        Aposta<?> aposta = ApostaUtil.createAposta(modalidade);
        return map(apostaNuvem, aposta);
    }

    @Override
    public Aposta<?> map(NuvemIntegracaoAposta apostaNuvem, Aposta<?> aposta) {
        aposta.setTipoConcurso(TipoConcurso.getByCodigo(apostaNuvem.tipoConcurso.toString().charAt(0)));
        aposta.setIndicadorSurpresinha(IndicadorSurpresinha.getByCodigo(apostaNuvem.indicadorSurpresinha));
        aposta.setValor(new Decimal(apostaNuvem.valor));
        aposta.setConcursoAlvo(apostaNuvem.concursoAlvo);
        aposta.setDataInclusao(new Data(apostaNuvem.dataInclusao));
        aposta.setSubcanalCarrinhoAposta(Subcanal.getByCodigo(apostaNuvem.subcanal));
        aposta.setIndicadorBolao(apostaNuvem.reservaCotaBolao != null ? Boolean.TRUE : Boolean.FALSE);
        if (aposta instanceof AbstractApostaNumerica) {
            AbstractApostaNumerica apostaNumerica = (AbstractApostaNumerica) aposta;
            List<Integer> prognosticos = new PalpitesSequenciais(apostaNuvem.prognostico).getPrognosticos();
            apostaNumerica.setPrognosticos(prognosticos);
        }
        return aposta;
    }
}
