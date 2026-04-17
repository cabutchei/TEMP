# Jython script for wsadmin
# Local MQ setup for silce on Docker: mq/QM1/DEV.APP.SVRCONN

import os

MQ_ALIAS = "DefaultNode01/LocalMQApp"
MQ_USER = os.environ.get("IBM_MQ_USERNAME", "app")
MQ_PASSWORD = os.environ.get("IBM_MQ_PASSWORD", "passw0rd")
MQ_QMGR = os.environ.get("IBM_MQ_QUEUE_MANAGER", "QM1")
MQ_HOST = os.environ.get("IBM_MQ_HOST", "mq")
MQ_PORT = os.environ.get("IBM_MQ_PORT", "1414")
MQ_CHANNEL = os.environ.get("IBM_MQ_CHANNEL", "DEV.APP.SVRCONN")
MQ_TRANSPORT_TYPE = os.environ.get("IBM_MQ_TRANSPORT_TYPE", "1")
MQ_WMQ_TRANSPORT = "CLIENT"

if MQ_TRANSPORT_TYPE not in ["1", "CLIENT", "client"]:
    MQ_WMQ_TRANSPORT = MQ_TRANSPORT_TYPE

CONNECTION_FACTORIES = [('conn/MQ-SIBAR', 'MQ_SIBAR_LOCAL'), ('conn/MQ-SIICO', 'MQ_SIICO_LOCAL'), ('conn/MQ-ALTA-BR-SISPL', 'MQ_ALTA_BR_SISPL_LOCAL'), ('conn/MQ-ALTA-BR-SIDMO', 'MQ_ALTA_BR_SIDMO_LOCAL')]
QUEUES = [{'jndi': 'queue/ecoApostasDuplaSena', 'admin_name': 'LQ.LOG.SILCE.ECO_DUPLASENA', 'queue_name': 'LQ.LOG.SILCE.ECO_DUPLASENA'}, {'jndi': 'queue/ecoApostasLoteca', 'admin_name': 'LQ.LOG.SILCE.ECO_LOTECA', 'queue_name': 'LQ.LOG.SILCE.ECO_LOTECA'}, {'jndi': 'queue/ecoApostasLotofacil', 'admin_name': 'LQ.LOG.SILCE.ECO_LOTOFACIL', 'queue_name': 'LQ.LOG.SILCE.ECO_LOTOFACIL'}, {'jndi': 'queue/ecoApostasLotogol', 'admin_name': 'LQ.LOG.SILCE.ECO_LOTOGOL', 'queue_name': 'LQ.LOG.SILCE.ECO_LOTOGOL'}, {'jndi': 'queue/ecoApostasLotomania', 'admin_name': 'LQ.LOG.SILCE.ECO_LOTOMANIA', 'queue_name': 'LQ.LOG.SILCE.ECO_LOTOMANIA'}, {'jndi': 'queue/ecoApostasMegaSena', 'admin_name': 'LQ.LOG.SILCE.ECO_MEGA', 'queue_name': 'LQ.LOG.SILCE.ECO_MEGA'}, {'jndi': 'queue/ecoApostasQuina', 'admin_name': 'LQ.LOG.SILCE.ECO_QUINA', 'queue_name': 'LQ.LOG.SILCE.ECO_QUINA'}, {'jndi': 'queue/ecoApostasTimemania', 'admin_name': 'LQ.LOG.SILCE.ECO_TIMEMANIA', 'queue_name': 'LQ.LOG.SILCE.ECO_TIMEMANIA'}, {'jndi': 'queue/ecoPagamentoPremio', 'admin_name': 'LQ.LOG.SILCE.ECO_PREMIOS', 'queue_name': 'LQ.LOG.SILCE.ECO_PREMIOS'}, {'jndi': 'queue/eventoTemporizador', 'admin_name': 'LQ.LOG.SILCE.EVENTOS', 'queue_name': 'LQ.LOG.SILCE.EVENTOS'}, {'jndi': 'queue/logApostasDuplaSena', 'admin_name': 'LQ.LOG.SISPLC.APOSTAS_DUPLASENA', 'queue_name': 'LQ.LOG.SISPLC.APOSTAS_DUPLASENA'}, {'jndi': 'queue/logApostasLoteca', 'admin_name': 'LQ.LOG.SISPLC.APOSTAS_LOTECA', 'queue_name': 'LQ.LOG.SISPLC.APOSTAS_LOTECA'}, {'jndi': 'queue/logApostasLotofacil', 'admin_name': 'LQ.LOG.SISPLC.APOSTAS_LOTOFACIL', 'queue_name': 'LQ.LOG.SISPLC.APOSTAS_LOTOFACIL'}, {'jndi': 'queue/logApostasLotogol', 'admin_name': 'LQ.LOG.SISPLC.APOSTAS_LOTOGOL', 'queue_name': 'LQ.LOG.SISPLC.APOSTAS_LOTOGOL'}, {'jndi': 'queue/logApostasLotomania', 'admin_name': 'LQ.LOG.SISPLC.APOSTAS_LOTOMANIA', 'queue_name': 'LQ.LOG.SISPLC.APOSTAS_LOTOMANIA'}, {'jndi': 'queue/logApostasMegaSena', 'admin_name': 'LQ.LOG.SISPLC.APOSTAS_MEGA', 'queue_name': 'LQ.LOG.SISPLC.APOSTAS_MEGA'}, {'jndi': 'queue/logApostasQuina', 'admin_name': 'LQ.LOG.SISPLC.APOSTAS_QUINA', 'queue_name': 'LQ.LOG.SISPLC.APOSTAS_QUINA'}, {'jndi': 'queue/logApostasTimemania', 'admin_name': 'LQ.LOG.SISPLC.APOSTAS_TIMEMANIA', 'queue_name': 'LQ.LOG.SISPLC.APOSTAS_TIMEMANIA'}, {'jndi': 'queue/logTrilhaAuditoria', 'admin_name': 'LQ.LOG.SILCE.TRILHA_GRAVA', 'queue_name': 'LQ.LOG.SILCE.TRILHA_GRAVA'}, {'jndi': 'queue/perguntaBuscaParametrosJogos', 'admin_name': 'perguntaBuscaParametrosJogos', 'queue_name': 'LQ.REQ.SISPLC.BUSCA_PARAMETROS'}, {'jndi': 'queue/perguntaConsultaBilhete', 'admin_name': 'LQ.REQ.SISPLP.PREMIO', 'queue_name': 'LQ.REQ.SISPLP.PREMIO'}, {'jndi': 'queue/perguntaGeraNSBi', 'admin_name': 'LQ.REQ.SISPLP.CONSULTAS', 'queue_name': 'LQ.REQ.SISPLP.CONSULTAS'}, {'jndi': 'queue/perguntaPagaBilhete', 'admin_name': 'LQ.REQ.SISPLP.PREMIO', 'queue_name': 'LQ.REQ.SISPLP.PREMIO'}, {'jndi': 'queue/perguntaTrilhaConsulta', 'admin_name': 'LQ.REQ.SILCE.TRILHA_CONSULTA', 'queue_name': 'LQ.REQ.SILCE.TRILHA_CONSULTA'}, {'jndi': 'queue/respostaBuscaParametrosJogos', 'admin_name': 'respostaBuscaParametrosJogos', 'queue_name': 'LQ.RSP.SISPL.BUSCA_PARAMETROS'}, {'jndi': 'queue/respostaConsultaBilhete', 'admin_name': 'LQ.RSP.SISPLP.PREMIO', 'queue_name': 'LQ.RSP.SISPLP.PREMIO'}, {'jndi': 'queue/respostaGeraNSBi', 'admin_name': 'LQ.RSP.SISPLP.CONSULTAS', 'queue_name': 'LQ.RSP.SISPLP.CONSULTAS'}, {'jndi': 'queue/respostaPagaBilhete', 'admin_name': 'LQ.RSP.SISPLP.PREMIO', 'queue_name': 'LQ.RSP.SISPLP.PREMIO'}, {'jndi': 'queue/respostaTemporizador', 'admin_name': 'LQ.LOG.SISPL.EVENTOS_RETORNO', 'queue_name': 'LQ.LOG.SISPL.EVENTOS_RETORNO'}, {'jndi': 'queue/respostaTrilhaConsulta', 'admin_name': 'LQ.RSP.SILCE.TRILHA_CONSULTA', 'queue_name': 'LQ.RSP.SILCE.TRILHA_CONSULTA'}, {'jndi': 'queue/perguntaServicoSicdu', 'admin_name': 'LQ.REQ.SERVICO.SICDU', 'queue_name': 'LQ.REQ.SERVICO.SICDU'}, {'jndi': 'queue/respostaServicoSicdu', 'admin_name': 'LQ.RSP.SERVICO.SICDU', 'queue_name': 'LQ.RSP.SERVICO.SICDU'}, {'jndi': 'queue/perguntaConsultaCarteiraEletronica', 'admin_name': 'SIDMO.REQ.CONSULTA_CARTEIRA', 'queue_name': 'SIDMO.REQ.CONSULTA_CARTEIRA'}, {'jndi': 'queue/perguntaCreditaCarteiraEletronica', 'admin_name': 'SIDMO.REQ.CREDITA_CARTEIRA', 'queue_name': 'SIDMO.REQ.CREDITA_CARTEIRA'}, {'jndi': 'queue/perguntaDebitaCarteiraEletronica', 'admin_name': 'SIDMO.REQ.DEBITA_CARTEIRA', 'queue_name': 'SIDMO.REQ.DEBITA_CARTEIRA'}, {'jndi': 'queue/perguntaEstornaDebitoCarteiraEletronica', 'admin_name': 'SIDMO.REQ.ESTORNO_CARTEIRA', 'queue_name': 'SIDMO.REQ.ESTORNO_CARTEIRA'}, {'jndi': 'queue/respostaConsultaCarteiraEletronica', 'admin_name': 'SIDMO.RSP.CONSULTA_CARTEIRA', 'queue_name': 'SIDMO.RSP.CONSULTA_CARTEIRA'}, {'jndi': 'queue/respostaCreditaCarteiraEletronica', 'admin_name': 'SIDMO.RSP.CREDITA_CARTEIRA', 'queue_name': 'SIDMO.RSP.CREDITA_CARTEIRA'}, {'jndi': 'queue/respostaDebitaCarteiraEletronica', 'admin_name': 'SIDMO.RSP.DEBITA_CARTEIRA', 'queue_name': 'SIDMO.RSP.DEBITA_CARTEIRA'}, {'jndi': 'queue/respostaEstornaDebitoCarteiraEletronica', 'admin_name': 'SIDMO.RSP.ESTORNO_CARTEIRA', 'queue_name': 'SIDMO.RSP.ESTORNO_CARTEIRA'}, {'jndi': 'queue/processaCompra', 'admin_name': 'LQ.LOG.SILCE.COMPRAS', 'queue_name': 'LQ.LOG.SILCE.COMPRAS'}, {'jndi': 'queue/perguntaValidaPagamento', 'admin_name': 'perguntaValidaPagamento', 'queue_name': 'LQ.REQ.SILCE.VALIDA_PAGTO'}, {'jndi': 'queue/respostaValidaPagamento', 'admin_name': 'respostaValidaPagamento', 'queue_name': 'LQ.RSP.SILCE.VALIDA_PAGTO'}, {'jndi': 'queue/perguntaConsultaContaSid09', 'admin_name': 'SIBAR.REQ.CONSULTA_CONTA_SID09', 'queue_name': 'SIBAR.REQ.CONSULTA_CONTA_SID09'}, {'jndi': 'queue/respostaConsultaContaSid09', 'admin_name': 'SIBAR.RSP.CONSULTA_CONTA_SID09', 'queue_name': 'SIBAR.RSP.CONSULTA_CONTA_SID09'}, {'jndi': 'queue/logApostasDiaDeSorte', 'admin_name': 'logApostasDiaDeSorte', 'queue_name': 'LQ.LOG.SISPLC.APOSTAS_DIADESORTE'}, {'jndi': 'queue/ecoApostasDiaDeSorte', 'admin_name': 'ecoApostasDiaDeSorte', 'queue_name': 'LQ.LOG.SILCE.ECO_DIADESORTE'}, {'jndi': 'queue/ecoPagamentoPremioErro', 'admin_name': 'ecoPagamentoPremioErro', 'queue_name': 'LQ.LOG.SILCE.ECO_PREMIOS_ERRO'}, {'jndi': 'queue/logApostasSuperSete', 'admin_name': 'logApostasSuperSete', 'queue_name': 'LQ.LOG.SISPLC.APOSTAS_SUPERSETE'}, {'jndi': 'queue/ecoApostasSuperSete', 'admin_name': 'ecoApostasSuperSete', 'queue_name': 'LQ.LOG.SILCE.ECO_SUPERSETE'}, {'jndi': 'queue/reqEnvioNSGD', 'admin_name': 'requisicaoEnvioNSGD', 'queue_name': 'RQ.REQ.SIGEL.SERVICO'}, {'jndi': 'queue/respostaEnvioNSGD', 'admin_name': 'ecoEnvioNSGD', 'queue_name': 'LQ.RSP.SIGEL.SILCE'}, {'jndi': 'queue/ecoApostasMaisMilionaria', 'admin_name': 'ecoApostasMaisMilionaria', 'queue_name': 'LQ.LOG.SILCE.ECO_MILIONARIA'}, {'jndi': 'queue/logApostasMaisMilionaria', 'admin_name': 'logApostasMaisMilionaria', 'queue_name': 'LQ.LOG.SISPLC.APOSTAS_MILIONARIA'}, {'jndi': 'queue/processaCompraMock', 'admin_name': 'LQ.LOG.SILCE.COMPRAS.MOCK', 'queue_name': 'LQ.LOG.SILCE.COMPRAS.MOCK'}, {'jndi': 'queue/eventoTemporizadorMock', 'admin_name': 'LQ.LOG.SILCE.EVENTOS.MOCK', 'queue_name': 'LQ.LOG.SILCE.EVENTOS.MOCK'}, {'jndi': 'queue/batimentoLiquidacaoPix', 'admin_name': 'LQ.REQ.SILCE.BATIMENTO_LIQUIDACAO_PIX', 'queue_name': 'LQ.REQ.SILCE.BATIMENTO_LIQUIDACAO_PIX'}, {'jndi': 'queue/perguntaConsultaSiico', 'admin_name': 'perguntaConsultaSiico', 'queue_name': 'LOCAL.PERGUNTA_CONSULTA_SIICO'}, {'jndi': 'queue/respostaConsultaSiico', 'admin_name': 'respostaConsultaSiico', 'queue_name': 'LOCAL.RESPOSTA_CONSULTA_SIICO'}, {'jndi': 'queue/reqPagamentoNuvem', 'admin_name': 'reqPagamentoNuvem', 'queue_name': 'LOCAL.REQ_PAGAMENTO_NUVEM'}, {'jndi': 'queue/rspPagamentoNuvem', 'admin_name': 'rspPagamentoNuvem', 'queue_name': 'LOCAL.RSP_PAGAMENTO_NUVEM'}, {'jndi': 'queue/reqIntegracaoCompraNuvem', 'admin_name': 'reqIntegracaoCompraNuvem', 'queue_name': 'LOCAL.REQ_INTEGRACAO_COMPRA_NUVEM'}, {'jndi': 'queue/rspIntegracaoCompraNuvem', 'admin_name': 'rspIntegracaoCompraNuvem', 'queue_name': 'LOCAL.RSP_INTEGRACAO_COMPRA_NUVEM'}]


def list_ids(type_name, scope=None):
    if scope:
        data = AdminConfig.list(type_name, scope)
    else:
        data = AdminConfig.list(type_name)
    return [line for line in data.splitlines() if line]


def find_by_name(type_name, name, scope=None):
    for obj in list_ids(type_name, scope):
        try:
            if AdminConfig.showAttribute(obj, 'name') == name:
                return obj
        except:
            pass
    return None


def get_cell():
    return list_ids('Cell')[0]


def get_server():
    for obj in list_ids('Server'):
        if AdminConfig.showAttribute(obj, 'name') == 'server1':
            return obj
    return list_ids('Server')[0]


def ensure_auth_alias(alias, user, password, description):
    for obj in list_ids('JAASAuthData'):
        try:
            if AdminConfig.showAttribute(obj, 'alias') == alias:
                print 'Auth alias already exists: ' + alias
                AdminConfig.modify(obj, [['userId', user], ['password', password], ['description', description]])
                return
        except:
            pass
    print 'Creating auth alias: ' + alias
    AdminTask.createAuthDataEntry('[-alias ' + alias + ' -user ' + user + ' -password ' + password + ' -description "' + description + '"]')


def ensure_cf(scope, name, jndi):
    existing = find_by_name('MQQueueConnectionFactory', name, scope)
    if existing:
        print 'MQ CF already exists: ' + jndi
        AdminConfig.modify(existing, [['queueManager', MQ_QMGR], ['host', MQ_HOST], ['port', MQ_PORT], ['channel', MQ_CHANNEL], ['transportType', MQ_WMQ_TRANSPORT], ['authDataAlias', MQ_ALIAS], ['xaRecoveryAuthAlias', MQ_ALIAS]])
        try:
            mapping = AdminConfig.showAttribute(existing, 'mapping')
            if mapping:
                AdminConfig.modify(mapping, [['authDataAlias', MQ_ALIAS]])
        except:
            pass
        return
    print 'Creating MQ CF: ' + jndi
    AdminTask.createWMQConnectionFactory(scope, '[-type QCF -name ' + name + ' -jndiName ' + jndi + ' -qmgrName ' + MQ_QMGR + ' -wmqTransportType ' + MQ_WMQ_TRANSPORT + ' -qmgrHostname ' + MQ_HOST + ' -qmgrPortNumber ' + MQ_PORT + ' -qmgrSvrconnChannel ' + MQ_CHANNEL + ' -componentAuthAlias ' + MQ_ALIAS + ' -containerAuthAlias ' + MQ_ALIAS + ' -xaRecoveryAuthAlias ' + MQ_ALIAS + ' -clientReconnectOptions DISABLED]')
    existing = find_by_name('MQQueueConnectionFactory', name, scope)
    try:
        mapping = AdminConfig.showAttribute(existing, 'mapping')
        if mapping:
            AdminConfig.modify(mapping, [['authDataAlias', MQ_ALIAS]])
    except:
        pass


def ensure_queue(scope, admin_name, jndi, queue_name):
    existing = find_by_name('MQQueue', admin_name, scope)
    if existing:
        print 'MQ queue resource already exists: ' + jndi
        try:
            AdminConfig.modify(existing, [['baseQueueManagerName', MQ_QMGR], ['queueManagerPort', MQ_PORT]])
        except:
            pass
        return
    print 'Creating MQ queue resource: ' + jndi + ' -> ' + queue_name
    AdminTask.createWMQQueue(scope, '[-name "' + admin_name + '" -jndiName ' + jndi + ' -queueName "' + queue_name + '" -qmgr ' + MQ_QMGR + ' -persistence APP -priority APP -expiry APP -ccsid 1208 -useNativeEncoding true -integerEncoding Normal -decimalEncoding Normal -floatingPointEncoding IEEENormal -useRFH2 false -sendAsync QDEF -readAhead QDEF -mqmdReadEnabled false -mqmdWriteEnabled false -mqmdMessageContext DEFAULT -messageBody UNSPECIFIED -replyToStyle DEFAULT]')


def update_activation_spec_templates():
    replacements = {
        'channel': MQ_CHANNEL,
        'connectionNameList': MQ_HOST + '(' + MQ_PORT + ')',
        'hostName': MQ_HOST,
        'port': MQ_PORT,
        'queueManager': MQ_QMGR
    }
    for scope in [get_cell(), get_server()]:
        for adapter in list_ids('J2CResourceAdapter', scope):
            try:
                auth = AdminConfig.showAttribute(adapter, 'name')
            except:
                auth = ''
            try:
                if AdminConfig.showAttribute(adapter, 'providerType') != 'WebSphere MQ Jakarta Messaging Provider':
                    continue
            except:
                pass
            for prop in list_ids('J2EEResourceProperty', adapter):
                try:
                    name = AdminConfig.showAttribute(prop, 'name')
                except:
                    continue
                if name in replacements:
                    AdminConfig.modify(prop, [['value', replacements[name]]])


def main():
    cell = get_cell()
    ensure_auth_alias(MQ_ALIAS, MQ_USER, MQ_PASSWORD, 'Local MQ application alias for silce')
    for jndi, name in CONNECTION_FACTORIES:
        ensure_cf(cell, name, jndi)
    for item in QUEUES:
        ensure_queue(cell, item['admin_name'], item['jndi'], item['queue_name'])
    update_activation_spec_templates()
    AdminConfig.save()
    print 'MQ resource configuration saved.'


main()
