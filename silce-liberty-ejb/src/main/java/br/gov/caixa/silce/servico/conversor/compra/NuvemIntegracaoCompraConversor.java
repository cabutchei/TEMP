package br.gov.caixa.silce.servico.conversor.compra;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import br.gov.caixa.silce.dominio.servico.compra.MsgNuvemIntegracaoCompraConfirmacao;
import br.gov.caixa.silce.dominio.servico.compra.MsgNuvemIntegracaoCompraSolicitacao;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoAposta;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoCompra;
import br.gov.caixa.silce.dominio.servico.compra.NuvemIntegracaoReserva;
import br.gov.caixa.silce.servico.conversor.AbstractJsonConversor;
import br.gov.caixa.util.StringUtil;


public class NuvemIntegracaoCompraConversor
        extends AbstractJsonConversor<MsgNuvemIntegracaoCompraConfirmacao, MsgNuvemIntegracaoCompraSolicitacao> {

    private static NuvemIntegracaoCompraConversor instancia = new NuvemIntegracaoCompraConversor();

    private static final Logger LOG = LogManager.getLogger(NuvemIntegracaoCompraConversor.class, new MessageFormatMessageFactory());

    private final Gson gson = new Gson();

    public static NuvemIntegracaoCompraConversor getInstance() {
        return instancia;
    }

    /**
     * Chamado quando enviamos uma mensagem para a fila de confirmação LQ.LOG.SILCE.INTEGRACAO_NUVEM_CONFIRMACAO
     */
    @Override
    protected JsonObject toJsonObject(MsgNuvemIntegracaoCompraConfirmacao confirmacao) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String convertToString(MsgNuvemIntegracaoCompraConfirmacao obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.addMixInAnnotations(
                    NuvemIntegracaoReserva.class,
                    NuvemIntegracaoReservaMixin.class);

            return mapper.writer().writeValueAsString(obj);

        } catch (Exception e) {
            LOG.error("Ocorreu um erro ao tentar converter um JSON para String. Valor recebido: {0}", obj, e);
        }

        return null;
    }

    /**
     * Chamado quando recebemos uma mensagem da fila de solicitação
     * LQ.LOG.SILCE.INTEGRACAO_NUVEM_COMPRA_LEGADO
     */
    @Override
    protected MsgNuvemIntegracaoCompraSolicitacao fromJsonObject(JsonElement jsonSolicitacao) {
        throw new UnsupportedOperationException();
    }

    public MsgNuvemIntegracaoCompraSolicitacao fromJsonObject(String json) {

        ObjectMapper mapper = new ObjectMapper();

        mapper.addMixInAnnotations(MsgNuvemIntegracaoCompraSolicitacao.class, JsonIdentityInfoMixin.class);
        mapper.addMixInAnnotations(NuvemIntegracaoCompra.class, JsonIdentityInfoMixin.class);
        mapper.addMixInAnnotations(NuvemIntegracaoAposta.class, JsonIdentityInfoMixin.class);
        mapper.addMixInAnnotations(NuvemIntegracaoReserva.class, JsonIdentityInfoMixin.class);

        try {
            MsgNuvemIntegracaoCompraSolicitacao parsed =
                    mapper.readValue(json, MsgNuvemIntegracaoCompraSolicitacao.class);

            // TODO melhorar validações

            if (parsed.apostador == null || parsed.apostador.idNuvem == null) {
                throw new IllegalArgumentException(
                        "Campo 'apostador' ou 'apostador.idNuvem' está ausente.");
            }

            if (parsed.compra == null || parsed.compra.idNuvem == null) {
                throw new IllegalArgumentException(
                        "Campo 'compra' ou 'compra.idNuvem' está ausente.");
            }

            if (parsed.apostas == null || parsed.apostas.isEmpty()) {
                throw new IllegalArgumentException(
                        "Campo 'apostas' está ausente ou vazio.");
            }

            return parsed;

        } catch (Exception e) {
            LOG.warn("Erro ao converter solicitação de compra nuvem do JSON: {0}", e.getMessage());
            return null;
        }
    }

    @Override
    public MsgNuvemIntegracaoCompraSolicitacao convertFromString(String s) {

        if (StringUtil.isEmpty(s)) return null;

        try {
            MsgNuvemIntegracaoCompraSolicitacao convertido = fromJsonObject(s);
            return convertido;
        } catch (Exception e) {
            LOG.error("Ocorreu um erro ao tentar converter uma String para JSON. Valor recebido: {0}", s, e);
            return null;
        }
    }
}
