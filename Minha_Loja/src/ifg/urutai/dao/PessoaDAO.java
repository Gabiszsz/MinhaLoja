package ifg.urutai.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import ifg.urutai.model.Pessoa;
import ifg.urutai.connection.ConnectionFactory;

public class PessoaDAO {
	Connection connection;
	public PessoaDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void add(Pessoa pessoa) { //adicionar pessoa
		String sql = "INSERT INTO Pessoa (nome, data_nascimento) VALUES (?, ?)";

		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setString(1, pessoa.getNome());
			statement.setDate(2, pessoa.getDataDeNascimento());
			statement.execute();
		}catch (SQLException e) {
			Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public Pessoa findbyId(int id) {  // buscar pessoa
		String sql = "SELECT * FROM Pessoa WHERE id_Pessoa = ?";
		Pessoa pessoa = new Pessoa();

		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setInt(1, id);

			ResultSet rs = statement.executeQuery();
			rs.next();
			pessoa = new Pessoa();
			pessoa.setIdPessoa(rs.getInt("id_Pessoa"));
			pessoa.setNome(rs.getString("nome"));
			pessoa.setDataDeNascimento(rs.getDate("data_nascimento"));

		} catch (SQLException e) {
			Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE,null,e);
		}
		return pessoa;

	}
	public void updateNameById(Pessoa pessoa) { // atualizar pessoa
		String sql = "UPDATE Pessoa SET nome = ?, data_nascimento = ? WHERE id_Pessoa = ?";

		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setString(1, pessoa.getNome());
			statement.setInt(3, pessoa.getIdPessoa());
			statement.setDate(2, pessoa.getDataDeNascimento());
			statement.execute();
		}catch (SQLException e) {
			Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public void deleteByName(Pessoa pessoa) { //remover pessoa
		String sql = "DELETE FROM Pessoa WHERE nome = ?";

		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setString(1, pessoa.getNome());
			statement.execute();
		}catch (SQLException e) {
			Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public List<Pessoa> findAll(){ //buscar todos
		String sql = "SELECT * FROM Pessoa";

		List<Pessoa> list = new ArrayList<>();

		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.execute();
			ResultSet rs = statement.getResultSet();
			Pessoa pessoa = null;

			while (rs.next()) {
				pessoa = new Pessoa();
				pessoa.setIdPessoa(rs.getInt("id_Pessoa"));
				pessoa.setNome(rs.getString("nome"));
				pessoa.setDataDeNascimento(rs.getDate("data_nascimento"));

				list.add(pessoa);
			}

		}catch (SQLException e) {
			Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		return list;
	}

}


