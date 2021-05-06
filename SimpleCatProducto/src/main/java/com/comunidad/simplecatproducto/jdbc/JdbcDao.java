package com.comunidad.simplecatproducto.jdbc;


import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JdbcDao {
	
	protected static final Logger log = Logger.getLogger(JdbcDao.class);
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected JdbcTemplate jdbcTemplate;

    public final void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    protected void checkAffected(int affected) {
        if (affected > 1) {
            throw new RuntimeException("La operacion afecto mas de 1 registro.");
        }
        if (affected < 1) {
            throw new RuntimeException("La operacion no afecto ningun registro.");
        }
    }
}
