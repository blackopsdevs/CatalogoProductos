/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Santiago
 */
public class CerrarSentencias {
    public void cerrarResultSet(ResultSet rs) {
        try {
            if (rs != null && rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException ex) {
        }
    }

    public void cerrarStatement(Statement st) {
        try {
            if (st != null && st.isClosed()) {
                st.close();
            }
        } catch (SQLException ex) {
        }
    }
}
