package modele.labyrinthe;

import java.util.ArrayList;

public class NoeudCellule {

	private Cellule cellule;
	private ArrayList<NoeudCellule> listeFils;
	private NoeudCellule pere;

	public NoeudCellule(Cellule cellule, NoeudCellule pere) {

		this.cellule = cellule;
		this.pere = pere;
		this.listeFils = new ArrayList<NoeudCellule>();

	}

	public NoeudCellule(Cellule cellule) {

		this.cellule = cellule;
		this.pere = null;
		this.listeFils = new ArrayList<NoeudCellule>();

	}

	public NoeudCellule ajouterFils(Cellule c) {

		NoeudCellule n = new NoeudCellule(c, this);

		this.listeFils.add(n);

		return n;

	}

	public NoeudCellule getFils(int indiceFils) {
		return this.listeFils.get(indiceFils);
	}

	public NoeudCellule getPere() {

		return this.pere;

	}

	public ArrayList<Cellule> getCheminRacine() {

		ArrayList<Cellule> cheminRacine = new ArrayList<Cellule>();
		cheminRacine.add(this.getCellule());
		NoeudCellule noeudTmp;

		noeudTmp = this.getPere();
		while (noeudTmp != null && noeudTmp.getPere() != null) {
			cheminRacine.add(noeudTmp.getCellule());
			noeudTmp = noeudTmp.getPere();
		}

		return cheminRacine;

	}

	public String toString() {

		String str = " ";
		str += "( " + this.cellule;

		if (this.listeFils != null) {
			for (int i = 0; i < listeFils.size(); i++) {
				str += this.listeFils.get(i).toString();
			}
		}

		str += ")";

		return str;

	}

	public int getSize() {

		return this.listeFils.size();

	}

	public Cellule getCellule() {

		return this.cellule;

	}

}
