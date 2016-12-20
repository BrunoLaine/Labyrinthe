package modele.labyrinthe;

import java.awt.Dimension;
import java.util.ArrayList;

import outils.Position;


public class Labyrinthe  {

	private Cellule[][] plateau;
	private NoeudCellule racine;
	private NoeudCellule noeudCible;
	private Cellule celluleCible;
	private Dimension dimensionPlateau;
	private ArrayList<Cellule> cheminCibleRacine;

	//liste pour stocker les cellules déja présente dans l'arbre
	private ArrayList<Cellule> listeTmpArbre;

	public Labyrinthe(Dimension dim) {

		this.plateau = new Cellule[dim.height][dim.width];
		this.dimensionPlateau = dim;

		this.genererPlateau();
		this.genererLabyrinthe();
		this.noeudCible = null;
		this.cheminCibleRacine = new ArrayList<Cellule>();
		this.celluleCible = this.getCellule(this.dimensionPlateau.height-1, this.dimensionPlateau.width-1);
		listeTmpArbre = new ArrayList<Cellule>();

	}

	public  void rechercheNoeudCellule(Cellule cel, NoeudCellule noeudCourant) {

		if (noeudCourant.getCellule().equals(cel)) {
			this.noeudCible = noeudCourant;
			return;
		}

		for (int i=0; i<noeudCourant.getSize(); i++) {
			rechercheNoeudCellule(cel, noeudCourant.getFils(i));

		}

		//return null;

	}


	private void genererPlateau() {

		for (int i=0; i<this.dimensionPlateau.height; i++) {
			for (int j=0; j<this.dimensionPlateau.width; j++) {

				Cellule nouvelleCellule = new Cellule();
/*
				if (i==0) nouvelleCellule.ajouterDirection(Direction.NORD);
				if (j==0) nouvelleCellule.ajouterDirection(Direction.OUEST);
				if (j==this.dimensionPlateau.width-1) nouvelleCellule.ajouterDirection(Direction.EST);
				if (i==this.dimensionPlateau.height-1) nouvelleCellule.ajouterDirection(Direction.SUD);
*/
				nouvelleCellule.setPosition(new Position(i,j));
				this.plateau[i][j] = nouvelleCellule;
			}
		}
	}

	public void genererLabyrinthe() {

		for (int i=0; i<this.dimensionPlateau.height;i++) {
			
			for (int j=0; j<this.dimensionPlateau.width; j++) {
				
				Cellule celluleCourante = this.getCellule(i, j);
				celluleCourante.rendreInaccessible();
                      if (i==0) celluleCourante.ajouterDirection(Direction.NORD);
      				if (j==0) celluleCourante.ajouterDirection(Direction.OUEST);
      				if (j==this.dimensionPlateau.width-1) celluleCourante.ajouterDirection(Direction.EST);
      				if (i==this.dimensionPlateau.height-1) celluleCourante.ajouterDirection(Direction.SUD);
				
			}
			
		}
		
		
		ArrayList<Cellule> plateauTmp = new ArrayList<Cellule>();

		int indiceLigne = (int) (Math.random() * this.dimensionPlateau.height);
		int indiceColonne = (int) (Math.random() * this.dimensionPlateau.width);
		Position positionActuelle = new Position (indiceLigne, indiceColonne);
		ArrayList<Direction> directionsInaccessible;
		Cellule celluleCourante = null, nouvelleCellule;
		Direction nouvelleDirection = null;

		nouvelleCellule = plateau[positionActuelle.getLigne()][positionActuelle.getColonne()];

		while (plateauTmp.size() < (this.dimensionPlateau.height*this.dimensionPlateau.width)) {

			if (plateauTmp.isEmpty()) {

				directionsInaccessible = nouvelleCellule.getInaccessible();
				nouvelleDirection = directionsInaccessible.get((int) (Math.random()*directionsInaccessible.size()));
				nouvelleDirection = null;
				nouvelleCellule.setDirectionGenerationLabyrinthe(nouvelleDirection);
			}

			if (nouvelleDirection != null) {
				switch (nouvelleDirection) {

				case NORD:
					nouvelleCellule.ajouterDirection(Direction.SUD);
					break;

				case SUD:
					nouvelleCellule.ajouterDirection(Direction.NORD);
					break;

				case EST:
					nouvelleCellule.ajouterDirection(Direction.OUEST);
					break;

				case OUEST:
					nouvelleCellule.ajouterDirection(Direction.EST);
					break;

				}
			}


			plateauTmp.add(nouvelleCellule);
			celluleCourante = getNouvelleCellulePossible(plateauTmp);
			if (celluleCourante != null) {
				nouvelleDirection = celluleCourante.getDirectionGenerationLabyrinte();
				positionActuelle = celluleCourante.getPosition();
				positionActuelle = getNouvellePosition(positionActuelle, nouvelleDirection);
				nouvelleCellule = plateau[positionActuelle.getLigne()][positionActuelle.getColonne()];
			}

		}

		
		
		for (int i=0; i<this.dimensionPlateau.width; i++)
			this.plateau[0][i].retirerDirection(Direction.NORD);

		for (int i=0; i<this.dimensionPlateau.width; i++)
			this.plateau[this.dimensionPlateau.height-1][i].retirerDirection(Direction.SUD);

		for (int i=0; i<this.dimensionPlateau.height; i++)
			this.plateau[i][0].retirerDirection(Direction.OUEST);

		for (int i=0; i<this.dimensionPlateau.height; i++)
			this.plateau[i][this.dimensionPlateau.width-1].retirerDirection(Direction.EST);



		this.racine = new NoeudCellule(this.getCellule(0, 0));
		listeTmpArbre = new ArrayList<Cellule>();
		this.genererArbre(racine);
		
	}


	//Cette fonction ajoute les cellules accessible autour de la cellule présente dans le noeud passé en parametre
	public void genererArbre(NoeudCellule noeud) {

		//Condition d'arret: si toutes les cellules sont dans la listeTmpArbre, toute les cellules sont dans l'arbre !
		if (listeTmpArbre.size() == this.dimensionPlateau.width*this.dimensionPlateau.height) { return; }

		//On récupere la cellule dans le noeud passé en paramètre
		Cellule celluleCourante = noeud.getCellule();

		//On ajoute la nouvelle cellule a la liste des cellules ajoutées
		listeTmpArbre.add(celluleCourante);

		int i;
		//On récupère les directions aceessibles de la cellule courante
		ArrayList<Direction> directionsAccessibles = celluleCourante.getAccessible();
		Position nouvellePosition;
		Cellule nouvelleCellule;
		//Pour chaque direction inaccessible verifier si la nouvelle cellule visé par 
		//cette direction n'as pas déja été ajouté a l'arbre grace a listeTmpArbre 
		//si c'est le cas on ajoute la nouvelle cellule en tant que fils du noeud courant 
		//puis on effectue un appel recursif a genererArbre sur le nouveau noeud crée

		for (i=0; i<directionsAccessibles.size();i++) {

			nouvellePosition = this.getNouvellePosition(celluleCourante.getPosition(), directionsAccessibles.get(i));
			nouvelleCellule = this.getCellule(nouvellePosition.getLigne(), nouvellePosition.getColonne());

			if (!listeTmpArbre.contains(nouvelleCellule)) {
				genererArbre(noeud.ajouterFils(nouvelleCellule));
			} 
		}

	}

	//Retourne la nouvelle position selon la direction
	public Position getNouvellePosition(Position p, Direction direction) {

		int ligne = p.getLigne();
		int colonne = p.getColonne();

		switch (direction) {

		case NORD:
			--ligne;
			break;

		case SUD:
			++ligne;
			break;

		case EST:
			++colonne;
			break;

		case OUEST:
			--colonne;
			break;

		}

		if (ligne >= 0 && ligne < this.dimensionPlateau.height && colonne >=0 && colonne < this.dimensionPlateau.width) {
		return new Position(ligne, colonne);
		} else {
			return null;
		}

	}


	// Retourne une nouvelle cellule parmis les cellules repondant au critère suivant:
	// En suivant la direction associé, ces cellules 
	// conduisent toutes à une cellule non présente dans la liste de cellules passé en parametre
	// mais présente dans le plateau. La cellule retourné est choisie aleatoirement parmis toutes ces cellules.
	public Cellule getNouvelleCellulePossible(ArrayList<Cellule> cellulesLabyTmp) {


		ArrayList<Direction> directionsInaccessible;
		Cellule celluleCourante = null;
		Direction directionTmp;
		Position positionTmp , positionActuelle;
		Cellule celluleTmp;
		ArrayList<Direction> directionsPossible = new ArrayList<Direction>();
		ArrayList<Cellule> cellulesPossible = new ArrayList<Cellule>();

		for (int i=0; i<cellulesLabyTmp.size(); i++) {

			celluleCourante = cellulesLabyTmp.get(i);
			positionActuelle = celluleCourante.getPosition();
			directionsInaccessible = celluleCourante.getInaccessible();
			directionsPossible = new ArrayList<Direction>();


			for (int j=0; j<directionsInaccessible.size(); j++) {

				directionTmp = directionsInaccessible.get(j);
				positionTmp = getNouvellePosition(positionActuelle, directionTmp);
				//System.out.println(directionsInaccessible.toString()+"  "+directionTmp+" "+positionActuelle.getLigne()+" "+positionActuelle.getColonne()+" || "+positionTmp.getLigne()+" "+positionTmp.getColonne());  


				celluleTmp = plateau[positionTmp.getLigne()][positionTmp.getColonne()];
				// System.out.println(!plateauTmp.contains(celluleTmp)+"  "+positionActuelle.getLigne()+"  "+positionActuelle.getColonne()+"  "+positionTmp.getLigne()+"  "+positionTmp.getColonne());
				if (!cellulesLabyTmp.contains(celluleTmp)) {
					directionsPossible.add(directionTmp);
				}
			}

			if(!directionsPossible.isEmpty()) {

				int indiceAleatNouvelleDirection = (int) (Math.random()*directionsPossible.size());
				celluleCourante.setDirectionGenerationLabyrinthe(directionsPossible.get(indiceAleatNouvelleDirection));
				cellulesPossible.add(celluleCourante);
				//    		     System.out.println(directionsPossible.get(indiceAleatNouvelleDirection)+" "+celluleCourante.getInaccessible().toString()+" "+celluleCourante.getPosition().toString());

			}

		}

		if (!cellulesPossible.isEmpty()) {

			int indiceAleatNouvelleCellule = (int) (Math.random()*cellulesPossible.size());

			celluleCourante = cellulesPossible.get(indiceAleatNouvelleCellule);
			Direction nouvelleDirection = celluleCourante.getDirectionGenerationLabyrinte();
			celluleCourante.ajouterDirection(nouvelleDirection);
			//    	   System.out.println(indiceAleatNouvelleCellule+" Chosen "+nouvelleDirection+" "+celluleCourante.getInaccessible().toString()+" "+celluleCourante.getPosition().toString());

			return celluleCourante;

		}	 
		return null;



	}

	public boolean deplacer(ObjetDansCellule obj, Direction dir) {

		boolean estJoueur = (dir != null);
		Position positionActuelle, nouvellePosition;
		positionActuelle = obj.getPosition();

		Cellule celluleCourante, nouvelleCellule;

		celluleCourante = this.plateau[positionActuelle.getLigne()][positionActuelle.getColonne()];
		
		if (estJoueur) {
			
			nouvellePosition = getNouvellePosition(positionActuelle,dir);
			
		} else {

			dir = celluleCourante.getAccessible().get((int) (Math.random()*celluleCourante.getAccessible().size()));
			nouvellePosition = getNouvellePosition(positionActuelle, dir);

		}

		if(!celluleCourante.isAccessible(dir)) return false;

		nouvelleCellule = this.plateau[nouvellePosition.getLigne()][nouvellePosition.getColonne()];

		if (estJoueur) {
			racine = new NoeudCellule(nouvelleCellule);
			listeTmpArbre = new ArrayList<Cellule>();
			this.genererArbre(racine);
		}
		deplacer(obj, nouvellePosition);
		return true;

	}

	public void deplacer(ObjetDansCellule obj, Position pos) {
		this.plateau[obj.getPosition().getLigne()][obj.getPosition().getColonne()].retirerObjet(obj);
		this.plateau[pos.getLigne()][pos.getColonne()].ajouterObjet(obj);
		obj.setPosition(pos);

	}



	public void afficherChemin(ObjetDansCellule indicationChemin) {

		for (Cellule celluleCourante: this.cheminCibleRacine)
			celluleCourante.retirerObjet(indicationChemin);

		this.rechercheNoeudCellule(this.celluleCible,racine);
		this.cheminCibleRacine = this.noeudCible.getCheminRacine();

		for (Cellule celluleCourante: this.cheminCibleRacine)
			celluleCourante.ajouterObjet(indicationChemin);

	}
	
	public void effacerChemin(ObjetDansCellule indicationChemin) {
		
		for (Cellule celluleCourante: this.cheminCibleRacine)
			celluleCourante.retirerObjet(indicationChemin);
		
	}

	public void afficherLabyrinthe() {

		for (int i=0; i<this.dimensionPlateau.height; i++) {
			for (int j=0; j<this.dimensionPlateau.width; j++) {

				System.out.print("AC:"+plateau[i][j].getAccessible().toString()+" INAC"+plateau[i][j].getInaccessible().toString()+" "+i+"  "+j+" ");

			}
			System.out.print("\n");
		}

	}

	public Cellule[][] getPlateau() {
		return plateau;
	}


	public void setPlateau(Cellule[][] plateau) {
		this.plateau = plateau;
	}

	public Cellule getCellule(int ligne, int colonne) {

		return plateau[ligne][colonne];

	}

	public void setCellule(Cellule cellule) {

		plateau[cellule.getPosition().getLigne()][cellule.getPosition().getColonne()] = cellule;

	}

	public Dimension getDimensionPlateau() {

		return this.dimensionPlateau;

	}

}
