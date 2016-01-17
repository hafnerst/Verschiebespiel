import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import de.fhwgt.verschiebespiel.logik;

public class ListenerBewegung implements ActionListener
{
	/* Wird vom ausgewählten Button ausgelöst */
	public void actionPerformed(ActionEvent e) 
	{
		/* Beschafft sich anhand des Events den auslösenden Button */
		JButton tmpButton = (JButton) e.getSource();
		String beschriftung = tmpButton.getActionCommand();
		/* Beschaffung des Hauptfensters und der Spielelogik */
		Hauptfenster hf = (Hauptfenster) tmpButton.getTopLevelAncestor();
		logik spielelogik = hf.getSpielelogik();
		/* Der Spielelogik mitteilen, welcher Button bewegt werden soll */
		spielelogik.move(Integer.parseInt(beschriftung));
	}	
}