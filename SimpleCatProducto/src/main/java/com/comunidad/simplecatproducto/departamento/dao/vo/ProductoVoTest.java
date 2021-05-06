package com.comunidad.simplecatproducto.departamento.dao.vo;

import java.util.List;

import com.comunidad.simplecatproducto.common.GenericoVo;

public class ProductoVoTest extends GenericoVo {
	private int id;
	private String nombre;
	private String descripcion;
	private List<SubproductoVoTest> listSubproductoVo;

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

	public List<SubproductoVoTest> getListSubproductoVo() {
		return listSubproductoVo;
	}

	public void setListSubproductoVo(List<SubproductoVoTest> listSubproductoVo) {
		this.listSubproductoVo = listSubproductoVo;
	}

	@Override
	public String toString() {
		return "ProductoVo [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", listSubproductoVo="
				+ listSubproductoVo + "]";
	}

}
