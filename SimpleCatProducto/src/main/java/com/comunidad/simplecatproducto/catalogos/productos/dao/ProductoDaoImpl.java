/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.productos.dao;

import com.comunidad.simplecatproducto.catalogos.productos.dao.vo.ProductosVo;
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
public class ProductoDaoImpl extends JdbcDao implements ProductoDao {

    private static final Logger log = Logger.getLogger(ProductoDaoImpl.class);

    private String qryCrear;
    private String qryModificar;
    private String qryConsultar;
    private String qryConsultarTodo;
    private String qryBorrar;
    private String nextId;
    private String existe;
    private JdbcQueryParameters qryTodo;
    private JdbcQueryParameters qryCountTodo;
    private String qryCatProd;

    private static ParameterizedRowMapper<ProductosVo> MAPPER_PRODUCTOS = (ResultSet rs, int rowNum) -> {
        ProductosVo obj = new ProductosVo();
        obj.setId(rs.getInt("ID"));
        obj.setIdCatego(rs.getInt("ID_CATEG"));
        obj.setNombre(rs.getString("NOMBRE"));
        obj.setDescripcion(rs.getString("DESCR"));
        obj.setPath(rs.getString("PATH"));
        obj.setStatus(rs.getInt("STATUS_FLAG"));
        obj.setFechaExpiracion(rs.getDate("EXPIRY_DT"));
        obj.setFechaUltActual(rs.getDate("LAST_UPDATE_DT"));
        obj.setFechaAlta(rs.getDate("START_DATE"));
        obj.setUserUltActual(rs.getString("LAST_UPDATE_USER"));
        obj.setBorrado(rs.getInt("DEL_LOGIC"));

        return obj;
    };

    private static final ParameterizedRowMapper<ItemComboboxModel> MAPPER_CAT_PROD = (ResultSet rs, int rowNum) -> {
        ItemComboboxModel obj = new ItemComboboxModel();
        obj.setId(rs.getInt("ID"));
        obj.setValue(rs.getString("NOMBRE"));

        return obj;
    };

    @Override
    public int guardar(ProductosVo productosVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("guardar...");
        }

        int affected = jdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement(qryCrear);
            ps.setInt(1, productosVo.getId());
            ps.setInt(2, productosVo.getIdCatego());
            ps.setString(3, productosVo.getNombre());
            ps.setString(4, productosVo.getDescripcion());
            if (productosVo.getFoto() != null) {
                ps.setBinaryStream(5, productosVo.getFoto());
            }
            ps.setString(6, productosVo.getPath());
            ps.setInt(7, productosVo.getStatus());
            ps.setDate(8, DateUtils.convertJavaDateToSqlDate(productosVo.getFechaExpiracion()));
            ps.setDate(9, DateUtils.convertJavaDateToSqlDate(productosVo.getFechaUltActual()));
            ps.setDate(10, DateUtils.convertJavaDateToSqlDate(productosVo.getFechaAlta()));
            ps.setString(11, productosVo.getUserUltActual());
            ps.setInt(12, productosVo.getBorrado());
            return ps;
        });

        return affected;
    }

    @Override
    public int actualizar(ProductosVo productosVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("actualizar...");
        }

        int affected = jdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement(qryModificar);
            ps.setInt(1, productosVo.getIdCatego());
            ps.setString(2, productosVo.getNombre());
            ps.setString(3, productosVo.getDescripcion());
            if (productosVo.getFoto() != null) {
                ps.setBinaryStream(4, productosVo.getFoto());
            }
            ps.setString(5, productosVo.getPath());
            ps.setInt(6, productosVo.getStatus());
            ps.setDate(7, DateUtils.convertJavaDateToSqlDate(productosVo.getFechaExpiracion()));
            ps.setDate(8, DateUtils.convertJavaDateToSqlDate(productosVo.getFechaUltActual()));
            ps.setString(9, productosVo.getUserUltActual());
            ps.setInt(10, productosVo.getId());
            return ps;
        });
        return affected;
    }

    @Override
    public int existe(ProductosVo productosVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("existe...");
        }
        StringBuffer stb = new StringBuffer(existe);
        ArrayList parameters = new ArrayList();
        if (productosVo.getId() != 0) {
            parameters.add(productosVo.getId());
        }
        Object[] args = parameters.toArray();
        return jdbcTemplate.queryForInt(stb.toString(), args);
    }

    @Override
    public ProductosVo buscar(ProductosVo productosVo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProductosVo> lista(ProductosVo productosVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("lista...");
        }

        StringBuffer stb = new StringBuffer(qryTodo.getSqlBase());
        ArrayList parameters = new ArrayList();
        Properties params = qryTodo.getOptionalParameters();

        if (productosVo != null) {
            if (productosVo.getId() != 0) {
                stb.append(" ").append(params.get("id"));
                parameters.add(productosVo.getId());
            }
            if (productosVo.getIdCatego() != 0) {
                stb.append(" ").append(params.get("idCatego"));
                parameters.add(productosVo.getIdCatego());
            }
            if (productosVo.getStatus() != 0) {
                stb.append(" ").append(params.get("estatus"));
                parameters.add(productosVo.getStatus());
            }
        }
        if (qryTodo.getSqlLast() != null && !qryTodo.getSqlLast().equals("")) {
            stb.append(" ").append(qryTodo.getSqlLast());
        }

        Object[] args = parameters.toArray();

        return (List<ProductosVo>) jdbcTemplate.query(stb.toString(), args, MAPPER_PRODUCTOS);
    }

    @Override
    public List<ItemComboboxModel> catalogo(int id, int estatus) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("catalogo...");
        }
        StringBuffer stb = new StringBuffer(qryCatProd);
        ArrayList parameters = new ArrayList();
        if (id != 0) {
            parameters.add(id);
        }
        if (estatus != 0) {
            parameters.add(estatus);
        }
        Object[] args = parameters.toArray();
        return (List<ItemComboboxModel>) jdbcTemplate.query(stb.toString(), args, MAPPER_CAT_PROD);
    }

    @Override
    public int borrar(ProductosVo productosVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("borrar...");
        }

        int affected = jdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement(qryBorrar);
            ps.setInt(1, productosVo.getBorrado());
            ps.setInt(2, productosVo.getId());
            return ps;
        });
        return affected;
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

    public void setQryCatProd(String qryCatProd) {
        this.qryCatProd = qryCatProd;
    }

    public void setQryBorrar(String qryBorrar) {
        this.qryBorrar = qryBorrar;
    }

}
