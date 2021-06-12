package br.com.TCCMapa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MapaUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6159025895631302641L;

	@Id
    @Column(name="id", nullable=false, unique=true)
    private int id;
	
	@Column(name="nomeMapa", nullable=false, unique=true)
    private String nomeMapa;
	
	@ManyToOne
	@JoinColumn(name="usuario")
	private Usuario usuario;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeMapa() {
		return nomeMapa;
	}

	public void setNomeMapa(String nomeMapa) {
		this.nomeMapa = nomeMapa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
}
