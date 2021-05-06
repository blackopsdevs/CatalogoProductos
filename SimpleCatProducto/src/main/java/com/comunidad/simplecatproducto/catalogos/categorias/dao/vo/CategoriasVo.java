/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.categorias.dao.vo;

import com.comunidad.simplecatproducto.catalogos.productos.dao.vo.ProductosVo;
import com.comunidad.simplecatproducto.common.GenericoVo;
import java.io.FileInputStream;
import java.util.List;

/**
 *
 * @author Santiago
 */
public class CategoriasVo extends GenericoVo {

    private int id;
    private int idDepart;
    private String nombreDepart;
    private String nombre;
    private String descripcion;
    private FileInputStream foto;
    private String path;
    private List<ProductosVo> listProductoVo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDepart() {
        return idDepart;
    }

    public void setIdDepart(int idDepart) {
        this.idDepart = idDepart;
    }

    public String getNombreDepart() {
        return nombreDepart;
    }

    public void setNombreDepart(String nombreDepart) {
        this.nombreDepart = nombreDepart;
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

    public List<ProductosVo> getListProductoVo() {
        return listProductoVo;
    }

    public void setListProductoVo(List<ProductosVo> listProductoVo) {
        this.listProductoVo = listProductoVo;
    }

    @Override
    public String toString() {
        return "CategoriasVo{" + "id=" + id + ", idDepart=" + idDepart + ", nombreDepart=" + nombreDepart + ", nombre=" + nombre + ", descripcion=" + descripcion + ", foto=" + foto + ", path=" + path + ", listProductoVo=" + listProductoVo + '}';
    }

}
