package br.com.TCCMapa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;

import br.com.TCCMapa.model.Usuario;
import br.com.TCCMapa.utils.ConnectionManager;

public class ArquivosDAO {

	private UsuarioDAO usuarioDao = new UsuarioDAO();
	
	public void excluirGeoJsonUsuario() {
		PreparedStatement ps = null;
		Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
    	String nomeUsuario = usuarioLogado.getNomeUsuario();
		try {
			ConnectionManager connectionManager = new ConnectionManager();
			Connection conn = connectionManager.getConnection();
			
			ps = conn.prepareStatement("delete from usuarioGeoJsonLayers where usuario = ?");
			ps.setInt(1,usuarioDao.getUsuario(nomeUsuario).getId());
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
		Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
    	String nomeUsuario = usuarioLogado.getNomeUsuario();
		try {
			ConnectionManager connectionManager = new ConnectionManager();
			Connection conn = connectionManager.getConnection();
			
			ps = conn.prepareStatement("insert into usuarioGeoJsonLayers (id,geoJsonLayer,usuario) values (nextVal('formas_seq'),?,?)");
			ps.setString(1,geoJsonLayer);
			ps.setInt(2,usuarioDao.getUsuario(nomeUsuario).getId());
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
		Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
    	String nomeUsuario = usuarioLogado.getNomeUsuario();
		try {
			ConnectionManager connectionManager = new ConnectionManager();
			Connection conn = connectionManager.getConnection();
			
			ps = conn.prepareStatement("SELECT geoJsonLayer FROM usuarioGeoJsonLayers WHERE usuario = ?");
			ps.setInt(1, usuarioDao.getUsuario(nomeUsuario).getId());
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
