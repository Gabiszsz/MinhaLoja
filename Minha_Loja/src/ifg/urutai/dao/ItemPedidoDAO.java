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
import ifg.urutai.model.ItemPedido;
import ifg.urutai.model.Pedido;
import ifg.urutai.model.Produto;

public class ItemPedidoDAO {

	Connection connection;
	public ItemPedidoDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void add(ItemPedido ItemPedido) { //adicionar 
		String sql = "INSERT INTO item_Pedido (quantidade, id_Pedido, id_Produto) VALUES (?, ?, ?)";

		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setInt(1, ItemPedido.getQuantidade());
			statement.setInt(2, ItemPedido.getPedido().getIdPedido());
			statement.setInt(3, ItemPedido.getProduto().getIdProduto());
			statement.execute();
		}catch (SQLException e) {
			Logger.getLogger(ItemPedidoDAO.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public ItemPedido findbyId(int id) {  // buscar 
		String sql = "SELECT * FROM item_Pedido WHERE id_Item_Pedido = ?";
		ItemPedido ItemPedido = new ItemPedido();
		
		PedidoDAO pedidoDAO = new PedidoDAO();
		ProdutoDAO produtoDAO = new ProdutoDAO();

		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setInt(1, id);

			ResultSet rs = statement.executeQuery();
			rs.next();
			ItemPedido = new ItemPedido();
			ItemPedido.setIdItemPedido(rs.getInt("id_Item_Pedido"));
			ItemPedido.setQuantidade(rs.getInt("quantidade"));
			int id_Pedido = rs.getInt("id_Pedido");
			int id_Produto = rs.getInt("id_Produto");

			Pedido pedido = pedidoDAO.findbyId(id_Pedido);
			ItemPedido.setPedido(pedido);

			Produto produto = produtoDAO.findbyId(id_Produto);
			ItemPedido.setProduto(produto);

		} catch (SQLException e) {
			Logger.getLogger(ItemPedidoDAO.class.getName()).log(Level.SEVERE,null,e);
		}
		return ItemPedido;

	}
	public void updateNameById(ItemPedido ItemPedido) { // atualizar 
		String sql = "UPDATE item_Pedido SET quantidade = ? WHERE id_Item_Pedido = ?";

		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setInt(1, ItemPedido.getQuantidade());
			statement.setInt(2, ItemPedido.getIdItemPedido());
			statement.execute();
		}catch (SQLException e) {
			Logger.getLogger(ItemPedidoDAO.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public void deleteByName(ItemPedido ItemPedido) { //remover 
		String sql = "DELETE FROM item_Pedido WHERE id_Item_Pedido = ?";

		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setInt(1, ItemPedido.getIdItemPedido());
			statement.execute();
		}catch (SQLException e) {
			Logger.getLogger(ItemPedidoDAO.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public List<ItemPedido> findAllByIdPedido(int id_Pedido){ //buscar todos
		String sql = "SELECT * FROM item_Pedido WHERE id_Item_Pedido = ?";

		List<ItemPedido> list = new ArrayList<>();

		PedidoDAO pedidoDAO = new PedidoDAO();
		ProdutoDAO produtoDAO = new ProdutoDAO();

		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.execute();
			ResultSet rs = statement.getResultSet();
			ItemPedido ItemPedido = null;

			while (rs.next()) {
				ItemPedido = new ItemPedido();
				ItemPedido.setIdItemPedido(rs.getInt("id_Item_Pedido"));
				ItemPedido.setQuantidade(rs.getInt("quantidade"));
				int id_Produto = rs.getInt("id_Produto");

				Pedido pedido = pedidoDAO.findbyId(id_Pedido);
				Produto produto = produtoDAO.findbyId(id_Produto);

				ItemPedido.setPedido(pedido);
				ItemPedido.setProduto(produto);

				list.add(ItemPedido);
			}

		}catch (SQLException e) {
			Logger.getLogger(ItemPedidoDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		return list;
	}

}
