package br.gov.caixa.silce.dominio.servico.comum;

public enum StatusPagamento {

	// Rejeitado pelo Meio de Pagamento
	// A COMPRA deve ser tratada como DÉBITO REJEITADO
	REJEITADO,

	// Aprovado pelo Meio de Pagamento
	// A COMPRA deve ser tratada como DÉBITO AUTORIZADO
	APROVADO,

	// Feito, porém não passou pelas validações da interação entre o Meio de Pagamento e o SILCE 
	// A COMPRA deve ser ESTORNADA
	INVALIDO,

	// Descrição a ser adicionada
	// A COMPRA deve ser ESTORNADA
	ESTORNADO,

	// Não foi feito o pagamento
	// A COMPRA deve ser CANCELADA
	INEXISTENTE,

	// Não foi feito o pagamento
	// A COMPRA deve ser CANCELADA
	CANCELADO,

	// Estorno (pedido de devolucao) em processamento pelo PIX
	EM_PROCESSAMENTO,

	// Estorno (pedido de devolucao) processado pelo PIX
	DEVOLVIDO,

	// Estorno (pedido de devolucao) nao realizado pelo PIX
	NAO_REALIZADO,

	// A Compra ja foi realizada e a cobrança do PIX tambem. Status aguardando o PAGAMENTO do PIX.
	ATIVA;
}
