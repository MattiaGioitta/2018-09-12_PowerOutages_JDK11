package it.polito.tdp.poweroutages.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.poweroutages.model.Adiacenza;
import it.polito.tdp.poweroutages.model.Interruzione;
import it.polito.tdp.poweroutages.model.Nerc;

public class PowerOutagesDAO {
	
	public void loadAllNercs(Map<Integer, Nerc> idMap) {

		String sql = "SELECT id, value FROM nerc";
		

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				idMap.put(n.getId(), n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		
	}

	public List<Adiacenza> getAdiacenze(Map<Integer, Nerc> idMap) {
		final String sql ="SELECT p1.nerc_id AS n1,p2.nerc_id AS n2,COUNT(DISTINCT(MONTH(p1.date_event_began))) AS peso " + 
				"FROM poweroutages AS p1, poweroutages AS p2 " + 
				"WHERE p1.nerc_id<>p2.nerc_id " + 
				"AND YEAR(p1.date_event_began)=YEAR(p2.date_event_began) " + 
				"AND p1.nerc_id<p2.nerc_id " + 
				"GROUP BY p1.nerc_id,p2.nerc_id";
		List<Adiacenza> lista = new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				Adiacenza a = new Adiacenza(idMap.get(res.getInt("n1")), idMap.get(res.getInt("n2")),res.getInt("peso"));
				lista.add(a);
			}
			conn.close();
			return lista;
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	public List<Interruzione> getInterruzioni(Map<Integer, Nerc> idMap) {
		final String sql = "SELECT p1.nerc_id AS n,p1.date_event_began AS t1,p1.date_event_finished AS t2 " + 
				"FROM poweroutages AS p1 " + 
				"ORDER BY p1.date_event_began";
		List<Interruzione> l = new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				Interruzione i = new Interruzione(idMap.get(res.getInt("n")),res.getTimestamp("t1").toLocalDateTime(),res.getTimestamp("t2").toLocalDateTime());
				l.add(i);
			}
			conn.close();
			return l;
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
}
