package spaceadventure;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import spaceadventure.modelo.Fase;

public class Container extends JFrame {
    private JanelaLogin janelaLogin; // Referência à janela de login
    private String nomeJogador; // Nome do jogador

    public Container() {
        exibirJanelaLogin();
    }

    private void exibirJanelaLogin() {
        janelaLogin = new JanelaLogin(this); // Cria a janela de login passando a referência do container
        janelaLogin.adicionarListenerContinuar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nomeJogador = janelaLogin.getNomeJogador(); // Obtém o nome do jogador selecionado
                iniciarJogo();
            }
        });
        janelaLogin.adicionarListenerNovoJogo(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nomeJogador = null; // Reseta o nome do jogador para um novo jogo
                criarNovoJogo();
            }
        });
    }


    public void iniciarJogo() {
        // Adiciona uma nova instância de Fase ao container, passando o nome do jogador
        add(new Fase(nomeJogador));
        setTitle("Space Adventure");
        setSize(1024, 728);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        janelaLogin.dispose(); // Fecha a janela de login
    }
    public void setNomeJogador(String nome) {
        nomeJogador = nome;
    }

    private void criarNovoJogo() {
    }

    public static void main(String[] args) {
        new Container();
    }
}
