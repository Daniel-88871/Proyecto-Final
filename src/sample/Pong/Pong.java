package sample.Pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class
Pong implements ActionListener, KeyListener {

	public static Pong pong;
	public int width = 700, height = 700;
	public Renderer renderer;
	public Jugador jugador1;
	public Jugador jugador2;
	public Pilota pilota;
	public boolean bot = false, Dificultat;
	public boolean w, s, up, down;
	public int gameStatus = 0, Puntatge = 7, guanyador;
	public int Dificultatbot, botMoves, botCooldown = 0;
	public Random random;
	public JFrame jframe;

	public Pong() {
		Timer timer = new Timer(20, this);
		random = new Random();
		jframe = new JFrame("Pong");
		renderer = new Renderer();

		jframe.setSize(width + 15, height + 35);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(renderer);
		jframe.addKeyListener(this);

		timer.start();
	}

	public void start() {
		gameStatus = 2;
		jugador1 = new Jugador(this, 1);
		jugador2 = new Jugador(this, 2);
		pilota = new Pilota(this);
	}

	public void update() {
		if (jugador1.punts >= Puntatge) {
			guanyador = 1;
			gameStatus = 3;
		}

		if (jugador2.punts >= Puntatge) {
			gameStatus = 3;
			guanyador = 2;
		}

		if (w) {
			jugador1.move(true);
		}

		if (s) {
			jugador1.move(false);
		}

		if (!bot) {
			if (up) {
				jugador2.move(true);
			}

			if (down) {
				jugador2.move(false);
			}
		}
		else {
			if (botCooldown > 0) {
				botCooldown--;

				if (botCooldown == 0) {
					botMoves = 0;
				}
			}

			if (botMoves < 10) {
				if (jugador2.y + jugador2.height / 2 < pilota.y) {
					jugador2.move(false);
					botMoves++;
				}

				if (jugador2.y + jugador2.height / 2 > pilota.y) {
					jugador2.move(true);
					botMoves++;
				}

				if (Dificultatbot == 0) {
					botCooldown = 20;
				}

				if (Dificultatbot == 1) {
					botCooldown = 15;
				}

				if (Dificultatbot == 2) {
					botCooldown = 10;
				}
			}
		}

		pilota.update(jugador1, jugador2);
	}

	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (gameStatus == 0) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 50));

			g.drawString("PONG", width / 2 - 75, 50);

			if (!Dificultat) {
				g.setFont(new Font("Arial", 1, 30));

				g.drawString("Presiona espai per jugar", width / 2 - 150, height / 2 - 25);
				g.drawString("Presiona shift per jugar amb el bot", width / 2 - 200, height / 2 + 25);
				g.drawString("<< Punts per guanyar: " + Puntatge + " >>", width / 2 - 150, height / 2 + 75);
			}
		}

		if (Dificultat) {
			String string = Dificultatbot == 0 ? "Facil" : (Dificultatbot == 1 ? "Mitja" : "Dificil");

			g.setFont(new Font("Arial", 1, 30));

			g.drawString("<< Dificultat del bot: " + string + " >>", width / 2 - 180, height / 2 - 25);
			g.drawString("Presiona espai per jugar", width / 2 - 150, height / 2 + 25);
		}

		if (gameStatus == 1) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("PAUSA", width / 2 - 103, height / 2 - 25);
		}

		if (gameStatus == 1 || gameStatus == 2) {
			g.setColor(Color.WHITE);

			g.setStroke(new BasicStroke(5f));

			g.drawLine(width / 2, 0, width / 2, height);

			g.setStroke(new BasicStroke(2f));

			g.drawOval(width / 2 - 150, height / 2 - 150, 300, 300);

			g.setFont(new Font("Arial", 1, 50));

			g.drawString(String.valueOf(jugador1.punts), width / 2 - 90, 50);
			g.drawString(String.valueOf(jugador2.punts), width / 2 + 65, 50);

			jugador1.render(g);
			jugador2.render(g);
			pilota.render(g);
		}

		if (gameStatus == 3) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 50));

			g.drawString("PONG", width / 2 - 75, 50);

			if (bot && guanyador == 2) {
				g.drawString("Guanya el bot!", width / 2 - 170, 200);
			}
			else {
				g.drawString("Guanya el " + guanyador + "!", width / 2 - 165, 200);
			}

			g.setFont(new Font("Arial", 1, 30));

			g.drawString("Presiona espai per jugar de nou", width / 2 - 185, height / 2 - 25);
			g.drawString("Presiona esc per anar al menú", width / 2 - 140, height / 2 + 25);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (gameStatus == 2) {
			update();
		}

		renderer.repaint();
	}

	public static void main(String[] args)
	{
		pong = new Pong();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W) {
			w = true;
		}
		else if (id == KeyEvent.VK_S) {
			s = true;
		}
		else if (id == KeyEvent.VK_UP) {
			up = true;
		}
		else if (id == KeyEvent.VK_DOWN) {
			down = true;
		}
		else if (id == KeyEvent.VK_RIGHT) {

			if (Dificultat) {
				if (Dificultatbot < 2) {
					Dificultatbot++;
				}
				else {
					Dificultatbot = 0;
				}
			}
			else if (gameStatus == 0) {
				Puntatge++;
			}
		}
		else if (id == KeyEvent.VK_LEFT) {
			if (Dificultat) {
				if (Dificultatbot > 0) {
					Dificultatbot--;
				}
				else {
					Dificultatbot = 2;
				}
			}
			else if (gameStatus == 0 && Puntatge > 1) {
				Puntatge--;
			}
		}
		else if (id == KeyEvent.VK_ESCAPE && (gameStatus == 2 || gameStatus == 3)) {
			gameStatus = 0;
		}
		else if (id == KeyEvent.VK_SHIFT && gameStatus == 0) {
			bot = true;
			Dificultat = true;
		}
		else if (id == KeyEvent.VK_SPACE) {
			if (gameStatus == 0 || gameStatus == 3) {
				if (!Dificultat) {
					bot = false;
				}
				else {
					Dificultat = false;
				}
				start();
			}
			else if (gameStatus == 1) {
				gameStatus = 2;
			}
			else if (gameStatus == 2) {
				gameStatus = 1;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W) {
			w = false;
		}
		else if (id == KeyEvent.VK_S) {
			s = false;
		}
		else if (id == KeyEvent.VK_UP) {
			up = false;
		}
		else if (id == KeyEvent.VK_DOWN) {
			down = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}