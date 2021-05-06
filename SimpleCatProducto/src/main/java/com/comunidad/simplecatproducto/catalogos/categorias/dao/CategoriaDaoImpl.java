/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.categorias.dao;

import com.comunidad.simplecatproducto.catalogos.categorias.dao.vo.CategoriasVo;
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
public class CategoriaDaoImpl extends JdbcDao implements CategoriaDao {

    private static final Logger log = Logger.getLogger(CategoriaDaoImpl.class);

    private String qryCrear;
    private String qryModificar;
    private String qryConsultar;
    private String qryConsultarTodo;
    private String qryBorrar;
    private String nextId;
    private String existe;
    private JdbcQueryParameters qryTodo;
    private JdbcQueryParameters qryCountTodo;
    private String qryCatCatego;

    private static ParameterizedRowMapper<CategoriasVo> MAPPER_CATEGORIA = (ResultSet rs, int rowNum) -> {
        CategoriasVo obj = new CategoriasVo();
        obj.setId(rs.getInt("ID"));
        obj.setIdDepart(rs.getInt("ID_DEPA"));
        obj.setNombre(rs.getString("NOMBRE"));
        obj.setDescripcion(rs.getString("DESCR"));
        /*InputStream is = rs.getBinaryStream("FOTO");
        String nameI = homeSistema + "/temp/"+obj.getId()+".png";
        int r = 0;
        try {
        output = new FileOutputStream(new File(nameI));
        while ((r = is.read()) != -1) {
        output.write(r);
        }
        } catch (FileNotFoundException ex) {
        java.util.logging.Logger.getLogger(DepartamentoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
        java.util.logging.Logger.getLogger(DepartamentoDaoImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
        try {
        is.close();
        output.flush();
        output.close();
        } catch (IOException e) {
        java.util.logging.Logger.getLogger(DepartamentoDaoImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        }
        obj.setPath(nameI);*/
        //obj.setFoto((FileInputStream) rs.getBinaryStream("FOTO"));
        obj.setPath(rs.getString("PATH"));
        obj.setStatus(rs.getInt("STATUS_FLAG"));
        obj.setFechaExpiracion(rs.getDate("EXPIRY_DT"));
        obj.setFechaUltActual(rs.getDate("LAST_UPDATE_DT"));
        obj.setFechaAlta(rs.getDate("START_DATE"));
        obj.setUserUltActual(rs.getString("LAST_UPDATE_USER"));
        obj.setBorrado(rs.getInt("DEL_LOGIC"));

        return obj;
    };

    private static final ParameterizedRowMapper<ItemComboboxModel> MAPPER_CAT_CATEGO = (ResultSet rs, int rowNum) -> {
        ItemComboboxModel obj = new ItemComboboxModel();
        obj.setId(rs.getInt("ID"));
        obj.setValue(rs.getString("NOMBRE"));

        return obj;
    };

    @Override
    public int guardar(CategoriasVo categoriasVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("guardar...");
        }
        int affected = jdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement(qryCrear);
            ps.setInt(1, categoriasVo.getId());
            ps.setInt(2, categoriasVo.getIdDepart());
            ps.setString(3, categoriasVo.getNombre());
            ps.setString(4, categoriasVo.getDescripcion());
            if (categoriasVo.getFoto() != null) {
                ps.setBinaryStream(5, categoriasVo.getFoto());
            }
            ps.setString(6, categoriasVo.getPath());
            ps.setInt(7, categoriasVo.getStatus());
            ps.setDate(8, DateUtils.convertJavaDateToSqlDate(categoriasVo.getFechaExpiracion()));
            ps.setDate(9, DateUtils.convertJavaDateToSqlDate(categoriasVo.getFechaUltActual()));
            ps.setDate(10, DateUtils.convertJavaDateToSqlDate(categoriasVo.getFechaAlta()));
            ps.setString(11, categoriasVo.getUserUltActual());
            ps.setInt(12, categoriasVo.getBorrado());
            return ps;
        });

        return affected;
    }

    @Override
    public int actualizar(CategoriasVo categoriasVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("actualizar...");
        }

        int affected = jdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement(qryModificar);
            ps.setInt(1, categoriasVo.getIdDepart());
            ps.setString(2, categoriasVo.getNombre());
            ps.setString(3, categoriasVo.getDescripcion());
            if (categoriasVo.getFoto() != null) {
                ps.setBinaryStream(4, categoriasVo.getFoto());
            }
            ps.setString(5, categoriasVo.getPath());
            ps.setInt(6, categoriasVo.getStatus());
            ps.setDate(7, DateUtils.convertJavaDateToSqlDate(categoriasVo.getFechaExpiracion()));
            ps.setDate(8, DateUtils.convertJavaDateToSqlDate(categoriasVo.getFechaUltActual()));
            ps.setString(9, categoriasVo.getUserUltActual());
            ps.setInt(10, categoriasVo.getId());
            return ps;
        });
        return affected;
    }

    @Override
    public int existe(CategoriasVo categoriasVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("existe...");
        }
        StringBuffer stb = new StringBuffer(existe);
        ArrayList parameters = new ArrayList();
        if (categoriasVo.getId() != 0) {
            parameters.add(categoriasVo.getId());
        }
        Object[] args = parameters.toArray();
        return jdbcTemplate.queryForInt(stb.toString(), args);
    }

    @Override
    public CategoriasVo buscar(CategoriasVo categoriasVo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CategoriasVo> lista(CategoriasVo categoriasVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("listDepartamentoVo...");
        }

        StringBuffer stb = new StringBuffer(qryTodo.getSqlBase());
        ArrayList parameters = new ArrayList();
        Properties params = qryTodo.getOptionalParameters();

        if (categoriasVo != null) {
            if (categoriasVo.getId() != 0) {
                stb.append(" ").append(params.get("id"));
                parameters.add(categoriasVo.getId());
            }
            if (categoriasVo.getIdDepart() != 0) {
                stb.append(" ").append(params.get("idDepa"));
                parameters.add(categoriasVo.getIdDepart());
            }
            if (categoriasVo.getStatus() != 0) {
                stb.append(" ").append(params.get("estatus"));
                parameters.add(categoriasVo.getStatus());
            }
        }
        if (qryTodo.getSqlLast() != null && !qryTodo.getSqlLast().equals("")) {
            stb.append(" ").append(qryTodo.getSqlLast());
        }

        Object[] args = parameters.toArray();

        return (List<CategoriasVo>) jdbcTemplate.query(stb.toString(), args, MAPPER_CATEGORIA);
    }

    @Override
    public List<ItemComboboxModel> catalogo(int id, int estatus) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("catCategoriasVo...");
        }
        StringBuffer stb = new StringBuffer(qryCatCatego);
        ArrayList parameters = new ArrayList();
        if (id != 0) {
            parameters.add(id);
        }
        if (estatus != 0) {
            parameters.add(estatus);
        }
        Object[] args = parameters.toArray();
        return (List<ItemComboboxModel>) jdbcTemplate.query(stb.toString(), args, MAPPER_CAT_CATEGO);
    }

    @Override
    public int borrar(CategoriasVo categoriasVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("borrar...");
        }
        int affected = jdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement(qryBorrar);
            ps.setInt(1, categoriasVo.getBorrado());
            ps.setInt(2, categoriasVo.getId());
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

    public void setQryCatCatego(String qryCatCatego) {
        this.qryCatCatego = qryCatCatego;
    }

    public void setQryBorrar(String qryBorrar) {
        this.qryBorrar = qryBorrar;
    }

}
