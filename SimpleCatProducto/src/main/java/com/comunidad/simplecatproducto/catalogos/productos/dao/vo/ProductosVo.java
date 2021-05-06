/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.productos.dao.vo;

import com.comunidad.simplecatproducto.common.GenericoVo;
import com.comunidad.simplecatproducto.departamento.dao.vo.SubproductoVoTest;
import java.io.FileInputStream;
import java.util.List;

/**
 *
 * @author Santiago
 */
public class ProductosVo extends GenericoVo {

    private int id;
    private int idCatego;
    private String nombre;
    private String descripcion;
    private List<SubproductoVoTest> listSubproductoVo;
    private FileInputStream foto;
    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCatego() {
        return idCatego;
    }

    public void setIdCatego(int idCatego) {
        this.idCatego = idCatego;
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

    public FileInputStream getFoto() {
        return foto;
    }

    public void setFoto(FileInputStream foto) {
        this.foto = foto;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ProductosVo{" + "id=" + id + ", idCatego=" + idCatego + ", nombre=" + nombre + ", descripcion=" + descripcion + ", listSubproductoVo=" + listSubproductoVo + ", foto=" + foto + ", path=" + path + '}';
    }

}
