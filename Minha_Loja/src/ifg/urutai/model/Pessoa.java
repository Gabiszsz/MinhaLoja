package ifg.urutai.model;

import java.sql.Date;

public class Pessoa {
	private Integer idPessoa;
	private String nome;
	private Date dataDeNascimento;

	public Integer getIdPessoa() {
		return idPessoa;
	}
	public String getNome() {
		return nome;
	}
	public Date getDataDeNascimento() {
		return dataDeNascimento;

	} 
	public void setDataDeNascimento (Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}
	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}
	public void setNome(String nome) {
		this.nome = nome;

	}
	
}
