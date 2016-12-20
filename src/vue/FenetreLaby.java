package vue;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import modele.labyrinthe.Labyrinthe;

@SuppressWarnings("serial")
public class FenetreLaby extends JFrame {
	
	private int tailleFenetre = 400;
	
	public FenetreLaby(Labyrinthe laby){
		this.setTitle("Vue Labyrinthe");
		this.setSize(tailleFenetre, tailleFenetre);
		//this.setResizable(false);
		//this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setContentPane(new PanelCellules(laby.getPlateau()));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(900,0);
		
		this.setVisible(true); // affichage
	}
}