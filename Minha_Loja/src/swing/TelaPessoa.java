package swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.util.List;
import ifg.urutai.dao.PessoaDAO;
import ifg.urutai.model.Pessoa;

public class TelaPessoa {

	public static void abrir() {
		JFrame frame = new JFrame("Pessoa");
		frame.setSize(450, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Painel principal
		JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
		painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Painel de formulário (campos e botões)
		JPanel painelFormulario = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel lblId = new JLabel("ID:");
		JTextField txtId = new JTextField();
		JLabel lblNome = new JLabel("Nome:");
		JTextField txtNome = new JTextField();
		JLabel lblData = new JLabel("Data Nascimento (yyyy-mm-dd):");
		JTextField txtData = new JTextField();

		JButton btnAdicionar = new JButton("ADICIONAR");
		JButton btnBuscarPorId = new JButton("BUSCAR POR ID");
		JButton btnAtualizar = new JButton("ATUALIZAR");
		JButton btnRemover = new JButton("REMOVER");
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

		// Adicionando componentes no GridBagLayout
		gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.1;
		painelFormulario.add(lblId, gbc);
		gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.9;
		painelFormulario.add(txtId, gbc);

		gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.1;
		painelFormulario.add(lblNome, gbc);
		gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.9;
		painelFormulario.add(txtNome, gbc);

		gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.1;
		painelFormulario.add(lblData, gbc);
		gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.9;
		painelFormulario.add(txtData, gbc);

		// Painel de botões
		JPanel painelBotoes = new JPanel(new GridLayout(2, 3, 10, 10));
		painelBotoes.add(btnAdicionar);
		painelBotoes.add(btnAtualizar);
		painelBotoes.add(btnRemover);
		painelBotoes.add(btnBuscarPorId);
		painelBotoes.add(btnBuscarTodos);
		painelBotoes.add(btnLimpar);

		// Área de texto para resultados
		JTextArea areaResultado = new JTextArea(8, 30);
		areaResultado.setEditable(false);
		JScrollPane scroll = new JScrollPane(areaResultado);

		// Adiciona ao painel principal
		painelPrincipal.add(painelFormulario, BorderLayout.NORTH);
		painelPrincipal.add(painelBotoes, BorderLayout.CENTER);
		painelPrincipal.add(scroll, BorderLayout.SOUTH);

		frame.add(painelPrincipal);
		frame.setVisible(true);

		PessoaDAO dao = new PessoaDAO();

		// Função para limpar campos
		Runnable limparCampos = () -> {
			txtId.setText("");
			txtNome.setText("");
			txtData.setText("");
			areaResultado.setText("");
		};

		btnAdicionar.addActionListener((ActionEvent e) -> {
			String nome = txtNome.getText().trim();
			String dataStr = txtData.getText().trim();

			if (nome.isEmpty() || dataStr.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Preencha nome e data de nascimento!", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}

			try {
				Pessoa p = new Pessoa();
				p.setNome(nome);
				p.setDataDeNascimento(Date.valueOf(dataStr));
				dao.add(p);
				JOptionPane.showMessageDialog(frame, "Pessoa adicionada com sucesso!");
				limparCampos.run();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Erro ao adicionar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnAtualizar.addActionListener((ActionEvent e) -> {
			String idStr = txtId.getText().trim();
			String nome = txtNome.getText().trim();
			String dataStr = txtData.getText().trim();

			if (idStr.isEmpty() || nome.isEmpty() || dataStr.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Preencha ID, nome e data de nascimento para atualizar!", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}

			try {
				int id = Integer.parseInt(idStr);
				Pessoa p = new Pessoa();
				p.setIdPessoa(id);
				p.setNome(nome);
				p.setDataDeNascimento(Date.valueOf(dataStr));
				dao.updateNameById(p);
				JOptionPane.showMessageDialog(frame, "Pessoa atualizada com sucesso!");
				limparCampos.run();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "ID inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Erro ao atualizar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnRemover.addActionListener((ActionEvent e) -> {
			String nome = txtNome.getText().trim();

			if (nome.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Informe o nome para remover!", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}

			int confirma = JOptionPane.showConfirmDialog(frame, "Tem certeza que deseja remover a pessoa com nome: " + nome + " ?", "Confirmação", JOptionPane.YES_NO_OPTION);
			if (confirma != JOptionPane.YES_OPTION) return;

			try {
				Pessoa p = new Pessoa();
				p.setNome(nome);
				dao.deleteByName(p);
				JOptionPane.showMessageDialog(frame, "Pessoa removida com sucesso!");
				limparCampos.run();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Erro ao remover: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnBuscarPorId.addActionListener((ActionEvent e) -> {
			String idStr = txtId.getText().trim();

			if (idStr.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Informe o ID para buscar!", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}

			try {
				int id = Integer.parseInt(idStr);
				Pessoa p = dao.findbyId(id);
				if (p.getIdPessoa() == 0) {
					areaResultado.setText("Pessoa não encontrada.");
				} else {
					txtNome.setText(p.getNome());
					txtData.setText(p.getDataDeNascimento().toString());
					areaResultado.setText("Pessoa encontrada com sucesso.");
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "ID inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Erro ao buscar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnBuscarTodos.addActionListener((ActionEvent e) -> {
			areaResultado.setText("");
			List<Pessoa> pessoas = dao.findAll();
			if (pessoas.isEmpty()) {
				areaResultado.setText("Nenhuma pessoa cadastrada.");
			} else {
				for (Pessoa p : pessoas) {
					areaResultado.append("ID: " + p.getIdPessoa() + "\nNome: " + p.getNome()
					+ "\nNascimento: " + p.getDataDeNascimento() + "\n");
				}
			}
		});

		btnLimpar.addActionListener(e -> limparCampos.run());
	}
}