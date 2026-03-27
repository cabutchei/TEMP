package br.caixa.loterias.model.legado;

import com.google.type.Decimal;
import lombok.Data;

import java.util.Date;

@Data
public class ApostadorLegado {

    private Long idNuvem;

    private Long idLegado;

    private String cpf;

    private String email;

    private String nome;

    private String primeiroNome;

    private Date dataNascimento;

    private Date dataUltimoAcesso;

    private Date dataAtualizacaoContadorTentativasAcesso;

    private Date dataFimBloqueioVinculacao;

    private Integer contadorTentativasAcesso;

    private String telefone;

    private String cep;

    private Boolean aceitaTermosUso;

    private NotificacaoLegado ultimaNotificacao;

    private Boolean aceitaReceberNoticias;

    private String municipio;

    private SituacaoApostadorLegado situacao;

    private Date dataCadastro;

    private LotericaLegado loterica;

    private Object rapidao = new Object();

    private String sexo;

    private Object ultimoTermoDeUsoAceito = new Object();

    private Date dataUltimoAceiteTermoDeUso;

    private String mercadoPagoCustomerId;

    private Date dataSelecaoLoterica;

    private Decimal limiteDiario;

    private Decimal limiteDiarioAutorizado;

    private Date dataCalculoLimiteDiarioAutorizado;

    private SubCanalLegado subcanal;

    private Boolean autoAvaliacaoRespondida;

    private Date dataRespostaAutoavaliacao;

    private Date dataProximaRespostaAutoavaliacao;

    private Date dataUltimaConsultaSicow;
}
