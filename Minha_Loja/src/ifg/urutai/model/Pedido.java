package ifg.urutai.model;

import java.sql.Date;

public class Pedido {
	private int idPedido;
	private Date dataPedido;
	private Pessoa pessoa;
	
	public int getIdPedido() {
		return idPedido;
	}
	public Date getDataPedido() {
		return dataPedido;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}
