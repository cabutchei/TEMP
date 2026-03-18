package br.gov.caixa.silce.dominio.entidade;

import java.io.Serializable;

import br.gov.caixa.silce.dominio.Subcanal;
import br.gov.caixa.util.CEP;
import br.gov.caixa.util.CPF;
import br.gov.caixa.util.Data;
import br.gov.caixa.util.Email;

public class ApostadorVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int TAMANHO_TO_STRING = 600;

	private Long id;
	private CPF cpf;
	private Email email;
	private String nome;
	private Data dataNascimento;
	private Data dataUltimoAcesso;
	private Data dataAtualizacaoContadorTentativasAcesso;
	private Data dataFimBloqueioVinculacao;
	private Integer contadorTentativasAcesso;
	private Integer telefoneDdd;
	private Integer telefoneNumero;
	private CEP cep;
	private Boolean aceitaTermosUso;
	private Boolean aceitaReceberNoticias;
	private Long municipioCodigoUf;
	private Long municipioNumero;
	private Long municipioDigitoVerificador;
	private Long situacaoId;
	private Data dataCadastro;
	private Long lotericaId;
	private Character sexo;
	private Subcanal subcanal;
	
	private MunicipioPK municipioPK;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CPF getCpf() {
		return cpf;
	}

	public void setCpf(CPF cpf) {
		this.cpf = cpf;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Data getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Data dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Data getDataUltimoAcesso() {
		return dataUltimoAcesso;
	}

	public void setDataUltimoAcesso(Data dataUltimoAcesso) {
		this.dataUltimoAcesso = dataUltimoAcesso;
	}

	public Data getDataAtualizacaoContadorTentativasAcesso() {
		return dataAtualizacaoContadorTentativasAcesso;
	}

	public void setDataAtualizacaoContadorTentativasAcesso(Data dataAtualizacaoContadorTentativasAcesso) {
		this.dataAtualizacaoContadorTentativasAcesso = dataAtualizacaoContadorTentativasAcesso;
	}

	public Data getDataFimBloqueioVinculacao() {
		return dataFimBloqueioVinculacao;
	}

	public void setDataFimBloqueioVinculacao(Data dataFimBloqueioVinculacao) {
		this.dataFimBloqueioVinculacao = dataFimBloqueioVinculacao;
	}

	public Integer getContadorTentativasAcesso() {
		return contadorTentativasAcesso;
	}

	public void setContadorTentativasAcesso(Integer contadorTentativasAcesso) {
		this.contadorTentativasAcesso = contadorTentativasAcesso;
	}

	public Integer getTelefoneDdd() {
		return telefoneDdd;
	}

	public void setTelefoneDdd(Integer telefoneDdd) {
		this.telefoneDdd = telefoneDdd;
	}

	public Integer getTelefoneNumero() {
		return telefoneNumero;
	}

	public void setTelefoneNumero(Integer telefoneNumero) {
		this.telefoneNumero = telefoneNumero;
	}

	public CEP getCep() {
		return cep;
	}

	public void setCep(CEP cep) {
		this.cep = cep;
	}

	public Boolean getAceitaTermosUso() {
		return aceitaTermosUso;
	}

	public void setAceitaTermosUso(Boolean aceitaTermosUso) {
		this.aceitaTermosUso = aceitaTermosUso;
	}

	public Boolean getAceitaReceberNoticias() {
		return aceitaReceberNoticias;
	}

	public void setAceitaReceberNoticias(Boolean aceitaReceberNoticias) {
		this.aceitaReceberNoticias = aceitaReceberNoticias;
	}

	public Long getMunicipioCodigoUf() {
		return municipioCodigoUf;
	}

	public void setMunicipioCodigoUf(Long municipioCodigoUf) {
		this.municipioCodigoUf = municipioCodigoUf;
	}

	public Long getMunicipioNumero() {
		return municipioNumero;
	}

	public void setMunicipioNumero(Long municipioNumero) {
		this.municipioNumero = municipioNumero;
	}

	public Long getMunicipioDigitoVerificador() {
		return municipioDigitoVerificador;
	}

	public void setMunicipioDigitoVerificador(Long municipioDigitoVerificador) {
		this.municipioDigitoVerificador = municipioDigitoVerificador;
	}

	public Long getSituacaoId() {
		return situacaoId;
	}

	public void setSituacaoId(Long situacaoId) {
		this.situacaoId = situacaoId;
	}

	public Data getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Data dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Long getLotericaId() {
		return lotericaId;
	}

	public void setLotericaId(Long lotericaId) {
		this.lotericaId = lotericaId;
	}
	
	public MunicipioPK getMunicipioPK() {
		if (municipioPK == null) {
			municipioPK = new MunicipioPK(municipioCodigoUf, municipioNumero, municipioDigitoVerificador);
		}
		return municipioPK;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(TAMANHO_TO_STRING);
		builder.append("ApostadorVO [id=").append(id).append(", cpf=").append(cpf).append(", email=").append(email).append(", nome=").append(nome)
				.append(", dataNascimento=").append(dataNascimento).append(", dataUltimoAcesso=")
				.append(dataUltimoAcesso).append(", dataAtualizacaoContadorTentativasAcesso=")
				.append(dataAtualizacaoContadorTentativasAcesso).append(", dataFimBloqueioVinculacao=").append(dataFimBloqueioVinculacao)
				.append(", contadorTentativasAcesso=").append(contadorTentativasAcesso).append(", telefoneDdd=").append(telefoneDdd)
				.append(", telefoneNumero=").append(telefoneNumero).append(", cep=").append(cep)
				.append(", aceitaTermosUso=").append(aceitaTermosUso).append(", aceitaReceberNoticias=").append(aceitaReceberNoticias)
				.append(", municipioCodigoUf=").append(municipioCodigoUf).append(", municipioNumero=").append(municipioNumero)
				.append(", municipioDigitoVerificador=").append(municipioDigitoVerificador).append(", situacaoId=").append(situacaoId)
				.append(", dataCadastro=").append(dataCadastro).append(", lotericaId=").append(lotericaId).append(", municipioPK=")
				.append(municipioPK).append(']');
		return builder.toString();
	}

	public Character getSexo() {
		return sexo;
	}

	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}

	public Subcanal getSubcanal() {
		return subcanal;
	}

	public void setSubcanal(Subcanal subcanal) {
		this.subcanal = subcanal;
	}

}
