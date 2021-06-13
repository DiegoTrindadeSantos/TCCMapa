package br.com.TCCMapa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;

import br.com.TCCMapa.model.MapaUsuario;
import br.com.TCCMapa.model.Usuario;
import br.com.TCCMapa.utils.ConnectionManager;

public class ArquivosDAO {

	private UsuarioDAO usuarioDao = new UsuarioDAO();
	
	public void excluirGeoJsonUsuario() {
		PreparedStatement ps = null;
		MapaUsuario mapaUsuario = (MapaUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mapaUsuario");
		try {
			ConnectionManager connectionManager = new ConnectionManager();
			Connection conn = connectionManager.getConnection();
			
			ps = conn.prepareStatement("delete from usuarioGeoJsonLayers where mapaId = ?");
			ps.setInt(1,mapaUsuario.getId());
			ps.executeUpdate();
			ps.close();

			
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void salvarGeoJsonLayer(String geoJsonLayer) {
		PreparedStatement ps = null;
		MapaUsuario mapaUsuario = (MapaUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mapaUsuario");
		try {
			ConnectionManager connectionManager = new ConnectionManager();
			Connection conn = connectionManager.getConnection();
			
			ps = conn.prepareStatement("insert into usuarioGeoJsonLayers (id,geoJsonLayer,mapaId) values (nextVal('formas_seq'),?,?)");
			ps.setString(1,geoJsonLayer);
			ps.setInt(2,mapaUsuario.getId());
			ps.executeUpdate();
			ps.close();

			
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public List<String> obterGeoJsonFormas() {
		PreparedStatement ps = null;
		MapaUsuario mapaUsuario = (MapaUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mapaUsuario");
		try {
			ConnectionManager connectionManager = new ConnectionManager();
			Connection conn = connectionManager.getConnection();
			
			ps = conn.prepareStatement("SELECT geoJsonLayer FROM usuarioGeoJsonLayers WHERE mapaId = ?");
			ps.setInt(1, mapaUsuario.getId());
			ResultSet rs = ps.executeQuery();
			List<String> listaGeoLayer = null;
			if (rs != null) {
				listaGeoLayer= new ArrayList<String>();
			    while (rs.next()) {
			    	listaGeoLayer.add(rs.getString(1));
			    }
			    rs.close();
			    return listaGeoLayer;
			}
			ps.close();

		} catch (HibernateException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
