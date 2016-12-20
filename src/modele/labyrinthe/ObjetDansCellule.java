package modele.labyrinthe;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import modele.interfaces.ObservateurODC;
import outils.Position;

public class ObjetDansCellule {
	private String nom;
	private Position position;
	private Image image;
	private ArrayList<ObservateurODC> observateurs;

	//Constructeur
	public ObjetDansCellule(Position positionObjet, String nomObjet, String sourceImage) {

		this.position = positionObjet;
		this.nom = nomObjet;
		this.observateurs = new ArrayList<ObservateurODC>();
		
        this.setImage(sourceImage);
		
	}
	
	public ObjetDansCellule(String nomObjet,  String sourceImage) {

		this.nom = nomObjet;
		this.setImage(sourceImage);
		this.observateurs = new ArrayList<ObservateurODC>();
	}
	
	public void setImage(String sourceImage) {
		
		try {
			this.image = ImageIO.read(new File("ressources/"+sourceImage));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public Image getImage() {
		
		return this.image;
		
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public void setPosition(Position pos) {

		this.position = pos;
		this.notifierObs();
		
		
	}
	
	public Position getPosition() {
		
		return this.position;
		
	}


	public void ajouterObs(ObservateurODC obs) {
	
		this.observateurs.add(obs);

	}

	public void notifierObs() {

		for (int i = 0; i < this.observateurs.size(); i++) {

			this.observateurs.get(i).actualiserODC(this.getPosition());

		}

	}
	

}
