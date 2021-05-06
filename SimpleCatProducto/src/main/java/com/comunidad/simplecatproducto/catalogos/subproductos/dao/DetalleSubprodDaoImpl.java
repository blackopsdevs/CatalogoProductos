/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.subproductos.dao;

import com.comunidad.simplecatproducto.catalogos.subproductos.dao.vo.DetalleSubprodVo;
import com.comunidad.simplecatproducto.common.DateUtils;
import com.comunidad.simplecatproducto.jdbc.JdbcDao;
import com.comunidad.simplecatproducto.jdbc.JdbcQueryParameters;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

/**
 *
 * @author Santiago
 */
public class DetalleSubprodDaoImpl extends JdbcDao implements DetalleSubprodDao {

    private static final Logger log = Logger.getLogger(DetalleSubprodDaoImpl.class);

    private String qryCrear;
    private String qryModificar;
    private String qryConsultar;
    private String qryConsultarTodo;
    private String nextId;
    private String existe;
    private JdbcQueryParameters qryTodo;
    private JdbcQueryParameters qryCountTodo;
    private String qryCatSubprod;

    private static ParameterizedRowMapper<DetalleSubprodVo> MAPPER_DETALLESUBPROD = (ResultSet rs, int rowNum) -> {
        DetalleSubprodVo obj = new DetalleSubprodVo();
        obj.setId(rs.getInt("ID"));
        obj.setNombre(rs.getString("NOMBRE"));
        obj.setDescripcion(rs.getString("DESCR"));
        obj.setStatus(rs.getInt("STATUS_FLAG"));
        obj.setFechaExpiracion(rs.getDate("EXPIRY_DT"));
        obj.setFechaUltActual(rs.getDate("LAST_UPDATE_DT"));
        obj.setUserUltActual(rs.getString("LAST_UPDATE_USER"));

        return obj;
    };

    @Override
    public int guardar(DetalleSubprodVo detalleSubprodVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("guardar...");
        }

//        final int nextIdProd = jdbcTemplate.queryForInt(nextId);
//        if (nextIdProd == 0) {
//            detalleSubprodVo.setId(nextIdProd + 100);
//        } else {
//            detalleSubprodVo.setId(nextIdProd);
//        }
        int affected = jdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement(qryCrear);
            //ps.setInt(1, detalleSubprodVo.getId());
            ps.setInt(1, detalleSubprodVo.getIdSubprod());
            ps.setString(2, detalleSubprodVo.getNombre());
            ps.setString(3, detalleSubprodVo.getDescripcion());
            ps.setInt(4, detalleSubprodVo.getStatus());
            ps.setDate(5, DateUtils.convertJavaDateToSqlDate(detalleSubprodVo.getFechaExpiracion()));
            ps.setDate(6, DateUtils.convertJavaDateToSqlDate(detalleSubprodVo.getFechaUltActual()));
            ps.setString(7, detalleSubprodVo.getUserUltActual());
            return ps;
        });

        return affected;
    }

    @Override
    public int actualizar(DetalleSubprodVo detalleSubprodVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("actualizar...");
        }

        int affected = jdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement(qryModificar);
            ps.setInt(1, detalleSubprodVo.getIdSubprod());
            ps.setString(2, detalleSubprodVo.getNombre());
            ps.setString(3, detalleSubprodVo.getDescripcion());
            ps.setInt(4, detalleSubprodVo.getStatus());
            ps.setDate(5, DateUtils.convertJavaDateToSqlDate(detalleSubprodVo.getFechaExpiracion()));
            ps.setDate(6, DateUtils.convertJavaDateToSqlDate(detalleSubprodVo.getFechaUltActual()));
            ps.setString(7, detalleSubprodVo.getUserUltActual());
            ps.setInt(8, detalleSubprodVo.getId());
            return ps;
        });
        return affected;
    }

    @Override
    public int existe(DetalleSubprodVo detalleSubprodVo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DetalleSubprodVo buscar(DetalleSubprodVo detalleSubprodVo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DetalleSubprodVo> lista(DetalleSubprodVo detalleSubprodVo) throws Exception {
            if (log.isDebugEnabled()) {
            log.debug("lista...");
        }

        StringBuffer stb = new StringBuffer(qryTodo.getSqlBase());
        ArrayList parameters = new ArrayList();
        Properties params = qryTodo.getOptionalParameters();

        if (detalleSubprodVo != null) {
            if (detalleSubprodVo.getId() != 0) {
                stb.append(" ").append(params.get("id"));
                parameters.add(detalleSubprodVo.getId());
            }
            if (detalleSubprodVo.getIdSubprod() != 0) {
                stb.append(" ").append(params.get("idSubprod"));
                parameters.add(detalleSubprodVo.getIdSubprod());
            }
            if (detalleSubprodVo.getStatus() != 0) {
                stb.append(" ").append(params.get("estatus"));
                parameters.add(detalleSubprodVo.getStatus());
            }
        }
        if (qryTodo.getSqlLast() != null && !qryTodo.getSqlLast().equals("")) {
            stb.append(" ").append(qryTodo.getSqlLast());
        }

        Object[] args = parameters.toArray();

        return (List<DetalleSubprodVo>) jdbcTemplate.query(stb.toString(), args, MAPPER_DETALLESUBPROD);
    }

    @Override
    public List<DetalleSubprodVo> catalogo(int id, int estatus) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setQryCrear(String qryCrear) {
        this.qryCrear = qryCrear;
    }

    public void setQryModificar(String qryModificar) {
        this.qryModificar = qryModificar;
    }

    public void setQryConsultar(String qryConsultar) {
        this.qryConsultar = qryConsultar;
    }

    public void setQryConsultarTodo(String qryConsultarTodo) {
        this.qryConsultarTodo = qryConsultarTodo;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    public void setExiste(String existe) {
        this.existe = existe;
    }

    public void setQryTodo(JdbcQueryParameters qryTodo) {
        this.qryTodo = qryTodo;
    }

    public void setQryCountTodo(JdbcQueryParameters qryCountTodo) {
        this.qryCountTodo = qryCountTodo;
    }

    public void setQryCatSubprod(String qryCatSubprod) {
        this.qryCatSubprod = qryCatSubprod;
    }

}
