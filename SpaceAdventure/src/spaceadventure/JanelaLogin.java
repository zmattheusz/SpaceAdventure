package spaceadventure;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JanelaLogin extends JFrame {
    private ActionListener listenerContinuar;
    private ActionListener listenerNovoJogo;
    private String nomeJogador;
    private Container container;
    

    // Definições da Janela de Login
    public JanelaLogin(Container container) {
        this.container = container;
        setTitle("Login");
        setSize(1024, 728);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(200, 30, 50));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Criação dos Botões
        JButton continuarButton = new JButton("Continuar");
        JButton novoJogoButton = new JButton("Novo Jogo");

        continuarButton.setPreferredSize(new Dimension(450, 50));
        novoJogoButton.setPreferredSize(new Dimension(450, 50));

        // Separação dos Botões
        panel.add(Box.createVerticalGlue());
        panel.add(continuarButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(novoJogoButton);
        panel.add(Box.createVerticalGlue());

        continuarButton.setAlignmentX(CENTER_ALIGNMENT);
        novoJogoButton.setAlignmentX(CENTER_ALIGNMENT);

        continuarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> nomesJogadores = carregarNomesJogadores();

                if (nomesJogadores.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Não há jogadores salvos.");
                } else {
                    // Criação da  janela de seleção de jogador
                    JFrame janelaSelecao = new JFrame("Seleção de Jogador");
                    janelaSelecao.setSize(400, 200);
                    janelaSelecao.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    janelaSelecao.setLocationRelativeTo(null);
                    janelaSelecao.setResizable(false);

                    // Painel para a  janela
                    JPanel panelSelecao = new JPanel();
                    panelSelecao.setLayout(new BoxLayout(panelSelecao, BoxLayout.Y_AXIS));

                    // Combo box com os jogadores disponíveis
                    JComboBox<String> comboBoxJogadores = new JComboBox<>(nomesJogadores.toArray(new String[0]));
                    panelSelecao.add(comboBoxJogadores);

                    // Botão Selecionar
                    JButton selecionarButton = new JButton("Selecionar");
                    selecionarButton.setPreferredSize(new Dimension(150, 30));
                    panelSelecao.add(selecionarButton);

                    // Adiciona o painel à janela
                    janelaSelecao.add(panelSelecao);

                    // Listener para o botão Selecionar
                    selecionarButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            nomeJogador = (String) comboBoxJogadores.getSelectedItem();
                            System.out.println("Jogador selecionado: " + nomeJogador);
                            // Carregar o score do jogador selecionado
                            carregarScoreJogador(nomeJogador);
                            container.setNomeJogador(nomeJogador);
                            // Iniciar o jogo
                            container.iniciarJogo();
                            janelaSelecao.dispose(); // Fecha a janela de seleção
                        }
                    });

                    // Torna a janela de seleção visível
                    janelaSelecao.setVisible(true);
                }
            }
        });

        novoJogoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nomeJogador = JOptionPane.showInputDialog(null, "Digite seu nome:");

                if (nomeJogador != null && !nomeJogador.isEmpty()) {
                    if (verificarNomeJogador(nomeJogador)) {
                        System.out.println("Nome do jogador: " + nomeJogador);

                        // Salvar o nome do jogador no arquivo
                        salvarNomeJogador(nomeJogador);

                        // Exibir um aviso para clicar em "Continuar"
                        JOptionPane.showMessageDialog(null, "Clique em 'Continuar' para iniciar o jogo");

                       
                       
                    } else {
                        JOptionPane.showMessageDialog(null, "Jogador já existente. Por favor, escolha outro nome."); // Exibe uma mensagem informando que o jogador já existe
                    }
                } else {
                    System.out.println("Nome do jogador não fornecido");
                }
            }
        });


        add(panel); // Adiciona o painel à janela

        setVisible(true); // Torna a janela visível
    }

    public void adicionarListenerContinuar(ActionListener listener) {
        this.listenerContinuar = listener;
    }

    public void adicionarListenerNovoJogo(ActionListener listener) {
        this.listenerNovoJogo = listener;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    /*
     * Carrega os nomes dos jogadores a partir de um arquivo e retorna uma lista com esses nomes.
     */
    private List<String> carregarNomesJogadores() {
        List<String> nomesJogadores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("dados_jogador.txt"))) {
            String nomeJogador;
            while ((nomeJogador = reader.readLine()) != null) {
                nomesJogadores.add(nomeJogador);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar os nomes dos jogadores: " + e.getMessage());
        }

        return nomesJogadores;
    }

    /*
     * Verifica se um nome de jogador já existe na lista de nomes carregada anteriormente.
     */
    private boolean verificarNomeJogador(String nomeJogador) {
        List<String> nomesJogadores = carregarNomesJogadores();
        return !nomesJogadores.contains(nomeJogador);
    }

    /*
     * Salva o nome do jogador fornecido em um arquivo e cria um novo arquivo com o nome do jogador para armazenar seus dados.
     */
    private void salvarNomeJogador(String nomeJogador) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dados_jogador.txt", true))) {
            writer.write(nomeJogador);
            writer.newLine();

            // Criar arquivo do jogador
            criarArquivoJogador(nomeJogador);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o nome do jogador: " + e.getMessage());
        }
    }

    /*
     * Cria um novo arquivo para armazenar os dados do jogador com o nome fornecido.
     */
    private void criarArquivoJogador(String nomeJogador) {
        try {
            FileWriter fileWriter = new FileWriter(nomeJogador + ".txt");
            fileWriter.write("Score: 0");
            fileWriter.close();
            System.out.println("Arquivo do jogador " + nomeJogador + " criado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao criar o arquivo do jogador: " + e.getMessage());
        }
    }

    /*
     * Carrega o score do jogador a partir do arquivo correspondente ao seu nome.
     */
    private void carregarScoreJogador(String nomeJogador) {
        System.out.println("Carregando score do jogador: " + nomeJogador);
    }
}

