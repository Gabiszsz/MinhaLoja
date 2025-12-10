package swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import swing.TelaPessoa;
import swing.TelaProduto;
import swing.TelaPedido;
import swing.TelaItemPedido;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TelaPrincipal {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Minha Loja");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 350);
            frame.setLocationRelativeTo(null);

            JPanel painelPrincipal = new JPanel();
            painelPrincipal.setLayout(new BorderLayout());
            painelPrincipal.setBackground(new Color(255, 255, 255)); 

     
            JPanel painelTitulo = new JPanel();
            painelTitulo.setLayout(new BoxLayout(painelTitulo, BoxLayout.Y_AXIS));
            painelTitulo.setBackground(new Color(255, 255, 255));

            JLabel titulo = new JLabel("Bem-vindo à Minha Loja!");
            titulo.setFont(new Font("Arial", Font.BOLD, 20));
            titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel subtitulo = new JLabel("Escolha uma opção abaixo:");
            subtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
            subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

            painelTitulo.add(Box.createVerticalStrut(10));
            painelTitulo.add(titulo);
            painelTitulo.add(Box.createVerticalStrut(5));
            painelTitulo.add(subtitulo);
            painelTitulo.add(Box.createVerticalStrut(20));

            // Painel de botões
            JPanel painelBotoes = new JPanel();
            painelBotoes.setLayout(new GridLayout(4, 1, 10, 10));
            painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
            painelBotoes.setBackground(new Color(255, 255, 255));

            Font fonteBotao = new Font("Arial", Font.BOLD, 16);

            JButton btnPessoa = criarBotao("PESSOA", new Color(230, 249, 255), Color.BLACK, fonteBotao);
            JButton btnProduto = criarBotao("PRODUTO", new Color(230, 249, 255), Color.BLACK, fonteBotao);
            JButton btnPedido = criarBotao("PEDIDO", new Color(230, 249, 255), Color.BLACK, fonteBotao);
            JButton btnItemPedido = criarBotao("ITEM PEDIDO", new Color(230, 249, 255), Color.BLACK, fonteBotao);

            painelBotoes.add(btnPessoa);
            painelBotoes.add(btnProduto);
            painelBotoes.add(btnPedido);
            painelBotoes.add(btnItemPedido);

            // Ações
            btnPessoa.addActionListener(e -> TelaPessoa.abrir());
            btnProduto.addActionListener(e -> TelaProduto.abrir());
            btnPedido.addActionListener(e -> TelaPedido.abrir());
            btnItemPedido.addActionListener(e -> TelaItemPedido.abrir());


            painelPrincipal.add(painelTitulo, BorderLayout.NORTH);
            painelPrincipal.add(painelBotoes, BorderLayout.CENTER);

            frame.add(painelPrincipal);
            frame.setVisible(true);
        });
    }

    private static JButton criarBotao(String texto, Color corFundo, Color corTexto, Font fonte) {
        JButton botao = new JButton(texto);
        botao.setBackground(corFundo);
        botao.setForeground(corTexto);
        botao.setFont(fonte);
        botao.setFocusPainted(false);
        return botao;
    }

    private static void abrirJanelaCRUD(String entidade, Color corFundo) {
        JFrame janelaCRUD = new JFrame(entidade);
        janelaCRUD.setSize(300, 250);
        janelaCRUD.setLocationRelativeTo(null);

        JPanel painelCRUD = new JPanel(new GridLayout(4, 1, 10, 10));
        painelCRUD.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelCRUD.setBackground(corFundo);

        Font fonteCRUD = new Font("Arial", Font.PLAIN, 14);
        Color corBotao = Color.WHITE;

        JButton btnAdicionar = criarBotao("ADICIONAR", corBotao, corFundo, fonteCRUD);
        JButton btnBuscar = criarBotao("BUSCAR", corBotao, corFundo, fonteCRUD);
        JButton btnAtualizar = criarBotao("ATUALIZAR", corBotao, corFundo, fonteCRUD);
        JButton btnRemover = criarBotao("REMOVER", corBotao, corFundo, fonteCRUD);
        JButton btnBuscarTodos = criarBotao("BUSCAR TODOS", corBotao, corFundo, fonteCRUD);

        painelCRUD.add(btnAdicionar);
        painelCRUD.add(btnBuscar);
        painelCRUD.add(btnAtualizar);
        painelCRUD.add(btnRemover);
        painelCRUD.add(btnBuscarTodos);

        janelaCRUD.add(painelCRUD);
        janelaCRUD.setVisible(true);
    }
}

