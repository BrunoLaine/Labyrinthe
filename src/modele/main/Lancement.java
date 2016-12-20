package modele.main;
import java.awt.Dimension;

import vue.FenetreMenu;

import modele.jeu.InstanceLabyrinthe;


public class Lancement {

	private InstanceLabyrinthe instanceLabyrinthe;
	private FenetreMenu menu;

	public Lancement() {
		

		this.instanceLabyrinthe = new InstanceLabyrinthe(new Dimension(20, 20));
        this.menu = new FenetreMenu(this.instanceLabyrinthe);
        this.instanceLabyrinthe.go();
		 
	}


}