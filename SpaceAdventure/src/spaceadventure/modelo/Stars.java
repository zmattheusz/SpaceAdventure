package spaceadventure.modelo;

import java.awt.Image;


import javax.swing.ImageIcon;
import java.util.Random;

public class Stars {

	private Image imagem; // Imagem da estrela
	private int x, y; // Posição da estrela
	private int largura; // Dimensões da estrela
	private boolean isVisivel; // Indica se a estrela está visível na tela

	private static int VELOCIDADE = 5; // Velocidade da estrela

	public Stars(int x, int y) {
		this.x = x;
		this.y = y;
		isVisivel = true;
	}

	public void load() {
		// Carrega a imagem da estrela a partir de um arquivo
		ImageIcon referencia = new ImageIcon("res\\Star1.png");
		imagem = referencia.getImage();

		this.largura = imagem.getWidth(null);
		imagem.getHeight(null);
	}

	public void update() {
		if (this.x < 0) {
			// Se a estrela sair da tela pela esquerda, reposiciona ela à direita com uma nova posição aleatória
			this.x = largura;
			Random a = new Random();
			int m = a.nextInt(500);
			this.x = m + 1024;
			Random r = new Random();
			int n = r.nextInt(768);
			this.y = n;
		} else {
			// Atualiza a posição da estrela com base na velocidade
			this.x -= VELOCIDADE;
		}
	}
 // Getters and Setters
	public boolean isVisivel() {
		// Verifica se a estrela está visível na tela
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
