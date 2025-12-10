package swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import ifg.urutai.dao.ItemPedidoDAO;
import ifg.urutai.dao.PedidoDAO;
import ifg.urutai.dao.ProdutoDAO;
import ifg.urutai.model.ItemPedido;
import ifg.urutai.model.Pedido;
import ifg.urutai.model.Produto;

public class TelaItemPedido {
	public static void abrir() {
		JFrame frame = new JFrame("ItemPedido");
		frame.setSize(450, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
		painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel painelFormulario = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel lblId = new JLabel("ID Item Pedido:");
		JTextField txtId = new JTextField();

		JLabel lblQtd = new JLabel("Quantidade:");
		JTextField txtQtd = new JTextField();

		JLabel lblIdPedido = new JLabel("ID Pedido:");
		JTextField txtIdPedido = new JTextField();

		JLabel lblIdProduto = new JLabel("ID Produto:");
		JTextField txtIdProduto = new JTextField();

		JButton btnAdicionar = new JButton("ADICIONAR");
		JButton btnAtualizar = new JButton("ATUALIZAR");
		JButton btnRemover = new JButton("REMOVER");
		JButton btnBuscarPorId = new JButton("BUSCAR POR ID");
		JButton btnBuscarTodos = new JButton("BUSCAR TODOS");
		JButton btnLimpar = new JButton("LIMPAR");

		Color azul = new Color(230, 249, 255); // Cor
		Color texto = Color.BLACK;
		btnAdicionar.setBackground(azul);
		btnAdicionar.setForeground(texto);

		btnAtualizar.setBackground(azul);
		btnAtualizar.setForeground(texto);

		btnRemover.setBackground(azul);
		btnRemover.setForeground(texto);

		btnBuscarPorId.setBackground(azul);
		btnBuscarPorId.setForeground(texto);

		btnBuscarTodos.setBackground(azul);
		btnBuscarTodos.setForeground(texto);

		btnLimpar.setBackground(azul);
		btnLimpar.setForeground(texto);

		gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
		painelFormulario.add(lblId, gbc);
		gbc.gridx = 1; gbc.weightx = 1.0;
		painelFormulario.add(txtId, gbc);

		gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
		painelFormulario.add(lblQtd, gbc);
		gbc.gridx = 1; gbc.weightx = 1.0;
		painelFormulario.add(txtQtd, gbc);

		gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
		painelFormulario.add(lblIdPedido, gbc);
		gbc.gridx = 1; gbc.weightx = 1.0;
		painelFormulario.add(txtIdPedido, gbc);

		gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
		painelFormulario.add(lblIdProduto, gbc);
		gbc.gridx = 1; gbc.weightx = 1.0;
		painelFormulario.add(txtIdProduto, gbc);


		JPanel painelBotoes = new JPanel(new GridLayout(2, 3, 10, 10));
		painelBotoes.add(btnAdicionar);
		painelBotoes.add(btnAtualizar);
		painelBotoes.add(btnRemover);
		painelBotoes.add(btnBuscarPorId);
		painelBotoes.add(btnBuscarTodos);
		painelBotoes.add(btnLimpar);

		JTextArea areaResultado = new JTextArea(8, 30);
		areaResultado.setEditable(false);
		JScrollPane scroll = new JScrollPane(areaResultado);

		painelPrincipal.add(painelFormulario, BorderLayout.NORTH);
		painelPrincipal.add(painelBotoes, BorderLayout.CENTER);
		painelPrincipal.add(scroll, BorderLayout.SOUTH);

		frame.add(painelPrincipal);
		frame.setVisible(true);

		ItemPedidoDAO itemDAO = new ItemPedidoDAO();
		PedidoDAO pedidoDAO = new PedidoDAO();
		ProdutoDAO produtoDAO = new ProdutoDAO();

		Runnable limpar = () -> {
			txtId.setText("");
			txtQtd.setText("");
			txtIdPedido.setText("");
			txtIdProduto.setText("");
			areaResultado.setText("");
		};

		btnAdicionar.addActionListener((ActionEvent e) -> {
			try {
				int qtd = Integer.parseInt(txtQtd.getText().trim());
				int idPedido = Integer.parseInt(txtIdPedido.getText().trim());
				int idProduto = Integer.parseInt(txtIdProduto.getText().trim());

				Pedido pedido = pedidoDAO.findbyId(idPedido);
				Produto produto = produtoDAO.findbyId(idProduto);

				if (pedido.getIdPedido() == 0 || produto.getIdProduto() == 0) {
					JOptionPane.showMessageDialog(frame, "Pedido ou Produto não encontrado!");
					return;
				}

				ItemPedido item = new ItemPedido();
				item.setQuantidade(qtd);
				item.setPedido(pedido);
				item.setProduto(produto);

				itemDAO.add(item);
				JOptionPane.showMessageDialog(frame, "Item adicionado com sucesso!");
				limpar.run();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Erro ao adicionar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnAtualizar.addActionListener((ActionEvent e) -> {
			try {
				int id = Integer.parseInt(txtId.getText().trim());
				int qtd = Integer.parseInt(txtQtd.getText().trim());

				ItemPedido item = new ItemPedido();
				item.setIdItemPedido(id);
				item.setQuantidade(qtd);

				itemDAO.updateNameById(item);
				JOptionPane.showMessageDialog(frame, "Item atualizado!");
				limpar.run();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Erro ao atualizar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnRemover.addActionListener((ActionEvent e) -> {
			try {
				int id = Integer.parseInt(txtId.getText().trim());
				int qtd = Integer.parseInt(txtQtd.getText().trim());

				ItemPedido item = new ItemPedido();
				item.setIdItemPedido(id);
				item.setQuantidade(qtd);

				int confirma = JOptionPane.showConfirmDialog(frame, "Deseja remover o item ID " + id + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
				if (confirma == JOptionPane.YES_OPTION) {
					itemDAO.deleteByName(item);
					JOptionPane.showMessageDialog(frame, "Item removido!");
					limpar.run();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Erro ao remover: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnBuscarPorId.addActionListener((ActionEvent e) -> {
			try {
				int id = Integer.parseInt(txtId.getText().trim());
				ItemPedido item = itemDAO.findbyId(id);

				if (item.getIdItemPedido() == 0 || item.getProduto() == null || item.getPedido() == null) {
					areaResultado.setText("ItemPedido não encontrado.");
				} else {
					txtQtd.setText(String.valueOf(item.getQuantidade()));
					txtIdPedido.setText(String.valueOf(item.getPedido().getIdPedido()));
					txtIdProduto.setText(String.valueOf(item.getProduto().getIdProduto()));
					areaResultado.setText("Item encontrado: " + item.getProduto().getNome());
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Erro ao buscar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnBuscarTodos.addActionListener((ActionEvent e) -> {
			areaResultado.setText("OBS: Esse botão precisa de implementação correta no DAO.\nUse findAllByIdPedido(id) passando um ID válido.\n");
		});

		btnLimpar.addActionListener(e -> limpar.run());
	}
}
