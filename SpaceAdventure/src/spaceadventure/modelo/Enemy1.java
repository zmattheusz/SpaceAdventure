package spaceadventure.modelo;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Enemy1 {

	private Image imagem; // Imagem do inimigo
	private int x, y; // Posição do inimigo
	private int largura, altura; // Dimensões do inimigo
	private boolean isVisivel; // Indica se o inimigo está visível na tela
	private static int VELOCIDADE = 5; // Velocidade do inimigo

	public Enemy1(int x, int y) {
		this.x = x;
		this.y = y;
		isVisivel = true;
	}

	public void load() {
		// Carrega a imagem do inimigo a partir de um arquivo
		ImageIcon referencia = new ImageIcon("res\\Enemy1.png");
		imagem = referencia.getImage();

		this.largura = imagem.getWidth(null);
		this.altura = imagem.getHeight(null);
	}

	public void update() {
		// Atualiza a posição do inimigo com base na velocidade
		this.x -= VELOCIDADE;
	}

//  Getters and setters
	public Rectangle getBounds() {
		return new Rectangle(x, y, largura, altura);
	}

	public boolean isVisivel() {
		// Verifica se o inimigo está visível na tela
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
