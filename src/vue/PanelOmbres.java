package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import modele.labyrinthe.Cellule;
import modele.labyrinthe.Direction;


@SuppressWarnings("serial")
public class PanelOmbres extends Component{
	private Cellule[][] cellules;
	private int largeurVue;

	private int xCoordinates[] = {0, 0, 0, 0};
	private int yCoordinates[] = {0, 0, 0, 0};
	private int largeurCellule;
	private int hauteurCellule;

	public PanelOmbres(Cellule[][] cellules, int largeurVue){
		this.setBounds(0,0,800,800);
		this.largeurVue = largeurVue;
		this.cellules = cellules;
		
		largeurCellule = this.getWidth()/largeurVue;
		hauteurCellule = this.getHeight()/largeurVue;

	}


	public void paint(Graphics g) {
		int i, j;		

		for(i=0; i<this.largeurVue; i++){
			for(j=0; j<this.largeurVue; j++){
				
				Cellule currentCellule = cellules[i][j];
				int posColonne = j;
				int posLigne = i;

				if(!currentCellule.estAccessible(Direction.SUD)){
					this.defPts(posColonne, posLigne+1, posColonne+1, posLigne+1, g);
				}
				
				if(!currentCellule.estAccessible(Direction.NORD)){
					this.defPts(posColonne, posLigne, posColonne+1, posLigne, g);
				}
				
				if(!currentCellule.estAccessible(Direction.EST)){
					this.defPts(posColonne+1, posLigne, posColonne+1, posLigne+1, g);
				}
				
				if(!currentCellule.estAccessible(Direction.OUEST)){
					this.defPts(posColonne, posLigne, posColonne, posLigne+1, g);
				}
			}
		}
	}
	
	private void defPts(int X1, int Y1, int X2, int Y2, Graphics g) {
		int xMilieu = this.getWidth()/2;
		int yMilieu = this.getHeight()/2;
		
		this.xCoordinates[0] = X1 * this.largeurCellule +1;
		this.yCoordinates[0] = Y1 * this.hauteurCellule +1;
		
		this.xCoordinates[1] = X2 * this.largeurCellule +1;
		this.yCoordinates[1] = Y2 * this.hauteurCellule +1;
		
		
		this.xCoordinates[2] = (xCoordinates[1] - xMilieu) * largeurVue * 2 + xMilieu;
		this.yCoordinates[2] = (yCoordinates[1] - yMilieu) * largeurVue * 2 + yMilieu;
		
		this.xCoordinates[3] = (xCoordinates[0] - xMilieu) * largeurVue * 2 + xMilieu;
		this.yCoordinates[3] = (yCoordinates[0] - yMilieu) * largeurVue * 2 + yMilieu;

		g.setColor(Color.darkGray);
		g.fillPolygon(xCoordinates, yCoordinates, 4);
	}


	public void actualiser(Cellule[][] cellules) {
		this.cellules = cellules;
		this.repaint();
	}


	public void actualiser() {
		this.repaint();
	}
}
