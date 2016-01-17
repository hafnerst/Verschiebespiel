import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.fhwgt.verschiebespiel.logik;

public class Hauptfenster extends JFrame
{
	/* Konstanten der Spielzustände anlegen */
	public static final int GAMEOVER = 1;
	public static final int GAMERUNNING = 0;
	public static final int MOVEERROR = -1;
	public static final String KORREKTEFOLGE = "12345678-";
	
	/* Member der Klasse Hauptfenster */
	private static final long serialVersionUID = 1L;
	private JPanel flaeche;
	private logik spielelogik;
	private Integer[] nummern;
	private JButton[] buttons;
	private Font myfont;
	private ListenerVerschiebe verschiebeListener;
	
	public Hauptfenster()
	{
		/* Operation für Close Button */
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		/* Titel setzen */
		this.setTitle("Verschiebespiel");
		/* Instanz von der Logik des Spiels erzeugen */
		spielelogik = new logik();
		/* Erzeugung eines Verschiebelisteners */
		verschiebeListener = new ListenerVerschiebe(this);
		/* Der Logik ein VerschiebeListener übergeben */
		spielelogik.addVerschiebeListener(verschiebeListener);
		
		/* Instanz von JPanel erzeugen */
		flaeche = new JPanel();
		/* Im Panel das Gridlayout erzeugen */
		flaeche.setLayout(new GridLayout(3,3,0,0));
		/* Anlegen der Font */
		myfont = new Font("Arial",Font.BOLD,16);
		
		/* Aufruf der Methode um das Spielfeld zu zeichnen */
		zeichneSpielfeld();
		
		/* Hinzufügen des Panels zum Hauptfenster */
		this.add(flaeche);
		
		/* Größe optimal und automatisch anhand der prefferedsize Elemente anpassen */
		this.pack();
		
		/* Panel sichtbar machen */
		flaeche.setVisible(true);
		
		/* Fenstergröße kann nicht mehr verändert werden */
		this.setResizable(false);
		/* Positioniert das Hauptfenster in der Mitte des Bildschirms */
		this.setLocationRelativeTo(null);
		/* Macht das Hauptfenster sichtbar */
		this.setVisible(true);
	}
	
	/* Erstellt das Spielfeld zu Beginn mit Hilfe eines Integer-Arrays */
	private void zeichneSpielfeld()
	{
		/* Abfragen der zufälligen Starnummern */
		nummern = spielelogik.getNummern();
				
		/* Speicherreservierung für 8 Button */
		buttons = new JButton[8];
		
		/* Schleife zum Erzeugen der Buttons und zum Befüllen des Panels */
		for(int i=0; i<(nummern.length - 1);i++)
		{
			erstelleButton(i, nummern[i]);
			flaeche.add(buttons[i]);
		}
	}
	/* Erstellt einen neuen Button, befüllt diesen und fügt Actionlistener hinzu */
	private void erstelleButton(int index, int nummer)
	{
		buttons[index] = new JButton();
		buttons[index].setText(Integer.toString(nummer));
		buttons[index].setPreferredSize(new Dimension(70,70));
		buttons[index].setFont(myfont);
		buttons[index].addActionListener(new ListenerBewegung());
		buttons[index].setActionCommand(Integer.toString(nummer));
	}
	
	/* Aktualisiert das Spielfeld bei Bewegung eines Buttons */
	public void aktualisiereSpielfeld(String aktuelleNummern)
	{
		/* Variable count dient für die Indezes des Button-Arrays */
		int count = 0;
		/* Säubert das Spielfeld */
		flaeche.removeAll();
		/* Befüllt mit Hilfe des übergegeben String das Spielfeld mit 8 Button und einem Label */
		for(int i=0; i< aktuelleNummern.length();i++)
		{
			if(aktuelleNummern.charAt(i) == '-')
			{
				JLabel tmpLabel = new JLabel();
				flaeche.add(tmpLabel);
			}
			else
			{
				erstelleButton(count, (int) (aktuelleNummern.charAt(i) - '0'));
				flaeche.add(buttons[count]);
				count++;
			}
		}
		this.pack();
		/* Löst bei korrekter Lösung ein Gameover Event aus */
		if(aktuelleNummern.equalsIgnoreCase(KORREKTEFOLGE))
		{
			spielelogik.SetSpielEnde();
		}
	}
	
	public logik getSpielelogik()
	{
		return spielelogik;
	}
	
	public static void main(String[] args) 
	{
		new Hauptfenster();
	}
}
