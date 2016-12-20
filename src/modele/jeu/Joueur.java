package modele.jeu;

import modele.labyrinthe.ObjetDansCellule;
import outils.Position;

public class Joueur extends ObjetDansCellule {

	private int vie;
	private int score;

	public Joueur(Position pos, String nomObjet, String sourceImage) {

		super(pos, nomObjet, sourceImage);

		this.vie = 0;
		this.score = 0;

	}

	public int getVie() {
		return vie;
	}

	public void setVie(int vie) {
		this.vie = vie;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
