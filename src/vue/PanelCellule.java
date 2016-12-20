package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import modele.interfaces.ObservateurCellule;
import modele.labyrinthe.Direction;
import modele.labyrinthe.ObjetDansCellule;

@SuppressWarnings("serial")
public class PanelCellule extends Component implements ObservateurCellule {
	private BufferedImage img = null;
	private ArrayList<ObjetDansCellule> objetsDansCellule;
	private ArrayList<Direction> directionsInaccessible;

	public PanelCellule() {
		this.directionsInaccessible = null;
		this.objetsDansCellule = null;

		try {
			img = ImageIO.read(new File("ressources\\pierre.jpg"));
		} catch (IOException e) {

			try {
				img = ImageIO.read(new File("ressources/pierre.jpg"));

			} catch (IOException ex) {

				System.out.println("Erreur au chargement de l'image");
			}

		}

	}

	public void paint(Graphics g) {

		if (this.directionsInaccessible != null && this.objetsDansCellule != null) {


			try {
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
			} catch (Exception e) {
				System.out.println("Erreur au dessin de l'image");
			}

			g.setColor(Color.black);
			//g.setColor(Color.blue);
			if (this.directionsInaccessible.contains(Direction.NORD))
				g.fillRect(0, 0, this.getWidth() , this.getHeight() / 10);

			//g.setColor(Color.green);
			if (this.directionsInaccessible.contains(Direction.SUD))
				g.fillRect(0, 9 * this.getHeight() / 10, this.getWidth(),
						this.getHeight() / 10 + 1);

			//g.setColor(Color.yellow);
			if (this.directionsInaccessible.contains(Direction.OUEST))
				g.fillRect(0, 0, this.getWidth() / 10, this.getHeight());

			//g.setColor(Color.black);
			if (this.directionsInaccessible.contains(Direction.EST))
				g.fillRect(9 * this.getWidth() / 10, 0,
						this.getWidth() / 10 + 1, this.getHeight());

			ObjetDansCellule objetDansCellule;
			for (int i = 0; i < this.objetsDansCellule.size(); i++) {
				objetDansCellule = this.objetsDansCellule.get(i);

				Image img = objetDansCellule.getImage();

				g.drawImage(img,
						(int) ((i % 2) * ((this.getWidth()*0.9) / objetsDansCellule.size()) + this.getWidth()*0.1), 
						(int) ((Math.ceil(i / 2)) * ((this.getHeight()*0.9) / objetsDansCellule.size()) + this.getWidth()*0.1),
						(int) ((this.getWidth()*0.8) / (objetsDansCellule.size())),
						(int) ((this.getHeight()*0.8) / (objetsDansCellule.size())), 
						this);

			}
		}
	}

	@Override
	public void actualiserCellule(
			ArrayList<ObjetDansCellule> objetsDansCellule,
			ArrayList<Direction> directionsInaccessible) {
		
		this.objetsDansCellule = objetsDansCellule;
		this.directionsInaccessible = directionsInaccessible;
		this.repaint();
	}
}
