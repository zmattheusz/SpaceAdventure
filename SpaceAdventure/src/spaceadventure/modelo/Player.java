package spaceadventure.modelo;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;
import javax.swing.ImageIcon;


public class Player {
    private int x, y; // Posição do jogador
    private int dx, dy; // Velocidade do jogador
    private Image imagem; // Imagem do jogador
    private int altura, largura; // Dimensões do jogador
    private List<Tiro> tiros; // Lista de tiros disparados pelo jogador
    private boolean isVisivel; // Indica se o jogador está visível na tela
    private String nomeJogador; // Nome do jogador

    public Player() {
        this.x = 100;
        this.y = 100;
        isVisivel = true;

        tiros = new ArrayList<Tiro>();
    }

    public void load() {
        // Carrega a imagem do jogador a partir de um arquivo
        ImageIcon referencia = new ImageIcon("res\\naveplayer.png");
        imagem = referencia.getImage();
        altura = imagem.getHeight(null);
        largura = imagem.getWidth(null);
    }
    public void limparTiros() {
    	tiros.clear();
    }

    public void update() {
        // Atualiza a posição do jogador com base na velocidade
        x += dx;
        y += dy;
    }

    public void tiroSimples() {
        // Dispara um tiro simples, adicionando-o à lista de tiros
        this.tiros.add(new Tiro(x + largura, y + (altura / 2)));
    }

    public Rectangle getBounds() {
        // Retorna um retângulo que representa a área ocupada pelo jogador
        return new Rectangle(x, y, largura, altura);
    }

    public void keyPressed(KeyEvent tecla) {
        int codigo = tecla.getKeyCode();
        if (codigo == KeyEvent.VK_A) {
            // Se a tecla A for pressionada, dispara um tiro simples
            tiroSimples();
        }
          // Metodos para fazer a nave se movimentar
        if (codigo == KeyEvent.VK_UP) {
            dy = -5;
        }
        if (codigo == KeyEvent.VK_DOWN) {
            dy = 5;
        }

        if (codigo == KeyEvent.VK_LEFT) {
            dx = -5;
        }
        if (codigo == KeyEvent.VK_RIGHT) {
            dx = 5;
        }
    }

    public void keyReleased(KeyEvent tecla) {
        int codigo = tecla.getKeyCode();

        if (codigo == KeyEvent.VK_UP) {
            dy = 0;
        }
        if (codigo == KeyEvent.VK_DOWN) {
            dy = 0;
        }

        if (codigo == KeyEvent.VK_LEFT) {
            dx = 0;
        }
        if (codigo == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }
 // Getters e setters...

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImagem() {
        return imagem;
    }

    public List<Tiro> getTiros() {
        return tiros;
    }

    public boolean isVisivel() {
        return isVisivel;
    }

    public void setVisivel(boolean isVisivel) {
        this.isVisivel = isVisivel;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public void setNomeJogador(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }

    public void setTiros(List<Tiro> tiros) {
        this.tiros = tiros;
    }
}
