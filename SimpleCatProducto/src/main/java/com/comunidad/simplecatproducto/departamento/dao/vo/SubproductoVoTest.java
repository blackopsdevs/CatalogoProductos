package com.comunidad.simplecatproducto.departamento.dao.vo;

import com.comunidad.simplecatproducto.common.GenericoVo;

public class SubproductoVoTest extends GenericoVo {
	private int id;
	private String nombre;
	private String descripcion;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
