package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;

public class Model {
	
	private Graph<Nerc,DefaultWeightedEdge> graph;
	private PowerOutagesDAO dao;
	private Map<Integer,Nerc> idMap;
	private Simulator sim;
	
	public Model() {
		this.dao = new PowerOutagesDAO();
		this.idMap = new HashMap<>();
	}

	public void createGraph() {
		this.dao.loadAllNercs(this.idMap);
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.graph, this.idMap.values());
		List<Adiacenza> adiacenze = this.dao.getAdiacenze(this.idMap);
		for(Adiacenza a : adiacenze) {
			if(this.graph.getEdge(a.getN1(), a.getN2()) == null) {
				Graphs.addEdgeWithVertices(this.graph, a.getN1(), a.getN2(),a.getPeso());
			}
		}
	}

	public Integer nVertici() {
		// TODO Auto-generated method stub
		return this.graph.vertexSet().size();
	}

	public Integer nArchi() {
		// TODO Auto-generated method stub
		return this.graph.edgeSet().size();
	}

	public List<Nerc> getVertices() {
		List<Nerc> l = new ArrayList<>();
		for(Nerc n : this.graph.vertexSet()) {
			l.add(n);
		}
		Collections.sort(l);
		return l;
	}

	public List<Vicino> vicini(Nerc n) {
		List<Vicino> l = new ArrayList<>();
		for(Nerc ne : Graphs.neighborListOf(this.graph, n)) {
			l.add(new Vicino(ne,this.graph.getEdgeWeight(this.graph.getEdge(n, ne))));
		}
		Collections.sort(l);
		return l;
	}
	
	public void simula(int k) {
		this.sim = new Simulator();
		sim.init(idMap, graph, k);
		sim.run();
	}
	public Integer getCatastrofi() {
		return this.sim.getNumCatastrofi();
	}

}
