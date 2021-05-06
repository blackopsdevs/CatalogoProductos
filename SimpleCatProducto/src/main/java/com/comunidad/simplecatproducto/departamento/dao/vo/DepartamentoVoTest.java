package com.comunidad.simplecatproducto.departamento.dao.vo;

import com.comunidad.simplecatproducto.catalogos.departamentos.dao.vo.*;
import java.util.List;

import com.comunidad.simplecatproducto.common.GenericoVo;
import java.io.FileInputStream;

public class DepartamentoVoTest extends GenericoVo {

    private int id;
    private String nombre;
    private String descripcion;
    private FileInputStream foto;
    private List<CategoriaVoTest> listCategoriaVo;

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

    public List<CategoriaVoTest> getListCategoriaVo() {
        return listCategoriaVo;
    }

    public void setListCategoriaVo(List<CategoriaVoTest> listCategoriaVo) {
        this.listCategoriaVo = listCategoriaVo;
    }

    public FileInputStream getFoto() {
        return foto;
    }

    public void setFoto(FileInputStream foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "DepartamentoVo{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", foto=" + foto + ", listCategoriaVo=" + listCategoriaVo + '}';
    }

}
