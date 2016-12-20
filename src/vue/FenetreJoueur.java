package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import modele.interfaces.ObservateurODC;
import modele.jeu.Joueur;
import modele.labyrinthe.Cellule;
import modele.labyrinthe.Labyrinthe;
import outils.Position;

@SuppressWarnings("serial")
public class FenetreJoueur extends JFrame implements ObservateurODC {

	private int distanceVue = 3;	//ne pas mettre 4 5 ou 6
	private int largeurVue = 2 * distanceVue + 1;
	private Cellule[][] cellules;
	private int tailleFenetre = 800;
	private Position pos;
	private Labyrinthe laby;
	private PanelJoueur panelJoueur;

	public FenetreJoueur(Labyrinthe laby, Joueur player) {
		this.pos = player.getPosition();
		this.laby = laby;
		this.cellules = new Cellule[largeurVue][largeurVue];

		this.setTitle("Vue Joueur");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		this.definirCellules();
		this.panelJoueur = new PanelJoueur(this.cellules, this.largeurVue,
				this.getLayeredPane());
		player.ajouterObs(this);       
		Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        this.panelJoueur.setPreferredSize(new Dimension(tailleFenetre,tailleFenetre));
        contentPane.add(this.panelJoueur);
        pack();
		this.setLocationRelativeTo(null);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(100,0);
		
		this.setVisible(true); // affichage

	}

	public void actualiserODC(Position pos) {
		if(this.pos!= pos){
		this.pos = pos;
		this.definirCellules();
		this.panelJoueur.actualiser(this.cellules);
		}
	}
	
	public void actualiserOmbres(){
		this.panelJoueur.actualiserOmbres();
	}

	public void definirCellules() {

		int i, j;

		for (i = 0; i < largeurVue; i++) {
			for (j = 0; j < largeurVue; j++) {
				try { // cas normal

					this.cellules[i][j] = this.laby.getCellule(
							this.pos.getLigne() - distanceVue + i,
							this.pos.getColonne() - distanceVue + j);

				} catch (Exception e) { // cas ou �a sort
					this.cellules[i][j] = new Cellule();
				}
			}
		}
	}
	
	
}