package spaceadventure.modelo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;

public class Fase extends JPanel implements ActionListener {

	private Image fundo;
	private Player player;
	private Timer timer;
	private List<Enemy1> enemy1;
	private boolean emJogo;
	private List<Stars> stars;
	private List<Enemy2> enemy2;
	private Font fonteScore;
	private int scoreX;
	private int scoreY;
	private int score = 0;
	private boolean scoreSalvo = false;
	private boolean fimJogo = false;
	private String nomeJogador;

	public Fase(String nomeJogador) {
		setFocusable(true);
		setDoubleBuffered(true);
		this.nomeJogador = nomeJogador;
		ImageIcon referencia = new ImageIcon("res/blackground.png");
		fundo = referencia.getImage();
		player = new Player();
		player.load();

		addKeyListener(new TecladoAdapter());

		timer = new Timer(5, this);
		timer.start();
		Score();
		emJogo = true;
		score = lerScoreJogador(nomeJogador); // Carrega o score atual do jogador
		inicializaInimigos();
		inicializaStars();
		inicializaInimigos2();

	}

	// Inicializa a lista de inimigos do tipo Enemy1
	public void inicializaInimigos() {
		int coordenadas[] = new int[40];
		enemy1 = new ArrayList<Enemy1>();
		for (int i = 0; i < coordenadas.length; i++) {
			int x = (int) (Math.random() * 8000 + 1024);
			int y = (int) (Math.random() * 650 + 30);
			enemy1.add(new Enemy1(x, y));
		}
	}

	// Inicializa a lista de estrelas do tipo Stars
	public void inicializaStars() {
		int coordenadas[] = new int[500];
		stars = new ArrayList<Stars>();
		for (int i = 0; i < coordenadas.length; i++) {
			int x = (int) (Math.random() * 1024 + 0);
			int y = (int) (Math.random() * 768 + 0);
			stars.add(new Stars(x, y));
		}
	}

	// Inicializa a lista de inimigos do tipo Enemy2
	public void inicializaInimigos2() {
		int coordenadas[] = new int[40];
		enemy2 = new ArrayList<Enemy2>();
		for (int i = 0; i < coordenadas.length; i++) {
			int x = (int) (Math.random() * 8000 + 1024);
			int y = (int) (Math.random() * 650 + 30);
			enemy2.add(new Enemy2(x, y));
		}
	}

	// Configuração do score
	public void Score() {
		fonteScore = new Font("Arial", Font.BOLD, 20);
		scoreX = 10;
		scoreY = 30;
	}

	// Variável estática para armazenar o score total
	private static int scoreTotal = 0;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graficos = (Graphics2D) g;

		if (emJogo) {
			graficos.drawImage(fundo, 0, 0, null);

			// Desenha as estrelas na tela
			for (Stars star : stars) {
				star.load();
				graficos.drawImage(star.getImagem(), star.getX(), star.getY(), this);
			}

			graficos.setFont(fonteScore);
			graficos.setColor(Color.WHITE);
			graficos.drawString("Score: " + score, scoreX, scoreY);

			graficos.drawImage(player.getImagem(), player.getX(), player.getY(), this);

			// Desenha os tiros do jogador na tela
			List<Tiro> tiros = player.getTiros();
			for (Tiro tiro : tiros) {
				tiro.load();
				graficos.drawImage(tiro.getImagem(), tiro.getX(), tiro.getY(), this);
			}

			// Desenha os inimigos do tipo Enemy2 na tela
			for (Enemy2 enemy : enemy2) {
				enemy.load();
				graficos.drawImage(enemy.getImagem(), enemy.getX(), enemy.getY(), this);
			}

			// Desenha os inimigos do tipo Enemy1 na tela
			for (Enemy1 enemy : enemy1) {
				enemy.load();
				graficos.drawImage(enemy.getImagem(), enemy.getX(), enemy.getY(), this);
			}
		} else {
			ImageIcon fimJogo = new ImageIcon("res/fimdejogo.png");
			graficos.drawImage(fimJogo.getImage(), 0, 0, null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		player.update();

		// Atualiza a posição das estrelas na tela
		for (Stars star : stars) {
			if (star.isVisivel()) {
				star.update();
			} else {
				star.setVisivel(false);
			}
		}
		// Verifica se o tiro e os inimigos estão visiveis e os remove
		List<Tiro> tiros = player.getTiros();
		Iterator<Tiro> iteratorTiro = tiros.iterator();
		while (iteratorTiro.hasNext()) {
			Tiro tiro = iteratorTiro.next();
			if (tiro.isVisivel()) {
				tiro.update();
			} else {
				iteratorTiro.remove();
			}
		}
		player.setTiros(tiros);

		Iterator<Enemy1> iteratorEnemy1 = enemy1.iterator();
		while (iteratorEnemy1.hasNext()) {
			Enemy1 enemy = iteratorEnemy1.next();
			if (enemy.isVisivel()) {
				enemy.update();
			} else {
				iteratorEnemy1.remove();
			}
		}

		Iterator<Enemy2> iteratorEnemy2 = enemy2.iterator();
		while (iteratorEnemy2.hasNext()) {
			Enemy2 enemy = iteratorEnemy2.next();
			if (enemy.isVisivel()) {
				enemy.update();
			} else {
				iteratorEnemy2.remove();
			}
		}

		checarColisoes(nomeJogador);

		// Salva o score do jogador quando o jogo termina
		if (!scoreSalvo && !player.isVisivel()) {
			if (nomeJogador != null && !nomeJogador.isEmpty()) {
				salvarScoreJogador(nomeJogador, score);
				scoreSalvo = true;
			} else {
				System.out.println("Nome do jogador inválido. O score não será salvo.");
			}
		}

		repaint();
	}

	public void checarColisoes(String jogadorAtual) {
		Rectangle formaNave = player.getBounds();
		Rectangle formaEnemy1;
		Rectangle formaTiro;
		Rectangle formaEnemy2;

		// Verifica colisões entre a nave do jogador e os inimigos do tipo Enemy1
		for (Enemy1 enemy : enemy1) {
			formaEnemy1 = enemy.getBounds();

			if (formaNave.intersects(formaEnemy1)) {
				player.setVisivel(false);
				enemy.setVisivel(false);
				emJogo = false;
			}

			// Verifica colisões entre os tiros do jogador e os inimigos do tipo Enemy1
			List<Tiro> tiros = player.getTiros();
			Iterator<Tiro> iteratorTiro = tiros.iterator();
			while (iteratorTiro.hasNext()) {
				Tiro tempTiro = iteratorTiro.next();
				formaTiro = tempTiro.getBounds();

				if (formaEnemy1.intersects(formaTiro)) {
					enemy.setVisivel(false);
					iteratorTiro.remove();
					score += 1;

				}
			}
		}

		// Verifica colisões entre a nave do jogador e os inimigos do tipo Enemy2
		for (Enemy2 enemy : enemy2) {
			formaEnemy2 = enemy.getBounds();

			if (formaNave.intersects(formaEnemy2)) {
				player.setVisivel(false);
				enemy.setVisivel(false);
				emJogo = false;
			}

			// Verifica colisões entre os tiros do jogador e os inimigos do tipo Enemy2
			List<Tiro> tiros = player.getTiros();
			Iterator<Tiro> iteratorTiro = tiros.iterator();
			while (iteratorTiro.hasNext()) {
				Tiro tempTiro = iteratorTiro.next();
				formaTiro = tempTiro.getBounds();

				if (formaEnemy2.intersects(formaTiro)) {
					enemy.setVisivel(false);
					iteratorTiro.remove();
					score += 1;
				}
			}
		}

		// Salva o score do jogador quando o jogo termina e exibe o score atual
		if (!emJogo && !scoreSalvo) {
			salvarScoreJogador(nomeJogador, score);
			scoreSalvo = true;
			lerScoreJogador(nomeJogador);
		}
	}

	// Método para Salvar o Score
	private void salvarScoreJogador(String nomeJogador, int scoreTotal) {
		String nomeArquivo = nomeJogador + ".txt";

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
			writer.write(nomeJogador + "," + scoreTotal);
		} catch (IOException e) {
			System.out.println("Erro ao salvar o score do jogador: " + e.getMessage());
		}
	}

	// Método para ler o Score
	private int lerScoreJogador(String nomeJogador) {
		String nomeArquivo = nomeJogador + ".txt";

		try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
			String line;
			if ((line = reader.readLine()) != null) {
				String[] dados = line.split(",");
				if (dados.length == 2 && dados[0].equals(nomeJogador)) {
					return Integer.parseInt(dados[1]);
				}
			}
		} catch (IOException e) {
			System.out.println("Erro ao ler arquivo de score do jogador: " + e.getMessage());
		}

		return 0;
	}

	// Reiniciar Jogo
	public void reiniciarJogo() {
		emJogo = true;
		fimJogo = false;
		player.setVisivel(true);
		scoreSalvo = false;
		inicializaInimigos();
		inicializaStars();
		inicializaInimigos2();
		timer.restart();
		player.limparTiros();
		repaint();
	}

	private class TecladoAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (!emJogo) {
					reiniciarJogo();
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
		}
	}

}
