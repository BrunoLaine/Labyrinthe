package modele.interfaces;

import java.util.ArrayList;
import modele.labyrinthe.Direction;
import modele.labyrinthe.ObjetDansCellule;

public interface ObservateurCellule {

	public void actualiserCellule(ArrayList<ObjetDansCellule> objetsDansCellule, ArrayList<Direction> directionsInaccessibles);

}
