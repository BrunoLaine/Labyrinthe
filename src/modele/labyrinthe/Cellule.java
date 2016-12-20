package modele.labyrinthe;

import java.util.ArrayList;

import modele.interfaces.ObservateurCellule;
import outils.Position;

public class Cellule {

	// Liste contenant les directions
	private ArrayList<Direction> directionsAccessible;
	private ArrayList<Direction> directionsInaccessible;
	private ArrayList<ObservateurCellule> observateurs;
	private ArrayList<ObjetDansCellule> objetsDansCellule;
	private Direction directionGenerationLabyrinthe;
	private Position position;

	public Cellule() {

		this.observateurs = new ArrayList<ObservateurCellule>();
		this.objetsDansCellule = new ArrayList<ObjetDansCellule>();
		this.directionsAccessible = new ArrayList<Direction>();
		this.directionsInaccessible = new ArrayList<Direction>();
		for (int i = 0; i < Direction.values().length; i++)
			this.directionsInaccessible.add(Direction.values()[i]);
		this.position = new Position(-1, -1);

	}

	public void ajouterDirection(Direction direction) {

		if (!this.directionsAccessible.contains(direction))
			this.directionsAccessible.add(direction);
		if (this.directionsInaccessible.contains(direction))
			this.directionsInaccessible.remove(direction);
		this.notifierObs();

	}

	public void retirerDirection(Direction direction) {

		if (this.directionsAccessible.contains(direction))
			this.directionsAccessible.remove(direction);
		if (!this.directionsInaccessible.contains(direction))
			this.directionsInaccessible.add(direction);
		this.notifierObs();

	}

	public void setDirectionGenerationLabyrinthe(Direction direction) {

		this.directionGenerationLabyrinthe = direction;

	}

	public boolean estAccessible(Direction direction) {

		return this.directionsAccessible.contains(direction);

	}

	public ArrayList<Direction> getInaccessible() {

		return this.directionsInaccessible;

	}

	public ArrayList<Direction> getAccessible() {

		return this.directionsAccessible;

	}

	public boolean isAccessible(Direction direction) {

		return this.directionsAccessible.contains(direction);

	}

	public String toString() {

		String toString = "";

		for (int i = 0; i < this.directionsInaccessible.size(); i++) {

			toString += this.directionsInaccessible.get(i).name() + "-";

		}

		return toString;

	}

	public void setPosition(Position p) {

		this.position = p;

	}

	public Direction getDirectionGenerationLabyrinte() {

		return this.directionGenerationLabyrinthe;

	}

	public Position getPosition() {

		return this.position;

	}

	public void ajouterObjet(ObjetDansCellule objet) {

		this.objetsDansCellule.add(objet);

		this.notifierObs();

	}

	public ObjetDansCellule retirerObjet(String nom) {

		for (int i = 0; i < objetsDansCellule.size(); i++) {
			if (objetsDansCellule.get(i).getNom() == nom) {
				ObjetDansCellule objTmp = this.objetsDansCellule.remove(i);
				this.notifierObs();
				return objTmp;
			}
		}

		return null;
	}

	public Boolean retirerObjet(ObjetDansCellule obj) {

		Boolean statut = this.objetsDansCellule.remove(obj);

		this.notifierObs();

		return statut;

	}
	
	public int getNombreObjets(){
		return this.objetsDansCellule.size();
	}

	public void viderCellule() {

		this.objetsDansCellule = new ArrayList<ObjetDansCellule>();

		this.notifierObs();

	}

	public void ajouterObservateur(ObservateurCellule obs) {

		this.observateurs.add(obs);

	}

	public void notifierObs() {

		for (int i = 0; i < this.observateurs.size(); i++) {
			this.observateurs.get(i).actualiserCellule(this.objetsDansCellule,
					this.directionsInaccessible);
		}

	}

	public void retirerDernierObs() {
		
		this.observateurs.remove(this.observateurs.size()-1);
		
	}
	
	public int getNombreObservateurs(){
		
		return this.observateurs.size();
		
	}

	public boolean contientObjet(ObjetDansCellule obj) {
		return this.objetsDansCellule.contains(obj);
	}
	
	public boolean contientObjet(String nom) {
		for (ObjetDansCellule obj: this.objetsDansCellule)
		if (obj.getNom()==nom)
			return true;
		
		return false;
	}
	
	public void rendreInaccessible() {
		
		this.retirerDirection(Direction.EST);
		this.retirerDirection(Direction.NORD);
		this.retirerDirection(Direction.OUEST);
		this.retirerDirection(Direction.SUD);
		
	}
	

}
