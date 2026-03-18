package br.gov.caixa.silce.dominio.exception;

/**
 * Exceção associada manipulação de NSB.
 */
public class NsbException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Método construtor
     *
     * @param codigoErro
     *        Código do Erro gerador da Exceção.
     */
    public NsbException(String mensagem) {
        super(mensagem);
    }

    public NsbException(String message, Exception cause) {
        super(message, cause);
    }

}