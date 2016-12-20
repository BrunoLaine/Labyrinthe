package modele.jeu;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;

import outils.Position;
import modele.labyrinthe.Direction;
import modele.labyrinthe.Labyrinthe;
import modele.labyrinthe.ObjetDansCellule;
import vue.FenetreJoueur;
import vue.FenetreLaby;
import vue.FenetreMenu;

@SuppressWarnings("serial")
public class InstanceLabyrinthe extends JFrame implements Serializable, KeyListener  {
	private Labyrinthe laby;
	public Joueur player;
	private FenetreLaby vueLaby;
	private ArrayList<ObjetDansCellule> monstres;
	private ObjetDansCellule indicationChemin;
	private FenetreJoueur vueJoueur;
	private Position positionInitiale;
	private Position positionFinale;
    private boolean afficherChemin;
	
	public InstanceLabyrinthe(Dimension dim) {
		
		this.laby = new Labyrinthe(dim);
		// Position positionInitiale = new Position((int)
		// (Math.random()*dim.height),(int) (Math.random()*dim.width));
		positionInitiale = new Position(0, 0);
		positionFinale = new Position(dim.height - 1, dim.width - 1);

		this.monstres = new ArrayList<ObjetDansCellule>();
		this.player = new Joueur(positionInitiale, "joueur", "pacman.png");

		this.indicationChemin = new ObjetDansCellule("indicationChemin",
				"indicationChemin.png");

		afficherChemin = false;
		this.laby.getCellule(positionInitiale.getLigne(),
				positionInitiale.getColonne()).ajouterObjet(this.player);
		positionInitiale = new Position((int) (Math.random() * dim.height),
				(int) (Math.random() * dim.width));

		//int nbMonstres = dim.width*dim.height/20;
		int nbMonstres = 5;
		for (int i = 0; i < nbMonstres; i++) {
			ObjetDansCellule ghost = new ObjetDansCellule(positionInitiale,
					"ghost", "ghost.png");
			this.monstres.add(ghost);
			this.laby.getCellule(positionInitiale.getLigne(),
					positionInitiale.getColonne()).ajouterObjet(ghost);
			positionInitiale = new Position((int) (Math.random() * dim.height),
					(int) (Math.random() * dim.width));
		}

		// interface
		
		
		this.vueLaby = new FenetreLaby(this.laby);
		this.vueLaby.addKeyListener(this);
		this.vueJoueur = new FenetreJoueur(this.laby, this.player);
		this.vueJoueur.addKeyListener(this);

		this.setVisible(true);  

	}

	public Position getPositionJoueur() {

		return this.player.getPosition();

	}

	public void deplacer(ObjetDansCellule obj, Direction une_direction) {

		this.laby.deplacer(obj, une_direction);
		if (this.laby.getCellule(this.player.getPosition().getLigne(),
				this.player.getPosition().getColonne()).contientObjet(
				"ghost")) {

			this.laby.deplacer(player, new Position(0, 0));
		}
		
		if (this.afficherChemin) this.laby.afficherChemin(this.indicationChemin);

	}

	public ObjetDansCellule getObjet(String nomObjet) {

		for (ObjetDansCellule objetCourant : this.monstres)
			if (objetCourant.getNom() == nomObjet)
				return objetCourant;

		return null;

	}

	@Override
	public void keyPressed(KeyEvent event) {

		int source = event.getKeyCode();

		if (source == KeyEvent.VK_UP || source == KeyEvent.VK_Z) {
			this.deplacer(this.player, Direction.NORD);
		}

		else if (source == KeyEvent.VK_DOWN || source == KeyEvent.VK_S) {
			this.deplacer(this.player, Direction.SUD);
		}

		else if (source == KeyEvent.VK_RIGHT || source == KeyEvent.VK_D) {
			this.deplacer(this.player, Direction.EST);
		}

		else if (source == KeyEvent.VK_LEFT || source == KeyEvent.VK_Q) {
			this.deplacer(this.player, Direction.OUEST);
		}
		
		else if (source == KeyEvent.VK_H) this.afficherChemin();


	}

	public void genererLabyrinthe() {
		
		this.laby.genererLabyrinthe();
		
	}
	
	public void afficherChemin() {
		
		if (this.afficherChemin) this.laby.effacerChemin(this.indicationChemin);
		
		this.afficherChemin = !this.afficherChemin;
	}
	
	// Procédure qui automatise le déroulement du jeu tant que la partie n'est
	// pas finie
	public void go() {

		while (this.player.getPosition().getLigne() != positionFinale
				.getLigne()
				|| this.player.getPosition().getColonne() != positionFinale
						.getColonne()) {

			for (ObjetDansCellule monstre : this.monstres) {

				this.deplacer(monstre, null);
			this.vueJoueur.actualiserOmbres();

			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
		

		this.vueJoueur.hide();
		this.vueLaby.hide();
		System.out.println("Victoire !");
		
	    try
	    {
	        Clip clip = AudioSystem.getClip();
	        clip.open(AudioSystem.getAudioInputStream(new File("ressources/gagne.wav")));
	        clip.start();
	    }
	    catch (Exception exc)
	    {
	        exc.printStackTrace(System.out);
	    }
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
