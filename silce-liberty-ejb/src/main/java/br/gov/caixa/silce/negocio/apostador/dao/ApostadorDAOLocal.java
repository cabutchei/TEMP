package br.gov.caixa.silce.negocio.apostador.dao;

import java.util.List;

import javax.ejb.Local;

import br.gov.caixa.dominio.ResultadoPesquisaPaginada;
import br.gov.caixa.dominio.exception.AmbienteException;
import br.gov.caixa.silce.dominio.entidade.Apostador;

import br.gov.caixa.silce.dominio.entidade.SituacaoApostador.Situacao;
import br.gov.caixa.silce.negocio.infra.dao.SilceDAO;
import br.gov.caixa.util.CPF;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Email;


@Local
public interface ApostadorDAOLocal extends SilceDAO<Apostador, Long> {

	/**
	 * Recupera um Apostador dado o cpf informado.
	 * 
	 * @param cpf
	 * @return
	 * @throws AmbienteException
	 */
	public Apostador findByCPF(CPF cpf);

	/**
	 * Recupera um Apostador dado o email informado.
	 * 
	 * @param email
	 * @return
	 * @throws AmbienteException
	 */
	public Apostador findByEmail(Email email);

	/**
	 * Recupera apostadores com {@link Situacao} ativo, em que a data do último acesso seja igual ou menor que a data
	 * informada.
	 */
	public List<Apostador> findAtivosByDataMaximaUltimoAcesso(Data data);

	/**
	 * Recupera apostadores com {@link Situacao} ativo, em que a data do último acesso seja igual a data informada.
	 */
	public List<Apostador> findAtivosByDataUltimoAcesso(Data data);

	/**
	 * Recupera um Apostador com o id diferente do informado, mas com o mesmo cpf informado.
	 * 
	 * @param id
	 * @param cpf
	 * @return
	 * @throws AmbienteException
	 */
	public Apostador findByCPFWithDifferentId(Long id, CPF cpf);

	public ResultadoPesquisaPaginada<Apostador> findByCpfEmailSituacao(CPF cpf, Email email, String nome, Situacao situacao, Long loterica, int first, int pageSize);

	Apostador findByCPFNome(CPF cpf, String nome);

	public Long findIdentificadorByCPF(CPF cpf);

	public List<Object[]> findQuantidadeUsuariosCompraram(Data diaInicio, Data diaFim);

	public List<Object[]> findQuantidadeUsuariosSemCompra(Data dataInicio, Data dataFim);

	public List<Object[]> findQuantidadeUsuariosNovos(Data dataInicio, Data dataFim);

	public List<Object[]> findQuantidadeUsuariosTotal();

	public List<Object[]> findQuantidadeUsuariosApenasComprasNegadas(Data dataInicio, Data dataFim);
	
	public ResultadoPesquisaPaginada<Apostador> findApostadorImpedidoSicow(CPF cpf, Data dataInicio, Data dataFim, int first, int pageSize);

}
