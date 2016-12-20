package outils;

public class Position {
	
	private int ligne;
	private int colonne;
	
	public Position(int ligne, int colonne){
		this.ligne = ligne;
		this.colonne = colonne;
	}

	public int getLigne(){return ligne;}
	public int getColonne(){return colonne;}

	public void setLigne(int ligne){this.ligne = ligne;}
	public void setColonne(int colonne){this.colonne = colonne;}
	
	public boolean equals(Position position){return (this.ligne == position.getLigne() && this.colonne == position.getColonne());}
}
