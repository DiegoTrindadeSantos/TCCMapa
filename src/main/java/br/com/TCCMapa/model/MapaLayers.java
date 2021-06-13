package br.com.TCCMapa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MapaLayers implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4185482891232653043L;

	@Id
    @Column(name="id", nullable=false, unique=true)
    private int id;
	
	@Column(name="geojsonlayer", nullable=false, unique=true)
    private String geoJsonLayer;
	
	@ManyToOne
	@JoinColumn(name="mapaid")
	private MapaUsuario mapaUsuario;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGeoJsonLayer() {
		return geoJsonLayer;
	}

	public void setGeoJsonLayer(String geoJsonLayer) {
		this.geoJsonLayer = geoJsonLayer;
	}

	public MapaUsuario getMapaUsuario() {
		return mapaUsuario;
	}

	public void setMapaUsuario(MapaUsuario mapaUsuario) {
		this.mapaUsuario = mapaUsuario;
	}
	
}
