package com.comunidad.simplecatproducto.departamento.dao.vo;

import java.util.List;

import com.comunidad.simplecatproducto.common.GenericoVo;

public class CategoriaVoTest extends GenericoVo {
	private int id;
	private String nombre;
	private String descripcion;
	private List<ProductoVoTest> listProductoVo;

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

	public List<ProductoVoTest> getListProductoVo() {
		return listProductoVo;
	}

	public void setListProductoVo(List<ProductoVoTest> listProductoVo) {
		this.listProductoVo = listProductoVo;
	}

	@Override
	public String toString() {
		return "CategoriaVo [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", listProductoVo="
				+ listProductoVo + "]";
	}

}
