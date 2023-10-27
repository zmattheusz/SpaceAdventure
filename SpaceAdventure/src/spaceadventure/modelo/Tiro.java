package spaceadventure.modelo;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Tiro {
    private Image imagem; // Imagem do tiro
    private int x, y; // Posição do tiro
    private int largura, altura; // Dimensões do tiro
    private boolean isVisivel; // Indica se o tiro está visível na tela
    private static final int LARGURA = 938; // Largura máxima da tela
    private static int VELOCIDADE = 2; // Velocidade do tiro

    public Tiro(int x, int y) {
        this.x = x;
        this.y = y;
        isVisivel = true;
    }

    public void load() {
        // Carrega a imagem do tiro a partir de um arquivo
        ImageIcon referencia = new ImageIcon("res\\TiroSimples.png");
        imagem = referencia.getImage();

        this.largura = imagem.getWidth(null);
        this.altura = imagem.getHeight(null);
    }

    public void update() {
        // Atualiza a posição do tiro com base na velocidade
        this.x += VELOCIDADE;

        // Verifica se o tiro saiu da tela, tornando-o invisível
        if (this.x > LARGURA) {
            isVisivel = false;
        }
    }
    // Representa a área ocupada pelo tiro
    public Rectangle getBounds() {
        return new Rectangle(x, y, largura, altura);
    }

 // Getters e setters...
    public boolean isVisivel() {
        return isVisivel;
    }

    public void setVisivel(boolean isVisivel) {
        this.isVisivel = isVisivel;
    }

    public static int getVELOCIDADE() {
        return VELOCIDADE;
    }

    public static void setVELOCIDADE(int vELOCIDADE) {
        VELOCIDADE = vELOCIDADE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImagem() {
        return imagem;
    }
}
