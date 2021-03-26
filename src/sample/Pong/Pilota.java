package sample.Pong;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Pilota {

	public int x, y, width = 25, height = 25;
	public int motionX, motionY;
	public Random random;
	private Pong pong;
	public int amountOfHits;

	public Pilota(Pong pong) {

		this.pong = pong;

		this.random = new Random();

		spawn();
	}

	public void update(Jugador jugador1, Jugador jugador2) {

		int speed = 5;
		this.x += motionX * speed;
		this.y += motionY * speed;

		if (this.y + height - motionY > pong.height || this.y + motionY < 0) {
			if (this.motionY < 0) {
				this.y = 0;
				this.motionY = random.nextInt(4);

				if (motionY == 0) {
					motionY = 1;
				}
			}

			else {
				this.motionY = -random.nextInt(4);
				this.y = pong.height - height;

				if (motionY == 0) {
					motionY = -1;
				}
			}
		}

		if (checkCollision(jugador1) == 1) {
			this.motionX = 1 + (amountOfHits / 5);
			this.motionY = -2 + random.nextInt(4);

			if (motionY == 0) {
				motionY = 1;
			}
			amountOfHits++;
		}

		else if (checkCollision(jugador2) == 1) {
			this.motionX = -1 - (amountOfHits / 5);
			this.motionY = -2 + random.nextInt(4);

			if (motionY == 0) {
				motionY = 1;
			}
			amountOfHits++;
		}

		if (checkCollision(jugador1) == 2) {
			jugador2.punts++;
			spawn();
		}

		else if (checkCollision(jugador2) == 2) {
			jugador1.punts++;
			spawn();
		}
	}

	public void spawn() {

		this.amountOfHits = 0;
		this.x = pong.width / 2 - this.width / 2;
		this.y = pong.height / 2 - this.height / 2;

		this.motionY = -2 + random.nextInt(4);

		if (motionY == 0) {
			motionY = 1;
		}

		if (random.nextBoolean()) {
			motionX = 1;
		}
		else {
			motionX = -1;
		}
	}

	public int checkCollision(Jugador jugador) {
		if (this.x < jugador.x + jugador.width && this.x + width > jugador.x && this.y < jugador.y + jugador.height && this.y + height > jugador.y) {
			return 1; //bounce
		}
		else if ((jugador.x > x && jugador.paddleNumber == 1) || (jugador.x < x - width && jugador.paddleNumber == 2)) {
			return 2; //score
		}

		return 0; //nothing
	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(x, y, width, height);
	}
}