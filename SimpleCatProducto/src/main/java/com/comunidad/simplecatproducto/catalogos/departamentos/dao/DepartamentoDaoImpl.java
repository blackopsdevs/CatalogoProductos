package com.comunidad.simplecatproducto.catalogos.departamentos.dao;

import com.comunidad.simplecatproducto.catalogos.departamentos.dao.vo.DepartamentosVo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.comunidad.simplecatproducto.common.DateUtils;
import com.comunidad.simplecatproducto.common.ItemComboboxModel;
import com.comunidad.simplecatproducto.jdbc.JdbcDao;
import com.comunidad.simplecatproducto.jdbc.JdbcQueryParameters;

public class DepartamentoDaoImpl extends JdbcDao implements DepartamentoDao {

    private static final Logger log = Logger.getLogger(DepartamentoDaoImpl.class);

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

    private static ParameterizedRowMapper<DepartamentosVo> MAPPER_DEPARTAMENTO = (ResultSet rs, int rowNum) -> {
        DepartamentosVo obj = new DepartamentosVo();
        obj.setId(rs.getInt("ID"));
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

    private static final ParameterizedRowMapper<ItemComboboxModel> MAPPER_CAT_DEPA = (ResultSet rs, int rowNum) -> {
        ItemComboboxModel obj = new ItemComboboxModel();
        obj.setId(rs.getInt("ID"));
        obj.setValue(rs.getString("NOMBRE"));

        return obj;
    };

    @Override
    public int guardar(DepartamentosVo departamentoVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("guardar...");
        }

        int affected = jdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement(qryCrear);
            ps.setInt(1, departamentoVo.getId());
            ps.setString(2, departamentoVo.getNombre());
            ps.setString(3, departamentoVo.getDescripcion());
            if (departamentoVo.getFoto() != null) {
                ps.setBinaryStream(4, departamentoVo.getFoto());
            }
            ps.setString(5, departamentoVo.getPath());
            ps.setInt(6, departamentoVo.getStatus());
            ps.setDate(7, DateUtils.convertJavaDateToSqlDate(departamentoVo.getFechaExpiracion()));
            ps.setDate(8, DateUtils.convertJavaDateToSqlDate(departamentoVo.getFechaUltActual()));
            ps.setDate(9, DateUtils.convertJavaDateToSqlDate(departamentoVo.getFechaAlta()));
            ps.setString(10, departamentoVo.getUserUltActual());
            ps.setInt(11, departamentoVo.getBorrado());
            return ps;
        });

        return affected;
    }

    @Override
    public int actualizar(DepartamentosVo departamentoVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("actualizar...");
        }

        int affected = jdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement(qryModificar);
            ps.setString(1, departamentoVo.getNombre());
            ps.setString(2, departamentoVo.getDescripcion());
            if (departamentoVo.getFoto() != null) {
                ps.setBinaryStream(3, departamentoVo.getFoto());
            }
            ps.setString(4, departamentoVo.getPath());
            ps.setInt(5, departamentoVo.getStatus());
            ps.setDate(6, DateUtils.convertJavaDateToSqlDate(departamentoVo.getFechaExpiracion()));
            ps.setDate(7, DateUtils.convertJavaDateToSqlDate(departamentoVo.getFechaUltActual()));
            ps.setString(8, departamentoVo.getUserUltActual());
            ps.setInt(9, departamentoVo.getId());
            return ps;
        });
        return affected;
    }

    @Override
    public int existe(DepartamentosVo departamentoVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("existe...");
        }
        StringBuffer stb = new StringBuffer(existe);
        ArrayList parameters = new ArrayList();
        if (departamentoVo.getId() != 0) {
            parameters.add(departamentoVo.getId());
        }
        Object[] args = parameters.toArray();
        return jdbcTemplate.queryForInt(stb.toString(), args);
    }

    @Override
    public DepartamentosVo buscar(DepartamentosVo departamentoVo) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public List<DepartamentosVo> lista(DepartamentosVo departamentoVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("listDepartamentoVo...");
        }

        StringBuffer stb = new StringBuffer(qryTodo.getSqlBase());
        ArrayList parameters = new ArrayList();
        Properties params = qryTodo.getOptionalParameters();

        if (departamentoVo != null) {
            if (departamentoVo.getId() != 0) {
                stb.append(" ").append(params.get("id"));
                parameters.add(departamentoVo.getId());
            }
            if (departamentoVo.getStatus() != 0) {
                stb.append(" ").append(params.get("estatus"));
                parameters.add(departamentoVo.getStatus());
            }
        }
        if (qryTodo.getSqlLast() != null && !qryTodo.getSqlLast().equals("")) {
            stb.append(" ").append(qryTodo.getSqlLast());
        }

        Object[] args = parameters.toArray();

        return (List<DepartamentosVo>) jdbcTemplate.query(stb.toString(), args, MAPPER_DEPARTAMENTO);
    }

    @Override
    public List<ItemComboboxModel> catalogo() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("catCategoriasVo...");
        }

        return (List<ItemComboboxModel>) jdbcTemplate.query(qryCatCatego, MAPPER_CAT_DEPA);
    }

    @Override
    public int borrar(DepartamentosVo departamentoVo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("borrar...");
        }

        int affected = jdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement(qryBorrar);
            ps.setInt(1, departamentoVo.getBorrado());
            ps.setInt(2, departamentoVo.getId());
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
