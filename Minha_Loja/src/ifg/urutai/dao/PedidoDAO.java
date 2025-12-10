package ifg.urutai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ifg.urutai.connection.ConnectionFactory;
import ifg.urutai.model.Pedido;
import ifg.urutai.model.Pessoa;

public class PedidoDAO {
	
	Connection connection;
	public PedidoDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void add(Pedido pedido) { //adicionar 
		String sql = "INSERT INTO Pedido (data_Pedido, id_Pessoa) VALUES (?, ?)";

		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setDate(1, pedido.getDataPedido());
			statement.setInt(2, pedido.getPessoa().getIdPessoa());
			statement.execute();
		}catch (SQLException e) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public Pedido findbyId(int id) {  // buscar 
		String sql = "SELECT * FROM Pedido WHERE id_Pedido = ?";
		Pedido pedido = new Pedido();
		PessoaDAO pessoaDAO = new PessoaDAO();

		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setInt(1, id);

			ResultSet rs = statement.executeQuery();
			rs.next();
			pedido = new Pedido();
			pedido.setIdPedido(rs.getInt("id_Pedido"));
			pedido.setDataPedido(rs.getDate("data_Pedido"));
			int id_Pessoa = rs.getInt("id_Pessoa");
			
			Pessoa pessoa = pessoaDAO.findbyId(id_Pessoa);
			
			pedido.setPessoa(pessoa);

		} catch (SQLException e) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE,null,e);
		}
		return pedido;

	}
	public void updateNameById(Pedido pedido) { // atualizar 
		String sql = "UPDATE Pedido SET data_Pedido = ? WHERE id_Pedido = ?";

		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setDate(1, pedido.getDataPedido());
			statement.setInt(2, pedido.getIdPedido());
			statement.execute();
		}catch (SQLException e) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public void deleteByName(Pedido pedido) { //remover 
		String sql = "DELETE FROM Pedido WHERE id_Pedido = ?";

		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setInt(1, pedido.getIdPedido());
			statement.execute();
		}catch (SQLException e) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public List<Pedido> findAll(){ //buscar 
		String sql = "SELECT * FROM Pedido";

		List<Pedido> list = new ArrayList<>();
		
		PessoaDAO pessoaDAO = new PessoaDAO();

		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.execute();
			ResultSet rs = statement.getResultSet();
			Pedido pedido = null;

			while (rs.next()) {
				pedido = new Pedido();
				pedido.setIdPedido(rs.getInt("id_Pedido"));
				pedido.setDataPedido(rs.getDate("data_Pedido"));
				int id_Pessoa = rs.getInt("id_Pessoa");
				
				Pessoa pessoa = pessoaDAO.findbyId(id_Pessoa);
				pedido.setPessoa(pessoa);

				list.add(pedido);
			}

		}catch (SQLException e) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		return list;
	}

}
