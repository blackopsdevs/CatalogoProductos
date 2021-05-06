/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.subproductos.dao.vo;

import com.comunidad.simplecatproducto.common.GenericoVo;
import java.io.FileInputStream;
import java.util.List;

/**
 *
 * @author Santiago
 */
public class SubproductosVo extends GenericoVo {

    private int id;
    private int idProd;
    private String nombre;
    private String descripcion;
    private double presio;
    private double presioAnt;
    private int cantidad;
    private FileInputStream foto;
    private String path;
    private List<DetalleSubprodVo> detalles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
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

    public double getPresio() {
        return presio;
    }

    public void setPresio(double presio) {
        this.presio = presio;
    }

    public double getPresioAnt() {
        return presioAnt;
    }

    public void setPresioAnt(double presioAnt) {
        this.presioAnt = presioAnt;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

    public List<DetalleSubprodVo> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleSubprodVo> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "SubproductosVo{" + "id=" + id + ", idProd=" + idProd + ", nombre=" + nombre + ", descripcion=" + descripcion + ", presio=" + presio + ", presioAnt=" + presioAnt + ", cantidad=" + cantidad + ", foto=" + foto + ", path=" + path + ", detalles=" + detalles + '}';
    }

}
