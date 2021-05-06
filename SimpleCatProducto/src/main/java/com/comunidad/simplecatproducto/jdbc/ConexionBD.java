/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author Santiago
 */
public class ConexionBD {
    private static ConexionBD instance = null;
    private BasicDataSource dataSource;

    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String database = "ADMGASTOS";
    private static final String hostname = "localhost";
    private static final String port = "3306";
    private static final String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
    private static final String username = "USRADMGASTOS";
    private static final String password = "PASSUSER123";
    
    private ConexionBD() {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
    }

    public static ConexionBD getInstance() {
        if (instance == null) {
            instance = new ConexionBD();
        }
        return instance;
    }

    public Connection getConection() {
        Connection con = null;
        try {
            con = dataSource.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Error al obtener la conexion" + ex.getMessage());
        }
        return con;
    }

    public void closeConnection(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Error al cerrar la conexion");
        }
    }

    public void beginTransaction(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.setAutoCommit(false);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Error al iniciar la transaccion");
        }
    }

    public void endTransaction(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Error al iniciar la transaccion");
        }
    }

    public void commit(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.commit();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Error al realizar el commit de la transaccion");
        }
    }

    public void rollback(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.rollback();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Error al iniciar el rolbackk de la transaccion");
        }
    }

}
