package vue;

import java.awt.GridLayout;
import javax.swing.JPanel;

import outils.Position;
import modele.labyrinthe.Cellule;

@SuppressWarnings("serial")
public class PanelCellules extends JPanel {

	private PanelCellule[][] panelsCellule;
	private int hauteur;
	private int largeur;

	public PanelCellules(Cellule[][] cellules) {
		//this.setBounds(0,0,800,800);
		this.largeur = cellules.length;
		this.hauteur = cellules[0].length;
		this.panelsCellule = new PanelCellule[largeur][hauteur];
		this.setLayout(new GridLayout(largeur, hauteur));

		int i, j;
		
		for (i = 0; i < largeur; i++) {
			for (j = 0; j < hauteur; j++) {
				this.panelsCellule[i][j] = new PanelCellule();
				this.add(panelsCellule[i][j]);
				
				if (!cellules[i][j].getPosition().equals(new Position(-1, -1))) {
					cellules[i][j].ajouterObservateur(panelsCellule[i][j]);
					cellules[i][j].notifierObs();
				}
			}
		}
	}

	public void actualiser(Cellule[][] cellules) {
		int i, j;
		
		for (i = 0; i < largeur; i++) {
			for (j = 0; j < hauteur; j++) {
				
				if (!cellules[i][j].getPosition().equals(new Position(-1, -1))) {
					while(cellules[i][j].getNombreObservateurs()>1) cellules[i][j].retirerDernierObs();
					cellules[i][j].ajouterObservateur(panelsCellule[i][j]);
					cellules[i][j].notifierObs();
				}
			}
		}
	}
}
