package br.gov.caixa.silce.dominio.servico.compra;

import java.util.Date;

import br.gov.caixa.util.CPF;

/**
 * Representação de um apostador como enviado pelo silce-compra-processamento.
 */
public class NuvemIntegracaoApostador extends DTONuvem implements IdLegado, IdNuvem {

    public Long idNuvem;

    public Long idLegado;

    public String cpf;

    public String email;

    public String nome;

    public String primeiroNome;

    public Date dataNascimento;

    public Date dataUltimoAcesso;

    public Date dataAtualizacaoContadorTentativasAcesso;

    public String telefone;

    public String cep;

    public NuvemIntegracaoUltimaNotificacao ultimaNotificacao;

    public Boolean aceitaReceberNoticias;

    public String municipio;

    public Long situacao;

    public Date dataCadastro;

    public NuvemIntegracaoLoterica loterica;

    public String rapidao;

    public String sexo;

    public String ultimoTermoDeUsoAceito;

    public Date dataUltimoAceiteTermoDeUso;

    public String mercadoPagoCustomerId;

    public String dataSelecaoLoterica;

    public Long limiteDiario;

    public Long limiteDiarioAutorizado;

    public Integer subcanal;

    public Boolean aceitaTermosUso;

    public Date dataFimBloqueioVinculacao;

    public Boolean autoAvaliacaoRespondida;

    public Date dataRespostaAutoAvaliacao;

    public Date dataProximaRespostaAutoavaliacao;

    public Date dataUltimaConsultaSicow;


    public NuvemIntegracaoApostador() {

    }

    public CPF getParsedCpf() {
        return new CPF(cpf);
    }

    public Long getIdLegado() {
        return this.idLegado;
    }

    public Long getIdNuvem() {
        return this.idNuvem;
    }
}
