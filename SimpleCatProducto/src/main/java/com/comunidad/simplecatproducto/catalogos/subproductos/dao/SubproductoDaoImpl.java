/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.subproductos.dao;

import com.comunidad.simplecatproducto.catalogos.productos.dao.ProductoDaoImpl;
import com.comunidad.simplecatproducto.catalogos.subproductos.dao.vo.SubproductosVo;
import com.comunidad.simplecatproducto.common.DateUtils;
import com.comunidad.simplecatproducto.common.ItemComboboxModel;
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
public class SubproductoDaoImpl extends JdbcDao implements SubproductoDao {

    private static final Logger log = Logger.getLogger(ProductoDaoImpl.class);

    private String qryCrear;
    private String qryModificar;
    private String qryConsultar;
    private String qryConsultarTodo;
    private String nextId;
    private String existe;
    private JdbcQueryParameters qryTodo;
    private JdbcQueryParameters qryCountTodo;
    private String qryCatSubprod;

    private static ParameterizedRowMapper<SubproductosVo> MAPPER_SUBPRODUCTOS = (ResultSet rs, int rowNum) -> {
        SubproductosVo obj = new SubproductosVo();
        obj.setId(rs.getInt("ID"));
        obj.setNombre(rs.getString("NOMBRE"));
        obj.setDescripcion(rs.getString("DESCR"));
        obj.setStatus(rs.getInt("STATUS_FLAG"));
        obj.setFechaExpiracion(rs.getDate("EXPIRY_DT"));
        obj.setFechaUltActual(rs.getDate("LAST_UPDATE_DT"));
        obj.setUserUltActual(rs.getString("LAST_UPDATE_USER"));

        return obj;
    };

    private static final ParameterizedRowMapper<ItemComboboxModel> MAPPER_CAT_SUBPROD = (ResultSet rs, int rowNum) -> {
        ItemComboboxModel obj = new ItemComboboxModel();
        obj.setId(rs.getInt("ID"));
        obj.setValue(rs.getString("NOMBRE"));

        return obj;
    };

    @Override
    public int guardar(SubproductosVo subproductosVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("guardar...");
        }

//        final int nextIdProd = jdbcTemplate.queryForInt(nextId);
//        if (nextIdProd == 0) {
//            subproductosVo.setId(nextIdProd + 100);
//        } else {
//            subproductosVo.setId(nextIdProd);
//        }
        int affected = jdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement(qryCrear);
            ps.setInt(1, subproductosVo.getId());
            ps.setInt(2, subproductosVo.getIdProd());
            ps.setString(3, subproductosVo.getNombre());
            ps.setString(4, subproductosVo.getDescripcion());
            if (subproductosVo.getFoto() != null) {
                ps.setBinaryStream(5, subproductosVo.getFoto());
            }
            ps.setString(6, subproductosVo.getPath());
            ps.setInt(7, subproductosVo.getStatus());
            ps.setDate(8, DateUtils.convertJavaDateToSqlDate(subproductosVo.getFechaExpiracion()));
            ps.setDate(9, DateUtils.convertJavaDateToSqlDate(subproductosVo.getFechaUltActual()));
            ps.setString(10, subproductosVo.getUserUltActual());
            return ps;
        });

        return affected;
    }

    @Override
    public int actualizar(SubproductosVo subproductosVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("actualizar...");
        }

        int affected = jdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement(qryModificar);
            ps.setInt(1, subproductosVo.getIdProd());
            ps.setString(2, subproductosVo.getNombre());
            ps.setString(3, subproductosVo.getDescripcion());
            ps.setInt(4, subproductosVo.getStatus());
            ps.setDate(5, DateUtils.convertJavaDateToSqlDate(subproductosVo.getFechaExpiracion()));
            ps.setDate(6, DateUtils.convertJavaDateToSqlDate(subproductosVo.getFechaUltActual()));
            ps.setString(7, subproductosVo.getUserUltActual());
            ps.setInt(8, subproductosVo.getId());
            return ps;
        });
        return affected;
    }

    @Override
    public int existe(SubproductosVo subproductosVo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SubproductosVo buscar(SubproductosVo subproductosVo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SubproductosVo> lista(SubproductosVo subproductosVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("lista...");
        }

        StringBuffer stb = new StringBuffer(qryTodo.getSqlBase());
        ArrayList parameters = new ArrayList();
        Properties params = qryTodo.getOptionalParameters();

        if (subproductosVo != null) {
            if (subproductosVo.getId() != 0) {
                stb.append(" ").append(params.get("id"));
                parameters.add(subproductosVo.getId());
            }
            if (subproductosVo.getIdProd() != 0) {
                stb.append(" ").append(params.get("idProd"));
                parameters.add(subproductosVo.getIdProd());
            }
            if (subproductosVo.getStatus() != 0) {
                stb.append(" ").append(params.get("estatus"));
                parameters.add(subproductosVo.getStatus());
            }
        }
        if (qryTodo.getSqlLast() != null && !qryTodo.getSqlLast().equals("")) {
            stb.append(" ").append(qryTodo.getSqlLast());
        }

        Object[] args = parameters.toArray();

        return (List<SubproductosVo>) jdbcTemplate.query(stb.toString(), args, MAPPER_SUBPRODUCTOS);
    }

    @Override
    public List<ItemComboboxModel> catalogo(int id, int estatus) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("catalogo...");
        }
        StringBuffer stb = new StringBuffer(qryCatSubprod);
        ArrayList parameters = new ArrayList();
        if (id != 0) {
            parameters.add(id);
        }
        if (estatus != 0) {
            parameters.add(estatus);
        }
        Object[] args = parameters.toArray();
        return (List<ItemComboboxModel>) jdbcTemplate.query(stb.toString(), args, MAPPER_CAT_SUBPROD);
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

    public static void setMAPPER_SUBPRODUCTOS(ParameterizedRowMapper<SubproductosVo> MAPPER_SUBPRODUCTOS) {
        SubproductoDaoImpl.MAPPER_SUBPRODUCTOS = MAPPER_SUBPRODUCTOS;
    }

}
