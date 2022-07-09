package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.nyc.model.Arco;
import it.polito.tdp.nyc.model.Hotspot;

public class NYCDao {
	
	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<String> popolaCmb(){
		String sql="SELECT DISTINCT h1.Provider AS p "
				+ "FROM nyc_wifi_hotspot_locations h1 "
				+ "ORDER BY h1.Provider ASC ";
		
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("p"));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<String> getVertici(String provider){
		String sql="SELECT DISTINCT h1.City AS c "
				+ "FROM nyc_wifi_hotspot_locations h1 "
				+ "WHERE h1.City IN (SELECT h2.City "
				+ "FROM nyc_wifi_hotspot_locations h2 "
				+ "WHERE h2.Provider=?)";
		
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, provider);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("c"));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public Arco getArco(String c1, String c2){
		String sql="SELECT h1.City AS c1, h2.City AS c2, AVG(h1.Latitude) AS lat1, AVG(h1.Longitude) AS long1, AVG(h2.Latitude) AS lat2, AVG(h2.Longitude) AS long2 "
				+ "FROM nyc_wifi_hotspot_locations h1, nyc_wifi_hotspot_locations h2 "
				+ "WHERE h1.City=? AND h2.City=? AND h1.Provider=h2.Provider "
				+ "GROUP BY h1.City, h2.City";
		Arco a= null;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, c1);
			st.setString(2, c2);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				a= new Arco(res.getString("c1"), res.getString("c2"), 0);
				LatLng latlong1= new LatLng(res.getDouble("lat1"), res.getDouble("long1"));
				LatLng latlong2= new LatLng(res.getDouble("lat2"), res.getDouble("long2"));
				double peso= LatLngTool.distance(latlong1, latlong2, LengthUnit.KILOMETER);
				a.setPeso(peso);
				
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return a;
	}
	
}
