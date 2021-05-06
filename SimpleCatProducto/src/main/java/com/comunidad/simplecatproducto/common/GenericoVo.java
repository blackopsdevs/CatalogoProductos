package com.comunidad.simplecatproducto.common;

import java.util.Date;

public class GenericoVo {

    private int status;
    private Date fechaExpiracion;
    private Date fechaUltActual;
    private String userUltActual;
    private Date fechaAlta;
    private int borrado;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public Date getFechaUltActual() {
        return fechaUltActual;
    }

    public void setFechaUltActual(Date fechaUltActual) {
        this.fechaUltActual = fechaUltActual;
    }

    public String getUserUltActual() {
        return userUltActual;
    }

    public void setUserUltActual(String userUltActual) {
        this.userUltActual = userUltActual;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public int getBorrado() {
        return borrado;
    }

    public void setBorrado(int borrado) {
        this.borrado = borrado;
    }

    @Override
    public String toString() {
        return "GenericoVo{" + "status=" + status + ", fechaExpiracion=" + fechaExpiracion + ", fechaUltActual=" + fechaUltActual + ", userUltActual=" + userUltActual + ", fechaAlta=" + fechaAlta + ", borrado=" + borrado + '}';
    }

}
