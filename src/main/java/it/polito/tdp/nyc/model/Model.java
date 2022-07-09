package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	
	private NYCDao dao;
	private Graph<String,DefaultWeightedEdge> grafo;
	private List<String> vertici;
	
	public Model() {
		this.dao= new NYCDao();
	}
	
	public List<String> popolaCmb(){
		return this.dao.popolaCmb();
	}
	public List<String> getVertici(){
		return this.vertici;
	}
	
	public void creaGrafo(String provider) {
		this.grafo= new SimpleWeightedGraph<String,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		this.vertici= new ArrayList<String>(this.dao.getVertici(provider));
		Graphs.addAllVertices(this.grafo, vertici);
		
		for(String s1: this.grafo.vertexSet()) {
			for(String s2: this.grafo.vertexSet()) {
				if(s1.compareTo(s2)!=0) {
					Arco a=this.dao.getArco(s1, s2);
					if(a!=null) {
						Graphs.addEdgeWithVertices(this.grafo, s1, s2, a.getPeso());
					}
				}
				
			}
		}
	}
	
	public String nVertici() {
		return "Grafo creato!"+"\n"+"#verici: "+ this.grafo.vertexSet().size()+"\n";
	}
	
	public String nArchi() {
		return "#archi: "+ this.grafo.edgeSet().size()+"\n";
	}
	
	public List<CittaDistanza> quartieriAdiacenti(String quartiere){
		List<CittaDistanza> list= new ArrayList<>();
		CittaDistanza CittaD=null;
		for(DefaultWeightedEdge e: this.grafo.edgesOf(quartiere)) {
			CittaD= new CittaDistanza(Graphs.getOppositeVertex(this.grafo, e, quartiere), this.grafo.getEdgeWeight(e));
			list.add(CittaD);
		}
		Collections.sort(list);
		
		return list;
	}
	
}
