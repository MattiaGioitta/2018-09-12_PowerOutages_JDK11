package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;
import it.polito.tdp.poweroutages.model.Event.EventType;

public class Simulator {
	
	//output 
	private List<Nerc> nercBonus;
	private int numCatastrofi;
	
	//stato del mondo
	private List<Interruzione> interruzioni;
	private PowerOutagesDAO dao;
	private int k;
	private Map<Nerc,Boolean> donatori;
	private Graph<Nerc,DefaultWeightedEdge> graph;
	private Map<Integer, Nerc> idMap;
	
	//coda degli eventi
	private PriorityQueue<Event> queue;
	
	public void init(Map<Integer,Nerc> idMap,Graph<Nerc,DefaultWeightedEdge> graph, int k) {
		this.dao = new PowerOutagesDAO();
		this.k = k;
		this.nercBonus = new ArrayList<>();
		this.numCatastrofi = 0;
		this.idMap = idMap;
		this.donatori = new HashMap<>();
		this.graph = graph;
		this.queue = new PriorityQueue<>();

		this.interruzioni = this.dao.getInterruzioni(this.idMap);
		for(Interruzione i : this.interruzioni) {
			this.queue.add(new Event(i.getTimeInizio(),i,EventType.INIZIO_INTERRUZIONE));
			this.queue.add(new Event(i.getTimeFine(),i,EventType.FINE_INTERRUZIONE));
		}
		
		for(Nerc n : this.graph.vertexSet()) {
			this.donatori.put(n, false);
		}
		
		
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		switch(e.getType()) {
		case INIZIO_INTERRUZIONE:
			Nerc donatore = cercaDonatore(e);
			if(donatore == null) {
				this.numCatastrofi++;
			}
			else if(this.donatori.get(donatore)==false) {
				this.donatori.replace(donatore, true);
				e.getN().getN().setUltimoDonatore(donatore);
				
			}
				
			else {
				this.numCatastrofi++;
			}
			break;
		case FINE_INTERRUZIONE:
			int giorni = (int) Duration.between(e.getN().getTimeInizio(), e.getN().getTimeFine()).toDays();
			e.getN().getN().setBonus(e.getN().getN().getBonus()+giorni);
			break;
		}
		
	}

	private Nerc cercaDonatore(Event e) {
		Nerc n = e.getN().getN();
		List<Nerc> possibili = n.donatoreUltimiMesi(k, e.getTime());
		if(possibili.size()==1) {
			return possibili.get(0);
		}
		else if(possibili.size()>1) {
			return this.cercaMinimo(possibili,n);
			
		}
		else if(possibili.size()==0) {
			return this.minorVicino(n);
		}
		return null;
	}

	private Nerc minorVicino(Nerc n) {
		double peso = Double.MAX_VALUE;
		Nerc top = null;
		for(Nerc ne : Graphs.neighborListOf(this.graph, n)) {
			double p = this.graph.getEdgeWeight(this.graph.getEdge(n, ne));
			if(p<peso) {
				peso=p;
				top=ne;
			}
				
		}
		return top;
	}

	private Nerc cercaMinimo(List<Nerc> possibili, Nerc n) {
		double peso = Double.MAX_VALUE;
		Nerc top = null;
		for(Nerc ne : possibili) {
			double p = this.graph.getEdgeWeight(this.graph.getEdge(n, ne));
			if(p<peso) {
				peso=p;
				top=ne;
			}
				
		}
		return top;
	}

	/**
	 * @return the numCatastrofi
	 */
	public int getNumCatastrofi() {
		return numCatastrofi;
	}

	
}
