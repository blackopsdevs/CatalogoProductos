package com.comunidad.simplecatproducto.departamento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.comunidad.simplecatproducto.common.DateUtils;
import com.comunidad.simplecatproducto.departamento.dao.vo.DepartamentoVoTest;
import com.comunidad.simplecatproducto.jdbc.JdbcDao;
import com.comunidad.simplecatproducto.jdbc.JdbcQueryParameters;

public class DepartamentoDaoImpl  extends JdbcDao implements DepartamentoDao{

	private static final Logger log = Logger.getLogger(DepartamentoDaoImpl.class);

	private String qryCrear;
	private String qryModificar;
	private String qryConsultar;
	private String qryConsultarTodo;
	private String nextId;
	private String existe;
	private JdbcQueryParameters qryTodo;
	private JdbcQueryParameters qryCountTodo;
	
	private static ParameterizedRowMapper<DepartamentoVoTest> MAPPER_DEPARTAMENTO = new ParameterizedRowMapper<DepartamentoVoTest>() {
		public DepartamentoVoTest mapRow(ResultSet rs, int rowNum) throws SQLException {
			DepartamentoVoTest obj = new DepartamentoVoTest();
			obj.setId(rs.getInt("ID"));
			obj.setNombre(rs.getString("NOMBRE"));
			obj.setDescripcion(rs.getString("DESCRO"));
			obj.setStatus(rs.getInt("STATUS_FLAG"));
			obj.setFechaExpiracion(rs.getDate("EXPIRY_DT"));
			obj.setFechaUltActual(rs.getDate("LAST_UPDATE_DT"));
			obj.setUserUltActual(rs.getString("LAST_UPDATE_USER"));

			return obj;
		}

	};
	
	@Override
	public int guardar(DepartamentoVoTest departamentoVo) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("guardar...");
		}
		final int nextIdProd = jdbcTemplate.queryForInt(nextId);
		int affected = jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(qryCrear);
				ps.setInt(1, nextIdProd);
				ps.setString(2, departamentoVo.getNombre());
				ps.setString(3, departamentoVo.getDescripcion());
				ps.setInt(4, departamentoVo.getStatus());
				ps.setDate(5, DateUtils.convertJavaDateToSqlDate(departamentoVo.getFechaExpiracion()));
				ps.setDate(6, DateUtils.convertJavaDateToSqlDate(departamentoVo.getFechaUltActual()));
				ps.setString(7, departamentoVo.getUserUltActual());
				return ps;
			}

		});

		return affected;
	}

	@Override
	public int actualizar(DepartamentoVoTest departamentoVo) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("actualizar...");
		}

		int affected = jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(qryModificar);
				ps.setString(1, departamentoVo.getNombre());
				ps.setString(2, departamentoVo.getDescripcion());
				ps.setInt(3, departamentoVo.getStatus());
				ps.setDate(4, DateUtils.convertJavaDateToSqlDate(departamentoVo.getFechaExpiracion()));
				ps.setDate(5, DateUtils.convertJavaDateToSqlDate(departamentoVo.getFechaUltActual()));
				ps.setString(6, departamentoVo.getUserUltActual());
				ps.setInt(7, departamentoVo.getId());
				return ps;
			}

		});
		return affected;
	}

	@Override
	public int existe(DepartamentoVoTest departamentoVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DepartamentoVoTest buscar(DepartamentoVoTest departamentoVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<DepartamentoVoTest> listDepartamentoVo(DepartamentoVoTest departamentoVo) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("listDepartamentoVo...");
		}

		StringBuffer stb = new StringBuffer(qryTodo.getSqlBase());
		ArrayList parameters = new ArrayList();
		Properties params = qryTodo.getOptionalParameters();
		
		if (departamentoVo.getId() != 0) {
			stb.append(" ").append(params.get("id"));
			parameters.add(departamentoVo.getId());
		}
		if (departamentoVo.getStatus() != 0) {
			stb.append(" ").append(params.get("estatus"));
			parameters.add(departamentoVo.getStatus());
		}
		
		if (qryTodo.getSqlLast() != null && !qryTodo.getSqlLast().equals("")) {
			stb.append(" ").append(qryTodo.getSqlLast());
		}
		
		Object[] args = parameters.toArray();
		
		return (List<DepartamentoVoTest>) jdbcTemplate.query(stb.toString(), args, MAPPER_DEPARTAMENTO);
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

}
