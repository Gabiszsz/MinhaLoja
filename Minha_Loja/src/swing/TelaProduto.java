package swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import ifg.urutai.dao.ProdutoDAO;
import ifg.urutai.model.Produto;

public class TelaProduto {

	public static void abrir() {
        JFrame frame = new JFrame("Produto");
        frame.setSize(450, 450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblId = new JLabel("ID:");
        JTextField txtId = new JTextField();
        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField();

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
        painelFormulario.add(lblNome, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        painelFormulario.add(txtNome, gbc);

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

        ProdutoDAO dao = new ProdutoDAO();

        Runnable limparCampos = () -> {
            txtId.setText("");
            txtNome.setText("");
            areaResultado.setText("");
        };

        btnAdicionar.addActionListener((ActionEvent e) -> {
            String nome = txtNome.getText().trim();
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha o nome!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Produto p = new Produto();
            p.setNome(nome);
            dao.add(p);
            JOptionPane.showMessageDialog(frame, "Produto adicionado!");
            limparCampos.run();
        });

        btnAtualizar.addActionListener((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                String nome = txtNome.getText().trim();
                if (nome.isEmpty()) throw new Exception("Nome não pode estar vazio.");
                Produto p = new Produto();
                p.setIdProduto(id);
                p.setNome(nome);
                dao.updateNameById(p);
                JOptionPane.showMessageDialog(frame, "Produto atualizado!");
                limparCampos.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRemover.addActionListener((ActionEvent e) -> {
            String nome = txtNome.getText().trim();
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Informe o nome para remover!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirma = JOptionPane.showConfirmDialog(frame, "Tem certeza que deseja remover o produto \"" + nome + "\"?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirma == JOptionPane.YES_OPTION) {
                Produto p = new Produto();
                p.setNome(nome);
                dao.deleteByName(p);
                JOptionPane.showMessageDialog(frame, "Produto removido!");
                limparCampos.run();
            }
        });

        btnBuscarPorId.addActionListener((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                Produto p = dao.findbyId(id);
                if (p.getIdProduto() == 0 || p.getNome() == null) {
                    areaResultado.setText("Produto não encontrado.");
                } else {
                    txtNome.setText(p.getNome());
                    areaResultado.setText("Produto encontrado.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBuscarTodos.addActionListener((ActionEvent e) -> {
            areaResultado.setText("");
            List<Produto> produtos = dao.findAll();
            if (produtos.isEmpty()) {
                areaResultado.setText("Nenhum produto cadastrado.");
            } else {
                for (Produto p : produtos) {
                    areaResultado.append("ID: " + p.getIdProduto() + "\nNome: " + p.getNome() + "\n");
                }
            }
        });

        btnLimpar.addActionListener(e -> limparCampos.run());
    }
}
