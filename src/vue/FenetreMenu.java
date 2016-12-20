package vue;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import modele.jeu.InstanceLabyrinthe;

public class FenetreMenu extends JFrame implements ActionListener {
	
	private InstanceLabyrinthe instance;
	private JButton boutonGenerer;
	private JButton boutonAfficherChemin;
	
	public FenetreMenu(InstanceLabyrinthe instance) {
		
		this.instance = instance;
		
		this.setTitle("Menu");
		this.setSize(100, 800);
		//this.setResizable(false);
		//this.setLocationRelativeTo(null);
		
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          
		FlowLayout currentLayout = new FlowLayout();
		
		this.setLayout(currentLayout);
	    boutonGenerer = new JButton("Generer");
	    boutonGenerer.addActionListener(this);		
		this.add(boutonGenerer);
		boutonAfficherChemin = new JButton("Chemin !");
		boutonAfficherChemin.addActionListener(this);		
		this.add(boutonAfficherChemin);
	    
		
		/*this.add(new JButton("Button 2"));
	    this.add(new JButton("Button 3"));
		*/
		this.setVisible(true);
	}
	
	   public void actionPerformed(ActionEvent e) {
		   
		   
	        if(e.getSource() == boutonGenerer) {
	        	this.instance.genererLabyrinthe();
	        } else if (e.getSource() == this.boutonAfficherChemin) {
	        	this.instance.afficherChemin();
	        }
	    }  

}
