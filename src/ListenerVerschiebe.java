import javax.swing.JOptionPane;

import de.fhwgt.verschiebespiel.VerschiebeEvent;
import de.fhwgt.verschiebespiel.VerschiebeListener;
import de.fhwgt.verschiebespiel.logik;

public class ListenerVerschiebe implements VerschiebeListener
{
	/* Benötigten Member des VerschiebeListeners */
	private Hauptfenster hf;
	private boolean erzeugeNeuesSpiel;
	
	public ListenerVerschiebe(Hauptfenster fenster)
	{
		hf = fenster;
		/* Legt fest, dass Spiel neu gestartet werden kann */
		erzeugeNeuesSpiel = true;
	}
	/* Dieses Event wird ausgelöst, wenn ein gültiger Bewegung durchgeführt wurde */
	public void GridChanged(VerschiebeEvent e) 
	{
		logik spielelogik = (logik)e.getSource();
		String aktuelleNummern = spielelogik.toString();
		
		hf.aktualisiereSpielfeld(aktuelleNummern);
	}
	/* Wird ausgelöst, wenn Gameover oder Moveerror ausgelöst wurde */
	public void StatusChanged(VerschiebeEvent e) 
	{
		/* Löst einen beep-Ton aus wenn ein ungültiges Feld angeklickt wurde */
		if(e.getStatus() == Hauptfenster.MOVEERROR)
		{
			System.out.println((char)7);
		}
		/* Erzeugt einen Dialog zum Auswählen ob Spiel neu gestartet werden soll */
		if(e.getStatus() == Hauptfenster.GAMEOVER && erzeugeNeuesSpiel == true)
		{
			/* Damit der Dialog nur einmal erzeugt wird */
			erzeugeNeuesSpiel = false;
			/* Erzeugt einen Ja-Nein-Dialog und speichert ergebnis */
			int eingabe = JOptionPane.showConfirmDialog(null,
                    "Neues Spiel?",
                    "Hurra-- Gewonnen!!",
                    JOptionPane.YES_NO_OPTION);
			/* Falls "ja" geklickt wird */
			if(eingabe == 0)
			{
				hf.dispose();
				new Hauptfenster();
			}
			/* Falls "Nein" geklickt wird */
			else
			{
				hf.dispose();
				System.exit(1);
			}
		}
	}
}
