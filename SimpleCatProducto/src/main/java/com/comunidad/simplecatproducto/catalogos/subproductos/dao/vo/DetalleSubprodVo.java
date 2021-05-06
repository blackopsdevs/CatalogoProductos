/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.subproductos.dao.vo;

import com.comunidad.simplecatproducto.common.GenericoVo;

/**
 *
 * @author Santiago
 */
public class DetalleSubprodVo extends GenericoVo {

    private int id;
    private int idSubprod;
    private String nombre;
    private String descripcion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSubprod() {
        return idSubprod;
    }

    public void setIdSubprod(int idSubprod) {
        this.idSubprod = idSubprod;
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

    @Override
    public String toString() {
        return "DetalleSubprodVo{" + "id=" + id + ", idSubprod=" + idSubprod + ", nombre=" + nombre + ", descripcion=" + descripcion + '}';
    }

}
