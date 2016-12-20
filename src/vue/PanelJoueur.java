package vue;

import java.awt.GridLayout;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import modele.labyrinthe.Cellule;

@SuppressWarnings("serial")
public class PanelJoueur extends JPanel {
	private JLayeredPane layeredPane;
	private PanelOmbres panelOmbres;
	private int largeurVue;
	private PanelCellules panelCellules;

	public PanelJoueur(Cellule[][] cellules, int largeurVue,
			JLayeredPane layeredPane) {
		
		this.layeredPane = layeredPane;
		this.largeurVue = largeurVue;
		
		this.setLayout(new GridLayout(1, 1));
		this.panelCellules = new PanelCellules(cellules);
		this.add(this.panelCellules);	

		this.panelOmbres = new PanelOmbres(cellules, this.largeurVue);
		this.layeredPane.add(this.panelOmbres);
		
	}

	public void actualiser(Cellule[][] cellules) {

		this.panelCellules.actualiser(cellules);
	
		this.layeredPane.remove(this.panelOmbres);	
		this.panelOmbres = new PanelOmbres(cellules, this.largeurVue);
		this.layeredPane.add(this.panelOmbres);
	}

	public void actualiserOmbres() {
		this.panelOmbres.actualiser();
		
	}
}