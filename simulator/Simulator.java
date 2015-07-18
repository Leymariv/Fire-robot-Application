package simulator;

import exception.*;
import java.util.*;

public class Simulator {

	/**
	 * File des evenements
	 */
	private PriorityQueue<Event> filePrio;

	/**
	 * Date actualisee a chaque evenement qui passe
	 */
	private long dateCour;

	/**
	 * Constructeur par defaut
	 */
	public Simulator() {
		dateCour = 0;
		Comparator<Event> comparator = new ComparateurEvent();
		/*
		 * 10 est la taille initiale de la file de priorité, sachant que cette
		 * taille s'actualise automatiquement quand la taille max est atteinte
		 */
		filePrio = new PriorityQueue<Event>(10, comparator);
	}

	public long getCurrentDate() {
		return this.dateCour;
	}

	public long getNextDate(){
		return filePrio.peek().getDate();
	}
	
	/**
	 * Comparaison des evenements pour l'utilisation de la file de priorite
	 */
	public class ComparateurEvent implements Comparator<Event> {
		public int compare(Event event1, Event event2) {
			long difference = event1.getDate() - event2.getDate();

			if (difference < 0) {
				return -1;
			} else if (difference > 0) {
				return 1;
			}
			return 0;
		}
	}

	/**
	 * Methode pour rajouter un evenement
	 */
	public void addEvent(Event evenement) {
		if (evenement.getDate() >= dateCour) {
			this.filePrio.add(evenement);
		}
	}

	/**
	 * Execute l'evenement le plus prioritaire
	 */
	public Event nextStep() {
		/*
		 * Appel de poll retourne l'element de priorite maximal, et le supprime
		 * de la file
		 */
		Event temp = filePrio.poll();
		this.dateCour = temp.getDate();
		try {
			temp.execute(this); // on éxecute
		} catch (ExcReservoir e) {
			;
		}
		return temp;
	}

	/**
	 * Permet de savoir s'il reste des evenements a executer dans la liste
	 */
	public boolean hasNext() {
		return filePrio.size()>0;
	}

}