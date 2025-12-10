package ifg.urutai.teste;

import java.sql.Date;
import java.util.List;

import ifg.urutai.dao.ProdutoDAO;
//import ifg.urutai.dao.PessoaDAO;
//import ifg.urutai.model.Pessoa;
import ifg.urutai.model.Produto;

public class Main {

	public static void main(String[] args) {
		//Pessoa pessoa = new Pessoa();

		//PessoaDAO pDAO = new PessoaDAO();
		//Pessoa pessoa = pDAO.findbyId(2);
		//pessoa.setNome("Gabriella Alves do Nascimento");
		//pDAO.updateNameById(pessoa);
		//pessoa.setDataDeNascimento(Date.valueOf("2005-07-22"));
		//pDAO.updateNameById(pessoa);
		//pessoa.setNome("Gabriella Alves do Nascimento");
		//pDAO.deleteByName(pessoa);
		


		/*
		List <Pessoa> lista = pDAO.findAll();

		for(Pessoa pessoa : lista)
			System.out.println(pessoa.getNome());
		 */

		//for(int i = 0; i <lista.size(); i++) 
		//System.out.println(lista.get(i).getNome());

		//PessoaDAO pDAO = new PessoaDAO();
		//pessoa.setNome("Gabriella Alves do Nascimento");
		//pDAO.deleteByName(pessoa);
		/*List<Pessoa> listaPessoas = pDAO.findAll();

		System.out.println("                                  Pessoas");
		System.out.println("------------------------------------------");
		listaPessoas.forEach(p ->{
			System.out.println("Código: "+p.getidPessoa());
			System.out.println("Nome: "+p.getNome());
		});
	}*/
		//pessoa = pDAO.findbyId(11);
		//pDAO.deleteByName(pessoa);



		//System.out.println("Id pessoa: " + pessoa.getidPessoa());
		//System.out.println("Nome pessoa: " + pessoa.getNome());
		//System.out.println("Data Nascimento pessoa: " + pessoa.getdataDeNascimento());



		//pessoa.setNome("Jo Soares");
		//pessoa.setDataDeNascimento(Date.valueOf("1938-01-16"));

		//PessoaDAO pDAO = new PessoaDAO();

		//pDAO.add(pessoa);
		//System.out.println("Pessoa adicionada com sucesso");
		
		
		
		
		
		// PRODUTO
		
		Produto produto = new Produto();
		produto.setNome("Iphone xr");
		
		ProdutoDAO produtoDAO = new ProdutoDAO();
		produtoDAO.add(produto);
	}
}