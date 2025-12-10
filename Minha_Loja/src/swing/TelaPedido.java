package swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.util.List;
import ifg.urutai.dao.PedidoDAO;
import ifg.urutai.dao.PessoaDAO;
import ifg.urutai.model.Pedido;
import ifg.urutai.model.Pessoa;

public class TelaPedido {
	public static void abrir() {
        JFrame frame = new JFrame("Pedido");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblId = new JLabel("ID Pedido:");
        JTextField txtId = new JTextField();

        JLabel lblData = new JLabel("Data do Pedido (yyyy-mm-dd):");
        JTextField txtData = new JTextField();

        JLabel lblIdPessoa = new JLabel("ID Pessoa:");
        JTextField txtIdPessoa = new JTextField();

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
        painelFormulario.add(lblData, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        painelFormulario.add(txtData, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        painelFormulario.add(lblIdPessoa, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        painelFormulario.add(txtIdPessoa, gbc);

        JPanel painelBotoes = new JPanel(new GridLayout(2, 3, 10, 10));
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnBuscarPorId);
        painelBotoes.add(btnBuscarTodos);
        painelBotoes.add(btnLimpar);

        JTextArea areaResultado = new JTextArea(10, 40);
        areaResultado.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaResultado);

        painelPrincipal.add(painelFormulario, BorderLayout.NORTH);
        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);
        painelPrincipal.add(scroll, BorderLayout.SOUTH);

        frame.add(painelPrincipal);
        frame.setVisible(true);

        PedidoDAO pedidoDAO = new PedidoDAO();
        PessoaDAO pessoaDAO = new PessoaDAO();

        Runnable limpar = () -> {
            txtId.setText("");
            txtData.setText("");
            txtIdPessoa.setText("");
            areaResultado.setText("");
        };

        btnAdicionar.addActionListener((ActionEvent e) -> {
            try {
                String data = txtData.getText().trim();
                int idPessoa = Integer.parseInt(txtIdPessoa.getText().trim());

                Pessoa pessoa = pessoaDAO.findbyId(idPessoa);
                if (pessoa.getIdPessoa() == 0 || pessoa.getNome() == null) {
                    JOptionPane.showMessageDialog(frame, "Pessoa não encontrada!");
                    return;
                }

                Pedido p = new Pedido();
                p.setDataPedido(Date.valueOf(data));
                p.setPessoa(pessoa);

                pedidoDAO.add(p);
                JOptionPane.showMessageDialog(frame, "Pedido adicionado!");
                limpar.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAtualizar.addActionListener((ActionEvent e) -> {
            try {
                int idPedido = Integer.parseInt(txtId.getText().trim());
                String data = txtData.getText().trim();

                Pedido p = new Pedido();
                p.setIdPedido(idPedido);
                p.setDataPedido(Date.valueOf(data));

                pedidoDAO.updateNameById(p);
                JOptionPane.showMessageDialog(frame, "Pedido atualizado!");
                limpar.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao atualizar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRemover.addActionListener((ActionEvent e) -> {
            try {
                int idPedido = Integer.parseInt(txtId.getText().trim());
                int confirm = JOptionPane.showConfirmDialog(frame, "Deseja remover o pedido ID " + idPedido + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    Pedido p = new Pedido();
                    p.setIdPedido(idPedido);
                    pedidoDAO.deleteByName(p);
                    JOptionPane.showMessageDialog(frame, "Pedido removido!");
                    limpar.run();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao remover: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBuscarPorId.addActionListener((ActionEvent e) -> {
            try {
                int idPedido = Integer.parseInt(txtId.getText().trim());
                Pedido p = pedidoDAO.findbyId(idPedido);
                if (p.getIdPedido() == 0 || p.getPessoa() == null) {
                    areaResultado.setText("Pedido não encontrado.");
                } else {
                    txtData.setText(p.getDataPedido().toString());
                    txtIdPessoa.setText(String.valueOf(p.getPessoa().getIdPessoa()));
                    areaResultado.setText("Pedido encontrado: " + p.getPessoa().getNome());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao buscar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBuscarTodos.addActionListener((ActionEvent e) -> {
            areaResultado.setText("");
            List<Pedido> lista = pedidoDAO.findAll();
            if (lista.isEmpty()) {
                areaResultado.setText("Nenhum pedido encontrado.");
            } else {
                for (Pedido p : lista) {
                    areaResultado.append("\nID Pedido: " + p.getIdPedido() + "\nData: " + p.getDataPedido() + "\nID Pessoa: " + (p.getPessoa() != null ? p.getPessoa().getIdPessoa() : "") + "\n");
                }
            }
        });

        btnLimpar.addActionListener(e -> limpar.run());
    }
}

